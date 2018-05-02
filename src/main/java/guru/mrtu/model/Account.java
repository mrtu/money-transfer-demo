package guru.mrtu.model;

import com.google.common.base.Objects;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicReference;

public final class Account implements AccountModelView {

    private final String iban;

    private final Deque<Transaction> transactionLog;

    private final AtomicReference<Float> balance;

    public Account(String iban,
                   float initialBalance) {
        this.iban = iban;
        this.transactionLog = new ConcurrentLinkedDeque<>();
        this.balance = new AtomicReference<>(initialBalance);
    }

    @Override
    public String getIban() {
        return iban;
    }

    @Override
    public List<Transaction> getTransactionLog() {
        //protect the access to the internal structure
        return new LinkedList<>(transactionLog);
    }

    @Override
    public float getCurrentBalance() {
        return balance.get();
    }

    public void addToTransactionLog(Transaction transaction) {
        transactionLog.push(transaction);
    }

    public float depositAmount(float value) {
        return this.balance.updateAndGet(current -> current + value);
    }

    public float withdrawAmount(float value) {
        return this.balance.updateAndGet(current -> current - value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equal(iban, account.iban);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(iban);
    }
}
