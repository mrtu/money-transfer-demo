package guru.mrtu.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

public class TransactionInfo {

    private final String id;

    private final float value;

    private final State transactionState;

    private final String from;

    private final String to;

    @JsonCreator
    public TransactionInfo(@JsonProperty("id") String id,
                           @JsonProperty("value") float value,
                           @JsonProperty("transactionState") State transactionState,
                           @JsonProperty("from") String from,
                           @JsonProperty("to") String to) {
        this.id = id;
        this.value = value;
        this.transactionState = transactionState;
        this.from = from;
        this.to = to;
    }

    public String getId() {
        return id;
    }

    public float getValue() {
        return value;
    }

    public State getTransactionState() {
        return transactionState;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionInfo that = (TransactionInfo) o;
        return Float.compare(that.value, value) == 0 &&
                Objects.equal(id, that.id) &&
                transactionState == that.transactionState &&
                Objects.equal(from, that.from) &&
                Objects.equal(to, that.to);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, value, transactionState, from, to);
    }

    public enum State {

        CREATED,
        WITHDRAWN,
        COMPLETED,
        CANCELLED;

    }

}
