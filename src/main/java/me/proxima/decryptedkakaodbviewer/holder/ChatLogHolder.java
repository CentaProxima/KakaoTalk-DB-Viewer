package me.proxima.decryptedkakaodbviewer.holder;

import me.proxima.decryptedkakaodbviewer.model.ChatLog;

import java.util.HashMap;

public class ChatLogHolder {
    private static ChatLogHolder inst = null;
    private final HashMap<Long, ChatLog> logs = new HashMap<>();

    public static ChatLogHolder getInstance(){
        if(inst == null)
            inst = new ChatLogHolder();
        return inst;
    }

    public void addLog(ChatLog log){
        this.logs.put(log.getId(), log);
    }

    public void deleteLog(ChatLog log){
        this.logs.remove(log.getId());
    }

    public void deleteLog(long id){
        this.logs.remove(id);
    }

    public ChatLog getLog(long id){
        return logs.get(id);
    }
}
