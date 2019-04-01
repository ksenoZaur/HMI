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
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
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
    public ScrollPane dog;
    public AnchorPane cat;
    public TextField itogoMain;
    public HBox hboxTest;
    public TextField itogo1;
    private Document document;
    public static Stage stage;

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
        this.itogo1.setText( String.valueOf( 0.0 ));
        this.document.setItogo(0.0);

        this.minTable.widthProperty().addListener((source, oldWidth, newWidth) ->   {
            TableHeaderRow header = (TableHeaderRow) minTable.lookup("TableHeaderRow");
            header.reorderingProperty().addListener((observable, oldValue, newValue) -> header.setReordering(false));
        });

        this.table.widthProperty().addListener((source, oldWidth, newWidth) -> {
            TableHeaderRow header = (TableHeaderRow) table.lookup("TableHeaderRow");
            header.reorderingProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    header.setReordering(false);
                }
            });
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
            this.setItogoBlud();
        });

        this.minTableCol2.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        this.minTableCol2.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

        this.primaryStage = Main.primary;
        this.numberVedomosti.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
//                if (!newValue.matches("\\d*")) {
//                    numberVedomosti.setText(newValue.replaceAll("[^\\d]", ""));
//                }
            }
        });

        this.priceField1.textProperty().addListener( new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {


                if(!newValue.matches("\\d*(\\.\\d{0,2})?")){

                    priceField1.setText(newValue.replaceAll("[^\\d\\.]",""));

                }

            }

        });
        this.priceField2.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {

                if(!newValue.matches("\\d*(\\.\\d{0,2})?")){

                    priceField2.setText(newValue.replaceAll("[^\\d\\.]",""));

                }
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
                    this.document.setCostOfSpices( priceField1.getText());
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
                this.document.setSaltCost( priceField2.getText());
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

        Double test = 1.0;
        Double test2;

        // ==== NUMBER (TEXT FIELD) ===
        this.numCol.setCellValueFactory(new PropertyValueFactory<>("number"));
        this.numCol.setCellFactory(TextFieldTableCell.<Notation, Integer>forTableColumn(new IntegerStringConverter()));

        test2 = 1/12.0;
        test -= test2;

        this.numCol.prefWidthProperty().bind(this.table.widthProperty().multiply(test2));

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

        test2 = 2/12.0;
        test -= test2;

        this.titleCol.prefWidthProperty().bind(this.table.widthProperty().multiply(test2));

        // ==== CODE (TEXT FIELD) ===
        this.codeCol.setCellValueFactory( new PropertyValueFactory<>("code"));
        this.codeCol.setCellFactory( TextFieldTableCell.<Notation>forTableColumn()  );
        this.codeCol.setEditable( false );

        test2 = 1/12.0;
        test -= test2;

        this.codeCol.prefWidthProperty().bind(this.table.widthProperty().multiply(test2));
//        this.titleCol.setMaxWidth(this.table.getWidth() * (1/12.0));

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

            if( doc.getBalanceStart() != null &&  doc.getArrived() != null && doc.getBalanceEnd() != null){

                this.table.getItems().get( row ).setUse(
                        this.setLastColumn( doc.getBalanceStart(), doc.getArrived(),doc.getBalanceEnd() ));
                setItogoMain();
                this.table.refresh();

            }
        });

        test2 = 2/12.0;
        test -= test2;

        this.balanceCol1.prefWidthProperty().bind(this.table.widthProperty().multiply(test2));

        // ==== BALANCE2 (TEXT FIELD) ===
        this.balanceCol2.setCellValueFactory(new PropertyValueFactory<>("balanceEnd"));
        this.balanceCol2.setCellFactory(TextFieldTableCell.<Notation, Double>forTableColumn(new DoubleStringConverter()));
        this.balanceCol2.setOnEditCommit((TableColumn.CellEditEvent<Notation, Double> event) -> {

            TablePosition<Notation, Double> pos = event.getTablePosition();
            Double newEndBalance = event.getNewValue();
            int row = pos.getRow();
            Notation doc = event.getTableView().getItems().get(row);
            doc.setBalanceEnd( newEndBalance );

            if( doc.getBalanceStart() != null &&  doc.getArrived() != null && doc.getBalanceEnd() != null){

                this.table.getItems().get( row ).setUse(
                        this.setLastColumn( doc.getBalanceStart(), doc.getArrived(),doc.getBalanceEnd() ));
                setItogoMain();
                this.table.refresh();

            }

        });

        test2 = 2/12.0;
        test -= test2;

        this.balanceCol2.prefWidthProperty().bind(this.table.widthProperty().multiply(test2));

        // ==== ARRIVED (TEXT FIELD) ===
        this.postupiloCol.setCellValueFactory(new PropertyValueFactory<>("arrived"));
        this.postupiloCol.setCellFactory(TextFieldTableCell.<Notation, Double>forTableColumn(new DoubleStringConverter()));
        this.postupiloCol.setOnEditCommit((TableColumn.CellEditEvent<Notation, Double> event) -> {

            TablePosition<Notation, Double> pos = event.getTablePosition();
            Double newArrived = event.getNewValue();
            int row = pos.getRow();
            Notation doc = event.getTableView().getItems().get(row);
            doc.setArrived( newArrived );

            if( doc.getBalanceStart() != null &&  doc.getArrived() != null && doc.getBalanceEnd() != null){

                this.table.getItems().get( row ).setUse(
                        this.setLastColumn( doc.getBalanceStart(), doc.getArrived(),doc.getBalanceEnd() ));
                setItogoMain();
                this.table.refresh();

            }

        });

        test2 = 2/12.0;
        test -= test2;

        this.postupiloCol.prefWidthProperty().bind(this.table.widthProperty().multiply(test2));

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


        test2 = Math.max(test, 2/12.0) - 0.0025;

        this.costsCol.prefWidthProperty().bind(this.table.widthProperty().multiply(test2));
        this.itogoMain.prefWidthProperty().bind(this.table.widthProperty().multiply(test2));
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
            responsiblePerson.setOnCloseRequest(event -> {
                Controller.self.getDocumnet().setFio1( null );
                Controller.self.getDocumnet().setFio2( null );
                Controller.self.getDocumnet().setPosition( null );

                stage.close();
            });

        Controller.stage = responsiblePerson;

//        responsiblePerson.setOnCloseRequest(e->e.consume());

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

        if(  Notation.counter == 1 ){

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
                if( !this.priceField1.getText().isEmpty() ){

                    double totalPrice = Double.valueOf( this.priceField1.getText() ) *
                            this.minTable.getItems().get( 0 ).getNumberOfDishes();

                    this.minTable.getItems().get( indexRow ).setTotalPrice( Math.round( totalPrice * 100.0 ) / 100.0);
                    this.setItogo();
                    this.minTable.refresh();

                }
                break;

            case 1:
                if ( !this.priceField2.getText().isEmpty() ){

                    double totalPrice = Double.valueOf( this.priceField2.getText() ) *
                            this.minTable.getItems().get( 1 ).getNumberOfDishes();

                    this.minTable.getItems().get( indexRow ).setTotalPrice( Math.round( totalPrice * 100.0 ) / 100.0 );
                    this.setItogo();
                    this.minTable.refresh();
                }
                break;
        }
    }

    public void setItogo(){

        this.itogo.setText( String.valueOf( Math.round( (this.minTable.getItems().get( 0 ).getTotalPrice() +
                this.minTable.getItems().get( 1 ).getTotalPrice()) * 100 )/100.0));

        this.document.setItogo( Double.valueOf( this.itogo.getText() ) );

        this.setNedorashod();

    }

    public void controlAction(ActionEvent actionEvent) {

        this.control.setText( String.valueOf( (Double.valueOf( control.getText() ) * 100 ) / 100 ));
        this.document.setControl( Double.valueOf( this.control.getText() ) );
        setNedorashod();
    }

    public void setNedorashod() {
        try {
            Double.valueOf( this.control.getText());

            this.nedorashod.setText(String.valueOf( Math.round( (Double.valueOf(this.itogo.getText()) -
                    Double.valueOf(this.control.getText())) * 100)/100.0));

            this.document.setSumma( Double.valueOf( this.nedorashod.getText() ) );

        } catch ( Exception ex) {
            System.out.println("Ошибка!");
        }
    }


    public double setLastColumn(double start, double add, double end){
        return start + add - end;
    }

    public void setItogoMain(){

        double total = 0.0;

        for( Notation notation: this.table.getItems()) {
            if( notation.getUse() != null ) {
                total += notation.getUse();
            }
        }

        this.itogoMain.setText( String.valueOf( total ));
        this.control.setText( String.valueOf( total ));
        this.controlAction( new ActionEvent());
    }

    public void itogoMainAction(ActionEvent actionEvent) {}

    public void setItogoBlud(){

        Double totalBlud = 0.0;
        for( NotationMinTable notationMinTable: this.minTable.getItems()){
            totalBlud += notationMinTable.getNumberOfDishes();
        }

        this.itogo1.setText( String.valueOf( totalBlud ));
        this.document.setItogoBlud( totalBlud );
    }
}