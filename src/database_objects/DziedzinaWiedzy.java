package database_objects;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import database_handler.Connector;

public class DziedzinaWiedzy implements TableObj {
    private int id;
    private String nazwa_dziedziny;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNazwa_dziedziny() {
        return nazwa_dziedziny;
    }

    public void setNazwa_dziedziny(String nazwa_dziedziny) {
        this.nazwa_dziedziny = nazwa_dziedziny;
    }

    public void setFromResultSet(ResultSet rs) {
        try {
            id = rs.getInt("id");
            nazwa_dziedziny = rs.getString("nazwa_dziedziny");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<DziedzinaWiedzy> getAll() {
        ArrayList result = Connector.select_query("SELECT * FROM DZIEDZINA_WIEDZY",
                TableType.DziedzinaWiedzy);

        return (ArrayList<DziedzinaWiedzy>) result;
    }

    public static ArrayList<Etykiety> getEtykietyByIdDziedziny(int dziedziny_wiedzy_id) {
        ArrayList result = Connector.select_query(
                "SELECT * FROM Etykiety WHERE id_dziedziny_wiedzy = " + dziedziny_wiedzy_id,
                TableType.Etykiety);


        return (ArrayList<Etykiety>) result;
    }

    public ArrayList<Etykiety> getEtykiety() {
        ArrayList result = Connector.select_query(
                "SELECT * FROM Etykiety WHERE id_dziedziny_wiedzy = " + id,
                TableType.Etykiety);


        return (ArrayList<Etykiety>) result;
    }
}
