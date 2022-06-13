package me.proxima.decryptedkakaodbviewer.handler;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import me.proxima.decryptedkakaodbviewer.holder.SearchOptionsHolder;
import me.proxima.decryptedkakaodbviewer.types.SearchOption;

public class SearchCheckBoxChangeHandler implements ChangeListener<Boolean> {
    private SearchOption searchOption;

    public SearchCheckBoxChangeHandler(SearchOption searchOption){
        this.searchOption = searchOption;
    }

    @Override
    public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
        SearchOptionsHolder holder = SearchOptionsHolder.getInstance();

        if(t1) {
            holder.setOption(searchOption);
            return;
        }
        holder.removeOption(searchOption);
    }
}
