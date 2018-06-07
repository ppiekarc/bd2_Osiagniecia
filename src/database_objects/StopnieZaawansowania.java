package database_objects;

import database_handler.Connector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;

public class StopnieZaawansowania implements TableObj {
    private int id;
    private Integer id_osiagniecia;
    private Integer id_etykiety;
    private Integer id_stopnia;
    private String stopien;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_osiagniecia() {
        return id_osiagniecia;
    }

    public void setId_osiagniecia(int id_osiagniecia) {
        this.id_osiagniecia = id_osiagniecia;
    }

    public int getId_etykiety() {
        return id_etykiety;
    }

    public void setId_etykiety(int id_etykiety) {
        this.id_etykiety = id_etykiety;
    }

    public int getId_stopnia() {
        return id_stopnia;
    }

    public void setId_stopnia(int id_stopnia) {
        this.id_stopnia = id_stopnia;
    }

    public String getStopien() {
        return stopien;
    }

    public void setStopien(String stopien) {
        this.stopien = stopien;
    }

    public void setFromResultSet(ResultSet rs) {
        try {
            id = rs.getInt("id");
            id_osiagniecia = rs.getInt("id_osiagniecia");
            id_etykiety = rs.getInt("id_etykiety");
            id_stopnia = rs.getInt("id_stopnia");
            stopien = rs.getString("stopien");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void setToPreparedStatement(PreparedStatement stmt) {
        try {
            stmt.setInt(1, id_osiagniecia);

            if (id_etykiety != null)
                stmt.setInt(2, id_etykiety);
            else
                stmt.setNull(2, Types.INTEGER);

            if (id_stopnia != null)
                stmt.setInt(3, id_stopnia);
            else
                stmt.setNull(3, java.sql.Types.INTEGER);

            stmt.setString(4, stopien);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insert() {
        String sql = "INSERT INTO STOPNIE_ZAAWANSOWANIA " +
                "(ID_OSIAGNIECIA, ID_ETYKIETY, ID_STOPNIA, STOPIEN) VALUES (?, ?, ?, ?)";
        String returnCols[] = { "ID" };

        Connector.insertToDb(sql, this, returnCols);
    }
}
