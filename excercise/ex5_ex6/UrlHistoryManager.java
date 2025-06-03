package excercise.ex5_ex6;

import java.util.HashSet;
import java.util.Set;

public class UrlHistoryManager {

    private Set<String>  visitedUrl ;

    public UrlHistoryManager(){
        visitedUrl = new HashSet<>();
    }

    public boolean shouldProcess(String url){
        if (visitedUrl.contains(url)){
            return false;
        }
        visitedUrl.add(url);
        return true;
    }

    public void reset(){
        visitedUrl.clear();
    }

    public int getVisitedCount(){
        return visitedUrl.size();
    }
}
