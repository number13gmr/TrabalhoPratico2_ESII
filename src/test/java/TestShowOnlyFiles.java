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

    @Test
    public void testShowOnlyFiles1() {
        try {
            ArrayList<String> show = m.showOnlyFiles(3);
            Assertions.assertEquals(3, show.size());
        } catch (NotInAValidIntervalException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testShowOnlyFiles2() {
        Assertions.assertThrows(NotInAValidIntervalException.class, () -> m.showOnlyFiles(4));
    }

    @Test
    public void testShowOnlyFiles3(){
        Assertions.assertThrows(NotInAValidIntervalException.class, () -> m.showOnlyFiles(-1));
    }
}
