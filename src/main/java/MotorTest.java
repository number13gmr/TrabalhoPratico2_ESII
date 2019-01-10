import java.util.Scanner;

public class MotorTest {
    public static void main(String[] args) {
        MotorDeBusca m=new MotorDeBusca();

        try {

            System.out.println("Insira o que pretende pesquisar: ");

            Scanner ler = new Scanner(System.in);
            String query = ler.nextLine();

            System.out.println(query);
            m.optimizeQuery(query);
            //m.insertQuery("Pedro o de pode os");
        } catch (NotInAValidInterval notInAValidInterval) {
            notInAValidInterval.printStackTrace();
        }
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
       // System.out.println(s.length);
       // for(int i=0;i<3;i++){
      //      System.out.println(s[i]);
       // }
    }
}
