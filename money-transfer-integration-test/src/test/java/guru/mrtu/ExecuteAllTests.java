package guru.mrtu;

import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;

import static com.google.common.primitives.Longs.tryParse;
import static java.lang.Math.toIntExact;
import static java.lang.String.valueOf;

public class ExecuteAllTests {

    private static final int DEFAULT_PORT = 4567;

    private static final String DEFAULT_HOST = "localhost";

    public static void main(String[] args) {

        int port = DEFAULT_PORT;
        String host = DEFAULT_HOST;
        if (args != null && args.length == 2) {
            Long couldBeThePort = tryParse(args[1]);
            if (couldBeThePort != null && couldBeThePort < Integer.MAX_VALUE) {
                port = toIntExact(couldBeThePort);
            } else {
                System.err.println("Invalid port " + args[1]);
                System.exit(-1);
            }
            host = args[0];
        }

        System.setProperty("host", host);
        System.setProperty("port", valueOf(port));

        JUnitCore engine = new JUnitCore();
        engine.addListener(new TextListener(System.out));
        engine.run(AllTests.class);
    }

}
