import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

public class TestOptimizeQuery {
    private MotorDeBusca m;

    @BeforeEach
    public void init() {
        m = new MotorDeBusca();
    }

    /**
     * Caso de teste em que é inserida uma String com carateres inferior a 2.
     * TestCaseID: 01
     */

    @Test
    public void testOptimizeQuery1() {
        Assertions.assertThrows(NotInAValidIntervalException.class, () -> m.optimizeQuery("a"));
    }

    /**
     * Caso de teste em que é inserida uma String com 2 carateres (LB).
     * TestCaseID: 02
     */

    @Test
    public void testOptimizeQuery2() {
        String[] expected = {"Zé"};

        try {
            Assertions.assertArrayEquals(expected, m.optimizeQuery("Zé"));
        } catch (NotInAValidIntervalException e) {
            e.printStackTrace();
        }
    }

    /**
     * Caso de teste em que é inserida uma String com 29 carateres (BUB).
     * TestCaseID: 03
     */

    @Test
    public void testOptimizeQuery3() {
        String s = "Pedro realiza teste com 29 cr";
        String[] optimize = {"Pedro", "realiza", "teste", "cr"};
        try {
            Assertions.assertArrayEquals(optimize, m.optimizeQuery(s));
        } catch (NotInAValidIntervalException e) {
            e.printStackTrace();
        }
    }

    /**
     * Caso de teste em que é inserida uma String com 30 carateres (UB).
     * TestCaseID: 04
     */

    @Test
    public void testOptimizeQuery4() {
        String s = "Pedro realiza teste com de crc";
        String[] optimize = {"Pedro", "realiza", "teste", "crc"};

        try {
            Assertions.assertArrayEquals(optimize, m.optimizeQuery(s));
        } catch (NotInAValidIntervalException e) {
            e.printStackTrace();
        }
    }

    /**
     * Caso de teste em que é inserida uma String com 31 carateres (AUB).
     * TestCaseID: 05
     */

    @Test
    public void testOptimizeQuery5() {
        String s = "Pedro realiza teste com 31 crct";
        Assertions.assertThrows(NotInAValidIntervalException.class, () -> m.optimizeQuery(s));
    }

    /**
     * Caso de teste em que é inserida uma String com stopwords.
     * TestCaseID: 06
     */

    @Test
    public void testOptimizeQuery6() {
        String[] expected = {"Pedro"};
        try {
            Assertions.assertArrayEquals(expected, m.optimizeQuery("O Pedro de"));
        } catch (NotInAValidIntervalException e) {
            e.printStackTrace();
        }
    }

    /**
     * Caso de teste em que é inserida uma String apenas com números.
     * TestCaseID: 07
     */
    @Test
    public void testOptimizeQuery7() {
            Assertions.assertThrows(IllegalArgumentException.class, () -> m.optimizeQuery("12345"));

    }

    /**
     * Caso de teste em que é inserida uma String com um nome válido e números.
     * TestCaseID: 08
     */

    @Test
    public void testOptimizeQuery8() {
        String[] expected = {"Pedro"};
        try {
            Assertions.assertArrayEquals(expected, m.optimizeQuery("Pedro1234"));
        } catch (NotInAValidIntervalException e) {
            e.printStackTrace();
        }
    }
}
