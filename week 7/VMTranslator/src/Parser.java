public class Parser {

    public static final int C_ARITHMETIC = 0;
    public static final int C_PUSH = 1;
    public static final int C_POP = 2;

    public static String lineTreatment(String line){

        if(line.startsWith("//")){
            return null;
        }

        else if(line.equals("")){
            return null;
        }

        else if (line.contains("//")) {
            line = line.substring(0, line.indexOf("//"));
            line = line.trim();
            return line;
        }

        else{
            line = line.trim();
            return line;
        }
    }

    public static int comandType(String line){
        String comandType = line.split(" ")[0];
        switch (comandType){
            case "push":
                return C_PUSH;
            case "pop":
                return C_POP;
            default:
                return C_ARITHMETIC;
        }
    }
}
