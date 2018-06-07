package database_objects;

import database_handler.Connector;

import java.sql.ResultSet;
import java.util.ArrayList;

public class TypyOsiagniec implements TableObj {
    private int id;
    private String typ;

    public int getId() {
        return id;
    }

    public String getTyp() {
        return typ;
    }

    public void setFromResultSet(ResultSet rs) {
        try {
            id = rs.getInt("id");
            typ = rs.getString("typ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static TypyOsiagniec getById (int id_) {
        ArrayList result = Connector.select_query("SELECT * FROM TYPY_OSIAGNIEC WHERE id = " + id_,
                TableType.TypyOsiagniec);

        if (result.isEmpty())
                return null;

        Object res = result.get(0);

        if (res instanceof TypyOsiagniec)
            return (TypyOsiagniec) res;

        return null;
    }

    static public ArrayList<TypyOsiagniec> getAll() {
        ArrayList result = Connector.select_query("SELECT * FROM TYPY_OSIAGNIEC", TableType.TypyOsiagniec);

        return (ArrayList<TypyOsiagniec>) result;
    }

}
