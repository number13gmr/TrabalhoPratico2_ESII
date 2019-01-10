import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class OcurrencesTest {

    @Test
    public void testN_Ocurrences(){
        MotorDeBusca m=new MotorDeBusca();

        try {
            m.readFiles("Files");
            m.optimizeQuery("Hoje amanha");
        } catch (EmptyDirectoryException e) {
            e.printStackTrace();
        } catch (NotFoundDirectory notFoundDirectory) {
            notFoundDirectory.printStackTrace();
        }catch (NotInAValidIntervalException minimumCaracteresNeeded) {
            minimumCaracteresNeeded.printStackTrace();}

            int [][] ocurrences=m.n_ocurrences();
            int [][] expected={
                    {0,0},{0,0},{0,0}};

        Assertions.assertArrayEquals(expected,m.n_ocurrences());



    }

    @Test
    public void testN_Ocurrences2(){
        MotorDeBusca m=new MotorDeBusca();

        try {
            m.readFiles("Files");
            m.optimizeQuery("Hoje Mario");
        } catch (EmptyDirectoryException e) {
            e.printStackTrace();
        } catch (NotFoundDirectory notFoundDirectory) {
            notFoundDirectory.printStackTrace();
        }catch (NotInAValidIntervalException minimumCaracteresNeeded) {
            minimumCaracteresNeeded.printStackTrace();}

        int [][] ocurrences=m.n_ocurrences();
        int [][] expected={
                {0,2},{0,1},{0,1}};

        Assertions.assertArrayEquals(expected,m.n_ocurrences());



    }
}
