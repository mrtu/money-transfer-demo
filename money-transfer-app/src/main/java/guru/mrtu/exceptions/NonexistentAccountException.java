package guru.mrtu.exceptions;

public final class NonexistentAccountException extends RuntimeException {

    public NonexistentAccountException(String iban) {
        super("Account not found: " + iban);
    }

}
