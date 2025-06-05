package excercise.ex5_ex6;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UrlDownloader {
    public static String download(String urlStr){
        StringBuilder content = new StringBuilder();

        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            int responseCode = conn.getResponseCode();
            if (responseCode!= 200){
                System.out.println("Failed to fetch: " + urlStr + " - Response code: " + responseCode);
                return null;
            }

            try(BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null){
                    content.append(line).append("\n");
                }
            }
        } catch (Exception e) {
            System.out.println("Error downloading " + urlStr + ": " + e.getMessage());
            return null;
        }
        return content.toString();
    }
}
