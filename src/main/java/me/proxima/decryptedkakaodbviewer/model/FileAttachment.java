package me.proxima.decryptedkakaodbviewer.model;

import org.json.simple.JSONObject;

public class FileAttachment implements Attachment{
    private String name;
    private String url;
    private String k;
    private long size;
    private long s;
    private long expire;
    private String cs;

    @Override
    public void fromJson(JSONObject obj) {
        if(obj.containsKey("name"))
            this.name = obj.get("name").toString();

        if(obj.containsKey("url"))
            this.url = obj.get("url").toString();

        if(obj.containsKey("k"))
            this.k = obj.get("k").toString();

        if(obj.containsKey("size"))
            this.size = Long.parseLong(obj.get("size").toString());

        if(obj.containsKey("s"))
            this.s = Long.parseLong(obj.get("s").toString());

        if(obj.containsKey("expire"))
            this.expire = Long.parseLong(obj.get("expire").toString());

        if(obj.containsKey("cs"))
            this.cs = obj.get("cs").toString();
    }

    public String getName(){
        return name;
    }

    public String getUrl(){
        return url;
    }

    public String getK(){
        return k;
    }

    public long getSize(){
        return size;
    }

    public long getS(){
        return s;
    }

    public long getExpire(){
        return expire;
    }

    public String getCs(){
        return cs;
    }
}
