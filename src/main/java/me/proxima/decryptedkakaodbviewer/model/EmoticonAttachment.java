package me.proxima.decryptedkakaodbviewer.model;

import org.json.simple.JSONObject;

public class EmoticonAttachment implements Attachment{
    private String name;
    private String path;
    private String type;
    private String alt;
    private String sound;
    private int width;
    private int height;
    private int xconVersion;
    private int s;
    private boolean emoticonDemoChatLog;

    @Override
    public void fromJson(JSONObject obj) {
        if(obj.containsKey("name"))
            this.name = obj.get("name").toString();

        if(obj.containsKey("path"))
            this.path = obj.get("path").toString();

        if(obj.containsKey("type"))
            this.type = obj.get("type").toString();

        if(obj.containsKey("alt"))
            this.alt = obj.get("alt").toString();

        if(obj.containsKey("sound"))
            this.sound = obj.get("sound").toString();

        if(obj.containsKey("width"))
            this.width = Integer.parseInt(obj.get("width").toString());

        if(obj.containsKey("height"))
            this.height = Integer.parseInt(obj.get("height").toString());

        if(obj.containsKey("xconVersion"))
            this.xconVersion = Integer.parseInt(obj.get("xconVersion").toString());

        if(obj.containsKey("s"))
            this.s = Integer.parseInt(obj.get("s").toString());

        if(obj.containsKey("emoticonDemoChatLog"))
            this.emoticonDemoChatLog = Boolean.parseBoolean(obj.get("emoticonDemoChatLog").toString());
    }

    public String getName(){
        return name;
    }

    public String getPath(){
        return path;
    }

    public String getType(){
        return type;
    }

    public String getAlt(){
        return alt;
    }

    public String getSound(){
        return sound;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public int getS(){
        return s;
    }

    public int getXconVersion(){
        return xconVersion;
    }

    public boolean isEmoticonDemoChatLog(){
        return emoticonDemoChatLog;
    }
}
