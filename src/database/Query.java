package database;

import java.sql.*;

public class Query {
    public static PreparedStatement getPreparedStatement(String sqlStatement) throws SQLException {
        return DbConnection
                .INSTANCE
                .getConnection()
                .prepareStatement(sqlStatement);
    }

    /*
     * Employees
     */

    public static boolean addNewPassenger(int passengerId, String flightNumber, Date flightDate,
                                          String passengerName, int phoneNumber, String address)
            throws SQLException {
        String insertString =
                "insert into passenger values(?, ?, ?, ?, ?, ?)";

        PreparedStatement insertStatement = getPreparedStatement(insertString);

        insertStatement.setInt(1, passengerId);
        insertStatement.setString(2, flightNumber);
        insertStatement.setDate(3, flightDate);
        insertStatement.setString(4, passengerName);
        insertStatement.setInt(5, phoneNumber);
        insertStatement.setString(6, address);

        return insertStatement.execute();

    }

    public static int changeAirline(int passengerId, String newAirlineName) throws SQLException {
        // only applies to departure.
        // It should change the flight and try to find a flight
        // that has the same destination + same date + different airline.
        // It should return an error if unable to find a different flight with the same
        // destination and same date.

        String updateString =
                "update passenger " +
                        "set flight_number = t.flight_number " +
                        "from (" +
                        "select *" +
                        "from departure_flight df1, departure_flight df2" +
                        "where df1.flight_number <> df2.flight_number and df1.departure_date = df2.departure_date " +
                        "and df1.departure_destination = df2.departure_destination " +
                        "and df1.airline_name = ?" +
                ") t " +
                "where passenger.flight_number <> t.flight_number " +
                "and passenger.flight_date = t.departure_date " +
                "passenger.id = ?";

        PreparedStatement updateStatement = getPreparedStatement(updateString);
        updateStatement.setString(1, newAirlineName);
        updateStatement.setInt(2, passengerId);

        return updateStatement.executeUpdate();
    }

    public static ResultSet findLostBaggage(int baggageId) throws SQLException {
        String selectString =
                "select * from baggage b, passenger p " +
                        "where b.passenger_id = p.id " +
                        "and b.baggage_number = ?";
        PreparedStatement selectStatement = getPreparedStatement(selectString);

        selectStatement.setInt(1, baggageId);
        return selectStatement.executeQuery();
    }

    public static int removePassenger(int passengerID) throws SQLException {
        String deleteString =
                "delete from passenger " +
                        "where passenger.id = ?";

        PreparedStatement deleteStatement = getPreparedStatement(deleteString);
        deleteStatement.setInt(1, passengerID);
        return deleteStatement.executeUpdate();
    }

    public static ResultSet showAllPassengers() throws SQLException {
        String selectString = "select * from passenger";

        PreparedStatement selectStatement = getPreparedStatement(selectString);
        return selectStatement.executeQuery();
    }

    public static ResultSet showPassengersOnEachFlight() throws SQLException {
        // only applies to departure flight
        String selectString =
                "select df.flight_number, df.arrival_date, COUNT(*) " +
                "from passenger p, departure_flight df " +
                "where p.flight_number = df.flight_number " +
                "and p.flight_date = df.departure_date" +
                "group by df.departure_flight";

        PreparedStatement selectStatement = getPreparedStatement(selectString);
        return selectStatement.executeQuery();
    }

    public static int updateArrivalFlightTime(String flightNumber, Date arrivalDate,
                                              Time newArrivalTime) throws SQLException {
        String updateString =
                "update arrival_flight " +
                        "set arrival_time = ? " +
                        "where arrival_flight.flight_number = ? " +
                        "and arrival_flight.arrival_time = ?";

        PreparedStatement updateStatement = getPreparedStatement(updateString);
        updateStatement.setTime(1, newArrivalTime);
        updateStatement.setString(2, flightNumber);
        updateStatement.setDate(3, arrivalDate);

        return updateStatement.executeUpdate();
    }

    public static ResultSet showAllArrivalFlights() throws SQLException {
        String selectString = "select * from arrival_flight";

        PreparedStatement selectStatement = getPreparedStatement(selectString);
        return selectStatement.executeQuery();
    }

    public static int updateDepartureFlightTime(String flightNumber, Date departureDate,
                                                  Time newDepartureTime) throws SQLException {
        String updateString =
                "update departure_flight " +
                "set departure_time = ? " +
                "where departure_flight.flight_number = ? " +
                "and departure_flight.departure_time = ?";

        PreparedStatement updateStatement = getPreparedStatement(updateString);
        updateStatement.setTime(1, newDepartureTime);
        updateStatement.setString(2, flightNumber);
        updateStatement.setDate(3, departureDate);

        return updateStatement.executeUpdate();
    }
    
    public static ResultSet showAllDepartureFlights() throws SQLException {
        String selectString = "select * from departure_flight";

        PreparedStatement selectStatement = getPreparedStatement(selectString);
        return selectStatement.executeQuery();
    }

    /*
     * Passengers
     */

    public static ResultSet vipLoungAvailable(int p_id) throws SQLException {
        String booleanString =
                "select distinct vip_lounge " +
                        "from (select distinct flight_number, terminal_number " +
                        "from arrival_flight " +
                        "union all " +
                        "select distinct flight_number, terminal_number " +
                        "from departure_flight) u, passenger p, terminals t " +
                        "where p.id = ? " +
                        "and (p.flight_number = u.flight_number " +
                        "or p.flight_number = u.flight_number) " +
                        "and u.terminal_number = t.terminal_number";
        PreparedStatement booleanStatement = getPreparedStatement(booleanString);
        booleanStatement.setInt(1, p_id);
        ResultSet rs = booleanStatement.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();

        // get number of columns
        int numCols = rsmd.getColumnCount();

        System.out.println(" ");

        // display column names;
        for (int i = 0; i < numCols; i++) {
            // get column name and print it

            System.out.printf("%-15s", rsmd.getColumnName(i + 1));
        }

        System.out.println(" ");

        while (rs.next()) {
            // for display purposes get everything from Oracle
            // as a string

            // simplified output formatting; truncation may occur

            Boolean vip_Lounge = rs.getBoolean("vip_lounge");
            if (vip_Lounge == true) {
                System.out.println("VIP lounge is available for your trip at the terminal!");
            } else {
                System.out.printf("Sorry there is currently no lounge available at your terminal!");
            }
        }
        return rs;
    }

    public static ResultSet nonEnglish_exch(int p_id) throws SQLException {
        String booleanString =
                "Select non_english_service" +
                        "from passenger p, departure flight df, arrival_flight af, customer_service cs" +
                        "where p.id= ?, p.flight_date=af.arrival_date, p.flight_date=df_arrival_date" +
                        "p.flight_number= df.flight_number, p.flight_number= af.flight_number" +
                        "af.terminal_number= cs.terminal_number, df.terminal_number=cs.terminal_number";
        PreparedStatement booleanStatement = getPreparedStatement(booleanString);
        booleanStatement.setInt(1, p_id);
        ResultSet rs = booleanStatement.executeQuery(booleanString);
        return rs;
    }

    public static ResultSet favoriteLocation(String restaurantName) throws SQLException {
        String selectString =
                "Select name, terminal_number" +
                        "from restaurant r" +
                        "where r.name= LIKE ?";
        PreparedStatement selectStatement = getPreparedStatement(selectString);
        selectStatement.setString(1, restaurantName);
        ResultSet rs = selectStatement.executeQuery();
        return rs;
    }

    public static ResultSet atehereStars(int p_id) throws SQLException {
        String selectString =
                "Select yelp_rating" +
                        "from restaurant r, uses u," +
                        "where u.id=?, u.general_service_id= r.id";
        PreparedStatement selectStatement = getPreparedStatement(selectString);
        selectStatement.setInt(1, p_id);
        ResultSet rs = selectStatement.executeQuery();
        return rs;
    }

    public static ResultSet showAllRestaurants() throws SQLException {
        String selectString =
                "Select *" +
                        "from restaurant";
        PreparedStatement selectStatement = getPreparedStatement(selectString);
        ResultSet rs = selectStatement.executeQuery();
        return rs;
    }
}
