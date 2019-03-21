package sample;

import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

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
    public TableView table;

    public TableColumn<Document, String> numCol;
    public TableColumn<Document, String> titleCol;
    public TableColumn codeCol;
    public TableColumn balanceCol1;
    public TableColumn postupiloCol;
    public TableColumn balanceCol2;
    public TableColumn costsCol;

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
//        this.institution.getSelectionModel().select( 0 );

        for( String current: this.unitConten ){

            this.unit.getItems().add( current );

        }
//        this.unit.getSelectionModel().select( 0 );

        // ==== NUMBER (TEXT FIELD) ===
        this.numCol.setCellValueFactory(new PropertyValueFactory<>("number"));
        this.numCol.setCellFactory(TextFieldTableCell.<Document> forTableColumn());


        // ==== GENDER (COMBO BOX) ===

        ObservableList<String> nameList = FXCollections.observableArrayList(Document.values());

        this.titleCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Document, String>, ObservableValue<String>>() {

            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Document, String> param) {
                Document person = param.getValue();
                // F,M
                String genderCode = person.getNumber();
//                Gender gender = Gender.getByCode(genderCode);
                return new SimpleObjectProperty<String>(genderCode);//new SimpleObjectProperty<Gender>(gender);
            }
        });

        this.titleCol.setCellFactory(ComboBoxTableCell.forTableColumn(nameList));

        this.titleCol.setOnEditCommit((TableColumn.CellEditEvent<Document, String> event) -> {
            TablePosition<Document, String> pos = event.getTablePosition();

            String title = event.getNewValue();

            int row = pos.getRow();
            Document person = event.getTableView().getItems().get(row);

            person.setNumber(title);
        });



        this.addNewLine();
    }

    public Controller(){

        this.institutionContent = this.readFile("src/directory/organization");
        this.unitConten = this.readFile("src/directory/unit");

    }

    public void addLineAction(ActionEvent actionEvent) {

        this.addNewLine();

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

    private ArrayList<String> readFile(String path){

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

    private void addNewLine() {

        if( Document.counter < Document.MAX_SZIE ) {

            this.table.getItems().add(new Document(String.valueOf(Document.counter++)));

        }

    }
}



