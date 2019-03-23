package sample;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.sun.javafx.scene.control.skin.TableHeaderRow;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Controller {

    public static Controller self;
    private Document document;

    // Поля 2 таба
    public TextField priceField1;
    public TextField priceField2;

    public TextField itogo;
    public TextField control;
    public TextField nedorashod;

    // Колонки малой таблицы
    public TableView<NotationMinTable> minTable;
    public TableColumn<NotationMinTable, Integer> minTableCol1;
    public TableColumn<NotationMinTable, Double> minTableCol2;

    // Шапка
    public JFXTextField numberVedomosti;
    public JFXDatePicker createDate;
    public DatePicker periodStart;
    public DatePicker periodEnd;
    public ComboBox institution;
    public ComboBox unit;

    // Колонки большой таблицы
    public TableView<Notation> table;
    public TableColumn<Notation, Integer> numCol;
    public TableColumn<Notation, String> titleCol;
    public TableColumn<Notation, String> codeCol;
    public TableColumn<Notation, Double> balanceCol1;
    public TableColumn<Notation, Double> postupiloCol;
    public TableColumn<Notation, Double> balanceCol2;
    public TableColumn<Notation, Double> costsCol;

    // Данные для инициализации комбобоксов
    private ArrayList<String> institutionContent;
    private ArrayList<String> unitConten;


    @FXML
    private AnchorPane topPart;

    @FXML
    private ScrollPane bottomPart;

    @FXML
    private Stage primaryStage;

    // Методы
    @FXML
    void initialize() {


        for(NotationMinTable current: this.document.getNotationMinTables() ) {
            this.minTable.getItems().add( current );
        }

        this.itogo.setText( String.valueOf( 0.0 ));

        this.minTable.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> source, Number oldWidth, Number newWidth)
            {
                TableHeaderRow header = (TableHeaderRow) minTable.lookup("TableHeaderRow");
                header.reorderingProperty().addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        header.setReordering(false);
                    }
                });
            }
        });

        this.table.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> source, Number oldWidth, Number newWidth)
            {
                TableHeaderRow header = (TableHeaderRow) table.lookup("TableHeaderRow");
                header.reorderingProperty().addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        header.setReordering(false);
                    }
                });
            }
        });

        this.minTableCol1.setCellValueFactory(new PropertyValueFactory<>("numberOfDishes"));
        this.minTableCol1.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        this.minTableCol1.setOnEditCommit((TableColumn.CellEditEvent<NotationMinTable, Integer> event) -> {
            TablePosition<NotationMinTable, Integer> pos = event.getTablePosition();

            Integer numberOfDishes = event.getNewValue();

            int row = pos.getRow();

            NotationMinTable notMin = event.getTableView().getItems().get(row);
            notMin.setNumberOfDishes( numberOfDishes );

            this.setTotalPrice( row );
        });

        this.minTableCol2.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        this.minTableCol2.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

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

        // ==== NUMBER (TEXT FIELD) ===
        this.numCol.setCellValueFactory(new PropertyValueFactory<>("number"));
        this.numCol.setCellFactory(TextFieldTableCell.<Notation, Integer>forTableColumn(new IntegerStringConverter()));


        // ==== TITLE (COMBO BOX) ===
        ObservableList<String> titleList = FXCollections.observableArrayList(Notation.codeList().keySet());
        this.titleCol.setCellValueFactory( new PropertyValueFactory<>("title"));
        this.titleCol.setCellFactory(new Callback<TableColumn<Notation, String>, TableCell<Notation, String>>() {
            @Override
            public TableCell<Notation, String> call(TableColumn<Notation, String> param) {

                final ComboBox<String> comboBox = new ComboBox<>(titleList);

                comboBox.setMaxWidth( Integer.MAX_VALUE );
                comboBox.setPrefWidth( titleCol.getWidth() );

                TableCell<Notation, String> cell = new TableCell<Notation, String>() {
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
                        Notation a = (Notation) table.getItems().get(cell.getIndex());
                        a.setTitle( comboBox.getSelectionModel().getSelectedItem() );
                        ((Notation) table.getItems().get( cell.getIndex() )).setCode(Notation.codeList().get( a.getTitle() ));
                        table.refresh();
                    }
                });
                return cell ;
            }
        });

        // ==== CODE (TEXT FIELD) ===
        this.codeCol.setCellValueFactory( new PropertyValueFactory<>("code"));
        this.codeCol.setCellFactory( TextFieldTableCell.<Notation>forTableColumn()  );
        this.codeCol.setEditable( false );

        // TODO Сделать проверку на ввод не денежной суммы ( формат //d*.//d{2} );
        //  Применение не только по нажатию Enter

        // ==== BALANCE1 (TEXT FIELD) ===
        this.balanceCol1.setCellValueFactory(new PropertyValueFactory<>("balanceStart"));
        this.balanceCol1.setCellFactory(TextFieldTableCell.<Notation, Double>forTableColumn(new DoubleStringConverter()));
        this.balanceCol1.setOnEditCommit((TableColumn.CellEditEvent<Notation, Double> event) -> {
            TablePosition<Notation, Double> pos = event.getTablePosition();

            Double newStartBalance = event.getNewValue();

            int row = pos.getRow();
            Notation doc = event.getTableView().getItems().get(row);

            doc.setBalanceStart( newStartBalance );
        });

        // ==== BALANCE2 (TEXT FIELD) ===
        this.balanceCol2.setCellValueFactory(new PropertyValueFactory<>("balanceEnd"));
        this.balanceCol2.setCellFactory(TextFieldTableCell.<Notation, Double>forTableColumn(new DoubleStringConverter()));
        this.balanceCol2.setOnEditCommit((TableColumn.CellEditEvent<Notation, Double> event) -> {

            TablePosition<Notation, Double> pos = event.getTablePosition();
            Double newEndBalance = event.getNewValue();
            int row = pos.getRow();
            Notation doc = event.getTableView().getItems().get(row);
            doc.setBalanceEnd( newEndBalance );

        });


        // ==== ARRIVED (TEXT FIELD) ===
        this.postupiloCol.setCellValueFactory(new PropertyValueFactory<>("arrived"));
        this.postupiloCol.setCellFactory(TextFieldTableCell.<Notation, Double>forTableColumn(new DoubleStringConverter()));
        this.postupiloCol.setOnEditCommit((TableColumn.CellEditEvent<Notation, Double> event) -> {

            TablePosition<Notation, Double> pos = event.getTablePosition();
            Double newArrived = event.getNewValue();
            int row = pos.getRow();
            Notation doc = event.getTableView().getItems().get(row);
            doc.setArrived( newArrived );

        });

        // ==== USE (TEXT FIELD) ===
        this.costsCol.setCellValueFactory(new PropertyValueFactory<>("use"));

        this.costsCol.setCellFactory(TextFieldTableCell.<Notation, Double>forTableColumn(new DoubleStringConverter()));

        this.costsCol.setOnEditCommit((TableColumn.CellEditEvent<Notation, Double> event) -> {

            TablePosition<Notation, Double> pos = event.getTablePosition();
            Double newUse = event.getNewValue();
            int row = pos.getRow();
            Notation doc = event.getTableView().getItems().get(row);
            doc.setUse( newUse );

        });

        this.addNewLine();
    }

    public Controller(){

        Controller.self = this;
        this.document = new Document();
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
        responsiblePerson.setOnCloseRequest(e->e.consume());

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

    public ArrayList<String> readFile(String path){

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

        if(  Notation.counter == 0 ){

            Notation notation = new Notation(Notation.counter++);
            this.document.add( notation );
            this.table.getItems().add( notation );

        } else if( Notation.counter < Document.MAX_ROW &&
                this.table.getItems().get( this.table.getItems().size() - 1).getTitle() != null ) {

            Notation notation = new Notation(Notation.counter++);
            this.document.add( notation );
            this.table.getItems().add( notation );

        }

    }

    public void numberAction(ActionEvent actionEvent) {

        this.document.setStatementNumber( this.numberVedomosti.getText() );

    }

    public void createDateAction(ActionEvent actionEvent) {

        this.document.setCreateDate( this.createDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

    }

    public void periodStartAction(ActionEvent actionEvent) {

        this.document.setPeriodStart( this.periodStart.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

    }

    public void periodEndAction(ActionEvent actionEvent) {

        this.document.setPeriodEnd( this.periodEnd.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

    }

    public void institutionAction(ActionEvent actionEvent) {

        this.document.setOrganization( (String) this.institution.getSelectionModel().getSelectedItem() );

    }

    public void unitAction(ActionEvent actionEvent) {
        this.document.setUnit( (String) this.unit.getSelectionModel().getSelectedItem() );
    }

    public Document getDocumnet() {
        return this.document;
    }

    public void priceField1Action(ActionEvent actionEvent) {

        this.getDocumnet().setCostOfSpices( this.priceField1.getText() );
        this.setTotalPrice( 0 );

    }

    public void priceField2Action(ActionEvent actionEvent) {

        this.getDocumnet().setSaltCost( this.priceField2.getText() );
        this.setTotalPrice( 1 );

    }

    public void setTotalPrice( int indexRow ){

        switch ( indexRow ){

            case 0:
                if( this.priceField1.getText() != null ){

                    double totalPrice = Double.valueOf( this.priceField1.getText() ) *
                            this.minTable.getItems().get( 0 ).getNumberOfDishes();

                    this.minTable.getItems().get( indexRow ).setTotalPrice( totalPrice );
                    this.setItogo();
                    this.minTable.refresh();

                }
                break;

            case 1:
                if ( this.priceField2.getText() != null ){

                    double totalPrice = Double.valueOf( this.priceField2.getText() ) *
                            this.minTable.getItems().get( 1 ).getNumberOfDishes();

                    this.minTable.getItems().get( indexRow ).setTotalPrice( totalPrice );
                    this.setItogo();
                    this.minTable.refresh();
                }
                break;
        }
    }

    public void setItogo(){

        this.itogo.setText( String.valueOf( this.minTable.getItems().get( 0 ).getTotalPrice() +
                this.minTable.getItems().get( 1 ).getTotalPrice()));

        this.setNedorashod();

    }

    public void controlAction(ActionEvent actionEvent) {
        setNedorashod();
    }

    public void setNedorashod() {
        try {
            Double.valueOf( this.control.getText());
            this.nedorashod.setText(String.valueOf(Double.valueOf(this.itogo.getText()) - Double.valueOf(this.control.getText())));
        } catch ( Exception ex) {
            System.out.println("Ошибка!");
        }
    }
}