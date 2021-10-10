package xyz.osamusasa.browser.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import lombok.Getter;

public class MenubarController {
    @Getter
    @FXML
    private Menu windowMenu;

    /**
     * 初期化処理(自動呼出し)
     *
     * initialize()メソッドは自動呼出しの対象となる
     */
    public void initialize() {

    }
    public void onNew(ActionEvent actionEvent) {
        System.out.println("on new window");
    }

    public void onNewTab(ActionEvent actionEvent) {
        System.out.println("on new tab");
    }

    public void onOpenFile(ActionEvent actionEvent) {
        System.out.println("on open file");
    }

    public void onCloseTab(ActionEvent actionEvent) {
        System.out.println("on close tab");
    }

    public void onCloseWindow(ActionEvent actionEvent) {
        System.out.println("on close window");
    }

    public void onSave(ActionEvent actionEvent) {
        System.out.println("on save");
    }

    public void onPrint(ActionEvent actionEvent) {
        System.out.println("on print");
    }

    public void onRedo(ActionEvent actionEvent) {
        System.out.println("on redo");
    }

    public void onUndo(ActionEvent actionEvent) {
        System.out.println("on undo");
    }

    public void onCut(ActionEvent actionEvent) {
        System.out.println("on cut");
    }

    public void onCopy(ActionEvent actionEvent) {
        System.out.println("on copy");
    }

    public void onPaste(ActionEvent actionEvent) {
        System.out.println("on paste");
    }

    public void onSelectAll(ActionEvent actionEvent) {
        System.out.println("on select all");
    }

    public void onShowAllHistory(ActionEvent actionEvent) {
        System.out.println("on show all history");
    }

    public void onDeleteHistory(ActionEvent actionEvent) {
        System.out.println("on delete history");
    }

    public void onShowBookmark(ActionEvent actionEvent) {
        System.out.println("on show bookmark");
    }

    public void onAddBookmark(ActionEvent actionEvent) {
        System.out.println("on add bookmark");
    }

    public void onDummy(ActionEvent actionEvent) {
        System.out.println("on dummy");
    }
}
