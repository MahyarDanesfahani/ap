package project.Library2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ConfigReader {

    public static String readStorageType(){
        try {
            List<String> lines = Files.readAllLines(Path.of("config.txt"));
            for (String line : lines){
                if (line.startsWith("storage=")){
                    return line.split("=")[1].trim();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "json" ;
    }

    public static int readThreadCount(){
        try {
            List<String>  lines = Files.readAllLines(Path.of("config.txt"));
            for (String line : lines){
                if (line.startsWith("thread-count=")){
                    return Integer.parseInt(line.split("=")[1].trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
