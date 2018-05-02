package guru.mrtu.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

import java.util.UUID;

public class AccountCreationRequest {

    private final Float initialBalance;

    private final String iban;

    @JsonCreator
    public AccountCreationRequest(@JsonProperty("balance") Float initialBalance) {
        this.initialBalance = initialBalance;
        this.iban = UUID.randomUUID().toString();
    }

    public Float getInitialBalance() {
        return initialBalance;
    }

    public String getIban() {
        return iban;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountCreationRequest that = (AccountCreationRequest) o;
        return Objects.equal(initialBalance, that.initialBalance) &&
                Objects.equal(iban, that.iban);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(initialBalance, iban);
    }
}
