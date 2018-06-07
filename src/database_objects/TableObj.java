package database_objects;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public interface TableObj {
    void setFromResultSet(ResultSet rs);
    default void setId(int id) {}
    default void setToPreparedStatement(PreparedStatement stmt) {}
}
