package guru.mrtu;

import guru.mrtu.exceptions.InsufficientFundsException;
import guru.mrtu.model.Account;
import guru.mrtu.model.Transaction;

import static java.util.Objects.requireNonNull;

class AccountTransactionHandler {

    private final Account account;

    AccountTransactionHandler(Account account) {
        this.account = requireNonNull(account);
    }

    void handleWithdrawTransaction(Transaction transaction) {
        synchronized (this) {
            //Add the CREATED transaction to account transaction log
            account.addToTransactionLog(transaction);
            //Apply the transaction as origin account
            checkAndWithdrawCachedBalance(account, transaction.getValue());
        }
    }

    void handleDepositTramsaction(Transaction transaction) {
        //Add the CREATED transaction to account transaction log
        account.addToTransactionLog(transaction);
        //Apply the transaction as destiny account
        account.depositAmount(transaction.getValue());
    }

    private void checkAndWithdrawCachedBalance(Account account,
                                               float value) {
        if (value > account.getCurrentBalance()) {
            //check whether the balance suffices the need
            throw new InsufficientFundsException(account.getIban(), value);
        } else {
            //the balance is build on top of the last transaction, proceed to updating the balance
            account.withdrawAmount(value);
        }
    }
}
