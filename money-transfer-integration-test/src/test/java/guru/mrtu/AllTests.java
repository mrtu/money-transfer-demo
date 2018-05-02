package guru.mrtu;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({AllowedOperationsIT.class, TransactionsEffectivenessIT.class})
public class AllTests {

}