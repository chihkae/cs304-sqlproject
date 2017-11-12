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

    public static boolean addNewPassenger(String flightNumber, Date flightDate, String passengerName,
                                            int phoneNumber, String address) throws SQLException {
        String insertString =
                "insert into passenger(flight_number, flight_date, name, phone_number, address) " +
                "values(?, ?, ?, ?, ?)";
        PreparedStatement insertStatement = getPreparedStatement(insertString);

        insertStatement.setString(1, flightNumber);
        insertStatement.setDate(2, flightDate);
        insertStatement.setString(3, passengerName);
        insertStatement.setInt(4, phoneNumber);
        insertStatement.setString(5, address);

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



    /*
     * Passengers
     */
}
