package project.HTML;

public class ItemCount {
    String item;
    int count ;

    public ItemCount(String item , int count){
        this.count=count;
        this.item=item;
    }

    public String getItem() {
        return item;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString(){
        return item+" : "+count;
    }
}
