package Testing;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        cardTest.class,
        cardDeckTest.class,
        playerTest.class,
        cardGameTest.class
})

public class TestSuite {

}