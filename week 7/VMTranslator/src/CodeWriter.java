import java.io.*;
import java.util.*;


public class CodeWriter {
    private BufferedWriter bw;
    public static Map<String, String> segments = new HashMap<>();


    public CodeWriter(BufferedWriter bw) {
        this.bw = bw;
        initializeSegments();
    }
    private void initializeSegments() {
        segments.put("local", "LCL");
        segments.put("argument", "ARG");
        segments.put("this", "THIS");
        segments.put("that", "THAT");
    }

    public void pushWriter(String arg1, String arg2,String fileName) throws IOException {
        if(segments.containsKey(arg1)){
            //addr = segmentPointer + i
            bw.write("@" + arg2 + "\n" +
                    "D=A\n" +
                    "@" + segments.get(arg1) + "\n" +
                    "A=A+D\n" +
                    "D=M\n" +
                    "@SP\n" +
                    "A=M\n" +
                    "M=D\n" +
                    "@SP\n" +
                    "M=M+1\n");
        }


    }
}
