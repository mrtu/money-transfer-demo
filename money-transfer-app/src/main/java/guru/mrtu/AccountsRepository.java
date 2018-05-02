package guru.mrtu;

import guru.mrtu.model.Account;

public interface AccountsRepository {

    /**
     * Stores an account
     *
     * @param iban    the account id
     * @param balance the account balance
     */
    void createAccount(String iban, float balance);

    /**
     * Obtain an account by providing an account id
     *
     * @param iban the account id
     * @return the account info
     */
    Account getAccount(String iban);
}
