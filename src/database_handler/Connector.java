package database_handler;

import java.sql.*;
import java.util.ArrayList;

import database_objects.TableFactory;
import database_objects.TableObj;
import database_objects.TableType;

public class Connector {
    static private final String USER = "SYSTEM";
    static private final String PASS = "system";
    static private final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";

    static private PreparedStatement stmt = null;
    static private Connection conn = null;
    static private Connector instance = null;

    static private int numberOfConn = 0;


    private Connector() {}

    static public Connector get_instance() {
        if (instance == null)
            instance = new Connector();

        return instance;
    }

    static public synchronized void connect() {
        if (numberOfConn == 0) {

            try {
                System.out.println("Connecting to database...");

                Class.forName("oracle.jdbc.OracleDriver");
                conn = DriverManager.getConnection(DB_URL, USER, PASS);

            } catch (ClassNotFoundException e) {
                System.err.println("SQL driver not found! Exiting...");
                e.printStackTrace();
                System.exit(1);
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            }

            if (conn != null) {
                System.out.println("Connected!");
            }
        }
        numberOfConn++;
    }

    static public synchronized void disconnect() {
        if (numberOfConn == 1) {

            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            }
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println("Successfully closed connection to database ...");
        }
        numberOfConn--;
    }

    static public void insertToDb(String sql, TableObj obj, String[] returnCols) {
        connect();
        try {
            if (obj != null) {
                PreparedStatement stmt = conn.prepareStatement(sql, returnCols);
                obj.setToPreparedStatement(stmt);
                stmt.executeUpdate();

                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next())
                    obj.setId((int)generatedKeys.getLong(1));

            } else {
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        finally {
            disconnect();
        }
    }

    /* only works for SELECT* @TODO change api */
    static public ArrayList<TableObj> select_query(String query, TableType type) {
        ResultSet rs;
        Statement stmt;

        ArrayList<TableObj> result = new ArrayList<>();
        connect();
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                TableObj obj = TableFactory.CreateTableObj(type, rs);
                obj.setFromResultSet(rs);
                result.add(obj);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        disconnect();
        return result;
    }
}
