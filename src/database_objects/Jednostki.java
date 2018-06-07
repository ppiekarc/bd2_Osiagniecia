package database_objects;

import database_handler.Connector;

import java.sql.ResultSet;
import java.util.ArrayList;

public class Jednostki implements TableObj {
    private int id;
    private String rodzaj_skali;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRodzaj_skali() {
        return rodzaj_skali;
    }

    public void setRodzaj_skali(String rodzaj_skali) {
        this.rodzaj_skali = rodzaj_skali;
    }

    public void setFromResultSet(ResultSet rs) {
        try {
            id = rs.getInt("id");
            rodzaj_skali = rs.getString("rodzaj_skali");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Jednostki> getAll() {
        ArrayList result = Connector.select_query("SELECT * FROM JEDNOSTKI",
                TableType.Jednostki);

        return (ArrayList<Jednostki>) result;
    }
}
