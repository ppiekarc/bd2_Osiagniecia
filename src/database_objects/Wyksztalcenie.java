package database_objects;

import database_handler.Connector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;

public class Wyksztalcenie extends Osiagniecia implements TableObj {
    private String nazwa_szkoly;


    @Override
    public String getNazwa_szkoly() {
        return nazwa_szkoly;
    }

    public void setNazwa_szkoly(String nazwa_szkoly) {
        this.nazwa_szkoly = nazwa_szkoly;
    }

    @Override
    public void setFromResultSet(ResultSet rs) {
        super.setFromResultSet(rs);
        try {
            nazwa_szkoly = rs.getString("nazwa_szkoly");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
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

            if (nazwa_szkoly != null)
                stmt.setString(9, nazwa_szkoly);
            else
                stmt.setNull(9, Types.VARCHAR);

            if (opis != null)
                stmt.setString(10, opis);
            else
                stmt.setNull(10, Types.VARCHAR);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insert() {
        String sql = "INSERT INTO OSIAGNIECIA " +
                "(ID_UZYTKOWNIKA, ID_TYPU, ID_OCENY, NAZWA, " +
                "DATA_UZYSKANIA, DATA_WYGASNIECIA, NAZWA_PODMIOTU, " +
                "MIEJSCE_UZYSKANIA, NAZWA_SZKOLY, OPIS) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";


        String returnCols[] = { "ID" };
        Connector.insertToDb(sql, this, returnCols);
    }
}
