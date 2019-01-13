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
                this.numeroOcurrences[i][j] = matches;
            }
        }
        return this.numeroOcurrences;
    }

    public HashMap<String, Integer> countDocumentsWithWord() {

        int count = 0, i, j;

        HashMap<String, Integer> hash = new HashMap<>();

        for (j = 0; j < numeroOcurrences[0].length; j++) {
            count = 0;
            for (i = 0; i < numeroOcurrences.length; i++) {
                if (numeroOcurrences[i][j] > 0) {
                    count++;
                }
            }
            hash.put(optimizedQuery[j], count);
        }
        return hash;
    }


    public double[][] resultMatrixCalc() {
        double calculo;
        matriz = new double[filesName.length][optimizedQuery.length];
        HashMap<String, Integer> hash;
        hash = countDocumentsWithWord();
        for (int i = 0; i < numeroOcurrences.length; i++) {
            for (int j = 0; j < numeroOcurrences[0].length; j++) {
                if (hash.containsKey(optimizedQuery[j])) {
                    Integer value = hash.get(optimizedQuery[j]);
                    if (value == 0) {
                        calculo = 0.0;
                    } else {
                        double x = (double) filesName.length;
                        double y = (double) value;
                        calculo = numeroOcurrences[i][j] * 1 + Math.log10(x / y);
                    }
                    matriz[i][j] = calculo;

                }
            }
        }
        return matriz;
    }

    public double[] calcGrauSim(){

        grauSlim = new double[filesName.length];
        double soma, mlxq, somaM, somaQ, divisor;


        for (int i = 0; i < matriz.length; i++) {
            soma = 0.0;
            somaM = 0.0;
            somaQ = 0.0;
            for (int j = 0; j < matriz[0].length; j++) {
                mlxq = matriz[i][j] * numeroOcurrences[i][j];
                soma += mlxq;
                somaM += Math.pow(matriz[i][j], 2);
                somaQ += Math.pow(numeroOcurrences[i][j],2);
            }
            divisor = ((Math.sqrt(somaM)) * (Math.sqrt(somaQ)));
            if(divisor == 0) {
                grauSlim[i] = 0.0;
            }else{
                grauSlim[i] = soma / ((Math.sqrt(somaM)) * (Math.sqrt(somaQ)));
            }
        }
        return grauSlim;
    }

    public void sortFilesBySim(){

        for (int i = 0; i <grauSlim.length-1 ; i++) {
            for (int j = 0; j < grauSlim.length - i - 1; j++) {
                if (grauSlim[j] < grauSlim[j + 1]) {
                    double temp = grauSlim[j];
                    String tempName = this.filesName[j];
                    grauSlim[j] = grauSlim[j + 1];
                    this.filesName[j] = this.filesName[j + 1];
                    grauSlim[j + 1] = temp;
                    this.filesName[j + 1] = tempName;
                }
            }
        }
    }

    public void similaridadeSuperior(double sup){
        this.sortFilesBySim();

        
        if(sup> this.grauSlim[0]){
            System.out.println("Nao existem ficheiros com grau superior ao inserido");
        }
        else {


            for (int i = 0; i < this.grauSlim.length; i++) {
                if (this.grauSlim[i] > sup) {
                    System.out.println(this.filesName[i] + " " + this.grauSlim[i]);
                }
            }

        }
    }


}



