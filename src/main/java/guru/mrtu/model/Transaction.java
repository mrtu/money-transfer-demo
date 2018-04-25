package guru.mrtu.model;

import java.util.UUID;

public class Transaction {

    private final UUID id;

    private final Account from;

    private final Account to;

    private final float value;

    private State movementState;

    public Transaction(Account from, Account to, float value) {
        this.value = value;
        this.id = UUID.randomUUID();
        this.from = from;
        this.to = to;
        this.movementState = State.CREATED;
    }

    enum State {

        CREATED,
        WITHDRAWN,
        COMPLETED,
        CANCELLED

    }
}


