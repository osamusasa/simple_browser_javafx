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

import java.util.Optional;

public class Controller {
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
    private void loadNewTab(String search) {

        // 新規タブ作成
        Tab tab = new Tab(search);
        WebView view = new WebView();
        WebEngine engine = view.getEngine();

        //ページロードイベントでアドレスバーの更新
        engine.getLoadWorker().stateProperty().addListener(
                (ov, oldState, newState) -> {
                    if (newState == State.SUCCEEDED) {
                        addressBar.setText(engine.getLocation());
                        tab.setText(engine.getTitle());
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
