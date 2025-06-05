package excercise.ex5_ex6;

import java.net.MalformedURLException;
import java.net.URL;

public class DomainFilter {
    private final String baseDomain;

    public DomainFilter(String baseDomain){
        this.baseDomain=baseDomain;
    }

    public boolean isAllowed(String url){
        try {

            URL parsedUrl = new URL(url);
            String host = parsedUrl.getHost();
            return host.endsWith(baseDomain);
        } catch (MalformedURLException e) {
            return false;
        }
    }
}
