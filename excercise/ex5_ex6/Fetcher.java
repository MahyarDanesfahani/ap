package excercise.ex5_ex6;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Fetcher {

    public static List<String> fetchHtml(String urlAddress) throws IOException {
        System.out.println("Going to fetch "+urlAddress+" ...");
        URL pageLocation = new URL(urlAddress);
        Scanner in = new Scanner(pageLocation.openStream());

        List<String> htmlLines=new ArrayList<>();
        while (in.hasNext()){
            htmlLines.add(in.next());
        }
        in.close();
        System.out.println(urlAddress+" fetched.");
        return htmlLines;
    }

    private final String allowedDomain = "znu.ac.ir";
    private final HtmlFileManager htmlFileManager ;
    private MediaDownloader mediaDownloader = new MediaDownloader();
    private Set<String> downloadedUrls =new HashSet<>();

    public Fetcher(String baseSavePath){
        this.htmlFileManager = new HtmlFileManager(baseSavePath);
    }

    public void fetch(String urlString){
        try {
            if (downloadedUrls.contains(urlString)){
                System.out.println("URL already downloaded: " + urlString);
                return;
            }

            if (!UrlUtils.isSameDomain(urlString,Conf.ALLOWED_DOMAIN)){
                System.out.println("Skipping foreign domain: " + urlString);
                return;
            }

            List<String> htmlLines = UrlDownloader.download(urlString);
            if (htmlLines == null){
                System.out.println("Failed to download: " + urlString);
                return;
            }

            String htmlPath = htmlFileManager.saveWithUrl(htmlLines,new URL(urlString));
            downloadedUrls.add(urlString);
            mediaDownloader.extractAndDownload(htmlPath,urlString);
            Thread.sleep(Conf.DOWNLOAD_DELAY_SECONDS * 1000L);
        } catch (Exception e) {
            System.out.println("Error while fetching URL: " + urlString);
            e.printStackTrace();
        }
    }

    private boolean isAllowedDomain(URL url){
        String host = url.getHost();
        return host.endsWith(allowedDomain);
    }

    private String createPathFromUrl(URL url){
        StringBuilder pathBuilder = new StringBuilder();

        String host = url.getHost();
        if (!host.equals(allowedDomain)){
            String subdomain = host.replace("."+allowedDomain,"");
            pathBuilder.append("_").append(subdomain).append("/");
        }

        String[] pathParse = url.getPath().split("/");
        for (int i = 0; i < pathParse.length-1; i++) {
            if (!pathParse[i].isEmpty()){
                pathBuilder.append(pathParse[i]).append("/");
            }
        }

        String fileName = pathParse[pathParse.length-1];
        if (fileName.isEmpty()){
            fileName = "index.html";
        }
        pathBuilder.append(fileName);
        return pathBuilder.toString();
    }

    public void fetchAndSave(String urlStr){
        try {
            URL url = new URL(urlStr);

            if (!isAllowedDomain(url)){
                System.out.println("Blocked URL (not allowed domain): " + urlStr);
                return;
            }

            String html = UrlDownloader.download(urlStr);
            if (html  == null || html.isEmpty()){
                System.out.println("Empty content for: " + urlStr);
                return;
            }

            String savePath = createPathFromUrl(url);
            htmlFileManager.setSaveFileBasePath(html, savePath);
            System.out.println("Saved HTML: " + urlStr + " → " + savePath);
        } catch (Exception e) {
            System.out.println("Error fetching URL: " + urlStr + " → " + e.getMessage());
        }
    }
}
