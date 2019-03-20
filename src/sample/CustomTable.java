package sample;

import javafx.scene.control.TableColumn;

import java.util.ArrayList;


/**
 *  Класс для представления таблицы в виде 2-ух частей: шапки и тела.
 * */
public class CustomTable {

    // Fields
    private ArrayList<TableColumn> header;
    private ArrayList<TableColumn> column;

    public CustomTable( int numberColumn ) {

        this.header = new ArrayList<>();
        this.column = new ArrayList<>();

        TableColumn buffer;

        for( int i = 0; i < numberColumn; i++ ){

            buffer = new TableColumn("title".concat(String.valueOf(i)));
            this.header.add( buffer );

            buffer = new TableColumn();
            this.column.add( buffer );
        }

    }

    // Methods


    public ArrayList<TableColumn> getHeader() {
        return header;
    }

    public ArrayList<TableColumn> getColumn() {
        return column;
    }
}
