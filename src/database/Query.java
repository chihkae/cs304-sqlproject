package database;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    public static ResultSet findLostBaggage(int baggageId) throws SQLException {
        String selectString =
                "select * from baggage b, passenger p " +
                "where b.passenger_id = p.id " +
                "and b.baggage_number = ?";
        PreparedStatement selectStatement = getPreparedStatement(selectString);

        selectStatement.setInt(1, baggageId);
        return selectStatement.executeQuery();
    }

    public static ResultSet addNewPassenger(int flightNumber, Date departureDate, String passengerName,
                                            int phoneNumber, String address) throws SQLException {
        String insertString =
                "insert into passenger(flight_number, departure_date, name, phone_number, address) " +
                "values(?, ?, ?, ?, ?)";
        PreparedStatement insertStatement = getPreparedStatement(insertString);

        insertStatement.setInt(1, flightNumber);
        insertStatement.setDate(2, departureDate);
        insertStatement.setString(3, passengerName);
        insertStatement.setInt(4, phoneNumber);
        insertStatement.setString(5, address);

        return insertStatement.executeQuery();

    }

    public static int changeAirline(int passengerId, String newAirlineName) throws Exception {
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
                "and passenger.departure_date = t.departure_date " +
                "passenger.id = ?";

        PreparedStatement updateStatement = getPreparedStatement(updateString);
        updateStatement.setString(1, newAirlineName);
        updateStatement.setInt(2, passengerId);

        return updateStatement.executeUpdate();
    }

    public static ResultSet updateArrivalFlight(int flightNumber, Date arrivalDate,
                                                Date newArrivalDate) throws SQLException {
        // TODO
        // TODO
        // TODO
        // TODO
        return null;
    }

    public static int removePassenger(int passengerID) throws SQLException {
        String deleteString =
                "delete from passenger " +
                "where passenger.id = ?";

        PreparedStatement deleteStatement = getPreparedStatement(deleteString);
        deleteStatement.setInt(1, passengerID);
        return deleteStatement.executeUpdate();
    }

    public static ResultSet showPassengersOnEachFlight() throws SQLException {
        return null;
    }

    public static ResultSet updateDepartureFlight() throws SQLException {
        return null;
    }

    /*
     * Passengers
     */
}
