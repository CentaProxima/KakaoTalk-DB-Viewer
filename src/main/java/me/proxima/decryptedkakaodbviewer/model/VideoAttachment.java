package me.proxima.decryptedkakaodbviewer.model;

import org.json.simple.JSONObject;

public class VideoAttachment implements Attachment{
    private String url;
    private String tk;
    private String cs;
    private long size;
    private int duration;
    private int width;
    private int height;

    @Override
    public void fromJson(JSONObject obj) {
        if(obj.containsKey("url"))
            this.url = obj.get("url").toString();

        if(obj.containsKey("tk"))
            this.tk = obj.get("tk").toString();

        if(obj.containsKey("cs"))
            this.cs = obj.get("cs").toString();

        if(obj.containsKey("size"))
            this.size = Long.parseLong(obj.get("size").toString());

        if(obj.containsKey("duration"))
            this.duration = Integer.parseInt(obj.get("duration").toString());

        if(obj.containsKey("width"))
            this.width = Integer.parseInt(obj.get("width").toString());

        if(obj.containsKey("height"))
            this.height = Integer.parseInt(obj.get("height").toString());
    }

    public String getUrl(){
        return url;
    }

    public String getTk(){
        return tk;
    }

    public String getCs(){
        return cs;
    }

    public long getSize(){
        return size;
    }

    public int getDuration(){
        return duration;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }
}
