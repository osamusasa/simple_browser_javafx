package xyz.osamusasa.browser;

import de.jangassen.MenuToolkit;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import xyz.osamusasa.browser.controller.RootController;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            //fxmlとcssを読み込んで画面表示
            FXMLLoader mainPaneLoader = new FXMLLoader(getClass().getResource("/fxml/root.fxml"));
            VBox root = mainPaneLoader.load();

            // Stage受け渡し
            RootController controller = mainPaneLoader.getController();

            controller.setStage(primaryStage);

            MenuToolkit tk = MenuToolkit.toolkit();
            // Create the default Application menu
            Menu defaultApplicationMenu = tk.createDefaultApplicationMenu("my browser");
            // Update the existing Application menu
            MenuToolkit.toolkit().setApplicationMenu(defaultApplicationMenu);

            // Window Menu
            Menu windowMenu = controller.getMenubarController().getWindowMenu();
            windowMenu.getItems().addAll(tk.createMinimizeMenuItem(), tk.createZoomMenuItem(), tk.createCycleWindowsItem(),
                    new SeparatorMenuItem(), tk.createBringAllToFrontItem());
            tk.autoAddWindowMenuItems(windowMenu);

            Scene scene = new Scene(root,800,600);
            scene.getStylesheets().add(getClass().getResource("/css/mainPane.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}