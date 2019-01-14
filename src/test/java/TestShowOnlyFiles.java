import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class TestShowOnlyFiles {
    MotorDeBusca m = new MotorDeBusca();

    @BeforeEach
    public void init() {
        try {
            m.readFiles("Files");
            m.optimizeQuery("Mario de Pedro");
            m.n_ocurrences();
            m.countDocumentsWithWord();
            m.resultMatrixCalc();
            m.calcGrauSim();
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

    /**
     * TestCaseID: 1
     * Dada a existencia de 3 ficheiros, verifica se o numero de ficheiros (3) pedido pelo utilizador
     * e igual ao que lista no final.
     */

    @Test
    public void testShowOnlyFiles1() {
        try {
            ArrayList<String> show = m.showOnlyFiles(3);
            Assertions.assertEquals(3, show.size());
        } catch (NotInAValidIntervalException e) {
            e.printStackTrace();
        }
    }

    /**
     * TestCaseID: 2
     * Dada a existencia de 3 ficheiros no repositorio, verifica se o metodo lanca a excecao
     * caso o utilizador insira que quer visualizar 4 ficheiros.
     */

    @Test
    public void testShowOnlyFiles2() {
        Assertions.assertThrows(NotInAValidIntervalException.class, () -> m.showOnlyFiles(4));
    }

    /**
     * TestCaseID: 3
     * Verifica se o metodo lanca excecao caso o utilizador insira um numero negativo de ficheiros que
     * quer visualizar.
     */
    @Test
    public void testShowOnlyFiles3(){
        Assertions.assertThrows(NotInAValidIntervalException.class, () -> m.showOnlyFiles(-1));
    }
}
