import java.io.BufferedWriter;
import java.io.IOException;

public class CodeWriter{

    public static void intructionC(String instruction, BufferedWriter bww){
        String destIns = parser.dest(instruction);
        String compIns = parser.comp(instruction);
        String jumpIns = parser.jump(instruction);

        String a;

        String compBinary;
        String destBinary = hackSymbols.dest.get(destIns);
        String jumpBinary = hackSymbols.jmp.get(jumpIns);
        if(compIns.contains("M")){
            a = "1";
            compIns = compIns.replace("M","A");
            compBinary = hackSymbols.comp.get(compIns);

        }
        else{
            a = "0";
            compBinary = hackSymbols.comp.get(compIns);
        }

        String cBinary = "111" + a + compBinary + destBinary + jumpBinary;
        try {
            bww.write(cBinary);
            bww.newLine();
        }
        catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo de saída.");
            e.printStackTrace();
        }
    }

    public static int intructionA(String asmInstructiom, BufferedWriter bww, int var_adress) {

        asmInstructiom = asmInstructiom.replace("@", "");
        String command = "";

        if (asmInstructiom.matches("\\d+")) {

            int number = Integer.parseInt(asmInstructiom);
            String binary_number = String.format("%16s", Integer.toBinaryString(number)).replace(' ', '0');
            command = "0" + binary_number.substring(1);


        }
        else if (!asmInstructiom.matches("\\d+")) {
            if (!hackSymbols.var.containsKey(asmInstructiom)) {
                hackSymbols.var.put(asmInstructiom, var_adress);
                var_adress = var_adress + 1;
            }

            int number = hackSymbols.var.get(asmInstructiom);
            String binary_number = String.format("%16s", Integer.toBinaryString(number)).replace(' ', '0');
            command = "0" + binary_number.substring(1);
        }

        try {
            bww.write(command);
            bww.newLine();
        }
        catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo de saída.");
            e.printStackTrace();
        }
        return var_adress;
    }

}
