package guru.mrtu.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public interface TransactionModelView {

    @JsonProperty("id")
    UUID getId();

    @JsonProperty("from")
    String getFromIban();

    @JsonProperty("to")
    String getToIban();

    @JsonProperty("value")
    float getValue();

    @JsonProperty("transactionState")
    Transaction.State getTransactionState();
}
