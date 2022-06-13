package me.proxima.decryptedkakaodbviewer.model;

import org.json.simple.JSONObject;

public class PhotoAttachment implements Attachment{
    private String thumbnailUrl;
    private int thumbnailHeight;
    private int thumbnailWidth;
    private String url;
    private String k;
    private String cs;
    private long size;
    private int width;
    private int height;
    private String mt;

    @Override
    public void fromJson(JSONObject obj) {
        if(obj.containsKey("thumbnailUrl"))
            this.thumbnailUrl = obj.get("thumbnailUrl").toString();

        if(obj.containsKey("thumbnailHeight"))
            this.thumbnailHeight = Integer.parseInt(obj.get("thumbnailHeight").toString());

        if(obj.containsKey("thumbnailWidth"))
            this.thumbnailWidth = Integer.parseInt(obj.get("thumbnailWidth").toString());

        if(obj.containsKey("url"))
            this.url = obj.get("url").toString();

        if(obj.containsKey("k"))
            this.k = obj.get("k").toString();

        if(obj.containsKey("cs"))
            this.cs = obj.get("cs").toString();

        if(obj.containsKey("s"))
            this.size = Long.parseLong(obj.get("s").toString());

        if(obj.containsKey("w"))
            this.width = Integer.parseInt(obj.get("w").toString());

        if(obj.containsKey("h"))
            this.height = Integer.parseInt(obj.get("h").toString());

        if(obj.containsKey("mt"))
            this.mt = obj.get("mt").toString();
    }

    public String getThumbnailUrl(){
        return thumbnailUrl;
    }

    public int getThumbnailHeight(){
        return thumbnailHeight;
    }

    public int getThumbnailWidth(){
        return thumbnailWidth;
    }

    public String getUrl(){
        return url;
    }

    public String getK(){
        return k;
    }

    public String getCs(){
        return cs;
    }

    public String getMt(){
        return mt;
    }

    public long getSize(){
        return size;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }
}
