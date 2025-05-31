package project.HTML;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ObjectCounter <E> {
    private List<ItemCount> counterList;

    public ObjectCounter() {
        this.counterList=new ArrayList<>();
    }

    public void add(String item){
        for (ItemCount ic : counterList){
            if (ic.item.equals(item)){
                ic.count++;
                return;
            }
        }
        counterList.add(new ItemCount(item,1));
    }

    public List<ItemCount> getTop(int k){
        return counterList.stream()
                .sorted((a,b)-> Integer.compare(b.count,a.count))
                .limit(k)
                .collect(Collectors.toList());
    }
}
