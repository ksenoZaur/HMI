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
        Scene scene = new Scene(root, 700, 400);
        scene.getStylesheets().add("css/style.css");
        primaryStage.setScene( scene );
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
