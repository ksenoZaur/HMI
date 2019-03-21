package sample;

import java.io.*;
import java.util.ArrayList;

/**
 *  Класс представляющий документ - форму ОП-13
 *
 * */
public class Document {

    // Fields
    private String number;
    private String title;
    private String code;
    private String balanceStart;
    private String arrived;
    private String balanceEnd;
    private String use;

    private static final String path = "src/directory/position";

    public static int counter = 0;
    public static final int MAX_SZIE = 7;

    public Document(String number) {
        this.number = number;
    }

    public static ArrayList<String> values() {

        ArrayList<String> position = new ArrayList<>();

        try {
            FileInputStream fi = new FileInputStream( path );
            BufferedReader br = new BufferedReader( new InputStreamReader( fi )  );
            String newLine;

            while( ( newLine = br.readLine()) != null ){
                position.add( newLine );
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return position;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBalanceStart() {
        return balanceStart;
    }

    public void setBalanceStart(String balanceStart) {
        this.balanceStart = balanceStart;
    }

    public String getArrived() {
        return arrived;
    }

    public void setArrived(String arrived) {
        this.arrived = arrived;
    }

    public String getBalanceEnd() {
        return balanceEnd;
    }

    public void setBalanceEnd(String balanceEnd) {
        this.balanceEnd = balanceEnd;
    }

    public String getUse() {
        return use;
    }

    public void setUse(String use) {
        this.use = use;
    }
}
