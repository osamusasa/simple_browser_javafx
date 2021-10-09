package xyz.osamusasa.browser;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import xyz.osamusasa.browser.records.WebHistoryEntry;
import xyz.osamusasa.browser.util.Resource;

import java.net.URL;
import java.util.ResourceBundle;

public class HistoryPaneController extends AbstractSubPaneController implements Initializable {
    @FXML
    ListView<WebHistoryEntry> listView;

    /**
     * 初期化処理(自動呼出し)
     *
     * initialize()メソッドは自動呼出しの対象となる
     */
    public void initialize(URL location, ResourceBundle resources) {
        // アクセス履歴を設定
        listView.itemsProperty().bindBidirectional(Resource.history.getResource());
        // クリックされたら対象のURLを新規タブで表示
        listView.setOnMouseClicked(event -> {
            ListView<WebHistoryEntry> view = (ListView<WebHistoryEntry>) event.getSource();
            if (event.getClickCount()==2) {
                System.err.println("Debug: " + view.contains(event.getX(), event.getY()));
                // 選択した状態で他の場所をダブルクリックすると選択されていたアイテムが表示される
                parentController.loadNewTab(view.getSelectionModel().getSelectedItem().getUrl());
                view.getSelectionModel().clearSelection();
            }
        });
    }
}
