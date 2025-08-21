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

    public static int labelScan(String docline, int lineCount) {
        if (docline.isEmpty()) {
            return lineCount;
        }
        else if(docline.startsWith("//")){
            return lineCount;

        }
        if (docline.charAt(0) == '(' && docline.charAt(docline.length() - 1) == ')') {
            String label = docline.replaceAll("[()]", "");
            if (!hackSymbols.var.containsKey(label)) {
                hackSymbols.var.put(label, lineCount);

            }

            return lineCount;
        }

        else {
            return lineCount + 1;
        }
    }
}