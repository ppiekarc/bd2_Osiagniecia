package database_objects;

import database_handler.Connector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;

public class Uprawnienia extends Osiagniecia implements TableObj {
    private Integer numer_licencji;

    @Override
    public String getNumer_licencji() {
        return Integer.toString(numer_licencji);
    }

    public void setNumer_licencji(int numer_licencji) {
        this.numer_licencji = numer_licencji;
    }

    @Override
    public void setFromResultSet(ResultSet rs) {
        super.setFromResultSet(rs);
        try {
            numer_licencji = rs.getInt("numer_licencji");
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

            if (numer_licencji != null)
                stmt.setInt(9, numer_licencji);
            else
                stmt.setNull(9, Types.INTEGER);

            if (opis != null)
                stmt.setString(10, opis);
            else
                stmt.setNull(10, Types.VARCHAR);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insert() {
        String sql = "INSERT INTO OSIAGNIECIA " +
                "(ID_UZYTKOWNIKA, ID_TYPU, ID_OCENY, NAZWA, " +
                "DATA_UZYSKANIA, DATA_WYGASNIECIA, NAZWA_PODMIOTU, " +
                "MIEJSCE_UZYSKANIA, NUMER_LICENCJI, OPIS) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        String returnCols[] = { "ID" };
        Connector.insertToDb(sql, this, returnCols);
    }
}
