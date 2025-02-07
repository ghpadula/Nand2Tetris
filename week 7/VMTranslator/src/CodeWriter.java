import java.io.*;
import java.util.*;


public class CodeWriter {
    private BufferedWriter bw;
    private String fileName;
    private static Map<String, String> segments = new HashMap<>();
    private int countLabel = 0;


    public CodeWriter(BufferedWriter bw,String fileName) {
        this.bw = bw;

        if (fileName.contains("/")){
            String[] parts = fileName.split("/");
            int index = parts.length -1;
            this.fileName = parts[index].replace(".vm","");
        }

        else{
            this.fileName = fileName;
        }

        initializeSegments();
    }
    private void initializeSegments() {
        segments.put("local", "LCL");
        segments.put("argument", "ARG");
        segments.put("this", "THIS");
        segments.put("that", "THAT");
    }

    public void pushWriter(String arg1, String arg2) throws IOException {
        if(segments.containsKey(arg1)){
            //addr = segmentPointer + i
            bw.write("@" + arg2 + "\n" +
                    "D=A\n" +
                    "@" + segments.get(arg1) + "\n" + //Example if arg1 = local, will be written @LCL
                    "A=A+D\n" +
                    "D=M\n");

        }
        else{
            switch (arg1){
                case "constant":
                    bw.write("@" + arg2 + "\n" +
                            "D=A\n");

                    break;

                case "static":
                    bw.write("@" + fileName + "." + arg2 + "\n" +   //@namefile.i
                            "D=M\n");

                    break;

                case "temp":
                    bw.write("@" + arg2 + "\n" +
                            "D=A\n" +
                            "@5\n"  +
                            "A=A+D\n" +
                            "D=M\n");

                    break;

                case "pointer":
                    if (arg2.equals("0")){
                        bw.write("@THIS\n" +
                                "D=M\n");

                    }
                    else{
                        bw.write("@THAT\n" +
                                "D=M\n");
                    }


            }
        }
        bw.write("@SP\n" +
                "A=M\n" +
                "M=D\n" +
                "@SP\n" +
                "M=M+1\n");


    }

    public void arithmeticWriter(String type) throws IOException {
        switch (type){
            case "eq":
                bw.write("@SP\n" +
                        "AM=M-1\n" +
                        "D=M\n" +
                        "A=A-1\n" +
                        "D=D-M\n" +
                        "@EQUAL." + countLabel + "\n" +
                        "D;JEQ" +
                        "@NOT_EQUAL." + countLabel + "\n" +
                        "0;JNE\n" +
                        "(EQUAL." + countLabel + ")\n" +
                        "@SP\n" +
                        "A=M-1\n" +
                        "M=-1\n" +
                        "@END." + countLabel + "\n" +
                        "0;JMP" +
                        "(NOT_EQUAL." + countLabel + ")\n" +
                        "@SP\n" +
                        "A=M-1\n" +
                        "M=0\n" +
                        "@END." + countLabel + "\n" +
                        "0;JMP" +
                        "(END." + countLabel + ")\n");
                countLabel++;
                break;

            case "lt":
                bw.write("@SP\n" +
                        "AM=M-1\n" +
                        "D=M\n" +
                        "A=A-1\n" +
                        "D=M-D\n" +
                        "@LESS_THAN." + countLabel + "\n" +
                        "D;JGT" +
                        "@NOT_LESS." + countLabel + "\n" +
                        "0;JMP\n" +
                        "(LESS_THAN." + countLabel + ")\n" +
                        "@SP\n" +
                        "A=M-1\n" +
                        "M=-1\n" +
                        "@END." + countLabel + "\n" +
                        "0;JMP" +
                        "(NOT_LESS." + countLabel + ")\n" +
                        "@SP\n" +
                        "A=M-1\n" +
                        "M=0\n" +
                        "@END." + countLabel + "\n" +
                        "0;JMP" +
                        "(END." + countLabel + ")\n");
                countLabel++;
                break;

            case "gt":
                bw.write("@SP\n" +
                        "AM=M-1\n" +
                        "D=M\n" +
                        "A=A-1\n" +
                        "D=M-D\n" +
                        "@GREATER_THAN." + countLabel + "\n" +
                        "D;JLT" +
                        "@NOT_GREATER." + countLabel + "\n" +
                        "0;JMP\n" +
                        "(GREATER_THAN." + countLabel + ")\n" +
                        "@SP\n" +
                        "A=M-1\n" +
                        "M=-1\n" +
                        "@END." + countLabel + "\n" +
                        "0;JMP" +
                        "(NOT_GREATER." + countLabel + ")\n" +
                        "@SP\n" +
                        "A=M-1\n" +
                        "M=0\n" +
                        "@END." + countLabel + "\n" +
                        "0;JMP" +
                        "(END." + countLabel + ")\n");
                countLabel++;
                break;

            case "add":
                bw.write("@SP\n" +
                        "AM=M-1\n" +
                        "D=M\n" +
                        "A=A-1\n" +
                        "M=D+M\n");
                break;

            case "sub":
                bw.write("@SP\n" +
                        "AM=M-1\n" +
                        "D=M\n" +
                        "A=A-1\n" +
                        "M=D-M\n");
                break;

            case "neg":
                bw.write("@SP\n" +
                        "A=M-1\n" +
                        "M=-M\n");
                break;

            case "end":
                bw.write("@SP\n" +
                        "AM=M-1\n" +
                        "D=M\n" +
                        "A=A-1\n" +
                        "M=D&M\n");
                break;

            case "or":
                bw.write("@SP\n" +
                        "AM=M-1\n" +
                        "D=M\n" +
                        "A=A-1\n" +
                        "M=D|M\n");
                break;

            case "not":
                bw.write("@SP\n" +
                        "A=M-1\n" +
                        "M=!M\n");
                break;
        }
    }
}
