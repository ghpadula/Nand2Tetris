import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Uso correto: java Main <arquivo_entrada>");
            return;
        }

        String inputFile = args[0];  // Nome do arquivo de entrada
        String outputFile = inputFile.replace(".vm", ".asm"); // Nome do arquivo de saíd

        try (
                BufferedReader brLabel = new BufferedReader(new FileReader(inputFile));
                BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))
        ) {
            String line;
            Parser p = new Parser();

            while ((line = brLabel.readLine()) != null) {
                line = p.lineTreatment(line);
                if (line == null){
                    continue;
                }
                bw.write("//" + line);
                bw.newLine();

            }



            System.out.println("Arquivo copiado com sucesso de " + inputFile + " para " + outputFile);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}

