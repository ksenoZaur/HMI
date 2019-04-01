package sample;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static Stage primary;

    @Override
    public void start(Stage primaryStage) throws Exception{

        Main.primary = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("lab3HMI.fxml"));
        primaryStage.setTitle("Форма ОП-13");
        Scene scene = new Scene(root, 1000, 500);
        primaryStage.setMinWidth(1000);
        primaryStage.setMinHeight(720);
//        primaryStage.widthProperty().addListener(new ChangeListener() {
//            @Override
//            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
//            }
//        });
        scene.getStylesheets().add("css/style.css");
        primaryStage.setScene( scene );
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
