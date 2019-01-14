import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class TestSimilaridadeSuperior {
    MotorDeBusca m = new MotorDeBusca();
    private double [] teste;
    @BeforeEach
    public void init() {
        try {
            m.readFiles("Files");
            m.optimizeQuery("Mario de Pedro");
            m.n_ocurrences();
            m.countDocumentsWithWord();
            m.resultMatrixCalc();
            teste = m.calcGrauSim();
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
     * Verifica se o metodo lanca excecao caso o utilizador deseje que lhe seja apresentado
     * todos os ficheiros com grau de superioridade superior ao existente nos ficheiros.
     */
    @Test
    public void testSimilaridadeSuperior1(){
        Assertions.assertThrows(NotInAValidIntervalException.class, () -> m.similaridadeSuperior(1));
    }

    /**
     * TestCaseID: 2
     * Verifica se o metodo retorna todos os ficheiros com um grau de superioridade,
     * superiores ao que o utilizador inseriu.
     */

    @Test
    public void testSimilaridadeSuperior2(){
        try {
            ArrayList<String> sim = m.similaridadeSuperior(0.2);
            Assertions.assertTrue(3 == sim.size());
        } catch (NotInAValidIntervalException e) {
            e.printStackTrace();
        }
    }

    /**
     * TestCaseID: 3
     * Verifica se o o utilizador retorna todos os ficheiros com um grau de superioridade,
     * superiores ao que o utilizador inseriu
     */

    @Test
    public void testSimilaridadeSuperior3(){
        try {
            ArrayList<String> sim = m.similaridadeSuperior(0.95);
            Assertions.assertTrue(2 == sim.size());
        } catch (NotInAValidIntervalException e) {
            e.printStackTrace();
        }
    }


}
