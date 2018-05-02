package guru.mrtu;

public interface TransactionManager {

    /**
     * Performs transaction of a value between two accounts
     *
     * @param fromIban origin account
     * @param toIban   destiny account
     * @param value    the value to transfer
     */
    void transfer(String fromIban, String toIban, float value);
}
