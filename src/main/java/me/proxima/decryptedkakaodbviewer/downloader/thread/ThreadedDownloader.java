package me.proxima.decryptedkakaodbviewer.downloader.thread;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.web.WebView;
import me.proxima.decryptedkakaodbviewer.downloader.Downloader;
import me.proxima.decryptedkakaodbviewer.downloader.response.DownloadResponse;
import me.proxima.decryptedkakaodbviewer.types.DownloadType;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

import java.io.ByteArrayInputStream;
import java.io.File;

public class ThreadedDownloader implements Runnable {
    private Downloader downloader;
    private DownloadType type;
    private Label lbl;
    private WebView imgView;

    private Button open;

    public ThreadedDownloader(Downloader downloader, DownloadType type, Label lbl, WebView imgView, Button open){
        this.downloader = downloader;
        this.type = type;
        this.lbl = lbl;
        this.imgView = imgView;
        this.open = open;
    }

    public ThreadedDownloader(Downloader downloader, DownloadType type, Label lbl, WebView imgView){
        this(downloader, type, lbl, imgView, null);
    }

    public ThreadedDownloader(Downloader downloader, DownloadType type, Label lbl){
        this(downloader, type, lbl, null);
    }

    @Override
    public void run() {
        try {
            File downloadFile = downloader.getDownloadFile();

            if(downloadFile.isFile()){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        lbl.setText("Downloaded "+type.name()+" File: "+downloadFile.getAbsolutePath());

                        if(imgView != null && (type == DownloadType.Photo || type == DownloadType.Emoticon)){
                            imgView.setVisible(true);
                            imgView.getEngine().load(downloadFile.toURI().toString());

                            if(downloadFile.getAbsolutePath().endsWith(".webp")){
                                open.setVisible(true);
                            }
                        }
                    }
                });
                return;
            }

            DownloadResponse response = downloader.download();

            if(response == null || !response.isSuccessed() || response.getFileSize() <= 0L) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        lbl.setText("Failed Download "+type.name()+" File: "+response.getFileName());
                    }
                });
                return;
            }

            if(!downloadFile.getParentFile().isDirectory())
                downloadFile.getParentFile().mkdirs();

            BufferedSource source = Okio.buffer(
                    Okio.source(
                            new ByteArrayInputStream(response.getFileContent())
                    )
            );

            BufferedSink sink = Okio.buffer(Okio.sink(downloadFile));
            sink.writeAll(source);
            sink.close();

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    lbl.setText("Downloaded "+type.name()+" File: "+downloadFile.getAbsolutePath());
                    if(imgView != null && (type == DownloadType.Photo || type == DownloadType.Emoticon)){
                        imgView.setVisible(true);
                        imgView.getEngine().load(downloadFile.toURI().toString());

                        if(downloadFile.getAbsolutePath().endsWith(".webp"))
                            open.setVisible(true);
                    }
                }
            });
        } catch (Exception e) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    StackTraceElement[] stackTraces = e.getStackTrace();

                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Download Error");
                    alert.setHeaderText("다운로드 도중 오류가 발생했습니다.");
                    StringBuilder builder = new StringBuilder();

                    for(StackTraceElement element : stackTraces){
                        builder.append(element);
                    }

                    alert.setContentText(builder.toString());
                }
            });
        }
    }
}