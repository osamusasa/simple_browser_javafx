package xyz.osamusasa.browser.controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lombok.Getter;

public class RootController {

    @FXML
    private MenuBar menubar;
    @FXML
    private BorderPane mainPane;
    @Getter
    @FXML
    private MenubarController menubarController;
    @Getter
    @FXML
    private Controller mainPaneController;

    /**
     * 初期化処理(自動呼出し)
     *
     * initialize()メソッドは自動呼出しの対象となる
     */
    public void initialize() {
    }
    public void setStage(Stage stage) {
        mainPaneController.setStage(stage);
    }
}
