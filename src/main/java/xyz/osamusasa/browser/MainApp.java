package xyz.osamusasa.browser;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            //fxmlとcssを読み込んで画面表示
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/mainPane.fxml"));
            BorderPane root = loader.load();

            // Stage受け渡し
            Controller controller = loader.getController();
            controller.setStage(primaryStage);

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