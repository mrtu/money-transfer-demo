package guru.mrtu.model;

import com.google.common.base.Objects;
import guru.mrtu.exceptions.IllegalTransactionException;

import java.util.UUID;

import static java.util.Objects.requireNonNull;

public final class Transaction implements TransactionModelView {

    private final UUID id;

    private final Account from;

    private final Account to;

    private final float value;

    private State transactionState;

    public Transaction(Account from, Account to, float value) {
        this.value = value;
        this.id = UUID.randomUUID();
        this.from = requireNonNull(from);
        this.to = requireNonNull(to);
        this.transactionState = State.CREATED;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String getFromIban() {
        return from.getIban();
    }

    @Override
    public String getToIban() {
        return to.getIban();
    }

    @Override
    public float getValue() {
        return value;
    }

    @Override
    public State getTransactionState() {
        return transactionState;
    }

    public void changeState(State from, State to) {
        synchronized (this) {
            if (!from.equals(transactionState) || from.level > to.level) {
                this.transactionState = State.CANCELLED;
                throw new IllegalTransactionException(id);
            } else {
                this.transactionState = to;
            }
        }
    }

    public void cancel() {
        this.transactionState = State.CANCELLED;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Float.compare(that.value, value) == 0 &&
                Objects.equal(id, that.id) &&
                Objects.equal(from, that.from) &&
                Objects.equal(to, that.to);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, from, to, value);
    }

    public enum State {

        CREATED(0),
        WITHDRAWN(1),
        COMPLETED(2),
        CANCELLED(2);

        private final int level;

        State(int stateLevel) {
            this.level = stateLevel;
        }

    }
}


