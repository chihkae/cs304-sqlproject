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

    public static String addNewPassenger(int passengerId, String departureFlightNumber, Date departureFlightDate,
                                         String passengerName, String phoneNumber, String address)
            throws SQLException {
        String insertString =
                "insert into passenger values(?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement insertStatement = getPreparedStatement(insertString);

        insertStatement.setInt(1, passengerId);
        insertStatement.setString(2, departureFlightNumber);
        insertStatement.setDate(3, departureFlightDate);
        insertStatement.setString(4, null);
        insertStatement.setDate(5, null);
        insertStatement.setString(6, passengerName);
        insertStatement.setString(7, phoneNumber);
        insertStatement.setString(8, address);

        int queryResultRowCount = insertStatement.executeUpdate();
        return showQueryUpdateMessage(queryResultRowCount);

    }
//    public static ResultSet showPassengers() throws SQLException{
//        String selectString=
//                "select * "+
//                        "from passenger p";
//        PreparedStatement selectStatement = getPreparedStatement(selectString);
//        return selectStatement.executeQuery();
//    }

    public static String changeAirline(int passengerId, String newAirlineName) throws SQLException {
        // only applies to departure.
        // It should change the flight and try to find a flight
        // that has the same destination + same date + different airline.
        // It should return an error if unable to find a different flight with the same
        // destination and same date.

        String updateString =
                "update passenger " +
                        "set flight_number = t.flight_number " +
                        "from ( " +
                        "select * " +
                        "from departure_flight df1, departure_flight df2 " +
                        "where df1.flight_number <> df2.flight_number and df1.departure_date = df2.departure_date " +
                        "and df1.destination = df2.destination " +
                        "and df1.airline_name = ? )t " +
                        "where passenger.flight_number <> t.flight_number " +
                        "and t.destination = (select distinct destination"+
                        "from passenger p, departure_flight df"+
                        "where p.departure_flight_number= df.flight_number" +
                        "p.id= ?)"+
                        "and passenger.flight_date = t.departure_date " +
                        "passenger.id = ?";

        PreparedStatement updateStatement = getPreparedStatement(updateString);
        updateStatement.setString(1, newAirlineName);
        updateStatement.setInt(2, passengerId);
        updateStatement.setInt(2, passengerId);

        int queryResultRowCount = updateStatement.executeUpdate();
        return showQueryUpdateMessage(queryResultRowCount);
    }

    public static ResultSet findLostBaggage(int baggageId) throws SQLException {
        String selectString =
                "select terminal_number, carousel_number, baggage_number, passenger_id, name, phone_number, address " +
                        "from baggage b, passenger p " +
                        "where b.passenger_id = p.id " +
                        "and b.baggage_number = ?";
        PreparedStatement selectStatement = getPreparedStatement(selectString);

        selectStatement.setInt(1, baggageId);
        return selectStatement.executeQuery();
    }

    public static String removePassenger(int passengerID) throws SQLException {
        String deleteString =
                "delete from passenger " +
                        "where passenger.id = ?";

        PreparedStatement deleteStatement = getPreparedStatement(deleteString);
        deleteStatement.setInt(1, passengerID);

        int queryResultRowCount = deleteStatement.executeUpdate();
        return showQueryUpdateMessage(queryResultRowCount);
    }

    public static ResultSet showAllPassengers() throws SQLException {
        String selectString = "select * from passenger";

        PreparedStatement selectStatement = getPreparedStatement(selectString);
        return selectStatement.executeQuery();
    }

    public static ResultSet showPassengersCountOnEachDepartureFlight() throws SQLException {
        // only applies to departure flight
        String selectString =
                "select df.flight_number, df.departure_date, COUNT(*) " +
                        "from passenger p, departure_flight df " +
                        "where p.departure_flight_number = df.flight_number "+
                        "and p.departure_date = df.departure_date "+
                        "group by df.flight_number, df.departure_date";

        PreparedStatement selectStatement = getPreparedStatement(selectString);
        return selectStatement.executeQuery();
    }

    public static String updateArrivalFlightTime(String flightNumber, Date arrivalDate,
                                              Time newArrivalTime) throws SQLException {
        String updateString =
                "update arrival_flight " +
                        "set arrival_time = ? " +
                        "where arrival_flight.flight_number = ? " +
                        "and arrival_flight.arrival_date = ?";

        PreparedStatement updateStatement = getPreparedStatement(updateString);
        updateStatement.setTime(1, newArrivalTime);
        updateStatement.setString(2, flightNumber);
        updateStatement.setDate(3, arrivalDate);

        int queryResultRowCount = updateStatement.executeUpdate();
        return showQueryUpdateMessage(queryResultRowCount);
    }

    public static ResultSet showAllArrivalFlights() throws SQLException {
        String selectString = "select * from arrival_flight";

        PreparedStatement selectStatement = getPreparedStatement(selectString);
        return selectStatement.executeQuery();
    }

    public static String updateDepartureFlightTime(String flightNumber, Date departureDate,
                                                Time newDepartureTime) throws SQLException {

        String updateString =
                "update departure_flight " +
                        "set departure_time = ? " +
                        "where departure_flight.flight_number = ? " +
                        "and departure_flight.departure_date = ?";

        PreparedStatement updateStatement = getPreparedStatement(updateString);
        updateStatement.setTime(1, newDepartureTime);
        updateStatement.setString(2, flightNumber);
        updateStatement.setDate(3, departureDate);

        int queryResultRowCount = updateStatement.executeUpdate();
        return showQueryUpdateMessage(queryResultRowCount);
    }

    public static ResultSet showAllDepartureFlights() throws SQLException {
        String selectString = "select * from departure_flight";

        PreparedStatement selectStatement = getPreparedStatement(selectString);
        return selectStatement.executeQuery();
    }

    /*
     * Passengers
     */

    public static String vipLoungeAvailable(int p_id) throws SQLException {

        String booleanString =
                "select distinct vip_lounge " +
                        "from (select distinct flight_number, terminal_number " +
                        "from arrival_flight " +
                        "union all " +
                        "select distinct flight_number, terminal_number " +
                        "from departure_flight) u, passenger p, terminals t " +
                        "where p.id = ? " +
                        "and (p.departure_flight_number = u.flight_number " +
                        "or p.arrival_flight_number = u.flight_number) " +
                        "and u.terminal_number = t.terminal_number";
        PreparedStatement booleanStatement = getPreparedStatement(booleanString);
        booleanStatement.setInt(1, p_id);
        ResultSet rs= booleanStatement.executeQuery();
        rs.next();
        try {
            Boolean vip_Lounge = rs.getBoolean("vip_lounge");
            if (vip_Lounge) {
                return "VIP lounge is available for your trip at the terminal!";
            } else {
                return "Sorry there is currently no lounge available at your terminal!";
            }
        } catch(SQLException e){
            return e.getMessage();
        }
    }


    public static String nonEnglish_exch(int p_id) throws SQLException {
        String booleanString =
                "Select distinct non_english_service " +
                        "from (select distinct flight_number, terminal_number from departure_flight " +
                        "union all " +
                        "select distinct flight_number, terminal_number from arrival_flight) u, "+
                        "passenger p, customer_service cs " +
                        "where p.id = ? "+
                        "and (p.departure_flight_number = u.flight_number "+
                        "or p.arrival_flight_number = u.flight_number) "+
                        "and u.terminal_number = cs.terminal_number "+
                        "and cs.type LIKE '%Exchange%' ";
        PreparedStatement booleanStatement = getPreparedStatement(booleanString);
        booleanStatement.setInt(1, p_id);
        ResultSet rs= booleanStatement.executeQuery();
        rs.next();
        try {
            Boolean non_english_service = rs.getBoolean("non_english_service");
            if (non_english_service) {
                return "Yes! Non english service will be provided at the currency exchange";
            } else {
                return "Sorry only english service is provided at the currency exchange";
            }
        } catch(SQLException e){
            return e.getMessage();
        }

    }

    public static ResultSet favoriteLocation(String restaurantName) throws SQLException {
        String selectString =
                "Select restaurant_name, terminal_number " +
                        "from restaurant r " +
                        "where r.restaurant_name LIKE ? ";
        PreparedStatement selectStatement = getPreparedStatement(selectString);
        selectStatement.setString(1, "%"+restaurantName+"%");
        return selectStatement.executeQuery();
    }

    public static ResultSet ateHereStars(int p_id) throws SQLException {
        String selectString =
                "Select distinct restaurant_name, yelp_rating " +
                        "from restaurant r, uses u " +
                        "where u.passenger_id=? and u.general_service_id= r.id";
        PreparedStatement selectStatement = getPreparedStatement(selectString);
        selectStatement.setInt(1, p_id);
        return selectStatement.executeQuery();
    }

    public static ResultSet showAllRestaurants() throws SQLException {
        String selectString =
                "Select * " +
                        "from restaurant";
        PreparedStatement selectStatement = getPreparedStatement(selectString);
        return selectStatement.executeQuery();
    }

    public static String createView(int passengerId) throws SQLException {
        String selectViewString =
                "Create or replace view v? "+
                        "as select * "+
                        "from passenger where id=?";
        PreparedStatement createViewStatement= getPreparedStatement(selectViewString);
        createViewStatement.setInt(1, passengerId);
        createViewStatement.setInt(2, passengerId);
        int queryResultRowCount = createViewStatement.executeUpdate();
        return showQueryUpdateMessage(queryResultRowCount);
    }

    public static ResultSet showView(int passengerId) throws SQLException {
        createView(passengerId);
        String showViewString = "Select * from v?";
        PreparedStatement showStatement = getPreparedStatement(showViewString);
        showStatement.setInt(1, passengerId);
        return showStatement.executeQuery();
    }

    /**
     * Helper functions
     */

    public static String showQueryUpdateMessage(int queryResultRowCount) {
        return "Query was successful. " + queryResultRowCount + " was affected.";
    }
}