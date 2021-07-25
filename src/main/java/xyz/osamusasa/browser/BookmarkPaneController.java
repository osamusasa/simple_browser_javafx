package xyz.osamusasa.browser;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import xyz.osamusasa.browser.util.Resource;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class BookmarkPaneController implements Initializable {
    @FXML private ListView<String> listView;

    /**
     * 初期化処理(自動呼出し)
     *
     * initialize()メソッドは自動呼出しの対象となる
     */
    public void initialize(URL location, ResourceBundle resources) {
        // ブックマークを設定
        ArrayList<String> bookmarks = Resource.bookmarks.getResource();
        listView.getItems().addAll(bookmarks);
    }
}
