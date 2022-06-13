package me.proxima.decryptedkakaodbviewer.db;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBConnection implements Closeable {
    private static final File KAKAO_FILE = new File("decrypted/KakaoTalk.db");
    private static final File KAKAO2_FILE = new File("decrypted/KakaoTalk2.db");

    private static DBConnection instance = null;

    private Connection connection = null;
    private PreparedStatement statement = null;

    DBConnection() throws FileNotFoundException{
        try {
            Class.forName("org.sqlite.JDBC");
            if (!KAKAO_FILE.isFile())
                throw new FileNotFoundException(KAKAO_FILE.getAbsolutePath()+" cannot found, Please, Decrypt KakaoTalk databases first.");

            if(!KAKAO2_FILE.isFile())
                throw new FileNotFoundException(KAKAO_FILE.getAbsolutePath()+" cannot found, Please, Decrypt KakaoTalk databases first.");

            connection = DriverManager.getConnection("jdbc:sqlite:" + KAKAO_FILE.getAbsolutePath());
            PreparedStatement ps = connection.prepareStatement("ATTACH DATABASE ? AS KAKAO2");
            ps.setString(1, KAKAO2_FILE.getAbsolutePath());
            ps.executeUpdate();
        } catch (ClassNotFoundException e) {
        } catch (SQLException e) {
        }
    }

    public static DBConnection getInstance() throws FileNotFoundException{
        if(instance == null)
            instance = new DBConnection();
        return instance;
    }

    public PreparedStatement prepareStatement(String sql){
        try {
            statement = connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e2){
            return null;
        }
        return statement;
    }

    @Override
    public void close() throws IOException {
        try {
            if(statement != null && !statement.isClosed())
                this.statement.close();
            if(!connection.isClosed()) {
                if(KAKAO2_FILE.isFile()) {
                    PreparedStatement ps = connection.prepareStatement("DETACH DATABASE KAKAO2");
                    ps.executeUpdate();
                    ps.close();
                }
                this.connection.close();
            }
            instance = null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
