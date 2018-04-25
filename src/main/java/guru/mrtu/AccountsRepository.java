package guru.mrtu;

import guru.mrtu.model.Account;

import java.util.HashMap;
import java.util.Map;

public class AccountsRepository {

    private static final AccountsRepository repository = new AccountsRepository();

    private final Map<String, Account> accounts;

    public AccountsRepository() {
        this.accounts = new HashMap<>();
    }

    public static AccountsRepository get() {
        return repository;
    }

    public boolean exists(String iban) {
        return accounts.containsKey(iban);
    }

    public Account getAccount(String iban) {
        return accounts.get(iban);
    }

}
