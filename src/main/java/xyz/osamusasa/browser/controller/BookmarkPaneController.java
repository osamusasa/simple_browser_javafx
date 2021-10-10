package xyz.osamusasa.browser.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import xyz.osamusasa.browser.util.Resource;

import java.net.URL;
import java.util.ResourceBundle;

public class BookmarkPaneController extends AbstractSubPaneController implements Initializable {
    @FXML private ListView<String> listView;

    /**
     * 初期化処理(自動呼出し)
     *
     * initialize()メソッドは自動呼出しの対象となる
     */
    public void initialize(URL location, ResourceBundle resources) {
        // ブックマークを設定
        listView.itemsProperty().bindBidirectional(Resource.bookmarks.getResource());
        // クリックされたら対象のURLを新規タブで表示
        listView.setOnMouseClicked(event -> {
            ListView<String> view = (ListView<String>) event.getSource();
            if (event.getClickCount()==2) {
                System.err.println("Debug: " + view.contains(event.getX(), event.getY()));
                // 選択した状態で他の場所をダブルクリックすると選択されていたアイテムが表示される
                loadNewTab(view.getSelectionModel().getSelectedItem());
                view.getSelectionModel().clearSelection();
            }
        });
    }

    /**
     * 削除ボタン
     *
     * @param e ActionEvent
     */
    public void onDeleteButton(ActionEvent e) {
        listView.getItems().remove(listView.getSelectionModel().getSelectedIndex());
    }
}
