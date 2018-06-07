package database_objects;

import java.sql.ResultSet;

public class PlikiPotwierdzajace implements TableObj {
    private int id;
    private int id_osiagniecia;
    private byte[] plik;

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

    public byte[] getPlik() {
        return plik;
    }

    public void setPlik(byte[] plik) {
        this.plik = plik;
    }

    public void setFromResultSet(ResultSet rs) {
        try {
            id = rs.getInt("id");
            id_osiagniecia = rs.getInt("id_osiagniecia");
            plik = rs.getBytes("plik");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
