import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestReadFiles {
    private MotorDeBusca m;

    /**
     * Metodo para inicializar o motor de busca
     */


    @BeforeEach
    public void init() {
        m = new MotorDeBusca();
    }

    /**
     * Caso de teste em que o diretorio nao existe
     * TestCaseID: 1
     */

    @Test
    public void testReadFiles1() {
        Assertions.assertThrows(NotFoundDirectory.class, () -> m.readFiles("test"));
    }

    /**
     * Caso de teste em que o diretorio existe e tem 3 ficheiros
     * TestCaseID: 2
     */

    @Test
    public void testReadFiles2() {

        int n = 0;
        try {
            n = m.readFiles("Files").length;


        } catch (NotFoundDirectory notFoundDirectory) {
            System.out.println(notFoundDirectory.getMessage());

        } catch (EmptyDirectoryException e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(3, n);

    }

    /**
     * Caso de teste em que o diretorio existe mas nao contem ficheiros
     * TestCaseID: 3
     */

    @Test
    public void testReadFiles3() {
        Assertions.assertThrows(EmptyDirectoryException.class, () -> m.readFiles("Files2"));
    }

}
