package guru.mrtu;

import guru.mrtu.exceptions.NonexistentAccountException;
import guru.mrtu.model.Account;
import guru.mrtu.model.Transaction;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static guru.mrtu.model.Transaction.State.*;
import static java.util.Objects.isNull;

public class TransactionManager {

    private final Map<UUID, Transaction> globalTransactionMap;

    private final Map<Account, AccountTransactionHandler> transactionHandlers;

    private static TransactionManager instance;

    private final AccountsRepository accounts;

    private TransactionManager() {
        this.accounts = AccountsRepository.instance();
        this.globalTransactionMap = new ConcurrentHashMap<>();
        this.transactionHandlers = new ConcurrentHashMap<>();
    }

    public void transfer(String fromIban, String toIban, float value) {
        Account accountFrom = getAccount(fromIban);
        Account accountTo = getAccount(toIban);
        //Create a transaction in CREATED state
        Transaction transaction = new Transaction(accountFrom, accountTo, value);
        try {
            //Add it too global transaction log
            globalTransactionMap.put(transaction.getId(), transaction);
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

    public static TransactionManager instance() {
        if (instance == null) {
            instance = new TransactionManager();
        }
        return instance;
    }

    private Account getAccount(String iban) {
        Account account = accounts.getAccount(iban);
        if (isNull(account)) {
            throw new NonexistentAccountException(iban);
        }
        return account;
    }

}
