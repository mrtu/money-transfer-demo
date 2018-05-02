package guru.mrtu;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.io.IOException;

import static java.lang.System.getProperty;
import static org.junit.Assert.*;

@RunWith(BlockJUnit4ClassRunner.class)
public class AllowedOperationsIT {

    private static AccountHandler accountHandler;

    private static TransactionHandler transactionHandler;

    @BeforeClass
    public static void initialize() {

        String host = getProperty("host");
        int port = Integer.parseInt(getProperty("port"));

        accountHandler = new AccountHandler(host, port);
        transactionHandler = new TransactionHandler(host, port);
    }

    @Test
    public void youCannotTransferIfYouAreBroke() throws IOException {
        String account1 = accountHandler.createAccount(100);
        String account2 = accountHandler.createAccount(0);

        //Ok should work
        assertTrue("Transaction should succeed",
                transactionHandler.applyTransaction(60, account1, account2));

        assertFalse("The transaction must fail",
                transactionHandler.applyTransaction(60, account1, account2));
    }

    @Test(expected = TestFailedException.class)
    public void youCannotTransferMoneyToYourSelf() throws IOException {
        String account = accountHandler.createAccount(100);

        try {
            transactionHandler.applyTransaction(60, account, account);
        } catch (TestFailedException e) {
            if (e.getStatus() != 400) {
                fail();
            }
            throw e;
        }

    }

    @Test(expected = TestFailedException.class)
    public void youCannotCreateAnAccountWithNegativeBalance() throws IOException {
        try {
            accountHandler.createAccount(-100);
        } catch (TestFailedException e) {
            if (e.getStatus() != 400) {
                fail();
            }
            throw e;
        }
    }

    @Test(expected = TestFailedException.class)
    public void youCannotTransferNegativeAmounts() throws IOException {
        String account1 = accountHandler.createAccount(100);
        String account2 = accountHandler.createAccount(100);

        try {
            transactionHandler.applyTransaction(-60, account1, account2);
        } catch (TestFailedException e) {
            if (e.getStatus() != 400) {
                fail();
            }
            throw e;
        }

    }

}
