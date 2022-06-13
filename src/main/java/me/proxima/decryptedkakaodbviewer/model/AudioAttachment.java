package me.proxima.decryptedkakaodbviewer.model;

import org.json.simple.JSONObject;

public class AudioAttachment implements Attachment{
    private String url;
    private int d;
    private long size;
    private long size3gp;
    private String k;
    private long expire;
    @Override
    public void fromJson(JSONObject obj) {
        if(obj.containsKey("url"))
            this.url = obj.get("url").toString();

        if(obj.containsKey("d"))
            this.d = Integer.parseInt(obj.get("d").toString());

        if(obj.containsKey("s"))
            this.size = Long.parseLong(obj.get("s").toString());

        if(obj.containsKey("size_3gp"))
            this.size3gp = Long.parseLong(obj.get("size_3gp").toString());

        if(obj.containsKey("k"))
            this.k = obj.get("k").toString();

        if(obj.containsKey("expire"))
            this.expire = Long.parseLong(obj.get("expire").toString());
    }

    public String getUrl(){
        return url;
    }

    public int getD(){
        return d;
    }

    public long getSize(){
        return size;
    }

    public long getSize3gp(){
        return size3gp;
    }

    public String getK(){
        return k;
    }

    public long getExpire(){
        return expire;
    }
}
