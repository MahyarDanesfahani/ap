package excercise.ex5_ex6;

import javax.swing.text.Document;
import javax.swing.text.Element;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;


public class MediaDownloader {

    private static final String IMAGE_DIR = "downloaded/images/";
    private static final String AUDIO_DIR = "downloaded/audios/";
    private Set<String> downloadedUrls = new HashSet<>();

    public MediaDownloader(){
        DirectoryTools.createDirectory(IMAGE_DIR);
        DirectoryTools.createDirectory(AUDIO_DIR);
    }

    private void downloadFile(String fileUrl , String folderPath){
        try {
            if (fileUrl == null || fileUrl.isEmpty() || downloadedUrls.contains(fileUrl)){
                return;
            }

            downloadedUrls.add(fileUrl);
            URL url = new URL(fileUrl);
            String fileName = Paths.get(url.getPath()).getFileName().toString();
            String filePath = folderPath+fileName;

            try (InputStream in = url.openStream(); OutputStream out = new FileOutputStream(filePath)){
                byte[] buffer = new byte[2048];
                int length;
                while ((length = in.read(buffer)) != -1){
                    out.write(buffer,0,length);
                }
                System.out.println("Downloaded: "+fileUrl);
            }

            Thread.sleep(Conf.DOWNLOAD_DELAY_SECONDS * 1000L);
        } catch (Exception e) {
            System.out.println("Error downloading " + fileUrl + ": " + e.getMessage());
        }
    }

    public void extractAndDownload(String htmlFilePath,String baseUrl){
        String html = new HtmlFileManager("").loadHtmlFromFile(htmlFilePath);
        Document doc = Jsoup.parse(html, baseUrl);

        Element imgTags = doc.select("img[src]");
        Element audioTags = doc.select("audio[src], audio source[src]");
        Element links = doc.select("a[href]");

        for (Element img : imgTags){
            String url = img.absUrl("src");
            downloadFile(url , IMAGE_DIR);
        }

        for (Element audio : audioTags){
            String url = audio.absUrl("src");
            downloadFile(url , AUDIO_DIR);
        }

        for (Element link : links){
            String url = link.absUrl("src");
            if (url.matches(".*\\\\.(mp3|wav|ogg)")){
                downloadFile(url,AUDIO_DIR);
            }
        }
    }


}
