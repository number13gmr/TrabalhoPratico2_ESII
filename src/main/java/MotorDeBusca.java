
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MotorDeBusca {


    private String[] filesName;
    private String[] fileContent;



    /**
     * @param directoryName , nome do diretorio que contém os ficheiros
     * @return Um vetor de strings com o couteudo dos ficheiros
     * @throws EmptyDirectoryException caso o diretorio em questao esteja vazio
     */

    public String[] readFiles(String directoryName) throws EmptyDirectoryException, NotFoundDirectory {

        File directory = new File(directoryName);

        if (!directory.exists()) {
            throw new NotFoundDirectory();
        } else {
            //Conta o numero de ficheiros e lista-os
            File[] fList = directory.listFiles(new FilenameFilter() {
                public boolean accept(File dir, String filename) {
                    return filename.endsWith(".txt");
                }
            });
            //Caso o diretorio esteja vazio
            if (fList.length == 0) {
                throw new EmptyDirectoryException();
            }
            fileContent = new String[fList.length];
            filesName = new String[fList.length];
            //Cria um vetor apenas com o nome dos ficheiros
            for (int i = 0; i < fList.length; i++) {
                if (fList[i].getName().endsWith(".txt")) {
                    filesName[i] = fList[i].getName();
                }
            }

            //Adiciona o conteudo dos ficheiros a um vetor de string
            for (int i = 0; i < filesName.length; i++) {
                File file = new File(directoryName + "/" + filesName[i]);


                BufferedReader b = null;

                try {
                    b = new BufferedReader(new FileReader(file));
                } catch (FileNotFoundException e) {
                    System.out.println(filesName[i]);
                    System.out.println("Problema ao abrir o ficheiro");
                }
                String aux = "";
                String aux2 = "";
                try {
                    while ((aux = b.readLine()) != null) {

                        aux2 = aux2 + " " + aux;
                    }
                    fileContent[i] = aux2;
                } catch (IOException ex) {
                    System.out.println("Erro ao ler o ficheiro");
                }

            }

            return this.fileContent;
        }
    }




}
