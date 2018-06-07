package database_objects;

import database_handler.Connector;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Vector;

public class Etykiety implements TableObj {
    private int id;
    private int id_dziedziny_wiedzy;
    private String etykieta;

    public int getId() {
        return id;
    }

    public int getId_dziedziny_wiedzy() {
        return id_dziedziny_wiedzy;
    }

    public String getEtykieta() {
        return etykieta;
    }

    public void setFromResultSet(ResultSet rs) {
        try {
            id = rs.getInt("id");
            id_dziedziny_wiedzy = rs.getInt("id_dziedziny_wiedzy");
            etykieta = rs.getString("etykieta");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Etykiety> getAll() {
        ArrayList result = Connector.select_query("SELECT * FROM ETYKIETY",
                TableType.Etykiety);

        return (ArrayList<Etykiety>) result;
    }

    public DziedzinaWiedzy getDziedzinaWiedzy() {
        ArrayList v = Connector.select_query("SELECT * FROM DZIEDZINA_WIEDZY WHERE id" + id_dziedziny_wiedzy,
                TableType.DziedzinaWiedzy);

        if (v.isEmpty())
            return null;

        Object result = v.get(0);
        if (result instanceof DziedzinaWiedzy)
            return (DziedzinaWiedzy) result;

        return null;
    }
}
