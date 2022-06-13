package me.proxima.decryptedkakaodbviewer;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import me.proxima.decryptedkakaodbviewer.view.LogDetailViewController;

import java.io.IOException;

public class MainApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent pane = FXMLLoader.load(MainApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(pane, 1455, 650);
        primaryStage.setScene(scene);
        primaryStage.setTitle("KakaoTalk Database Viewer v1.0");
        primaryStage.setOnCloseRequest(event -> {
            LogDetailViewController.getThreadPool().shutdown();
            Platform.exit();
        });
        primaryStage.show();
    }
}
