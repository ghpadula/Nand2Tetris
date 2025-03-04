import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Uso correto: java Main <arquivo_entrada>");
            return;
        }

        File directory = new File(args[0]);
        List<String> vmFiles = FileManager.returnVMFiles(directory);
        String outputFile = directory +"/Main.asm";

        for (String inputFile : vmFiles){

            try (
                    BufferedReader brLabel = new BufferedReader(new FileReader(inputFile));
                    BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile,true))
            ) {
                String line;
                Parser p = new Parser();
                CodeWriter c = new CodeWriter(bw,inputFile);

                while ((line = brLabel.readLine()) != null) {
                    line = p.lineTreatment(line);
                    if (line == null){
                        continue;
                    }
                    bw.write("//" + line);
                    bw.newLine();
                    int comand = p.comandType(line);
                    String ariType;
                    String arg1;
                    String arg2;

                    if(comand == p.C_ARITHMETIC){
                        ariType = p.ArithmeticType(line);
                        c.arithmeticWriter(ariType);

                    }
                    else if(comand == p.C_PUSH){
                        arg1 = p.getArg1(line);
                        arg2 = p.getArg2(line);
                        c.pushWriter(arg1,arg2);


                    }
                    else{
                        arg1 = p.getArg1(line);
                        arg2 = p.getArg2(line);
                        c.popWriter(arg1,arg2);


                    }

                }


                brLabel.close();
                System.out.println("Arquivo copiado com sucesso de " + inputFile + " para " + outputFile);


            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


}

