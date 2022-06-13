module me.proxima.decryptedkakaodbviewer {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;
    requires okio;
    requires org.jsoup;
    requires okhttp3;
    requires annotations;
    requires json.simple;
    requires org.apache.commons.lang3;
    requires java.desktop;

    opens me.proxima.decryptedkakaodbviewer to javafx.fxml;
    opens me.proxima.decryptedkakaodbviewer.view to javafx.fxml,javafx.web,org.apache.commons.lang3;
    opens me.proxima.decryptedkakaodbviewer.model to javafx.base;
    opens me.proxima.decryptedkakaodbviewer.db to java.sql;
    opens me.proxima.decryptedkakaodbviewer.downloader to okhttp3;
    opens me.proxima.decryptedkakaodbviewer.downloader.emoticon to org.jsoup;
    opens me.proxima.decryptedkakaodbviewer.downloader.thread to okio;
    opens me.proxima.decryptedkakaodbviewer.util to json.simple;

    exports me.proxima.decryptedkakaodbviewer;
}