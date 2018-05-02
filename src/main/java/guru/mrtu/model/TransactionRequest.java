package guru.mrtu.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import static com.google.common.base.Objects.equal;

public final class TransactionRequest {

    private final String ibanOrigin;

    private final String ibanDestiny;

    private final Float amount;

    @JsonCreator
    public TransactionRequest(@JsonProperty(value = "origin", required = true) String ibanOrigin,
                              @JsonProperty(value = "destiny", required = true) String ibanDestiny,
                              @JsonProperty(value = "amount", required = true) Float amount) {
        this.ibanOrigin = ibanOrigin;
        this.ibanDestiny = ibanDestiny;
        this.amount = amount;
    }

    public String getIbanOrigin() {
        return ibanOrigin;
    }

    public String getIbanDestiny() {
        return ibanDestiny;
    }

    public Float getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionRequest that = (TransactionRequest) o;
        return equal(ibanOrigin, that.ibanOrigin) &&
                equal(ibanDestiny, that.ibanDestiny) &&
                equal(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return com.google.common.base.Objects.hashCode(ibanOrigin, ibanDestiny, amount);
    }
}
