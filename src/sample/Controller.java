package sample;

import com.jfoenix.controls.JFXTextField;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

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

    public TableColumn<Document, Integer> numCol;
    public TableColumn<Document, String> titleCol;
    public TableColumn<Document, String> codeCol;
    public TableColumn<Document, Double> balanceCol1;
    public TableColumn<Document, Double> postupiloCol;
    public TableColumn<Document, Double> balanceCol2;
    public TableColumn<Document, Double> costsCol;

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
        this.numCol.setCellFactory(TextFieldTableCell.<Document, Integer>forTableColumn(new IntegerStringConverter()));


        // ==== TITLE (COMBO BOX) ===
        ObservableList<String> titleList = FXCollections.observableArrayList(Document.codeList().keySet());
        this.titleCol.setCellValueFactory( new PropertyValueFactory<>("title"));
        this.titleCol.setCellFactory(new Callback<TableColumn<Document, String>, TableCell<Document, String>>() {
            @Override
            public TableCell<Document, String> call(TableColumn<Document, String> param) {

                final ComboBox<String> comboBox = new ComboBox<>(titleList);

                comboBox.setMaxWidth( Integer.MAX_VALUE );
                comboBox.setPrefWidth( titleCol.getWidth() );

                TableCell<Document, String> cell = new TableCell<Document, String>() {
                    @Override
                    protected void updateItem(String reason, boolean empty) {
                        super.updateItem(reason, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            comboBox.setValue(reason);
                            setGraphic(comboBox);
                        }
                    }
                };

                comboBox.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
    //                    table.getItems().get( cell.getIndex() )
                        Document a = (Document) table.getItems().get(cell.getIndex());
                        a.setTitle( comboBox.getSelectionModel().getSelectedItem() );
                        ((Document) table.getItems().get( cell.getIndex() )).setCode(Document.codeList().get( a.getTitle() ));
                        table.refresh();
                    }
                });
                return cell ;
            }
        });

        // ==== CODE (TEXT FIELD) ===
        this.codeCol.setCellValueFactory( new PropertyValueFactory<>("code"));
        this.codeCol.setCellFactory( TextFieldTableCell.<Document>forTableColumn()  );
        this.codeCol.setEditable( false );

        // TODO Сделать проверку на ввод не денежной суммы ( формат //d*.//d{2} );
        //  Применение не только по нажатию Enter

        // ==== BALANCE1 (TEXT FIELD) ===
        this.balanceCol1.setCellValueFactory(new PropertyValueFactory<>("balanceStart"));
        this.balanceCol1.setCellFactory(TextFieldTableCell.<Document, Double>forTableColumn(new DoubleStringConverter()));
        this.balanceCol1.setOnEditCommit((TableColumn.CellEditEvent<Document, Double> event) -> {
            TablePosition<Document, Double> pos = event.getTablePosition();

            Double newStartBalance = event.getNewValue();

            int row = pos.getRow();
            Document doc = event.getTableView().getItems().get(row);

            doc.setBalanceStart( newStartBalance );
        });

        // ==== BALANCE2 (TEXT FIELD) ===
        this.balanceCol2.setCellValueFactory(new PropertyValueFactory<>("balanceEnd"));
        this.balanceCol2.setCellFactory(TextFieldTableCell.<Document, Double>forTableColumn(new DoubleStringConverter()));
        this.balanceCol2.setOnEditCommit((TableColumn.CellEditEvent<Document, Double> event) -> {

            TablePosition<Document, Double> pos = event.getTablePosition();
            Double newEndBalance = event.getNewValue();
            int row = pos.getRow();
            Document doc = event.getTableView().getItems().get(row);
            doc.setBalanceEnd( newEndBalance );

        });


        // ==== USE (TEXT FIELD) ===
        this.postupiloCol.setCellValueFactory(new PropertyValueFactory<>("arrived"));
        this.postupiloCol.setCellFactory(TextFieldTableCell.<Document, Double>forTableColumn(new DoubleStringConverter()));
        this.postupiloCol.setOnEditCommit((TableColumn.CellEditEvent<Document, Double> event) -> {

            TablePosition<Document, Double> pos = event.getTablePosition();
            Double newArrived = event.getNewValue();
            int row = pos.getRow();
            Document doc = event.getTableView().getItems().get(row);
            doc.setArrived( newArrived );

        });

        // ==== ARRIVED (TEXT FIELD) ===
        this.costsCol.setCellValueFactory(new PropertyValueFactory<>("use"));
        this.costsCol.setCellFactory(TextFieldTableCell.<Document, Double>forTableColumn(new DoubleStringConverter()));
        this.costsCol.setOnEditCommit((TableColumn.CellEditEvent<Document, Double> event) -> {

            TablePosition<Document, Double> pos = event.getTablePosition();
            Double newUse = event.getNewValue();
            int row = pos.getRow();
            Document doc = event.getTableView().getItems().get(row);
            doc.setUse( newUse );

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

            this.table.getItems().add(new Document(Document.counter++));

        }

    }
}


    // ==== GENDER (COMBO BOX) ===
//    ObservableList<String> titleList = FXCollections.observableArrayList(Document.values());
//
//        this.titleCol.setCellValueFactory(i -> {
//final StringProperty value = new SimpleStringProperty( i.getValue().getTitle() );
//        // binding to constant value
//        return Bindings.createObjectBinding(() -> value);
//        });
//
//        this.titleCol.setCellFactory(col -> {
//        TableCell<Document, StringProperty> c = new TableCell<>();
//final ComboBox<String> comboBox = new ComboBox<>(titleList);
//
//        comboBox.setOnAction(new EventHandler<ActionEvent>() {
//@Override
//public void handle(ActionEvent event) {
//
//        Document a = (Document) table.getItems().get(0);
//        a.setTitle( comboBox.getSelectionModel().getSelectedItem() );
//        }
//        });
//        c.itemProperty().addListener((observable, oldValue, newValue) -> {
//        if (oldValue != null) {
//        comboBox.valueProperty().unbindBidirectional(oldValue);
//        }
//        if (newValue != null) {
//        comboBox.valueProperty().bindBidirectional(newValue);
//        }
//        });
//        c.graphicProperty().bind(Bindings.when(c.emptyProperty()).then((Node) null).otherwise(comboBox));
//        return c;
//        });



