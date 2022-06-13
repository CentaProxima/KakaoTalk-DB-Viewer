package me.proxima.decryptedkakaodbviewer.downloader.emoticon;

import me.proxima.decryptedkakaodbviewer.downloader.Downloader;
import me.proxima.decryptedkakaodbviewer.downloader.response.DownloadResponse;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import javax.net.ssl.SSLException;
import java.io.File;

public class SoundDownloader implements Downloader {
    private String name;
    private String type;
    private String path;
    private File downloadFile;

    public SoundDownloader(String name, String type, String path){
        this.name = name;
        this.type = type;
        this.path = path;
        this.downloadFile = new File(new File(path), name);
    }

    public DownloadResponse download() throws Exception{
        DownloadResponse response = null;

        try{
            String url = "https://item.kakaocdn.net/dw/"+name;

            Connection.Response resp = Jsoup.connect(url)
                    .ignoreContentType(true)
                    .ignoreHttpErrors(true)
                    .execute();

            byte[] content = resp.bodyAsBytes();

            response = new DownloadResponse(downloadFile.getAbsolutePath(), content, content.length, resp.statusCode()/100 == 2);
        }catch(SSLException e){
            return null;
        }
        return response;
    }

    public File getDownloadFile(){
        return downloadFile;
    }
}
