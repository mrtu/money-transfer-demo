package guru.mrtu.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

import java.util.List;

public class AccountInfo {

    private final String iban;

    private final float currentBalance;

    private final List<TransactionInfo> transactionLog;


    public AccountInfo(@JsonProperty("iban") String iban,
                       @JsonProperty("currentBalance") float currentBalance,
                       @JsonProperty("transactionLog") List<TransactionInfo> transactionLog) {
        this.iban = iban;
        this.currentBalance = currentBalance;
        this.transactionLog = transactionLog;
    }

    public String getIban() {
        return iban;
    }

    public float getCurrentBalance() {
        return currentBalance;
    }

    public List<TransactionInfo> getTransactionLog() {
        return transactionLog;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountInfo that = (AccountInfo) o;
        return Float.compare(that.currentBalance, currentBalance) == 0 &&
                Objects.equal(iban, that.iban) &&
                Objects.equal(transactionLog, that.transactionLog);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(iban, currentBalance, transactionLog);
    }
}
