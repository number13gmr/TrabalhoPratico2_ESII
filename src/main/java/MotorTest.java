import java.util.Scanner;

public class MotorTest {
    public static void main(String[] args) {
        MotorDeBusca m=new MotorDeBusca();

        String[] s=null;
        try {
            s=m.readFiles("Files");
        } catch (EmptyDirectoryException e) {
          //  e.printStackTrace();
            System.out.println("vazio");
        } catch (NotFoundDirectory notFoundDirectory) {
            System.out.println(notFoundDirectory.getMessage());
            System.out.println("Diretorio nao existe");
        }
        System.out.println(s.length);
        for(int i=0;i<3;i++){
            System.out.println(s[i]);
        }

        try {
            m.optimizeQuery("Amanha Mario");
        } catch (MinimumCaracteresNeeded minimumCaracteresNeeded) {
            minimumCaracteresNeeded.printStackTrace();
        }
        int [][] retorn =m.n_ocurrences();
        //Print da matriz
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                System.out.print(retorn[i][j] + " ");
            }
            System.out.println("");
        }
       // System.out.println(s.length);
       // for(int i=0;i<3;i++){
      //      System.out.println(s[i]);
       // }
    }
}
