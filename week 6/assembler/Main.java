import java.io.*;
import java.util.*;


public class Main {
    public static void main(String[] args) {
        hackSymbols.initPredefinedSymbols();

        if (args.length != 1) {
            System.out.println("Uso correto: java Main <arquivo_entrada>");
            return;
        }

        String inputFile = args[0];  // Nome do arquivo de entrada
        String outputFile = inputFile.replace(".asm", ".hack"); // Nome do arquivo de saída

        try (
                BufferedReader brLabel = new BufferedReader(new FileReader(inputFile));
                BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))
        ) {
            String line;
            int countline = 0;
            int adress_var = 16;

            while ((line = brLabel.readLine()) != null) {
                line = line.replace(" ", "");
                countline = parser.labelScan(line, countline);
            }

            brLabel.close();
            BufferedReader br = new BufferedReader(new FileReader(inputFile));

            while ((line = br.readLine()) != null) {
                line = line.replace(" ", "");

                if (line.contains("//")) {
                    line = line.substring(0, line.indexOf("//"));
                }
                if (line.equals("")) {
                    continue;
                }
                if (line.charAt(0) == '(' && line.charAt(line.length() - 1) == ')') {
                    continue;

                }

                if (line.charAt(0) == '@') {
                    adress_var = CodeWriter.intructionA(line, bw, adress_var);
                }

                else if (line.substring(0, 2).equals("//")) {
                    continue;
                }

                else {
                    CodeWriter.intructionC(line,bw);

                }
                
            }

            System.out.println("Arquivo copiado com sucesso de " + inputFile + " para " + outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}







