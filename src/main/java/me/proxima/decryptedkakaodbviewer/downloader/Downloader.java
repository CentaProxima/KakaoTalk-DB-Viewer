package me.proxima.decryptedkakaodbviewer.downloader;

import me.proxima.decryptedkakaodbviewer.downloader.response.DownloadResponse;

import java.io.File;

public interface Downloader {
    DownloadResponse download() throws Exception;
    File getDownloadFile();
}
