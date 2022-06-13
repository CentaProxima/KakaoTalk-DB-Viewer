package me.proxima.decryptedkakaodbviewer.view;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import me.proxima.decryptedkakaodbviewer.holder.ChatLogHolder;
import me.proxima.decryptedkakaodbviewer.model.ChatLog;

public class LogDetailView extends Application {
    private Long chatId;

    public LogDetailView(long chatId){
        this.chatId = chatId;
    }

    @Override
    public void start(Stage stage) throws Exception {
        ChatLog log = ChatLogHolder.getInstance().getLog(chatId);

        LogDetailViewController controller = new LogDetailViewController(chatId);
        FXMLLoader loader = new FXMLLoader(LogDetailView.class.getResource("log-detail-view.fxml"));
        loader.setController(controller);
        Parent pane = loader.load();
        Scene scene = new Scene(pane, 1050, 600);
        scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(keyEvent.getCode() != KeyCode.ESCAPE)
                    return;
                stage.close();
                keyEvent.consume();
            }
        });
        stage.setTitle(log.getId()+" - "+log.getUser()+"'s sent message");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                ChatLogHolder.getInstance().deleteLog(chatId);
            }
        });
        stage.show();
    }
}
