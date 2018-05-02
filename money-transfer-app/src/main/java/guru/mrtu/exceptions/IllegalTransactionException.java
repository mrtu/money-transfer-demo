package guru.mrtu.exceptions;

import java.util.UUID;

public final class IllegalTransactionException extends RuntimeException {

    public IllegalTransactionException(UUID id) {
        super("Illegal transaction state for " + id);
    }

}
