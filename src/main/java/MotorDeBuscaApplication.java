import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MotorDeBuscaApplication {
    public static void main(String[] args) {
        Scanner s=new Scanner(System.in);
        String[] filesName = null;
        //Inicia o motor de busca
        MotorDeBusca motorDeBusca=new MotorDeBusca();
        //Le o nome do repositorio
        boolean dontstop=false;
        do {
            System.out.println("Insira o nome do repositorio:");
            String repo = s.nextLine();

            //Carrega os ficheiros para o Motor De Busca


            try {
                 filesName= motorDeBusca.readFiles(repo);
                dontstop=true;
            } catch (EmptyDirectoryException e) {
                System.out.println(e.getMessage());
            } catch (NotFoundDirectory notFoundDirectory) {
                System.out.println(notFoundDirectory.getMessage());
            }


        }while(!dontstop);


        dontstop=false;
        //Pede a Query ao utilizador
        do {
            System.out.println("Insira a sua Query:");
            String query = s.nextLine();

                //Insere a Query
            try {
                motorDeBusca.optimizeQuery(query);
                dontstop=true;
            } catch (NotInAValidIntervalException e) {
               e.printStackTrace();

            }
        }while(!dontstop);


        //Faz o calculo do numero de ocurrencias
        motorDeBusca.n_ocurrences();
        //Fase de preparaçao A
        motorDeBusca.resultMatrixCalc();
        //Fase de preparaçao B
        double[] grauSim=motorDeBusca.calcGrauSim();

        int n=-1;




    do {
        do {
            System.out.println("\n\n");
            System.out.println("1-Ordenar os ficheiros por grau de similaridade");
            System.out.println("2-Ate uma similaridade");
            System.out.println("3-Apresentar a similaridade para apenas x ficheiros");
            System.out.println("0-Sair");
            System.out.println("Insira um numero:");
            try {
                n = s.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Apenas os numeros indicados em cima");
                n = -1;
                s.nextLine();

            }


        } while (n <0 || n>3);
        switch (n){
            case 1:
                motorDeBusca.sortFilesBySim();
                for (int i = 0; i <filesName.length ; i++) {
                    System.out.println(filesName[i]+"="+grauSim[i]);
                }
                break;
            case 2:
                Boolean stop=false;
                double n2 = 0;
                do {

                    try {
                        System.out.println("A partir de que similaridade deseja ver:");
                        n2 = s.nextDouble();
                        stop=true;
                    } catch (InputMismatchException e) {
                        System.out.println("Apenas numeros");
                        n2 = -1;
                        s.nextLine();
                    }
                }while(!stop);


                try {
                    ArrayList<String> print=motorDeBusca.similaridadeSuperior(n2);
                    System.out.println(print.toString());
                } catch (NotInAValidIntervalException e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                stop=false;
                int n3 = 0;
                do {

                    try {
                        System.out.println("Quantos ficheiros deseja ver:");
                        n3 = s.nextInt();
                        stop=true;
                    } catch (InputMismatchException e) {
                        System.out.println("Apenas numeros inteiros");
                        n3 = -1;
                        s.nextLine();
                    }
                }while(!stop);


                try {
                   ArrayList<String> print2= motorDeBusca.showOnlyFiles(n3);
                    System.out.println(print2.toString());
                } catch (NotInAValidIntervalException e) {
                    e.printStackTrace();
                }

                break;
        }


    }while(n!=0);


    }
}
