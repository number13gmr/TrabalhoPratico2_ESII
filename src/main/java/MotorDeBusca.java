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

            return this.filesName;
        }
    }


    /**
     * Metodo resposavel por optimizar a Query, remover palavras que nao façam sentido procurar
     *
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

            if (optimizedQuery.length == 0) {
                throw new IllegalArgumentException("Nao existem palavras para pesquisar!");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        return this.optimizedQuery;

    }

    /**
     * Metodo responsavel por calcular o numero de ocurrencias da Query inserido pelo utlizador nos ficheiro
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

    /**
     * Conta em quantos documentos aparece pelo menos cada uma das palavras inseridas pelo utilizador
     * @return uma hash onde a chave e o nome da palavra e o valor e o numero de vezes que aparece
     * a palavra pelo menos uma vez em cada um dos ficheiros
     */

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

    /**
     * Método que executa o cálculo da fase de preparacao A.
     * @return uma matriz onde a linha e o nome do ficheiro e a coluna, a palavra,
     * em cada linha/coluna irá aparecer o valor do calculo associado a respetiva ficheiro/palavra
     */

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

    /**
     * Método que efetua o calculo da fase de calculo B.
     * @return um array com  grau de similaridade de cada ficheiro.
     */

    public double[] calcGrauSim() {

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
                somaQ += Math.pow(numeroOcurrences[i][j], 2);
            }
            divisor = ((Math.sqrt(somaM)) * (Math.sqrt(somaQ)));
            if (divisor == 0) {
                grauSlim[i] = 0.0;
            } else {
                grauSlim[i] = soma / ((Math.sqrt(somaM)) * (Math.sqrt(somaQ)));
            }
        }
        return grauSlim;
    }

    /**
     * Método que ordena os ficheiros por grau de similaridade por ordem descendente
     */

    public void sortFilesBySim() {

        for (int i = 0; i < grauSlim.length - 1; i++) {
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

    /**
     * Metodo responsável por devolver uma lista de strings com os ficheiros que apresentam um grau
     * de superioridade superior ao desejado pelo utilizador
     * @param sup valor minimo desejado pelo utilizador como grau de similaridade
     * @return lista de strings com os ficheiros que apresentam um grau de superioridade superior ao desejado
     * @throws NotInAValidIntervalException quando o utilizador insere um grau de superioridade superior ao que existe
     */

    public ArrayList<String> similaridadeSuperior(double sup) throws NotInAValidIntervalException {
        this.sortFilesBySim();
        ArrayList<String> simSup = new ArrayList<>();

        if (sup > this.grauSlim[0]) {
            throw new NotInAValidIntervalException("Nao existem ficheiros com grau superior ao inserido");
        } else {
            for (int i = 0; i < this.grauSlim.length; i++) {
                if (this.grauSlim[i] > sup) {
                    simSup.add(this.filesName[i] + " " + this.grauSlim[i]);
                }
            }
        }
        return simSup;
    }

    /**
     *
     * @param nmrFiles Numero de ficheiros que o utilizador quer que seja mostrado
     * @return uma lista de strings com um numero total de ficheiros que o utilizador inseriu, e o grau respetivo
     * @throws NotInAValidIntervalException quando o nmrFiles introduzido pelo utilizador e superior ao numero de ficheiros existentes ou menor ou igual a 0.
     */

    public ArrayList<String> showOnlyFiles(int nmrFiles) throws NotInAValidIntervalException {
        sortFilesBySim();
        ArrayList<String> show = new ArrayList<>();
        if (nmrFiles > filesName.length || nmrFiles <= 0) {
            throw new NotInAValidIntervalException("Apenas existem " + filesName.length);
        } else {
            for (int i = 0; i < nmrFiles; i++) {
                show.add("O ficheiro " + filesName[i] + " tem um grau de " + grauSlim[i] + " similaridade");
            }
        }
        return show;
    }


}



