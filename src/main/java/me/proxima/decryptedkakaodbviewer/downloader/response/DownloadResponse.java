package me.proxima.decryptedkakaodbviewer.downloader.response;

public class DownloadResponse {
    private String fileName;
    private byte[] fileContent;
    private long fileSize;
    private boolean successed;

    public DownloadResponse(String fileName, byte[] fileContent, long fileSize, boolean successed){
        this.fileName = fileName;
        this.fileContent = fileContent;
        this.fileSize = fileSize;
        this.successed = successed;
    }

    public String getFileName(){
        return fileName;
    }

    public byte[] getFileContent(){
        return fileContent;
    }

    public long getFileSize(){
        return fileSize;
    }

    public boolean isSuccessed(){
        return successed;
    }
}
