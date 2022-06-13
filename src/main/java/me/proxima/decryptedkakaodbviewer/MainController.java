package me.proxima.decryptedkakaodbviewer;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import me.proxima.decryptedkakaodbviewer.db.DBConnection;
import me.proxima.decryptedkakaodbviewer.handler.SearchCheckBoxChangeHandler;
import me.proxima.decryptedkakaodbviewer.holder.ChatLogHolder;
import me.proxima.decryptedkakaodbviewer.holder.SearchOptionsHolder;
import me.proxima.decryptedkakaodbviewer.model.ChatLog;
import me.proxima.decryptedkakaodbviewer.types.SearchOption;
import me.proxima.decryptedkakaodbviewer.view.LogDetailView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private final KeyCombination FOCUS_SEARCHBAR_SHORTCUT = new KeyCodeCombination(KeyCode.F, KeyCombination.CONTROL_DOWN);

    @FXML
    private TextField tfQuery;

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnReload;

    @FXML
    private CheckBox chkId;

    @FXML
    private CheckBox chkUser;

    @FXML
    private CheckBox chkChatRoom;

    @FXML
    private CheckBox chkCreatedAt;

    @FXML
    private CheckBox chkChat;

    @FXML
    private CheckBox chkAttachment;

    @FXML
    private TableView<ChatLog> tblDb;

    @FXML
    private TableColumn<ChatLog, Integer> colIdx;

    @FXML
    private TableColumn<ChatLog, Long> colId;

    @FXML
    private TableColumn<ChatLog, String> colUser;

    @FXML
    private TableColumn<ChatLog, Long> colChatRoom;

    @FXML
    private TableColumn<ChatLog, Long> colCreatedAt;

    @FXML
    private TableColumn<ChatLog, String> colChat;

    @FXML
    private TableColumn<ChatLog, String> colAttachment;

    @FXML
    private TextArea taMessage;

    @FXML
    private TextArea taAttachment;

    private ObservableList<ChatLog> logList;

    private void initTable(){
        DBConnection conn = null;
        try {
            conn = DBConnection.getInstance();
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }

        PreparedStatement ps = conn.prepareStatement("SELECT id, type, user_id, chat_id, created_at, message, attachment FROM chat_logs");
        PreparedStatement ps2 = conn.prepareStatement("SELECT name FROM KAKAO2.friends WHERE id=?");
        PreparedStatement ps3 = conn.prepareStatement("SELECT nickname FROM KAKAO2.block_friends WHERE user_id=?");
        PreparedStatement ps4 = conn.prepareStatement("SELECT nickname FROM KAKAO2.open_profile WHERE user_id=?");
        PreparedStatement ps5 = conn.prepareStatement("SELECT name FROM KAKAO2.open_link WHERE user_id=?");

        ArrayList list = new ArrayList();
        try {
            ResultSet rs = ps.executeQuery();

            int idx = 1;
            while(rs.next()){
                long id = rs.getLong("id");
                int type = rs.getInt("type");
                long userId = rs.getLong("user_id");
                long chatId = rs.getLong("chat_id");
                long createdAt = rs.getLong("created_at");
                String message = rs.getString("message");
                String attachment = rs.getString("attachment");

                String friend = null;

                ps2.setLong(1, userId);
                ResultSet rs2 = ps2.executeQuery();
                if(rs2.next()){
                    friend = rs2.getString("name");
                    rs2.close();
                }

                if(friend == null){
                    ps3.setLong(1, userId);
                    ResultSet rs3 = ps3.executeQuery();
                    if(rs3.next()){
                        friend = rs.getString("nickname");
                        rs3.close();
                    }
                }

                if(friend == null){
                    ps4.setLong(1, userId);
                    ResultSet rs4 = ps4.executeQuery();
                    if(rs4.next()){
                        friend = "나";
                        rs4.close();
                    }
                }

                if(friend == null){
                    ps5.setLong(1, userId);
                    ResultSet rs5 = ps5.executeQuery();
                    if(rs5.next()){
                        friend = rs5.getString("name");
                        rs5.close();
                    }
                }

                if(friend == null && !list.contains(userId)){
                    list.add(userId);
                }
                logList.add(new ChatLog(idx++, id, type, friend != null ? friend : String.valueOf(userId), chatId, createdAt, message, attachment));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                ps.close();
                ps2.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private ObservableList<ChatLog> filterList(List<ChatLog> list, String keyword){
        List<ChatLog> filteredList = new ArrayList<>();
        SearchOptionsHolder holder = SearchOptionsHolder.getInstance();
        for(ChatLog log : list){
            for(SearchOption option: SearchOption.values()){
                if(holder.isSearchable(option) && log.searchByKeyword(option, keyword)){
                    if(!filteredList.contains(log))
                        filteredList.add(log);
                    break;
                }
            }
        }
        return FXCollections.observableArrayList(filteredList);
    }

    @FXML
    private void search(){
        ObservableList<ChatLog> filtered = filterList(logList, tfQuery.getText());
        tblDb.setItems(filtered);
    }

    @FXML
    private void reload(){
        tblDb.setItems(logList);

        tfQuery.setText("");
        chkId.setSelected(false);
        chkUser.setSelected(false);
        chkChatRoom.setSelected(false);
        chkCreatedAt.setSelected(false);
        chkChat.setSelected(false);
        chkAttachment.setSelected(false);
    }

    private void openDetailView(){
        ChatLog log = tblDb.getSelectionModel().getSelectedItem();
        ChatLogHolder.getInstance().addLog(log);

        try {
            LogDetailView view = new LogDetailView(log.getId());
            view.start(new Stage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        EventHandler<KeyEvent> executeSearchShortcutEvent = event->{
            if(event.getCode() != KeyCode.ENTER)
                return;
            search();
            event.consume();
        };

        EventHandler<KeyEvent> focusSearchBarShortcutEvent = event->{
            if(!FOCUS_SEARCHBAR_SHORTCUT.match(event))
                return;
            tfQuery.requestFocus();
            event.consume();
        };

        tblDb.setOnKeyPressed(focusSearchBarShortcutEvent);
        tblDb.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(keyEvent.getCode() != KeyCode.ENTER)
                    return;
                openDetailView();
                keyEvent.consume();
            }
        });

        tfQuery.setOnKeyPressed(executeSearchShortcutEvent);
        btnSearch.setOnKeyPressed(executeSearchShortcutEvent);

        btnReload.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(keyEvent.getCode() != KeyCode.ENTER)
                    return;
                reload();
                keyEvent.consume();
            }
        });

        chkId.selectedProperty().addListener(new SearchCheckBoxChangeHandler(SearchOption.Id));
        chkUser.selectedProperty().addListener(new SearchCheckBoxChangeHandler(SearchOption.User));
        chkChatRoom.selectedProperty().addListener(new SearchCheckBoxChangeHandler(SearchOption.ChatRoom));
        chkCreatedAt.selectedProperty().addListener(new SearchCheckBoxChangeHandler(SearchOption.CreatedAt));
        chkChat.selectedProperty().addListener(new SearchCheckBoxChangeHandler(SearchOption.Chat));
        chkAttachment.selectedProperty().addListener(new SearchCheckBoxChangeHandler(SearchOption.Attachment));

        logList = FXCollections.observableArrayList();
        taMessage.setWrapText(true);
        taAttachment.setWrapText(true);

        colIdx.setCellValueFactory(new PropertyValueFactory<>("Idx"));
        colId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        colUser.setCellValueFactory(new PropertyValueFactory<>("User"));
        colChatRoom.setCellValueFactory(new PropertyValueFactory<>("ChatRoom"));
        colCreatedAt.setCellValueFactory(new PropertyValueFactory<>("CreatedAt"));
        colChat.setCellValueFactory(new PropertyValueFactory<>("Message"));
        colAttachment.setCellValueFactory(new PropertyValueFactory<>("Attachment"));

        tblDb.setItems(logList);
        tblDb.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ChatLog>() {
            @Override
            public void changed(ObservableValue observableValue, ChatLog oldone, ChatLog newone) {
                ChatLog log = tblDb.getSelectionModel().getSelectedItem();
                taMessage.setText(log != null ? log.getMessage() : "");
                taAttachment.setText(log != null ? log.getAttachment() : "");
            }
        });

        tblDb.setRowFactory(tblDb -> {
            TableRow<ChatLog> row = new TableRow<>();
            row.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getClickCount() == 2 && (!row.isEmpty())) {
                    openDetailView();
                }
                mouseEvent.consume();
            });
            return row;
        });
        initTable();
        tblDb.requestFocus();
    }
}
