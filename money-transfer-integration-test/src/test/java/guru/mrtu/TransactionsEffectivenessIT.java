package guru.mrtu;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;
import java.util.stream.IntStream;

import static java.lang.System.getProperty;
import static org.junit.Assert.assertEquals;

@RunWith(BlockJUnit4ClassRunner.class)
public class TransactionsEffectivenessIT {

    private final static Logger LOGGER = Logger.getLogger(TransactionsEffectivenessIT.class.getName());

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
    public void theTotalBalanceOfAllAccountsShouldRemainParallel() throws IOException, URISyntaxException {
        int numberAccounts = 100;
        int numTransactions = 10000;
        int initialBalance = 100;

        Random random = new Random();

        ArrayList<String> ibans = new ArrayList<>(numberAccounts);
        for (int i = 0; i < numberAccounts; i++) {
            ibans.add(accountHandler.createAccount(initialBalance));
        }


        IntStream.range(0, numTransactions)
                .parallel()
                .forEach(i -> {
                    String from = generateRandom(random, ibans);
                    String to = generateRandom(random, ibans, from);
                    try {
                        int amount = random.nextInt(60);
                        boolean wasApplied = transactionHandler.applyTransaction(amount, from, to);
                        LOGGER.info("Transferring\t" + amount + "\tfrom\t" + from + "\tto\t" + to + "\t" + wasApplied);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

        float totalBalance = 0;
        for (String ibam : ibans) {
            totalBalance += accountHandler.getAccount(ibam).getCurrentBalance();
        }

        assertEquals("Balance should match", numberAccounts * initialBalance, totalBalance, 0);

    }

    private static String generateRandom(Random random, ArrayList<String> ibans) {
        return ibans.get(random.nextInt(ibans.size()));
    }

    private static String generateRandom(Random random, ArrayList<String> ibans, String exclude) {
        String result;
        while ((result = ibans.get(random.nextInt(ibans.size()))).equalsIgnoreCase(exclude)) ;
        return result;
    }


}
