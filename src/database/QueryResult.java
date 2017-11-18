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

        addColumnNamesToListResult(listResult, resultSetMetaData, numCols);
        // iterate over rows in the ResultSet and add it to the ListResult
        addResultSetRowsToListResult(listResult, rs, numCols);

        // convert List<List<Object>> to Object[][]
        Object[][] arrayResult = listResult.stream().map(List::toArray).toArray(Object[][]::new);
        return arrayResult;
    }

    public static void addColumnNamesToListResult(List<List<Object>> listResult,
                                                  ResultSetMetaData resultSetMetaData,
                                                  int numCols) throws SQLException {
        List<Object> columnNames = new ArrayList<>();

        for (int x = 1; x <= numCols; x++) {
            // System.out.println(resultSetMetaData.getColumnName(x));
            // Add the column names to the columnNames list
            columnNames.add(resultSetMetaData.getColumnName(x));
        }

        listResult.add(columnNames);
    }

    public static void addResultSetRowsToListResult(List<List<Object>> listResult,
                                                    ResultSet rs, int numCols) throws SQLException {
        // Iterate over result set
        while (rs.next()) {
            // Get the current row values and add it to the listResult
            List<Object> row = getRowValues(rs, numCols);
            listResult.add(row);
        }
    }

    public static List<Object> getRowValues(ResultSet rs, int numCols) throws SQLException {
        List<Object> listOfRowValues = new ArrayList<>();

        // Iterate over each column in the row
        for (int y = 1; y <= numCols; y++) {
            // Get the string value of the column data.
            // getString() converts the data to a string. Ex. Date -> String and Integer -> String
            String columnValue = rs.getString(y);

            if (columnValue == null) {
                listOfRowValues.add("null");
            } else {
                listOfRowValues.add(columnValue);
            }
        }

        return listOfRowValues;
    }
}
