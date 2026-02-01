package oop4;

import java.util.List;

public class SearchResult<T> {
    private final List<T> items;
    private final int count;
    public SearchResult(List<T> items){
        this.items=items;
        this.count=items.size();
    }

    public List<T> getItems() {
        return items;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString(){
        return "Found"+count+"items";
    }
}
