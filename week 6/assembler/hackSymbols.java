import java.util.HashMap;
import java.util.Map;

class hackSymbols{
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