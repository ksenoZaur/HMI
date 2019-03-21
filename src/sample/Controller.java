package sample;

import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Controller {

    public JFXTextField numberVedomosti;
    public TextField priceField1;
    public TextField priceField2;
    public ComboBox institution;
    public ComboBox unit;

    private ArrayList<String> institutionContent;
    private ArrayList<String> unitConten;


    @FXML
    private AnchorPane topPart;

    @FXML
    private ScrollPane bottomPart;

    @FXML
    private Stage primaryStage;

    @FXML
    void initialize() {

        this.primaryStage = Main.primary;
        this.numberVedomosti.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    numberVedomosti.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        this.priceField1.textProperty().addListener( new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {


                if(!newValue.matches("\\d*(\\.\\d{0,2})?")){

                    priceField1.setText(newValue.replaceAll("[^\\d\\.]",""));

                }


//                if(!newValue.matches("\\d*(\\.\\d{0,2})?")){
//                    priceField1.setText(newValue.substring(0,newValue.length()-1));
//                }
            }

        });
        this.priceField2.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {

                if(!newValue.matches("\\d*(\\.\\d{0,2})?")){

                    priceField2.setText(newValue.replaceAll("[^\\d\\.]",""));

                }

//                if(!priceField2.getText().matches("\\d*(\\.\\d{0,2})?")){
//
//                    priceField2.setText(priceField2.getText().substring(0,newValue.length()-1));
//
//                }
            }
        });

        this.priceField1.focusedProperty().addListener( (obs, oldValue, newValue) -> {

            if ( !newValue ) {

                String tmp = this.priceField1.getText();
                if (!tmp.trim().isEmpty())
                    if (tmp.matches("\\d+\\.\\z")) {

                        this.priceField1.setText(tmp.replaceFirst("\\.\\z", ".00"));

                    } else if (tmp.matches("\\d+\\.\\d\\z")) {

                        this.priceField1.setText(tmp.concat("0"));

                    } else if (tmp.matches("\\d+\\z")) {

                        this.priceField1.setText(tmp.concat(".00"));

                    } else if (tmp.matches("\\d+\\.\\d{3,}\\z")) {

                        priceField1.setText( tmp.substring(0, tmp.indexOf(".")+3));

                    } else if ( tmp.matches("^\\.\\d{2,}\\z") ) {

                        priceField1.setText( "0".concat( tmp ));

                    }
//                "\\d+\\.\\d{2}\\z"
            }
        });

        this.priceField2.focusedProperty().addListener( (obs, oldValue, newValue) -> {

            if ( !newValue ) {

                String tmp = this.priceField2.getText();
                if (!tmp.trim().isEmpty())
                    if (tmp.matches("\\d+\\.\\z")) {

                        this.priceField2.setText(tmp.replaceFirst("\\.\\z", ".00"));

                    } else if (tmp.matches("\\d+\\.\\d\\z")) {

                        this.priceField2.setText(tmp.concat("0"));

                    } else if (tmp.matches("\\d+\\z")) {

                        this.priceField2.setText(tmp.concat(".00"));

                    } else if (tmp.matches("\\d+\\.\\d{3,}\\z")) {

                        priceField2.setText( tmp.substring(0, tmp.indexOf(".")+3));

                    } else if ( tmp.matches("^\\.\\d{2,}\\z") ) {

                        priceField2.setText( "0".concat( tmp ));

                    }
//                "\\d+\\.\\d{2}\\z"
            }
        });

        for( String current: this.institutionContent ){

            this.institution.getItems().add( current );

        }

        for( String current: this.unitConten ){

            this.unit.getItems().add( current );

        }
    }

    public Controller(){

        this.institutionContent = this.readFile("/src/directory/organization");
        this.unitConten = this.readFile("/src/directory/unit");

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

    public void linkAction(ActionEvent actionEvent) {

        try {

        Parent root = FXMLLoader.load(getClass().getResource("lab3HMI2.fxml"));

        Stage responsiblePerson = new Stage();

        // Set position of second window, related to primary window.
        responsiblePerson.setX(Main.primary.getX() + 200);
        responsiblePerson.setY(Main.primary.getY() + 100);

        responsiblePerson.setResizable( false );
        responsiblePerson.initModality(Modality.APPLICATION_MODAL);

        responsiblePerson.setTitle("Сохранение");

        responsiblePerson.setScene(new Scene(root, 470, 275));
        responsiblePerson.show();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    public ArrayList<String> readFile( String path ){

        ArrayList<String> tmp = new ArrayList<>();

        try{

            FileInputStream fstream = new FileInputStream( path );
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;

            while ((strLine = br.readLine()) != null){
                tmp.add( strLine );
            }

        }catch (IOException e){

            System.out.println("Ошибка чтения файла");

        }

        return  tmp;
    }
}



