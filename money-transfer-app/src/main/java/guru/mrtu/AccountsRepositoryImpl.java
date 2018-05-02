package guru.mrtu;

import guru.mrtu.model.Account;

import java.util.HashMap;
import java.util.Map;

public class AccountsRepositoryImpl implements AccountsRepository {

    private static final AccountsRepository repository = new AccountsRepositoryImpl();

    private final Map<String, Account> accounts;

    public AccountsRepositoryImpl() {
        this.accounts = new HashMap<>();
    }

    public static AccountsRepository instance() {
        return repository;
    }

    @Override
    public void createAccount(String iban, float balance) {
        accounts.put(iban, new Account(iban, balance));

    }

    @Override
    public Account getAccount(String iban) {
        return accounts.get(iban);
    }

}
