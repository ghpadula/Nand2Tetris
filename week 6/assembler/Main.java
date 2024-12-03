import java.io.*;
import java.util.*;


public class Main extends init {
    public static void main(String[] args) {
        init.initPredefinedSymbols();

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
            //String lineLabelScan;
            int countline = 0;
            int adress_var = 16;

            while ((line = brLabel.readLine()) != null) {
                line = line.replace(" ", "");
                countline = labelScan(line, countline);
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
                    adress_var = Main.intructionA(line, bw, adress_var);
                }

                else if (line.substring(0, 2).equals("//")) {
                    continue;
                }

                else {
                    intructionC(line,bw);

                }
                countline++;
            }

            System.out.println("Arquivo copiado com sucesso de " + inputFile + " para " + outputFile);
        } catch (IOException e) {
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
            if (!var.containsKey(asmInstructiom)) {
                var.put(asmInstructiom, var_adress);
                var_adress = var_adress + 1;
            }
            int number = var.get(asmInstructiom);
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

    public static int labelScan(String docline, int lineCount) {
        if (docline.isEmpty()) {
            return lineCount;
        }
        else if(docline.startsWith("//")){
            return lineCount;

        }
        if (docline.charAt(0) == '(' && docline.charAt(docline.length() - 1) == ')') {
            String label = docline.replaceAll("[()]", "");
            if (!var.containsKey(label)) {
                var.put(label, lineCount);

            }

            return lineCount;
        }

        else {
            return lineCount + 1;
        }
    }

    public static void intructionC(String instruction,BufferedWriter bww){
        String destIns = parser.dest(instruction);
        String compIns = parser.comp(instruction);
        String jumpIns = parser.jump(instruction);

        String a;

        String compBinary;
        String destBinary = dest.get(destIns);
        String jumpBinary = jmp.get(jumpIns);
        if(compIns.contains("M")){
            a = "1";
            compIns = compIns.replace("M","A");
            compBinary = comp.get(compIns);

        }
        else{
            a = "0";
            compBinary = comp.get(compIns);
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
}





class parser{
    public static String dest(String docline){
        if(docline.contains("=")){
            String dest = docline.split("=")[0];
            return dest;
        }
        else{
            return "null";
        }
    }

    public static String comp(String docline){
        String comp;
        if(docline.contains("=") && !docline.contains(";")){
            comp = docline.split("=")[1];
            return comp;
        }

        else if(!docline.contains("=") && docline.contains(";")){
            comp = docline.split(";")[0];
            return comp;
        }
        else if (docline.contains("=") && docline.contains(";")) {
            comp = docline.split("=")[1];
            comp = comp.split(";")[0];


            return comp;
        }
        return "";
    }

    public static String jump(String docline){
        if(docline.contains(";")){
            String jump = docline.split(";")[1];
            return jump;
        }
        else{
            return "null";
        }
    }
}


class init{  //Ver se tem como melhorar
    public static Map<String, Integer> var = new HashMap<>();
    public static Map<String, String> jmp = new HashMap<>();
    public static Map<String, String> dest = new HashMap<>();
    public static Map<String, String> comp = new HashMap<>();
    public static void initPredefinedSymbols(){
        var.put("R0",0);
        var.put("R1",1);
        var.put("R2",2);
        var.put("R3",3);
        var.put("R4",4);
        var.put("R5",5);
        var.put("R6",6);
        var.put("R7",7);
        var.put("R8",8);
        var.put("R9",9);
        var.put("R10",10);
        var.put("R11",11);
        var.put("R12",12);
        var.put("R13",13);
        var.put("R14",14);
        var.put("R15",15);
        var.put("SCREEN",16384);
        var.put("KBD",24576);
        var.put("SP",0);
        var.put("LCL",1);
        var.put("ARG",2);
        var.put("THIS",3);
        var.put("THAT",4);
        comp.put("0","101010");
        comp.put("1","111111");
        comp.put("-1","111010");
        comp.put("D","001100");
        comp.put("A","110000");
        comp.put("!D","001101");
        comp.put("!A","110001");
        comp.put("-D","001111");
        comp.put("-A","110011");
        comp.put("D+1","011111");
        comp.put("A+1","110111");
        comp.put("D-1","001110");
        comp.put("A-1","110010");
        comp.put("D+A","000010");
        comp.put("D-A","010011");
        comp.put("A-D","000111");
        comp.put("D&A","000000");
        comp.put("D|A","010101");
        dest.put("null","000");
        dest.put("M","001");
        dest.put("D","010");
        dest.put("MD","011");
        dest.put("A","100");
        dest.put("AM","101");
        dest.put("AD","110");
        dest.put("ADM","111");
        jmp.put("null","000");
        jmp.put("JGT","001");
        jmp.put("JEQ","010");
        jmp.put("JGE","011");
        jmp.put("JLT","100");
        jmp.put("JNE","101");
        jmp.put("JLE","110");
        jmp.put("JMP","111");



    }
}