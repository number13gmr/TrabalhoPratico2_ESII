import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestCalcGrauSim {
    MotorDeBusca m = new MotorDeBusca();

    @BeforeEach
    public void init() {
        try {
            m.readFiles("Files");

        } catch (EmptyDirectoryException e) {
            e.printStackTrace();
        } catch (NotFoundDirectory notFoundDirectory) {
            notFoundDirectory.printStackTrace();
        }


    }

    /**
     * TestCaseID: 1
     * Verifica se o metodo efetua o calculo corretamente, verificando se o array que o metodo retorna
     * e igual ao esperado
     */

    @Test
    public void testCalcGrauSim1() {
        try {
            m.optimizeQuery("Mario de Pedro");
        } catch (NotInAValidIntervalException e) {
            e.printStackTrace();
        }
        double[][] matrice = m.n_ocurrences();
        double[][] matrice2 = m.resultMatrixCalc();

        double[] expected = {0.9727041202799769, 0.9819511386468481, 0.9025341732201746};
        Assertions.assertArrayEquals(expected, m.calcGrauSim());
    }

    /**
     * TestCaseID: 2
     * Verifica se caso o divisor seja 0, ou seja, impossivel, o metodo defina o
     * calculo do ficheiro como 0.
     */

    @Test
    public void testCalcGrauSim2() {
        try {
            m.optimizeQuery("Joao de Pedro");
        } catch (NotInAValidIntervalException e) {
            e.printStackTrace();
        }
        double[][] matrice = m.n_ocurrences();
        double[][] matrice2 = m.resultMatrixCalc();
        double[] novo = m.calcGrauSim();

        double[] expected = {0.0, 1.0, 0.0};
        Assertions.assertArrayEquals(expected, m.calcGrauSim());
    }
}
