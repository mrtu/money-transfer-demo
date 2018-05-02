package guru.mrtu.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;

@JsonSerialize(as = AccountModelView.class)
public interface AccountModelView {

    @JsonProperty("iban")
    String getIban();

    @JsonProperty("currentBalance")
    float getCurrentBalance();

    @JsonProperty("transactionLog")
    List<Transaction> getTransactionLog();

}
