package xyz.osamusasa.browser;

import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import xyz.osamusasa.browser.records.WebHistoryEntry;
import xyz.osamusasa.browser.util.Resource;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Controller {
    @Setter
    @Getter
    private Stage stage;

    @FXML private TextField addressBar;
    @FXML private TabPane tabPane;

    /**
     * 初期化処理(自動呼出し)
     *
     * initialize()メソッドは自動呼出しの対象となる
     */
    public void initialize() {
        loadNewTab("https://www.google.co.jp/");
    }

    /**
     * アドレスバーでEnter
     *
     * @param e ActionEvent
     */
    public void onAddressBarEvent(KeyEvent e) {
        switch(e.getCode()) {
            case ENTER:load(addressBar.getText());break;
            default:
                break;
        }
    }

    /**
     * 戻るボタン
     *
     * @param e ActionEvent
     */
    public void onBack(ActionEvent e) {
        currentWebView().ifPresent(webView -> {
            WebEngine engine = webView.getEngine();
            WebHistory history = engine.getHistory();
            if ( history.getCurrentIndex() > 0 ) history.go(-1);
        });
    }

    /**
     * 進むボタン
     *
     * @param e ActionEvent
     */
    public void onNext(ActionEvent e) {
        currentWebView().ifPresent(webView -> {
            WebEngine engine = webView.getEngine();
            WebHistory history = engine.getHistory();
            if (history.getCurrentIndex() < history.getEntries().size() - 1) history.go(1);
        });
    }

    /**
     * 新規タブボタン
     *
     * @param e ActionEvent
     */
    public void onNewTab(ActionEvent e) {
        loadNewTab("https://www.google.co.jp/");
    }

    /**
     * ブックマーク表示ボタン
     *
     * @param e ActionEvent
     */
    @FXML
    public void onBookmarkViewer(ActionEvent e) {
        AbstractSubPaneController.showSubPaneController(this, "/fxml/bookmarkPane.fxml");
    }

    /**
     * ブックマーク追加ボタン
     *
     * @param e ActionEvent
     */
    @FXML
    public void onBookmarking(ActionEvent e) {
        String bookmarkingURL = addressBar.getText();
        List<String> bookmarks = Resource.bookmarks.getResource();
        bookmarks.add(bookmarkingURL);
        boolean success = Resource.save(getClass().getResource(Resource.PATH_BOOKMARK), new ArrayList<>(bookmarks));
        System.out.println("save bookmark: " + success);
        if (!success) {
            System.err.println("bookmarking error");
        }
    }

    /**
     * アクセス履歴に追加する。
     *
     * @param entry アクセス履歴
     */
    private void recodeHistory(WebHistoryEntry entry) {
        List<WebHistoryEntry> history = Resource.history.getResource();System.err.println(history);
        history.add(entry);
        boolean success = Resource.save(getClass().getResource(Resource.PATH_HISTORY), new ArrayList<>(history));
        System.out.println("save history: " + success);
        if (!success) {
            System.err.println("recoding history error: " + entry);
        }
    }

    /**
     * 履歴を表示
     *
     * @param e ActionEvent
     */
    @FXML
    public void onShowingHistory(ActionEvent e) {
        AbstractSubPaneController.showSubPaneController(this, "/fxml/historyPane.fxml");
    }

    /**
     * URLロード
     *
     * @param search URL
     */
    public void load(String search) {
        final String searchStr;
        if ( !search.matches("^https{0,1}://.+") ) {
            //httpじゃないならgoogle検索を実行
            searchStr = "https://www.google.co.jp/search?q="+search;
        } else {
            searchStr = search;
        }

        currentWebView().ifPresentOrElse(
                webView -> webView.getEngine().load(searchStr),
                () -> loadNewTab(searchStr)
        );
    }

    /**
     * 新規タブでURLロード
     *
     * @param search URL
     */
    public void loadNewTab(String search) {

        if (search == null || search.isEmpty()) {
            return;
        }

        // 新規タブ作成
        Tab tab = new Tab(search);
        WebView view = new WebView();
        WebEngine engine = view.getEngine();

        //ページロードイベントでアドレスバーの更新
        engine.getLoadWorker().stateProperty().addListener(
                (ov, oldState, newState) -> {
                    if (newState == State.SUCCEEDED) {
                        addressBar.setText(engine.getLocation());
                        tab.setText(engine.getTitle().substring(0, Math.min(engine.getTitle().length(), 15)));

                        recodeHistory(new WebHistoryEntry(engine.getLocation(), engine.getTitle(), LocalDateTime.now()));
                    }
                }
        );

        // URLロード
        engine.load(search);

        // 作成したタブを追加
        tab.setContent(view);
        tabPane.getTabs().add(tab);

        // 新規タブを表示
        tabPane.getSelectionModel().select(tab);
    }

    /**
     * 現在選択されているタブのWebViewを返す
     *
     * @return 選択されているWebView
     */
    private Optional<WebView> currentWebView() {
        return Optional.ofNullable(tabPane.getSelectionModel().getSelectedItem())
                .map(Tab::getContent)
                .filter(n -> n instanceof WebView)
                .map(n -> (WebView)n);
    }
}
