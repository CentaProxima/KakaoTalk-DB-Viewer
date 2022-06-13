package me.proxima.decryptedkakaodbviewer.downloader;

import me.proxima.decryptedkakaodbviewer.downloader.response.DownloadResponse;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class KakaoGenericFileDownloader implements Downloader{
    private static final File TEMP_ROOT = new File("_temp/");
    private String url;
    private File downloadFile;

    public KakaoGenericFileDownloader(String url, String type){
        this.url = url;
        if(!TEMP_ROOT.isDirectory())
            TEMP_ROOT.mkdirs();

        URL _url = null;
        try {
            _url = new URL(url);
            String[] files = _url.getFile().split("/");
            downloadFile = new File(TEMP_ROOT, type+"/"+files[files.length-1]);
            if(!downloadFile.getParentFile().isDirectory())
                downloadFile.getParentFile().mkdirs();
        } catch (MalformedURLException e) {
        }
    }
    public KakaoGenericFileDownloader(long chatId, String url, String type, String filename){
        this.url = url;
        if(!TEMP_ROOT.isDirectory())
            TEMP_ROOT.mkdirs();
        downloadFile = new File(TEMP_ROOT, type+"/"+chatId+"/"+filename);
    }

    public DownloadResponse download(){
        DownloadResponse response = null;

        try{
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .build();

            Request req = new Request.Builder()
                    .addHeader("Accept-Language", Locale.getDefault().getLanguage())
                    .addHeader("User-Agent", String.format("KT/%s An/%s %s", new String[]{"9.7.7", "5.1.1", Locale.getDefault().getLanguage()}))
                    .url(this.url)
                    .build();

            Response resp = client.newCall(req).execute();
            byte[] content = resp.body().bytes();
            resp.close();

            response = new DownloadResponse(downloadFile.getAbsolutePath(), content, content.length, resp.code()/100 == 2);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return response;
    }

    public File getDownloadFile(){
        return downloadFile;
    }
}
