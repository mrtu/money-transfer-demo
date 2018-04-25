package guru.mrtu.model;

import java.util.LinkedList;
import java.util.List;

public class Account {

    private final String iban;

    private final List<Transaction> transactionLog;

    private final Balance balance;

    public Account(String iban,
                   Balance balance) {
        this.iban = iban;
        this.transactionLog = new LinkedList<>();
        this.balance = balance;
    }

    public void addTransaction(Transaction transaction) {
        this.transactionLog.add(transaction);
    }

    public class Balance {

        private float value;

        Balance() {
            this.value = 0;
        }

        private boolean withdraw(float amount, Account destiny) {
            synchronized (this) {
                if (this.value < amount) {
                    return false;
                } else {
                    this.value -= amount;
                    return true;
                }
            }
        }

    }


}
