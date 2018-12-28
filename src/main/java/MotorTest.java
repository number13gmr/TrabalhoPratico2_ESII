public class MotorTest {
    public static void main(String[] args) {
        MotorDeBusca m=new MotorDeBusca();
        String[] s=null;
        try {
            s=m.readFiles("Fil");
        } catch (EmptyDirectoryException e) {
          //  e.printStackTrace();
            System.out.println("vazio");
        } catch (NotFoundDirectory notFoundDirectory) {
            System.out.println("Diretorio nao existe");
        }
       // for(int i=0;i<3;i++){
      //      System.out.println(s[i]);
       // }
    }
}
