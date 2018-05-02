package guru.mrtu.exceptions;

public final class InsufficientFundsException extends RuntimeException {

    public InsufficientFundsException(String iban, float value) {
        super("There are insufficient funds to perform the requested transfer account:" + iban + ", value:" + value);
    }

}
