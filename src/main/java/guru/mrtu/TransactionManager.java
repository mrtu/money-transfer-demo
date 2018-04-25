package guru.mrtu;

import guru.mrtu.model.Account;
import guru.mrtu.model.Transaction;

import static java.util.Objects.isNull;

public class TransactionManager {

    private final AccountsRepository accounts;

    public TransactionManager() {
        this.accounts = AccountsRepository.get();
    }

    public boolean transfer(String fromIban, String toIban, float value) {
        Account accountFrom = getAccount(fromIban);
        Account accountTo = getAccount(toIban);
        Transaction transaction = new Transaction(accountFrom, accountTo, value);

    }

    private Account getAccount(String iban) {
        Account account = accounts.getAccount(iban);
        if (isNull(account)) {
            throw new UnexisistingAccountException(iban):
        }
        return account;
    }

}
