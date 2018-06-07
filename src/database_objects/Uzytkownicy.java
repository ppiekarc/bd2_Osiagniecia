package database_objects;

import java.sql.ResultSet;

public class Uzytkownicy implements TableObj {
    private int id;
    private int id_przelozonego;
    private String stanowisko;
    private String rola;
    private String czas_aktywny;

    public int getId() {
        return id;
    }

    public int getId_przelozonego() {
        return id_przelozonego;
    }

    public String getStanowisko() {
        return stanowisko;
    }

    public String getRola() {
        return rola;
    }

    public String getCzas_aktywny() {
        return czas_aktywny;
    }

    public void setFromResultSet(ResultSet rs) {
        try {
            id = rs.getInt("id");
            id_przelozonego = rs.getInt("id_przelozonego");
            stanowisko = rs.getString("stanowisko");
            rola = rs.getString("rola");
            czas_aktywny = rs.getString("czas_aktywny");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}
