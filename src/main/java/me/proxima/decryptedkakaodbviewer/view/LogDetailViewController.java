package me.proxima.decryptedkakaodbviewer.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebView;
import me.proxima.decryptedkakaodbviewer.downloader.KakaoGenericFileDownloader;
import me.proxima.decryptedkakaodbviewer.downloader.emoticon.EmoticonDownloader;
import me.proxima.decryptedkakaodbviewer.downloader.emoticon.SoundDownloader;
import me.proxima.decryptedkakaodbviewer.downloader.thread.ThreadedDownloader;
import me.proxima.decryptedkakaodbviewer.holder.ChatLogHolder;
import me.proxima.decryptedkakaodbviewer.model.*;
import me.proxima.decryptedkakaodbviewer.types.ChatMessageType;
import me.proxima.decryptedkakaodbviewer.types.DownloadType;
import me.proxima.decryptedkakaodbviewer.util.JsonUtils;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LogDetailViewController implements Initializable {
    private static final ExecutorService pool = Executors.newSingleThreadExecutor();

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일 HH:mm:ss");

    @FXML
    private TextArea taMessage;

    @FXML
    private TextArea taAttachment;

    @FXML
    private Label lblChatType;

    @FXML
    private Label lblCreatedAt;

    @FXML
    private Label lblDownloadedF;

    @FXML
    private Button btnOpenInBrowser;

    @FXML
    private Button btnFileDownload;

    @FXML
    private Label lblDownloadedS;

    @FXML
    private WebView imgPhotoOrEmo;

    private long chatId;

    public LogDetailViewController(long chatId) {
        this.chatId = chatId;
    }

    @FXML
    public void clickDownload(MouseEvent event) {
        ChatLog log = ChatLogHolder.getInstance().getLog(chatId);

        ChatMessageType type = ChatMessageType.from(log.getType());
        Attachment attachment = JsonUtils.getAttachmentObjFromJson(type, log.getAttachment());

        KakaoGenericFileDownloader downloader = null;

        DownloadType dtype = null;
        if(attachment instanceof FileAttachment){
            FileAttachment fileAttachment = (FileAttachment) attachment;
            downloader = new KakaoGenericFileDownloader(log.getId(), fileAttachment.getUrl(), "files", fileAttachment.getName());
            dtype = DownloadType.File;
        } else if(attachment instanceof VideoAttachment){
            VideoAttachment videoAttachment = (VideoAttachment) attachment;
            downloader = new KakaoGenericFileDownloader(log.getId(), videoAttachment.getUrl(), "vidoes", videoAttachment.getTk());
            dtype = DownloadType.Video;
        } else if(attachment instanceof AudioAttachment){
            AudioAttachment audioAttachment = (AudioAttachment) attachment;
            downloader = new KakaoGenericFileDownloader(log.getId(), audioAttachment.getUrl(), "audioes", audioAttachment.getK());
            dtype = DownloadType.Audio;
        }

        lblDownloadedF.setText("Downloading "+downloader.getDownloadFile().getAbsolutePath()+"...");
        ThreadedDownloader thDownloader = new ThreadedDownloader(downloader, dtype, lblDownloadedF);
        pool.submit(thDownloader);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        taMessage.setWrapText(true);
        taAttachment.setWrapText(true);
        lblDownloadedF.setWrapText(true);
        lblDownloadedS.setWrapText(true);

        ChatLog log = ChatLogHolder.getInstance().getLog(chatId);

        taMessage.setText(log.getMessage());
        taAttachment.setText(JsonUtils.getElementsString(log.getAttachment()));
        lblChatType.setText("ChatType: "+ ChatMessageType.from(log.getType()).toString());
        lblCreatedAt.setText("CreatedAt(KST): "+sdf.format(new Date(log.getCreatedAt()*1000)));

        ChatMessageType type = ChatMessageType.from(log.getType());
        Attachment attachment = JsonUtils.getAttachmentObjFromJson(type, log.getAttachment());

        switch(type){
            case Photo:
                if(attachment instanceof PhotoAttachment){
                    PhotoAttachment photo = (PhotoAttachment)attachment;
                    KakaoGenericFileDownloader downloader = new KakaoGenericFileDownloader(photo.getUrl(), "images");
                    ThreadedDownloader thDownloader = new ThreadedDownloader(downloader, DownloadType.Photo, lblDownloadedF, imgPhotoOrEmo);
                    lblDownloadedF.setText("Downloading "+downloader.getDownloadFile().getAbsolutePath()+"...");
                    pool.submit(thDownloader);
                }
                break;
            case AnimatedEmoticon:
            case Avatar:
            case Sticker:
            case AnimatedSticker:
            case Spritecon:
            case AnimatedStickerEx:
                if(attachment instanceof EmoticonAttachment){
                    try {
                        EmoticonAttachment emoticon = (EmoticonAttachment)attachment;

                        EmoticonDownloader downloader = new EmoticonDownloader(emoticon.getPath(), emoticon.getType(), "_temp/emoticons");
                        ThreadedDownloader thDownloader = new ThreadedDownloader(downloader, DownloadType.Emoticon, lblDownloadedF, imgPhotoOrEmo, btnOpenInBrowser);
                        lblDownloadedF.setText("Downloading "+downloader.getDownloadFile().getAbsolutePath()+"...");
                        pool.submit(thDownloader);

                        if(emoticon.getSound() != null && !StringUtils.isBlank(emoticon.getSound())){
                            SoundDownloader sDownloader = new SoundDownloader(emoticon.getSound(), emoticon.getType(), "_temp/emoticons/sound");
                            ThreadedDownloader thSDownloader = new ThreadedDownloader(sDownloader, DownloadType.Sound, lblDownloadedS);
                            lblDownloadedS.setText("Downloading "+downloader.getDownloadFile().getAbsolutePath()+"...");
                            pool.submit(thSDownloader);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case File:
            case Video:
            case Audio:
                btnFileDownload.setVisible(true);
                break;
        }
    }

    public static ExecutorService getThreadPool(){
        return pool;
    }

    public void openBrowser(MouseEvent mouseEvent) {
        ChatLog log = ChatLogHolder.getInstance().getLog(chatId);

        ChatMessageType type = ChatMessageType.from(log.getType());
        switch(type){
            case AnimatedEmoticon:
            case Sticker:
            case Avatar:
            case AnimatedSticker:
            case Spritecon:
            case AnimatedStickerEx:
                Attachment attachment = JsonUtils.getAttachmentObjFromJson(type, log.getAttachment());
                if(attachment instanceof EmoticonAttachment
                        && Desktop.isDesktopSupported()
                        && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)){
                    try {
                        EmoticonAttachment emoticonAttachment = (EmoticonAttachment)attachment;
                        Desktop desktop = Desktop.getDesktop();
                        desktop.browse(new File("_temp/emoticons/"+emoticonAttachment.getPath()).toURI());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                break;
            default:
                return;
        }
    }
}
