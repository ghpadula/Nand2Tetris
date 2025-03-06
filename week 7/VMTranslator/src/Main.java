import java.io.*;
import java.security.cert.CertPath;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Usage: java Main <input-file>");
            return;
        }

        String inputFile = args[0]; //input file name
        String outputFile = inputFile.replace(".vm", ".asm"); //output file name

        try (
                BufferedReader brLabel = new BufferedReader(new FileReader(inputFile));
                BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))
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



            System.out.println("File successfully copied from " + inputFile + " to " + outputFile);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}

