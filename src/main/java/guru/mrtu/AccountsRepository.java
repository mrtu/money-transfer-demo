package guru.mrtu;

import guru.mrtu.model.Account;
import guru.mrtu.model.AccountCreationRequest;

import java.util.HashMap;
import java.util.Map;

public class AccountsRepository {

    private static final AccountsRepository repository = new AccountsRepository();

    private final Map<String, Account> accounts;

    public AccountsRepository() {
        this.accounts = new HashMap<>();
    }

    public static AccountsRepository instance() {
        return repository;
    }

    public void createAccount(AccountCreationRequest request) {
        accounts.put(request.getIban(), new Account(request.getIban(), request.getInitialBalance()));
    }

    public Account getAccount(String iban) {
        return accounts.get(iban);
    }

}
