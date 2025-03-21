import java.io.*;
import java.util.*;


public class CodeWriter {
    private BufferedWriter bw;
    private String fileName;
    private static Map<String, String> segments = new HashMap<>();
    private int countLabel = 0;
    private int funcReturnCount = 0;


    public CodeWriter(BufferedWriter bw,String fileName) {
        this.bw = bw;


        initializeSegments();
    }

    public void setFileName(String fileName) {
        if (fileName.contains("/")){
            String[] parts = fileName.split("/");
            int index = parts.length -1;
            this.fileName = parts[index].replace(".vm","");
        }

        else{
            this.fileName = fileName;
        }
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
        finishPush();


    }

    public void finishPush() throws IOException {
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

    public void writeLabel(String labelName) throws IOException {
        bw.write("(" + labelName + ")\n");

    }

    public void writeGoto(String gotoLabel) throws IOException {
        bw.write("@" + gotoLabel + "\n");
        bw.write("0;JMP\n");
    }

    public void writeIfGoto(String gotoLabel) throws IOException {
        bw.write("@SP\n");
        bw.write("AM=M-1\n");
        bw.write("D=M\n"); //D=*SP
        bw.write("@" + gotoLabel + "\n");
        bw.write("D;JNE\n"); //if D != 0, JUMP
    }

    public void init() throws IOException {
        bw.write("@256\n");
        bw.write("D=A\n");
        bw.write("@SP\n");
        bw.write("M=D\n");
        writeCall("Sys.init","0");
    }

    public void writeCall(String arg1,String arg2) throws IOException {
        String funcName = arg1;
        int nArgs = Integer.valueOf(arg2);

        bw.write("@"+funcName + "$return." + funcReturnCount + "\n" +
                "D=A\n");
        finishPush();//SAVE RETURN ADRESS

        bw.write("@LCL\n"+
                "D=M\n");
        finishPush(); //SAVE LCL IF THE CALLER

        bw.write("@ARG\n"+
                "D=M\n");
        finishPush(); //SAVE ARG IF THE CALLER

        bw.write("@THIS\n"+
                "D=M\n");
        finishPush(); //SAVE THIS IF THE CALLER

        bw.write("@THAT\n"+
                "D=M\n");
        finishPush(); //SAVE THAT IF THE CALLER

        bw.write("@SP\n"+
                "D=M\n" +
                "@" + (nArgs + 5) + "\n"+
                "D=D-A\n"+
                "@ARG\n"+
                "M=D\n");//REPOSITIONS ARG

        bw.write("@SP\n"+
                "D=M\n" +
                "@LCL\n" +
                "M=D\n");//REPOSITIONS LCL

        bw.write("@" + funcName + "\n" +
                "0;JMP\n"); //TRANSFERS CONTROL TO THE CALLED FUNCION

        bw.write("("+ funcName + "$return." + funcReturnCount + ")\n");
         //RETURN LABEL

        funcReturnCount++; //Increment return count
    }

    public void writeFunction(String arg1,String arg2) throws IOException {
        String functionName = arg1;
        int nVars = Integer.valueOf(arg2);

        writeLabel(functionName);

        for(int i = 0; i<nVars;i++){
            bw.write("//push 0\n");
            pushWriter("constant","0");
        }
    }

    public void writeReturn() throws IOException {

        bw.write("@LCL\n" +
                "D=M\n" +
                "@endFrame\n" +
                "M=D\n"); //set endFrame = LCL

        bw.write("@5\n" +
                "A=D-A\n" +
                "D=M\n" +
                "@retADDR\n" +
                "M=D\n");  //get the return address

        popWriter("argument","0"); //repositions the return value for the caller

        bw.write("@ARG\n" +
                "D=M+1\n" +
                "@SP\n" +
                "M=D\n"); //repositions SP of the caller

        bw.write("@endFrame\n" +
                "A=M-1\n" +
                "D=M\n" +
                "@THAT\n" +
                "M=D\n"); //Restores THAT of the caller

        bw.write("@2\n" +
                "D=A\n" +
                "@endFrame\n" +
                "A=M-D\n" +
                "D=M\n" +
                "@THIS\n" +
                "M=D\n"); //Restores THIS of the caller

        bw.write("@3\n" +
                "D=A\n" +
                "@endFrame\n" +
                "A=M-D\n" +
                "D=M\n" +
                "@ARG\n" +
                "M=D\n"); //Restores ARG of the caller

        bw.write("@4\n" +
                "D=A\n" +
                "@endFrame\n" +
                "A=M-D\n" +
                "D=M\n" +
                "@LCL\n" +
                "M=D\n"); //Restores LCL of the caller

        bw.write("@retADDR\n" +
                "A=M\n" +
                "0;JMP\n"); //goes to return address in the caller`s code
    }
}

