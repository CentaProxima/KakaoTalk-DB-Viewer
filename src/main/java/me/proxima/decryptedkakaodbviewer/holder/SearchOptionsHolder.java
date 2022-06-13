package me.proxima.decryptedkakaodbviewer.holder;

import me.proxima.decryptedkakaodbviewer.types.SearchOption;

import java.util.ArrayList;
import java.util.List;

public class SearchOptionsHolder {
    private static SearchOptionsHolder inst = null;
    private List<SearchOption> holder = new ArrayList<>();

    public static SearchOptionsHolder getInstance(){
        if(inst == null)
            inst = new SearchOptionsHolder();
        return inst;
    }

    public void setOption(SearchOption option){
        if(holder.contains(option))
            return;
        holder.add(option);
    }

    public void removeOption(SearchOption option){
        if(!holder.contains(option))
            return;
        holder.remove(option);
    }

    public boolean isSearchable(SearchOption option){
        return holder.contains(option);
    }
}
