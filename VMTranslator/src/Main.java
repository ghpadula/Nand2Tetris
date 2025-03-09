import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Usage: java Main <input-file>");
            return;
        }

        File input = new File(args[0]);
        List<String> vmFiles = FileManager.returnVMFiles(input);
        String outputFile = FileManager.getDir(input);


        try(
                BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))
        ){
            CodeWriter c = new CodeWriter(bw,"");
            c.init();
            for (String inputFile : vmFiles){
                try (
                        BufferedReader brLabel = new BufferedReader(new FileReader(inputFile))

                ) {

                    String line;
                    Parser p = new Parser();
                    c.setFileName(inputFile);

                    while ((line = brLabel.readLine()) != null) {
                        line = p.lineHandling(line);
                        if (line == null){
                            continue;
                        }

                        bw.write("//" + line);
                        bw.newLine();
                        int command = p.comandType(line);
                        String ariType;
                        String arg1;
                        String arg2;

                        switch (command){
                            case Parser.C_ARITHMETIC:
                                ariType = p.ArithmeticType(line);
                                c.arithmeticWriter(ariType);
                                break;

                            case Parser.C_PUSH:
                                arg1 = p.getArg1(line);
                                arg2 = p.getArg2(line);
                                c.pushWriter(arg1,arg2);
                                break;

                            case Parser.C_POP:
                                arg1 = p.getArg1(line);
                                arg2 = p.getArg2(line);
                                c.popWriter(arg1,arg2);
                                break;

                            case Parser.C_LABEL:
                                arg1 = p.getArg1(line);
                                c.writeLabel(arg1);
                                break;

                            case Parser.C_GOTO:
                                arg1 = p.getArg1(line);
                                c.writeGoto(arg1);
                                break;
                            case Parser.C_IF:
                                arg1 = p.getArg1(line);
                                c.writeIfGoto(arg1);
                                break;
                            case Parser.C_CALL:
                                arg1 = p.getArg1(line);
                                arg2 = p.getArg2(line);
                                c.writeCall(arg1,arg2);
                                break;
                            case Parser.C_FUNCTION:
                                arg1 = p.getArg1(line);
                                arg2 = p.getArg2(line);
                                c.writeFunction(arg1,arg2);
                                break;
                            case Parser.C_RETURN:
                                c.writeReturn();
                                break;

                        }

                    }


                    brLabel.close();
                    System.out.println("Arquivo copiado com sucesso de " + inputFile + " para " + outputFile);


                } catch (IOException e) {
                    throw new RuntimeException("Error processing input file: " + inputFile, e);
                }
            }
        }catch (IOException e) {
            throw new RuntimeException("Error opening or writing to output file " + outputFile, e);
        }
    }


}

