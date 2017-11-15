package database;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QueryResult {
    public static Object[][] parseResultSet(ResultSet rs) throws SQLException {
        ResultSetMetaData resultSetMetaData = rs.getMetaData();
        int numCols = resultSetMetaData.getColumnCount();

        List<List<Object>> listResult = new ArrayList<>();
        List<Object> columnNames = new ArrayList<>();

        addColumnNames(resultSetMetaData, columnNames, numCols);
        addResultSetRowsToListResult(listResult, rs, numCols);

        Object[][] arrayResult = listResult.stream().map(List::toArray).toArray(Object[][]::new);
        return arrayResult;
    }

    public static void addColumnNames(ResultSetMetaData resultSetMetaData, List<Object> columnNames,
                                      int numCols) throws SQLException {
        for (int x = 1; x <= numCols; x++) {
            // System.out.println(resultSetMetaData.getColumnName(x));
            columnNames.add(resultSetMetaData.getColumnName(x));
        }
    }

    public static void addResultSetRowsToListResult(List<List<Object>> listResult,
                                                    ResultSet rs, int numCols) throws SQLException {
        while (rs.next()) {
            List<Object> row = getRowValues(rs, numCols);
            listResult.add(row);
        }
    }

    public static List<Object> getRowValues(ResultSet rs, int numCols) throws SQLException {
        List<Object> listOfRowValues = new ArrayList<>();

        for (int y = 1; y <= numCols; y++) {
            listOfRowValues.add(rs.getString(y));
        }

        return listOfRowValues;
    }
}
