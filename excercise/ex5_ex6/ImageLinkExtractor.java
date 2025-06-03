package excercise.ex5_ex6;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImageLinkExtractor {
    private HtmlFileManager htmlFileManager;

    public ImageLinkExtractor(String basePath){
        htmlFileManager = new HtmlFileManager(basePath);
    }

    public void extractAndSaveImageLink(String htmlFilePath , String outputFilepath){
        String htmlCountent = htmlFileManager.loadHtmlFromFile(htmlFilePath);
        Pattern imgSrcPattern = Pattern.compile("<img\\\\s+[^>]*src=[\\\"']([^\\\"']+)[\\\"'][^>]*>",Pattern.CASE_INSENSITIVE);
        Matcher matcher = imgSrcPattern.matcher(htmlCountent);

        List<String> imageLinks = new ArrayList<>();
        while (matcher.find()){
            String src = matcher.group(1);
            if (!src.isEmpty()){
                imageLinks.add(src);
            }
        }

        StringBuilder sb = new StringBuilder();
        for (String link : imageLinks){
            sb.append(link).append(System.lineSeparator());
        }
        FileTools.writeToFile(outputFilepath,sb.toString());
    }
}
