package guru.mrtu;

public class UnexisistingAccountException extends RuntimeException {

    public UnexisistingAccountException(String iban) {
        super("Account not found: " + iban);
    }

}
