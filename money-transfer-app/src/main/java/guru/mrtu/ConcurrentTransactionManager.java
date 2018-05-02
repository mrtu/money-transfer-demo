package guru.mrtu;

import guru.mrtu.exceptions.NonexistentAccountException;
import guru.mrtu.model.Account;
import guru.mrtu.model.Transaction;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static guru.mrtu.model.Transaction.State.*;
import static java.util.Objects.isNull;

public class ConcurrentTransactionManager implements TransactionManager {

    private static ConcurrentTransactionManager instance;
    private final Map<Account, AccountTransactionHandler> transactionHandlers;
    private final AccountsRepository accounts;

    private ConcurrentTransactionManager() {
        this.accounts = AccountsRepositoryImpl.instance();
        this.transactionHandlers = new ConcurrentHashMap<>();
    }

    public static ConcurrentTransactionManager instance() {
        if (instance == null) {
            instance = new ConcurrentTransactionManager();
        }
        return instance;
    }

    @Override
    public void transfer(String fromIban, String toIban, float value) {
        Account accountFrom = getAccount(fromIban);
        Account accountTo = getAccount(toIban);
        //Create a transaction in CREATED state
        Transaction transaction = new Transaction(accountFrom, accountTo, value);
        try {
            //If there is no handler, just create one and handle the withdraw
            transactionHandlers.computeIfAbsent(accountFrom, AccountTransactionHandler::new)
                    .handleWithdrawTransaction(transaction);
            //Change the state of transaction to WITHDRAWN
            transaction.changeState(CREATED, WITHDRAWN);

            //If there is no handler, just create one and handle the deposit
            transactionHandlers.computeIfAbsent(accountTo, AccountTransactionHandler::new)
                    .handleDepositTramsaction(transaction);
            //Mark the transaction as completed
            transaction.changeState(WITHDRAWN, COMPLETED);
        } catch (Exception e) {
            //Something went wrong, cancel the transaction
            transaction.cancel();
            throw e;
        }
    }

    private Account getAccount(String iban) {
        Account account = accounts.getAccount(iban);
        if (isNull(account)) {
            throw new NonexistentAccountException(iban);
        }
        return account;
    }

}
