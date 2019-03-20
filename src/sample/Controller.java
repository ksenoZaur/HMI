package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Controller {

    @FXML
    private AnchorPane topPart;

    @FXML
    private ScrollPane bottomPart;

    @FXML
    private Stage primaryStage;

    @FXML
    void initialize() {

        this.primaryStage = Main.primary;
        
    }

    public void addLineAction(ActionEvent actionEvent) {

        TableView<Dog> tv = new TableView<>();

        tv.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );

        TableColumn<Dog, String> col = new TableColumn<Dog, String>("FirstName");
        col.setCellValueFactory(new PropertyValueFactory<Dog, String>("firstName"));
        tv.getColumns().add(col);
        tv.setEditable(true);

        col = new TableColumn<Dog, String>("LastName");
        col.setCellValueFactory(new PropertyValueFactory<Dog, String>("lastName"));
        col.setCellFactory(TextFieldTableCell.forTableColumn());

        col.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Dog, String>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<Dog, String> event) {

                System.out.println(tv.getItems().get(1).getLastName());
            }
        });

        tv.getColumns().add(col);

        for (int i = 0; i < 30; i++) {
            tv.getItems().add(new Dog("Test" + i, "Test" + i));
        }


        AnchorPane secondaryLayout = new AnchorPane();
        secondaryLayout.getChildren().add(tv);

        Scene secondScene = new Scene(secondaryLayout, 300, 300);
        secondScene.getStylesheets().add("css/style.css");

        // New window (Stage)
        Stage newWindow = new Stage();
        newWindow.setTitle("Show image");
        newWindow.setScene(secondScene);

        // Set position of second window, related to primary window.
        newWindow.setX(Main.primary.getX() + 200);
        newWindow.setY(Main.primary.getY() + 100);

        newWindow.show();

    }
}




