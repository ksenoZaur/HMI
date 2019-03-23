package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControllerMod {

    public ComboBox profession;
    public TextField fio2;
    public TextField fio1;

    @FXML
    private ComboBox<?> bulgalterPerson;

    @FXML
    private ComboBox<String> person;

    @FXML
    private ComboBox<String> position;

    @FXML
    private Button save;

    @FXML
    private Button abbort;

    /**
     *  Метод по закрытию модального окна.
     *  При закрытии сбрасываются значения полей формы и обьекта.
     */
    @FXML
    void abbortAction(ActionEvent event) {

        //TODO Обдумать мозможность и целесообразность сохранения значения полей
        Controller.self.getDocumnet().setFio1( null );
        Controller.self.getDocumnet().setFio2( null );
        Controller.self.getDocumnet().setPosition( null );

        Stage stage = (Stage) abbort.getScene().getWindow();
        stage.close();

    }

    @FXML
    void saveAction(ActionEvent event) {
        // TODO выгрузка в excel



        this.abbortAction( event );
    }

    @FXML
    void initialize() {

        // Заполняем комбобокс списком должностей
        for( String current: Controller.self.readFile("src/directory/professions") ){

            this.profession.getItems().add( current );

        }

    }

    public void professionAction(ActionEvent actionEvent) {

        // Меняем должность
        Controller.self.getDocumnet().setPosition( (String)this.profession.getSelectionModel().getSelectedItem() );

    }

    public void fio2Action(ActionEvent actionEvent) {

        Controller.self.getDocumnet().setFio2( this.fio2.getText() );
    }

    public void fio1Action(ActionEvent actionEvent) {

        Controller.self.getDocumnet().setFio1( this.fio1.getText() );

    }
}