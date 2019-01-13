import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MotorDeBusca {


    private String[] filesName;
    private String[] fileContent;
    private String[] optimizedQuery;

    //Matriz com o numero de ocurrencias
    private double[][] numeroOcurrences;

    private double[][] matriz;

    private double[] grauSlim;

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
                filesName[i] = fList[i].getName();
            }


            //Adiciona o conteudo dos ficheiros a um vetor de string
            for (int i = 0; i < filesName.length; i++) {
                File file = new File(directoryName + "/" + filesName[i]);


                BufferedReader b = null;

                try {
                    b = new BufferedReader(new FileReader(file));
                } catch (FileNotFoundException e) {
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


    /**
     * Metodo resposavel por optimizar a Query, remover palavras que nao façam sentido procurar
     * @param query String inserida pelo utilizador
     * @return Query otimizada
     */


    public String[] optimizeQuery(String query) throws NotInAValidIntervalException {

        if (query.length() < 2 || query.length() > 30) {
            throw new NotInAValidIntervalException();
        }
        String[] stopwords;

        List<String> lines = null;

        try {

            query = query.replaceAll("[0-9]", "");
            System.out.println(query + " QUERY *** ");

            if(query.isEmpty()){

            }

            lines = Files.readAllLines(Paths.get("stopwords.txt"), StandardCharsets.UTF_8);
            stopwords = lines.toArray(new String[lines.size()]);
            String stringStopwords = null;



            //MOSTRA TODAS AS PALAVRAS DO STOPWORDS

            for (int i = 0; i < stopwords.length; i++) {
                if (i == 0) {
                    stringStopwords = stopwords[i];
                } else {
                    stringStopwords += " " + stopwords[i];
                }
            }

            //LIMPA QUERY
            String[] split = null;
            split = query.split(" ");
            List<String> novo = new ArrayList<>();


            for (int i = 0; i < split.length; i++) {
                Matcher m = Pattern.compile(Pattern.quote(split[i]), Pattern.CASE_INSENSITIVE).matcher(stringStopwords);

                int matches = 0;

                while (m.find() && matches == 0) {
                    matches++;
                }

                if (matches == 0) {
                    novo.add(split[i]);
                }
            }



            this.optimizedQuery = novo.toArray(new String[0]);

            if(optimizedQuery.length == 0){
                throw new IllegalArgumentException("Nao existem palavras para pesquisar!");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        return this.optimizedQuery;

    }

    /**
     * Metodo responsavel por calcular o numero de ocurrencias da Query inserido pelo utlizador nos ficheiro
     *
     * @return matriz com um numero de ocurrencias de cada palavra nos ficheiros
     */
    public double[][] n_ocurrences() {


        this.numeroOcurrences = new double[this.filesName.length][this.optimizedQuery.length];

        for (int i = 0; i < this.filesName.length; i++) {
            for (int j = 0; j < this.optimizedQuery.length; j++) {


                Matcher m = Pattern.compile(Pattern.quote(this.optimizedQuery[j]), Pattern.CASE_INSENSITIVE).matcher(this.fileContent[i]);

                double matches = 0.0;
                //Conta quantos encontrou
                while (m.find()) {
                    matches++;
                }

                //Atribiu quantos encontrou
                //System.out.println("Encontrou:" + matches + " palavra" + this.optimizedQuery[j] + " ficheiro" + this.fileContent[i]);
                this.numeroOcurrences[i][j] = matches;
            }
        }
        return this.numeroOcurrences;
    }

}



