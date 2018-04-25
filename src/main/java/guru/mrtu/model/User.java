package guru.mrtu.model;

import java.util.List;

public class User {

    private final String name;

    private final List<Account> accounts;

    public User(String name, List<Account> accounts) {
        this.name = name;
        this.accounts = accounts;
    }
}
