package me.proxima.decryptedkakaodbviewer.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import me.proxima.decryptedkakaodbviewer.types.SearchOption;

public class ChatLog {
    private SimpleIntegerProperty idx;
    private SimpleLongProperty id;
    private SimpleIntegerProperty type;
    private SimpleStringProperty user;
    private SimpleLongProperty chatRoom;
    private SimpleLongProperty createdAt;
    private SimpleStringProperty message;
    private SimpleStringProperty attachment;

    public ChatLog(int idx, long id, int type, String user, long chatRoom, long createAt, String message, String attachment){
        this.idx = new SimpleIntegerProperty(idx);
        this.id = new SimpleLongProperty(id);
        this.type = new SimpleIntegerProperty(type);
        this.user = new SimpleStringProperty(user);
        this.chatRoom = new SimpleLongProperty(chatRoom);
        this.createdAt = new SimpleLongProperty(createAt);
        this.message = new SimpleStringProperty(message);
        this.attachment = new SimpleStringProperty(attachment);
    }

    public void setIdx(int idx){
        this.idx = new SimpleIntegerProperty(idx);
    }

    public int getIdx(){
        return idx.get();
    }

    public void setId(int id){
        this.id = new SimpleLongProperty(id);
    }

    public long getId(){
        return id.get();
    }

    public void setType(int type){
        this.type = new SimpleIntegerProperty(type);
    }

    public int getType(){
        return type.get();
    }

    public void setUser(String user){
        this.user = new SimpleStringProperty(user);
    }

    public String getUser(){
        return user.get() != null ? user.get() : "";
    }

    public void setChatRoom(long chatRoom){
        this.chatRoom = new SimpleLongProperty(chatRoom);
    }

    public long getChatRoom(){
        return chatRoom.get();
    }

    public void setCreatedAt(long createdAt){
        this.createdAt = new SimpleLongProperty(createdAt);
    }

    public long getCreatedAt(){
        return createdAt.get();
    }

    public void setMessage(String message){
        this.message = new SimpleStringProperty(message);
    }

    public String getMessage(){
        return message.get() != null ? message.get() : "";
    }

    public void setAttachment(String attachment){
        this.attachment = new SimpleStringProperty(attachment);
    }

    public String getAttachment(){
        return attachment.get() != null ? attachment.get() : "";
    }

    public boolean searchByKeyword(SearchOption option, String keyword){
        switch(option){
            case Id:
                return String.valueOf(id.get()).contains(keyword);
            case User:
                return user.get() != null ? user.get().contains(keyword) : false;
            case ChatRoom:
                return String.valueOf(chatRoom.get()).contains(keyword);
            case CreatedAt:
                return String.valueOf(createdAt.get()).contains(keyword);
            case Chat:
                return message.get() != null ? message.get().contains(keyword) : false;
            case Attachment:
                return attachment.get() != null ? attachment.get().contains(keyword) : false;
            default:
                return false;
        }
    }
}
