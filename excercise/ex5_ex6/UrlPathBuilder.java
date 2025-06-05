package excercise.ex5_ex6;

import java.net.MalformedURLException;
import java.net.URL;

public class UrlPathBuilder {

    public static String UrlPathBuilder(String url ,String baseDomain){
        try {
            URL parsedUrl = new URL(url);
            String host = parsedUrl.getHost();
            String path = parsedUrl.getPath();

            StringBuilder directory = new StringBuilder();
            if (!host.equals(baseDomain)){
                String subdomainPart =host.replace("."+baseDomain , "");
                directory.append("_").append(subdomainPart).append("/");
            }
            String[] pathParts = path.split("/");
            for (int i = 1; i < pathParts.length-1 ; i++) {
                if (!pathParts[i].isEmpty()){
                    directory.append(pathParts[i]).append("/");
                }
            }
            return directory.toString();
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error URL : " + url);
        }
    }

    public static String extractFileNameFromUrl(String url){
        try {
            URL parsedUrl = new URL(url);
            String path = parsedUrl.getPath();
            return path.substring(path.lastIndexOf("/")+1);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error URL : " + url);
        }
    }
}
