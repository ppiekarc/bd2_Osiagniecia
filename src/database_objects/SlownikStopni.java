package database_objects;

import database_handler.Connector;

import java.sql.ResultSet;
import java.util.ArrayList;

public class SlownikStopni implements TableObj {
    private int id;
    private String nazwa_stopnia;
    private String kategoria;
    private String hierarchia;


    public int getId() {
        return id;
    }

    public String getNazwa_stopnia() {
        return nazwa_stopnia;
    }

    public String getKategoria() {
        return kategoria;
    }

    public String getHierarchia() {
        return hierarchia;
    }

    public void setFromResultSet(ResultSet rs) {
        try {
            id = rs.getInt("id");
            nazwa_stopnia = rs.getString("nazwa_stopnia");
            kategoria = rs.getString("kategoria");
            hierarchia = rs.getString("hierarchia");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    static public ArrayList<String> getAllKategories() {
        ArrayList result = Connector.select_query("SELECT * FROM SLOWNIK_STOPNI",
                TableType.SlownikStopni);
        ArrayList<String> kategories = new ArrayList<>();

        for (SlownikStopni s : (ArrayList<SlownikStopni>) result ) {
            if (kategories.contains(s.kategoria))
                continue;

            kategories.add(s.kategoria);

        }

        return kategories;
    }

    static public ArrayList<SlownikStopni> getByKategory(String category) {
        ArrayList result = Connector.select_query("SELECT * FROM SLOWNIK_STOPNI " +
                        "WHERE KATEGORIA = '" + category + "'", TableType.SlownikStopni);

        return (ArrayList<SlownikStopni>) result;
    }

}
