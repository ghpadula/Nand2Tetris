import java.io.*;
import java.util.*;


public class CodeWriter {
    private BufferedWriter bw;
    private String fileName;
    public static Map<String, String> segments = new HashMap<>();


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
}
