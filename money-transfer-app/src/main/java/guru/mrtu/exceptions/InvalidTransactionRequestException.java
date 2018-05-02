package guru.mrtu.exceptions;

import guru.mrtu.model.TransactionRequest;

public final class InvalidTransactionRequestException extends RuntimeException {

    public InvalidTransactionRequestException(TransactionRequest transactionRequest) {
        super("Invalid transaction, " + transactionRequest.toString());
    }

    public InvalidTransactionRequestException() {
        super("Invalid transaction, IOError");
    }

}
