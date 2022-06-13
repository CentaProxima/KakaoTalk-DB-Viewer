package me.proxima.decryptedkakaodbviewer.util;

import me.proxima.decryptedkakaodbviewer.model.*;
import me.proxima.decryptedkakaodbviewer.types.ChatMessageType;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.text.SimpleDateFormat;
import java.util.Date;

public class JsonUtils {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일 HH:mm:ss.SSS");

    public static String getElementsString(String jsonString){
        JSONParser parser = new JSONParser();
        StringBuilder builder = new StringBuilder();
        try {
            JSONObject obj = (JSONObject)parser.parse(jsonString);
            for(Object key : obj.keySet()){
                Object value = obj.get(key);
                if(key.toString().equals("size")){
                    value = value.toString()+" bytes";
                }

                if(key.toString().equals("expire") || key.toString().toLowerCase().endsWith("at")){
                    value = sdf.format(new Date(Long.parseLong(value.toString())));
                }
                builder.append(key+": ");
                builder.append(value+"\n");
            }
        } catch (Exception e) {
            return null;
        }
        return builder.toString();
    }

    public static Attachment getAttachmentObjFromJson(ChatMessageType type, String json){
        try {
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject)parser.parse(json);

            switch(type){
                case Photo:
                    PhotoAttachment photo = new PhotoAttachment();
                    photo.fromJson(obj);
                    return photo;
                case AnimatedEmoticon:
                case Avatar:
                case Sticker:
                case AnimatedSticker:
                case Spritecon:
                case AnimatedStickerEx:
                    EmoticonAttachment emoticon = new EmoticonAttachment();
                    emoticon.fromJson(obj);
                    return emoticon;
                case File:
                    FileAttachment file = new FileAttachment();
                    file.fromJson(obj);
                    return file;
                case Video:
                    VideoAttachment video = new VideoAttachment();
                    video.fromJson(obj);
                    return video;
                case Audio:
                    AudioAttachment audio = new AudioAttachment();
                    audio.fromJson(obj);
                    return audio;
                default:
                    return null;
            }
        } catch (Exception e) {
            return null;
        }
    }
}
