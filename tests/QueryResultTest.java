import database.Query;
import database.QueryResult;
import org.junit.Assert;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryResultTest {
    @Test
    public void test1() {
        try {
            printOutObjects(QueryResult.parseResultSet(Query.showAllPassengers()));
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    // Helper function to print out the objects in the 2d array
    public void printOutObjects(Object[][] object2dArray) {
        for (Object[] objArr : object2dArray) {
            for (Object o : objArr) {
                System.out.println(o);
            }
            System.out.println("======== REACHED THE END OF THE ARRAY. ========");
        }
    }
}
