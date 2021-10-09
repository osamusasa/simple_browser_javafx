package xyz.osamusasa.browser;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Setter;

public abstract class AbstractSubPaneController {
    @Setter
    protected Controller parentController;

    /**
     * 親ウィンドウの新しいタブで、ページを読み込む
     *
     * @param url URL
     */
    protected void loadNewTab(String url) {
        parentController.loadNewTab(url);
    }

    public static void showSubPaneController(Controller parent, String resourceUrl) {
        try {
            //fxmlとcssを読み込んで画面表示
            FXMLLoader loader = new FXMLLoader(AbstractSubPaneController.class.getResource(resourceUrl));
            Parent root = loader.load();
            AbstractSubPaneController controller = loader.getController();
            controller.setParentController(parent);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);

            // 親コンポーネントを閉じたら、コンポーネントを閉じる
            parent.getStage().setOnHiding(event -> stage.close());

            stage.showAndWait();
        } catch(Exception exception) {
            exception.printStackTrace();
        }
    }
}
