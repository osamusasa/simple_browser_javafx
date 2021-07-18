package xyz.osamusasa.browser;

import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;

public class Controller {
    @FXML private TextField addressBar;
    @FXML private WebView webView;

    //初期化処理(自動呼出し)
    //initialize()メソッドは自動呼出しの対象となる
    public void initialize() {
        //ページロードイベントでアドレスバーの更新
        webView.getEngine().getLoadWorker().stateProperty().addListener(
                (ov, oldState, newState) -> {
                    if (newState == State.SUCCEEDED) {
                        addressBar.setText(webView.getEngine().getLocation());
                    }
                }
        );
        load("https://www.google.co.jp/");
    }

    //アドレスバーでEnter
    public void onAddressBarEvent(KeyEvent e) {
        switch(e.getCode()) {
            case ENTER:load(addressBar.getText());break;
            default:
                break;
        }
    }

    //戻るボタン
    public void onBack(ActionEvent e) {
        WebEngine engine = webView.getEngine();
        WebHistory history = engine.getHistory();
        if ( history.getCurrentIndex() > 0 ) history.go(-1);
    }

    //進むボタン
    public void onNext(ActionEvent e) {
        WebEngine engine = webView.getEngine();
        WebHistory history = engine.getHistory();
        if ( history.getCurrentIndex() < history.getEntries().size() - 1 ) history.go(1);
    }

    //URLロード
    public void load(String search) {
        if ( !search.matches("^https{0,1}://.+") ) {
            //httpじゃないならgoogle検索を実行
            search = "https://www.google.co.jp/search?q="+search;
        }

        webView.getEngine().load(search);
    }
}