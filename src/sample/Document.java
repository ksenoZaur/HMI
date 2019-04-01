package sample;

import java.util.ArrayList;

public class Document {

    public final static int MAX_ROW = 8;

    public String getCostOfSpices() {
        return costOfSpices;
    }

    public String getSaltCost() {
        return saltCost;
    }

    private Double itogo;
    private Double control;
    private Double summa;

    public Double getItogo() {
        return itogo;
    }

    public Double getControl() {
        return control;
    }

    public Double getSumma() {
        return summa;
    }

    public void setItogo(Double itogo) {
        this.itogo = itogo;
    }

    public void setControl(Double control) {
        this.control = control;
    }

    public void setSumma(Double summa) {
        this.summa = summa;
    }

    private String statementNumber;
    private String createDate;
    private String  periodStart;
    private String  periodEnd;
    private String organization;
    private String unit;
    private String fio1;
    private String position;
    private String fio2;


    private String costOfSpices;
    private String saltCost;

    private Double mainItogo;

    private ArrayList<Notation> notations;
    private ArrayList<NotationMinTable> notationMinTables;

    private Double itogoBlud;

    public Double getMainItogo() {
        return mainItogo;
    }

    public void setMainItogo(Double mainItogo) {
        this.mainItogo = mainItogo;
    }

    public Double getItogoBlud() {
        return itogoBlud;
    }

    public void setItogoBlud(Double itogoBlud) {
        this.itogoBlud = itogoBlud;
    }

    public Document() {
        this.notationMinTables = new ArrayList<NotationMinTable>(){{ add(new NotationMinTable());
            add(new NotationMinTable());}};
    }

    public ArrayList<NotationMinTable> getNotationMinTables() {
        return notationMinTables;
    }

    public void setCostOfSpices(String costOfSpices) {
        this.costOfSpices = costOfSpices;
    }

    public void setSaltCost(String saltCost) {
        this.saltCost = saltCost;
    }

    public void setNotationMinTables(ArrayList<NotationMinTable> notationMinTables) {
        this.notationMinTables = notationMinTables;
    }

    public String getStatementNumber() {
        return statementNumber;
    }

    public void setStatementNumber(String statementNumber) {
        this.statementNumber = statementNumber;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getPeriodStart() {
        return periodStart;
    }

    public void setPeriodStart(String periodStart) {
        this.periodStart = periodStart;
    }

    public String getPeriodEnd() {
        return periodEnd;
    }

    public void setPeriodEnd(String periodEnd) {
        this.periodEnd = periodEnd;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getFio1() {
        return fio1;
    }

    public void setFio1(String fio1) {
        this.fio1 = fio1;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getFio2() {
        return fio2;
    }

    public void setFio2(String fio2) {
        this.fio2 = fio2;
    }

    public ArrayList<Notation> getNotations() {
        return notations;
    }

    public void setNotations(ArrayList<Notation> notations) {
        this.notations = notations;
    }

    public void add( Notation notation ){

        if( this.notations == null ){

            this.notations = new ArrayList<>();

        }

        this.notations.add( notation );

    }
}
