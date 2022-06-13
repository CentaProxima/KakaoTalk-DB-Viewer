package me.proxima.decryptedkakaodbviewer.types;

public enum ChatMessageType {
    UNDEFINED(-999999,"undefined/*"),
    Category(-100, "undefined/*"),
    ECommerceNoticeFeed(-14, "undefined/*"),
    OpenLinkIllegalBlind(-13, "undefined/*"),
    UnverifiedPlusFriendSpamFeed(-12, "undefined/*"),
    DeletedAll(-11, "undefined/*"),
    AlimtalkSpamFeed(-10, "undefined/*"),
    PNCFeed(-9, "undefined/*"),
    SecretChatInSecureFeed(-7, "undefined/*"),
    SecretChatWelcomeFeed(-6, "undefined/*"),
    LostChatLogsFeed(-5, "undefined/*"),
    SpamFeed(-4, "undefined/*"),
    LastRead(-3, "undefined/*"),
    KakaoLink(-2, "text/plain"),
    TimeLine(-1, "undefined/*"),
    Feed(0, "undefined/*"),
    Text(1, "text/plain"),
    Photo(2, "image/*"),
    Video(3, "video/*"),
    Contact(4, "text/x-vcard"),
    Audio(5, "audio/*"),
    AnimatedEmoticon(6, "text/plain"),
    DigitalItemGift(7, "text/plain"),
    Link(9, "text/plain"),
    OldLocation(10, "text/location"),
    Avatar(11, "text/plain"),
    Sticker(12, "text/plain"),
    Schedule(13, "text/plain"),
    Vote(14, "text/plain"),
    CJ20121212(15, "text/plain"),
    Location(16, "text/location"),
    Profile(17, "text/profile"),
    File(18, "application/*"),
    AnimatedSticker(20, "text/plain"),
    Nudge(21, "text/plain"),
    Spritecon(22, "text/plain"),
    SharpSearch(23, "text/search"),
    Post(24, "text/plain"),
    AnimatedStickerEx(25, "text/plain"),
    Reply(26, "text/plain"),
    MultiPhoto(27, "image/*"),
    Mvoip(51, "text/plain"),
    VoxRoom(52, "text/plain"),
    Leverage(71, "text/leverage"),
    Alimtalk(72, "text/plain"),
    Plus(81, "text/plain"),
    PlusEvent(82, "text/plain"),
    PlusViral(83, "text/plain"),
    ScheduleForOpenLink(96, "text/plain"),
    VoteForOpenLink(97, "text/plain"),
    PostForOpenLink(98, "text/plain");

    private int code;
    private String mimeType;

    ChatMessageType(int code, String mimeType) {
        this.code = code;
        this.mimeType = mimeType;
    }

    public static ChatMessageType from(int code){
        for(ChatMessageType type : ChatMessageType.values()){
            if(type.code == code)
                return type;
        }
        return ChatMessageType.UNDEFINED;
    }

    public int intValue(){
        return code;
    }

    public String getMimeType(){
        return mimeType;
    }
}
