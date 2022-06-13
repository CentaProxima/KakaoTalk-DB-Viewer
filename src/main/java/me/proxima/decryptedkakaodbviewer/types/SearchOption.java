package me.proxima.decryptedkakaodbviewer.types;

public enum SearchOption {
    Id("Id"),
    User("User"),
    ChatRoom("ChatRoom"),
    CreatedAt("CreatedAt"),
    Chat("Chat"),
    Attachment("Attachment");

    String searchOption;

    SearchOption(String searchOption) {
        this.searchOption = searchOption;
    }

    public String toString(){
        return searchOption;
    }
}
