package database;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class QueryResult {
    public static Object[][] parseResultSet(ResultSet rs) throws SQLException {
        ResultSetMetaData resultSetMetaData = rs.getMetaData();

        int numCols = resultSetMetaData.getColumnCount();
        for (int x = 0; x < numCols; x++) {
            System.out.println(resultSetMetaData.getColumnName(x + 1));
        }

        return null;
    }
}
