package guru.mrtu.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

public class TransactionCreationRequest {

    private final String ibanOrigin;

    private final String ibanDestiny;

    private final float amout;

    public TransactionCreationRequest(String ibanOrigin,
                                      String ibanDestiny,
                                      float amount) {
        this.ibanOrigin = ibanOrigin;
        this.ibanDestiny = ibanDestiny;
        this.amout = amount;
    }

    @JsonProperty("origin")
    public String getIbanOrigin() {
        return ibanOrigin;
    }

    @JsonProperty("destiny")
    public String getIbanDestiny() {
        return ibanDestiny;
    }

    @JsonProperty("amount")
    public float getAmout() {
        return amout;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionCreationRequest that = (TransactionCreationRequest) o;
        return Float.compare(that.amout, amout) == 0 &&
                Objects.equal(ibanOrigin, that.ibanOrigin) &&
                Objects.equal(ibanDestiny, that.ibanDestiny);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ibanOrigin, ibanDestiny, amout);
    }
}
