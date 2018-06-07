package database_objects;

import database_handler.Connector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;

public class Oceny implements TableObj {
    private int id;
    private Integer id_jednostki;
    private int wartosc;

    private Jednostki jednostka = null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_jednostki() {
        return id_jednostki;
    }

    public void setId_jednostki(int id_jednostki) {
        this.id_jednostki = id_jednostki;
    }

    public int getWartosc() {
        return wartosc;
    }

    public void setWartosc(int wartosc) {
        this.wartosc = wartosc;
    }

    public String toString() {
        String value = "";
        String unit = "";

        if (jednostka != null)
            unit = jednostka.getRodzaj_skali();

        return Integer.toString(wartosc) + " " + unit;
    }

    public void setFromResultSet(ResultSet rs) {
        try {
            id = rs.getInt("id");
            id_jednostki = rs.getInt("id_jednostki");
            wartosc = rs.getInt("wartosc");

            jednostka = getJednostki();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Jednostki getJednostki() {
        if (jednostka != null)
            return jednostka;

        ArrayList v = Connector.select_query("SELECT * FROM JEDNOSTKI WHERE id = " + id_jednostki,
                TableType.Jednostki);

        if (v.isEmpty())
            return null;

        Object result = v.get(0);
        if (result instanceof Jednostki)
            return (Jednostki) result;

        return null;

    }

    public void setToPreparedStatement(PreparedStatement stmt) {
        try {
            if (id_jednostki != null)
                stmt.setInt(1, id_jednostki);
            else
                stmt.setNull(1, Types.INTEGER);

            stmt.setInt(2, wartosc);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insert() {
        String sql = "INSERT INTO OCENY (ID_JEDNOSTKI, WARTOSC) VALUES (?, ?)";
        String returnCols[] = { "ID" };

        Connector.insertToDb(sql, this, returnCols);
    }
}
