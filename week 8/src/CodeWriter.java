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
                    "A=D+M\n" +
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
                            "A=D+A\n" +
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
                    break;

            }
        }
        bw.write("@SP\n" + //writes the end of the push command to all segments,taking the value and putting it on top of the stack
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
                        "D;JEQ\n" +
                        "@NOT_EQUAL." + countLabel + "\n" +
                        "D;JNE\n" +
                        "(EQUAL." + countLabel + ")\n" +
                        "@SP\n" +
                        "A=M-1\n" +
                        "M=-1\n" +
                        "@END." + countLabel + "\n" +
                        "0;JMP\n" +
                        "(NOT_EQUAL." + countLabel + ")\n" +
                        "@SP\n" +
                        "A=M-1\n" +
                        "M=0\n" +
                        "@END." + countLabel + "\n" +
                        "0;JMP\n" +
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
                        "D;JGE\n" +
                        "@NOT_LESS." + countLabel + "\n" +
                        "0;JMP\n" +
                        "(NOT_LESS." + countLabel + ")\n" +
                        "@SP\n" +
                        "A=M-1\n" +
                        "M=-1\n" +
                        "@END." + countLabel + "\n" +
                        "0;JMP\n" +
                        "(LESS_THAN." + countLabel + ")\n" +
                        "@SP\n" +
                        "A=M-1\n" +
                        "M=0\n" +
                        "@END." + countLabel + "\n" +
                        "0;JMP\n" +
                        "(END." + countLabel + ")\n");
                countLabel++;
                break;

            case "gt":
                bw.write("@SP\n" +
                        "AM=M-1\n" +
                        "D=M\n" +
                        "A=A-1\n" +
                        "D=M-D\n" +
                        "@GREATER_THAN." + countLabel + "\n" + //test if x > y(y is the top of the stack)
                        "D;JGT\n" + //if true go to label true
                        "@NOT_GREATER." + countLabel + "\n" +
                        "0;JMP\n" +// if false go to label false
                        "(GREATER_THAN." + countLabel + ")\n" + // true label
                        "@SP\n" +
                        "A=M-1\n" +
                        "M=-1\n" +//put 1111111111111111 in the stack
                        "@END." + countLabel + "\n" +
                        "0;JMP\n" +
                        "(NOT_GREATER." + countLabel + ")\n" +
                        "@SP\n" +
                        "A=M-1\n" +
                        "M=0\n" + //put 0000000000000000 in the stack
                        "@END." + countLabel + "\n" +
                        "0;JMP\n" +
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
                        "M=M-D\n");
                break;

            case "neg":
                bw.write("@SP\n" +
                        "A=M-1\n" +
                        "M=-M\n");
                break;

            case "and":
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
    public void popWriter(String arg1, String arg2) throws IOException {
        if (segments.containsKey(arg1)){
            bw.write("@"+segments.get(arg1)+"\n" +
                    "D=M\n" +
                    "@" + arg2 + "\n" +
                    "D=D+A\n" +
                    "@R14\n" +
                    "M=D\n" +
                    "@SP\n" +
                    "AM=M-1\n" +
                    "D=M\n" +
                    "@R14\n" +
                    "A=M\n" +
                    "M=D\n");

        }
        else{
            switch (arg1){

                case "static":
                    bw.write("@SP\n" +
                            "AM=M-1\n" +
                            "D=M\n" +
                            "@" + fileName + "." + arg2 + "\n" +  //@FileName.i, example @Foo.5
                            "M=D\n");
                    break;

                case "temp":
                    bw.write("@5\n" +
                            "D=A\n" +
                            "@" + arg2 + "\n" +
                            "D=D+A\n" +
                            "@R14\n" +
                            "M=D\n" +
                            "@SP\n" +
                            "AM=M-1\n" +
                            "D=M\n" +
                            "@R14\n" +
                            "A=M\n" +
                            "M=D\n");
                    break;

                case "pointer":
                    if(Objects.equals(arg2, "0")){
                        bw.write("@SP\n" +
                                "AM=M-1\n" +
                                "D=M\n" +
                                "@THIS\n" +
                                "M=D\n");
                    }
                    else{
                        bw.write("@SP\n" +
                                "AM=M-1\n" +
                                "D=M\n" +
                                "@THAT\n" +
                                "M=D\n");
                    }
                    break;


            }
        }
    }
}
