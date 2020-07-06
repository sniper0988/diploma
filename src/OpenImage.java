import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class OpenImage extends Application {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        VBox root = new VBox(15.0);
        root.setAlignment(Pos.CENTER);

        Button button = new Button("Выполнить");
        button.setOnAction(this::onClickButton);
        root.getChildren().add(button);

        Scene scene = new Scene(root, 400.0, 150.0);
        stage.setTitle("OpenCV " + Core.VERSION);
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> {
            Platform.exit();
        });
        stage.show();
    }

    private void onClickButton(ActionEvent e){
        Mat img = Imgcodecs.imread("sources/tree.jpg");
        if(img.empty()){
            System.out.println("Не удалось загрузить изображение");
            return;
        }

        CvUtilsFX.showImage(img, "Текст в заголовке окна");
    }
}
