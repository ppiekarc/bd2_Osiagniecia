package database_objects;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import database_handler.*;
import javafx.scene.control.MenuItem;

public class Osiagniecia implements TableObj {
//    private static final List<String> columnNamesToShow = Arrays.asList(
//            "id_uzytkownika", "ocena", "typ", "nazwa", "data_uzyskania",
//            "data_wygasniecia", "nazwa_podmiotu", "miejsce_uzyskania",
//            "opis", "nazwa_szkoly", "numer_licencji", "czas_szkolenia"
//    );

    private static final List<String> columnNamesToShow = Arrays.asList(
            "id", "id_uzytkownika", "ocena", "typ", "dziedziny", "etykiety", "stopnie", "nazwa", "data_uzyskania",
            "data_wygasniecia", "nazwa_podmiotu", "miejsce_uzyskania",
            "opis", "nazwa_szkoly", "numer_licencji", "czas_szkolenia"
    );

    private static final String rowsNames = "OSIAGNIECIA.ID, OSIAGNIECIA.ID_UZYTKOWNIKA, " +
            "OSIAGNIECIA.ID_TYPU, OSIAGNIECIA.ID_OCENY, OSIAGNIECIA.NAZWA, OSIAGNIECIA.DATA_UZYSKANIA, " +
            "OSIAGNIECIA.DATA_WYGASNIECIA, OSIAGNIECIA.NAZWA_PODMIOTU, OSIAGNIECIA.MIEJSCE_UZYSKANIA, " +
            "OSIAGNIECIA.CZAS_SZKOLENIA, OSIAGNIECIA.NAZWA_SZKOLY, OSIAGNIECIA.NUMER_LICENCJI, OSIAGNIECIA.OPIS";

    protected int id;
    Integer id_uzytkownika;
    Integer id_oceny;

    Integer id_typu;
    String nazwa;
    Timestamp data_uzyskania;
    Timestamp data_wygasniecia;
    String nazwa_podmiotu;
    String miejsce_uzyskania;
    String opis;

    private String type;
    private Oceny ocena = null;
    private ArrayList<DziedzinaWiedzy> dziedziny = new ArrayList<>();
    private ArrayList<Etykiety> etykiety = new ArrayList<>();
    private ArrayList<SlownikStopni> stopnie = new ArrayList<>();


    private static SelectBuilder query = new SelectBuilder();
    private static boolean joinStopnieZaawansowaniaFlag;
    private static boolean joinDziedzinaFlag;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setId_typu(int id_typu) {
        this.id_typu = id_typu;
    }

    public int getId_uzytkownika() {
        return id_uzytkownika;
    }

    public void setId_uzytkownika(int id_uzytkownika) {
        this.id_uzytkownika = id_uzytkownika;
    }

    public int getId_oceny() {
        return id_oceny;
    }


    public String getNazwa_szkoly() { return "";}
    public String getNumer_licencji() { return "";}
    public String getCzas_szkolenia() { return "";}


    private void getOcena_() {
        ArrayList v = Connector.select_query("SELECT * FROM OCENY WHERE id = " + id_oceny,
                TableType.Oceny);

        if (v.isEmpty())
            return;

        Object result = v.get(0);
        if (result instanceof Oceny) {
            ocena = (Oceny) result;
        }
    }
    /* @TODO POSPRAWDZAC WSZYSTKIE JOINY */
    private void getStopienie_() {
        SelectBuilder sql = new SelectBuilder();

        sql.column("*")
                .from("SLOWNIK_STOPNI")
                .join("STOPNIE_ZAAWANSOWANIA s on SLOWNIK_STOPNI.id = s.ID_STOPNIA")
                .where("ID_OSIAGNIECIA = " + id);

        ArrayList result = Connector.select_query(sql.toString(), TableType.SlownikStopni);
        if (!result.isEmpty())
            stopnie = (ArrayList<SlownikStopni>) result;

    }

    private void getDziedziny_() {
        SelectBuilder sql = new SelectBuilder();

        sql.column("*")
                .from("DZIEDZINA_WIEDZY")
                .join("OSIAGNIECIA_DO_DZ_WIEDZY odw on DZIEDZINA_WIEDZY.id = odw.ID_DZIEDZINY_WIEDZY")
                .where("ID_OSIAGNIECIA = " + id);

        ArrayList result = Connector.select_query(sql.toString(), TableType.DziedzinaWiedzy);
        if (!result.isEmpty())
            dziedziny = (ArrayList<DziedzinaWiedzy>) result;
    }

    /* @TODO poprwaic where*/
    private void getEtykiety_() {
        SelectBuilder sql = new SelectBuilder();

        sql.column("*")
                .from("ETYKIETY")
                .join("STOPNIE_ZAAWANSOWANIA s on ETYKIETY.id = s.ID_ETYKIETY")
                .where("ID_OSIAGNIECIA = " + id);

        ArrayList result = Connector.select_query(sql.toString(), TableType.Etykiety);
        if (!result.isEmpty())
            etykiety = (ArrayList<Etykiety>) result;
    }

    public String getOcena() {
        if (ocena != null)
            return ocena.toString();

        return "";
    }

    public String getTyp () {
        return type;

    }

    public String getDziedziny() {
        StringBuilder str = new StringBuilder();

        for (DziedzinaWiedzy d : dziedziny) {
            str.append(" " + d.getNazwa_dziedziny());
        }

        return str.toString();
    }

    public String getEtykiety() {
        StringBuilder str = new StringBuilder();

        for (Etykiety e : etykiety) {
            str.append(" " + e.getEtykieta());
        }

        return str.toString();
    }

    public String getStopnie() {
        StringBuilder str = new StringBuilder();

        for (SlownikStopni s : stopnie) {
            str.append(" " + s.getNazwa_stopnia());
        }

        return str.toString();
    }

    public void setId_oceny(int id_oceny) {
        this.id_oceny = id_oceny;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public Timestamp getData_uzyskania() {
        return data_uzyskania;
    }

    public void setData_uzyskania(Timestamp data_uzyskania) {
        this.data_uzyskania = data_uzyskania;
    }

    public Timestamp getData_wygasniecia() {
        return data_wygasniecia;
    }

    public void setData_wygasniecia(Timestamp data_wygasniecia) {
        this.data_wygasniecia = data_wygasniecia;
    }

    public String getNazwa_podmiotu() {
        return nazwa_podmiotu;
    }

    public void setNazwa_podmiotu(String nazwa_podmiotu) {
        this.nazwa_podmiotu = nazwa_podmiotu;
    }

    public String getMiejsce_uzyskania() {
        return miejsce_uzyskania;
    }

    public void setMiejsce_uzyskania(String miejsce_uzyskania) {
        this.miejsce_uzyskania = miejsce_uzyskania;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public void setNumer_licencji(int numer_licencji) {}

    public void setNazwa_szkoly(String nazwa_szkoly) {}
    public void setCzas_szkolenia(int czas_szkolenia) {}

    public void setFromResultSet(ResultSet rs) {
        try {
            id = rs.getInt("id");
            id_uzytkownika = rs.getInt("id_uzytkownika");
            id_oceny = rs.getInt("id_oceny");
            id_typu = rs.getInt("id_typu");
            nazwa = rs.getString("nazwa");
            data_uzyskania = rs.getTimestamp("data_uzyskania");
            data_wygasniecia = rs.getTimestamp("data_wygasniecia");
            nazwa_podmiotu = rs.getString("nazwa_podmiotu");
            miejsce_uzyskania = rs.getString("miejsce_uzyskania");
            opis = rs.getString("opis");

            type = TypyOsiagniec.getById(id_typu).getTyp();
            getOcena_();
            getDziedziny_();
            getEtykiety_();
            getStopienie_();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void initQuery() {
        query.column(rowsNames)
                .from("OSIAGNIECIA")
                .groupBy(rowsNames);

        joinStopnieZaawansowaniaFlag = false;
        joinDziedzinaFlag = false;
    }

    private static void makeWhereOrExpr(List<MenuItem> items, String attr) {
        StringBuilder where_statemant = new StringBuilder("(");
        boolean first = true;

        if (items.isEmpty())
            return;

        for (MenuItem i : items) {
            if (first)
                first = false;
            else
                where_statemant.append(" or ");

            where_statemant.append(attr + " = " + i.getId());
        }

        where_statemant.append(")");
        query.where(where_statemant.toString());

    }

    public static void makeQueryByType(List<MenuItem> items) {
        makeWhereOrExpr(items, "id_typu");
    }

    public static void makeQueryByDziedzina(List<MenuItem> items) {
        if (items.isEmpty())
            return;

        if (!joinDziedzinaFlag) {
            query.join("OSIAGNIECIA_DO_DZ_WIEDZY odz on osiagniecia.id = odz.ID_OSIAGNIECIA");
            joinDziedzinaFlag = true;
        }
        makeWhereOrExpr(items, "ID_DZIEDZINY_WIEDZY");
    }

    public static void makeQueryByEtykieta(List<MenuItem> items) {
        if (items.isEmpty())
            return;

        if (!joinStopnieZaawansowaniaFlag) {
            query.join("STOPNIE_ZAAWANSOWANIA s on osiagniecia.id = s.ID_OSIAGNIECIA");
            joinStopnieZaawansowaniaFlag = true;
        }

        makeWhereOrExpr(items, "ID_ETYKIETY");
    }

    public static void makeQueryByStopien(List<MenuItem> items) {
        if (items.isEmpty())
            return;

        if (!joinStopnieZaawansowaniaFlag) {
            query.join("STOPNIE_ZAAWANSOWANIA s on osiagniecia.id = s.ID_OSIAGNIECIA");
            joinStopnieZaawansowaniaFlag = true;
        }
        makeWhereOrExpr(items,"ID_STOPNIA");

    }

    public static ArrayList<Osiagniecia> executeQuery() {
        System.out.println(query);
        ArrayList<TableObj> result = Connector.select_query(query.toString(), TableType.Osiagniecia);
        query.clear();

        ArrayList<Osiagniecia> resultOs = new ArrayList<>();

        for(TableObj t : result) {
                resultOs.add((Osiagniecia) t);
        }

        return resultOs;
    }

    public static List<String> getAllColumns() {
        return columnNamesToShow;
    }

    public void setToPreparedStatement(PreparedStatement stmt) {
        try {
            if (id_uzytkownika != null)
                stmt.setInt(1, id_uzytkownika);
            else
                stmt.setNull(1, Types.INTEGER);

            if (id_typu != null)
                stmt.setInt(2, id_typu);
            else
                stmt.setNull(2, Types.INTEGER);

            if (id_oceny != null)
                stmt.setInt(3, id_oceny);
            else
                stmt.setNull(3, Types.INTEGER);

            stmt.setString(4, nazwa);

            if (data_uzyskania != null)
                stmt.setTimestamp(5, data_uzyskania);
            else
                stmt.setNull(5, Types.TIMESTAMP);

            if (data_wygasniecia != null)
                stmt.setTimestamp(6, data_wygasniecia);
            else
                stmt.setNull(6, Types.TIMESTAMP);

            if (nazwa_podmiotu != null)
                stmt.setString(7, nazwa_podmiotu);
            else
                stmt.setNull(7, Types.VARCHAR);

            if (miejsce_uzyskania != null)
                stmt.setString(8, miejsce_uzyskania);
            else
                stmt.setNull(8, Types.VARCHAR);

            if (opis != null)
                stmt.setString(9, opis);
            else
                stmt.setNull(9, Types.VARCHAR);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insert() {
        String sql = "INSERT INTO OSIAGNIECIA " +
                "(ID_UZYTKOWNIKA, ID_TYPU, ID_OCENY, NAZWA, " +
                "DATA_UZYSKANIA, DATA_WYGASNIECIA, NAZWA_PODMIOTU, " +
                "MIEJSCE_UZYSKANIA, OPIS) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        String returnCols[] = { "ID" };

        Connector.insertToDb(sql, this, returnCols);
    }

    public void insertDziedziny(int id_dziedziny) {
        String sql = String.format("INSERT INTO OSIAGNIECIA_DO_DZ_WIEDZY " +
                "(ID_OSIAGNIECIA, ID_DZIEDZINY_WIEDZY) VALUES (%d, %d)", id, id_dziedziny);


        Connector.insertToDb(sql,null, null);
    }
}
