import java.io.File;
import java.util.*;

public class FileManager {


    public static List<String> returnVMFiles(File dir){
        List<String> vmFiles = new ArrayList<>();
        if(dir.exists()){
            if(dir.isDirectory()){
                for(File dirFiles : dir.listFiles()){
                    if(dirFiles.getName().endsWith(".vm")) {
                        vmFiles.add(dirFiles.getAbsolutePath());
                    }
                }

            }
            else if(dir.isFile() && dir.getName().endsWith(".vm")){
                vmFiles.add(dir.getAbsolutePath());
            }}
        System.out.println(vmFiles);
        return vmFiles;

    }

    public static String getDir(File input){
        String dir = "";
        if(input.isFile()){
            dir = input.getParent() +"/Main.asm";
        }
        else if(input.isDirectory()){
            dir = input.getAbsolutePath() + "/Main.asm";
        }
        
        return dir;
    }

}
