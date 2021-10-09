package xyz.osamusasa.browser.util;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import lombok.Getter;
import xyz.osamusasa.browser.records.WebHistoryEntry;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Resource<T> extends ResourceBundle {

    public static final String PATH_BOOKMARK = "/setting/bookmark.ser";
    public static final String KEY_BOOKMARK = "bookmark.ser";
    public static final String PATH_HISTORY = "/setting/history.ser";
    public static final String KEY_HISTORY = "history.ser";

    @Getter
    private final T resource;

    private final String key;

    public final static Resource<SimpleListProperty<String>> bookmarks;
    public final static Resource<SimpleListProperty<WebHistoryEntry>> history;

    static {
        bookmarks = Resource.<ArrayList<String>>loadResource(Resource.class.getResource(PATH_BOOKMARK))
                .map(list -> new Resource<>(new SimpleListProperty<>(FXCollections.observableList(list.getResource())),list.key))
                .orElseGet(()->{
                    try {
                        save(Paths.get(Resource.class.getResource("/").getPath(), PATH_BOOKMARK).toUri().toURL(), new ArrayList<WebHistoryEntry>());
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    return new Resource<>(new SimpleListProperty<>(FXCollections.observableArrayList()), KEY_BOOKMARK);
                });
        history = Resource.<ArrayList<WebHistoryEntry>>loadResource(Resource.class.getResource(PATH_HISTORY))
                .map(list -> new Resource<>(new SimpleListProperty<>(FXCollections.observableList(list.getResource())),list.key))
                .orElseGet(()->{
                    try {
                        save(Paths.get(Resource.class.getResource("/").getPath(), PATH_HISTORY).toUri().toURL(), new ArrayList<WebHistoryEntry>());
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    return new Resource<>(new SimpleListProperty<>(FXCollections.observableArrayList()), KEY_HISTORY);
                });
    }

    /**
     * コンストラクタ
     */
    private Resource (T data, String key) {
        this.resource = data;
        this.key = key;
        System.out.println("debug " + this.getClass().getCanonicalName() + ": key=" + key);
    }

    /**
     * シリアライズを使ってファイルから読み込む
     *
     * @param path シリアライズされたファイルのパス
     * @param <T> 読み込むクラス
     * @return 読み込んだクラス
     */
    @SuppressWarnings("unchecked")
    public static <T extends Serializable> Optional<T> load(URL path) {
        if (path == null) {
            return Optional.empty();
        }
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(path.openStream());
            return Optional.of((T)objectInputStream.readObject());
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            System.err.println("serialize error at path: " + path);
            e.printStackTrace();
            return Optional.empty();
        }
    }

    /**
     * シリアライズを使ってファイルに書き込む
     *
     * @param path シリアライズするファイルパス
     * @param data 書き込むデータ
     * @param <T> 書き込むクラス
     * @return シリアライズが成功したらtrue,失敗した時false
     */
    public static <T extends Serializable> boolean save(URL path, T data) {
        if (path == null) {
            return false;
        }
        try {
            File f = new File(path.getPath());
            if (!f.exists()) {
                Files.createDirectories(f.getParentFile().toPath());
                boolean isCreated = f.createNewFile();
                if (isCreated) {
                    System.out.println("create new File: " + f.toPath());
                } else {
                    System.err.println("file create error: " + f.toPath());
                }
            }
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(path.getPath()));
            objectOutputStream.writeObject(data);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * シリアライズされたファイルから一つのオブジェクトをもつResourceBundleを取得する
     *
     * @param path シリアライズされたファイルのパス
     * @param <T> 読み込むクラス
     * @return 読み込んだクラスをもつResourceBundle
     */
    public static <T extends Serializable> Optional<Resource<T>> loadResource(URL path) {
        if (path == null) {
            return Optional.empty();
        }
        return Resource.<T>load(path).map(r -> new Resource<>(r, path.getFile()));
    }

    @Override
    public Object handleGetObject(String key) {
        if (key == null) {
            throw new NullPointerException("key is null");
        }
        if (key.equals(this.key)) {
            return resource;
        }
        return null;
    }

    @Override
    public Enumeration<String> getKeys() {
        return Collections.enumeration(Collections.singletonList(this.key));
    }
}
