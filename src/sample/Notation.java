package sample;

import java.io.*;
import java.util.Date;
import java.util.HashMap;

/**
 *  Класс представляющий документ - форму ОП-13
 *
 * */
public class Notation {

    // Fields

    private Integer number;
    private String title;
    private String code;
    private Double balanceStart;
    private Double arrived;
    private Double balanceEnd;
    private Double use;

    private static final String path = "src/directory/position";

    public static int counter = 0;

    private static HashMap<String, String> codeList;

    static {

        Notation.codeList = Notation.init();

    }



    public Notation(Integer number) {

        this.number = number;

    }

    private static HashMap<String, String> init() {

        String[] position;
        HashMap<String, String> codeList = new HashMap<>();

        try {
            FileInputStream fi = new FileInputStream( path );
            BufferedReader br = new BufferedReader( new InputStreamReader( fi )  );
            String newLine;

            while( ( newLine = br.readLine()) != null ) {

                position = newLine.split("\\#");
//                this.titleList.add( position[ 0 ] );
                codeList.put( position[ 0 ], position[ 1 ]);
            }

        } catch (IOException e) {

            e.printStackTrace();

        }

        return codeList;
    }

    public static HashMap<String, String> codeList() {

        return codeList;

    }
    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
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

    public Double getBalanceStart() {
        return balanceStart;
    }

    public void setBalanceStart(Double balanceStart) {
        this.balanceStart = balanceStart;
    }

    public Double getArrived() {
        return arrived;
    }

    public void setArrived(Double arrived) {
        this.arrived = arrived;
    }

    public Double getBalanceEnd() {
        return balanceEnd;
    }

    public void setBalanceEnd(Double balanceEnd) {
        this.balanceEnd = balanceEnd;
    }

    public Double getUse() {
        return use;
    }

    public void setUse(Double use) {
        this.use = use;
    }

    public static String getPath() {
        return path;
    }
}
