import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestResultMatrixCalc {
    MotorDeBusca m=new MotorDeBusca();

    @BeforeEach
    public void init(){
        try {
            m.readFiles("Files");
        } catch (EmptyDirectoryException e) {
            e.printStackTrace();
        } catch (NotFoundDirectory notFoundDirectory) {
            notFoundDirectory.printStackTrace();
        }
        try {
            m.optimizeQuery("Mario de Escola");
        } catch (NotInAValidIntervalException e) {
            e.printStackTrace();
        }
        double[][] xd=m.n_ocurrences();

    }

    @Test
    public void testCase1(){
        double[][] expected= {{2.0,0.0},{1.0,0.0},{1.0,0.0}};
        Assertions.assertArrayEquals(expected,m.resultMatrixCalc());
    }


}
