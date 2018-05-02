package guru.mrtu.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

public class AccountCreationRequest {

    private final float balance;

    public AccountCreationRequest(float balance) {
        this.balance = balance;
    }

    @JsonProperty
    public float getBalance() {
        return balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountCreationRequest that = (AccountCreationRequest) o;
        return Float.compare(that.balance, balance) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(balance);
    }
}
