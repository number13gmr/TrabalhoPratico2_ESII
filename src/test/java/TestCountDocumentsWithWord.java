import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class TestCountDocumentsWithWord {
    private MotorDeBusca m;

    @BeforeEach
    public void testCountDocumentsWithWord(){
        m=new MotorDeBusca();
    }


    /**
     * Teste case #1
     */

    @Test
    public void testCount(){
        try {
            m.readFiles("Files");
        } catch (EmptyDirectoryException e) {
            e.printStackTrace();
        } catch (NotFoundDirectory notFoundDirectory) {
            notFoundDirectory.printStackTrace();
        }
        try {
            m.optimizeQuery("O LEITE Estg");
        } catch (NotInAValidIntervalException e) {
            e.printStackTrace();
        }
        m.n_ocurrences();

        HashMap<String,Integer> expected=new HashMap<>();
        expected.put("LEITE",1);
        expected.put("Estg",0);

        Assertions.assertTrue(expected.equals(m.countDocumentsWithWord()));
    }
}
