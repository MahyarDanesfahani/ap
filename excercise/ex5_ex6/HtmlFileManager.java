package excercise.ex5_ex6;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class HtmlFileManager {
    private String saveFileBasePath;
    private static int saveCounter=0;

    public HtmlFileManager(String saveFileBasePath) {
//        this.saveFileBasePath = DirectoryTools.createDirectoryWithTimeStamp(saveFileBasePath);

            this.saveFileBasePath = saveFileBasePath;
            DirectoryTools.createDirectory(saveFileBasePath);
        }

    public void save(List<String> lines) {
            try {
                String saveHtmlFileAddress = getSaveHtmlFileAddress();
                PrintWriter out = new PrintWriter(saveHtmlFileAddress);
                for (String line : lines) {
                    out.println(line);
                }
                out.close();

                System.out.println("save counter: " + saveCounter);
            }catch (Exception e){
                System.out.println("save failed: " + e.getMessage());
            }
        }

    public String loadHtmlFromFile(String filepath){
            StringBuilder content = new StringBuilder();

            try(BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
                String line;
                while ((line =  reader.readLine()) != null){
                    content.append(line).append("\n");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return content.toString();
        }

    public String getSaveHtmlFileAddress(){
        saveCounter++;
        return saveFileBasePath +"/"+ saveCounter;
    }

    public void setSaveFileBasePath(String htmlContent , String relativePath){
        try {
            String fullPath =saveFileBasePath+"/"+relativePath;
            Path filePath = Paths.get(fullPath);
            Files.createDirectories(filePath.getParent());
            Files.write(filePath,htmlContent.getBytes());
        } catch (IOException e) {
            System.out.println("Failed to save HTML: " + e.getMessage());
        }
    }
}