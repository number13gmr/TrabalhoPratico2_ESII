import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestCalcGrauSim {
    MotorDeBusca m=new MotorDeBusca();

    @BeforeEach
    public void init(){
        try {
            m.readFiles("Files");
            m.optimizeQuery("Mario de Pedro");
            double[][] matrice = m.n_ocurrences();
            double[][] matrice2 = m.resultMatrixCalc();
        } catch (EmptyDirectoryException e) {
            e.printStackTrace();
        } catch (NotFoundDirectory notFoundDirectory) {
            notFoundDirectory.printStackTrace();
        } catch (NotInAValidIntervalException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void testCalcGrauSim1(){
        double[] expected = {0.9727041202799769, 0.9819511386468481, 0.9025341732201746};
        Assertions.assertArrayEquals(expected, m.calcGrauSim());
    }
}
