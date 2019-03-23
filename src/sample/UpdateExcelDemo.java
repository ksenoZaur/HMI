package sample;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.*;
import java.util.HashMap;

public class UpdateExcelDemo {

    public final static File file = new File("src/directory/sample.XLS");
    private File resultFile;
    private Document document;


    public UpdateExcelDemo( Document document){

        this.document = document;

    }

    public void write(){

        try {

            FileInputStream inputStream = new FileInputStream( this.resultFile );

            // Get the workbook instance for XLS file
            HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
            // Get first sheet from the workbook
            HSSFSheet sheet = workbook.getSheetAt(0);

            HSSFCell cell;
            // Записываем номер документа
            if(  this.document.getStatementNumber() != null ) {

                cell = sheet.getRow(15).getCell(16);
                cell.setCellValue(this.document.getStatementNumber());

            }

            // Записываем дату создания
            if( this.document.getCreateDate() != null ) {

                cell = sheet.getRow(15).getCell(22);
                cell.setCellValue(this.document.getCreateDate());

            }

            // Отчетный период с
            if(  this.document.getPeriodStart() != null ) {

                cell = sheet.getRow(15).getCell(28);
                cell.setCellValue(this.document.getPeriodStart());

            }

            // Отчетный период по
            if( this.document.getPeriodEnd() != null ) {

                cell = sheet.getRow(15).getCell(32);
                cell.setCellValue(this.document.getPeriodEnd());

            }

            // Организация
            if( this.document.getOrganization() != null ) {
                cell = sheet.getRow(5).getCell(0);
                cell.setCellValue(this.document.getOrganization());
            }

            // Подразделение
            if( this.document.getUnit() != null ) {
                cell = sheet.getRow(7).getCell(0);
                cell.setCellValue(this.document.getUnit());
            }

            // Должность
            if( this.document.getPosition() != null ) {
                cell = sheet.getRow(48).getCell(15);
                cell.setCellValue(this.document.getPosition());
            }

            // ФИО1
            if( this.document.getFio1() != null ) {
                cell = sheet.getRow(48).getCell(33);
                cell.setCellValue(this.document.getFio1());
            }

            // ФИО2
            if( this.document.getFio2() != null ) {
                cell = sheet.getRow(50).getCell(15);
                cell.setCellValue(this.document.getFio2());
            }

            // Цена вверху
            if( this.document.getCostOfSpices() != null ) {

                String[] coast = this.document.getCostOfSpices().split("\\.");

                cell = sheet.getRow(39).getCell(2);
                cell.setCellValue( coast[ 0 ] );

                cell = sheet.getRow(39).getCell(19);
                cell.setCellValue( coast[ 1 ] );
            }

            // Цена внизу
            if( this.document.getSaltCost() != null ) {

                String[] coast = this.document.getSaltCost().split("\\.");

                cell = sheet.getRow(42).getCell(2);
                cell.setCellValue( coast[ 0 ] );

                cell = sheet.getRow(42).getCell(19);
                cell.setCellValue( coast[ 1 ] );
            }

            if( this.document.getItogo() != null ){

                cell = sheet.getRow(44).getCell(37);
                cell.setCellValue(  this.document.getItogo() );

            }

            if( this.document.getControl() != null ){

                cell = sheet.getRow(45).getCell(37);
                cell.setCellValue(  this.document.getControl() );

            }

            if( this.document.getControl() != null ){

                cell = sheet.getRow(46).getCell(37);
                cell.setCellValue(  this.document.getSumma() );

            }

            if (this.document.getNotations() != null) {

                Double totalBalanceStart = 0.0;
                Double totalBalanceEnd = 0.0;
                Double totalUse = 0.0;
                Double totalArrived = 0.0;


                int row = 24;
                for( Notation current: this.document.getNotations() ) {

                    if( current.getNumber() != null ) {

                        cell = sheet.getRow(row).getCell(0);
                        cell.setCellValue(  current.getNumber() );

                    }

                    if( current.getTitle() != null ) {

                        cell = sheet.getRow(row).getCell(4);
                        cell.setCellValue(  current.getTitle() );

                    }

                    if( current.getCode() != null ) {

                        cell = sheet.getRow(row).getCell(18);
                        cell.setCellValue(  current.getCode() );

                    }

                    if( current.getBalanceStart() != null ) {

                        cell = sheet.getRow(row).getCell(22);
                        cell.setCellValue(  current.getBalanceStart() );
                        totalBalanceStart += current.getBalanceStart();
                    }

                    if( current.getArrived() != null ) {

                        cell = sheet.getRow(row).getCell(29);
                        cell.setCellValue(  current.getArrived() );
                        totalArrived += current.getArrived();

                    }

                    if( current.getBalanceEnd() != null ) {

                        cell = sheet.getRow(row).getCell(36);
                        cell.setCellValue(  current.getBalanceEnd() );
                        totalBalanceEnd += current.getBalanceEnd();
                    }

                    if( current.getUse() != null ) {

                        cell = sheet.getRow(row).getCell(44);
                        cell.setCellValue(  current.getUse() );
                        totalUse += current.getUse();

                    }

                    row++;
                }

                cell = sheet.getRow(31).getCell(22);
                cell.setCellValue(  totalBalanceStart );

                cell = sheet.getRow(31).getCell(29);
                cell.setCellValue(  totalArrived );

                cell = sheet.getRow(31).getCell(36);
                cell.setCellValue(  totalBalanceEnd );

                cell = sheet.getRow(31).getCell(44);
                cell.setCellValue(  totalUse );

                row = 38;
                for( NotationMinTable current: this.document.getNotationMinTables() ) {

                    cell = sheet.getRow( row ).getCell( 30 );
                    cell.setCellValue( current.getNumberOfDishes() );

                    cell = sheet.getRow( row ).getCell( 37 );
                    cell.setCellValue( current.getTotalPrice() );

                    row += 3;
                }
            }

            inputStream.close();

            // Write File
            FileOutputStream out = new FileOutputStream(file);
            workbook.write(out);
            out.close();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setResultFile(File resultFile) {
        this.resultFile = resultFile;
    }
}
