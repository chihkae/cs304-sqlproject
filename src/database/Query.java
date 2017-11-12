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

    public static boolean addNewPassenger(int flightNumber, Date departureDate, String passengerName,
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

    public static ResultSet vipLoungAvailable(int p_id) throws SQLException {
        String booleanString =
                "Select vip_lounge" +
                        "from terminals t, passengers p, departure_flight df, arrival_flght af" +
                        "where p.id= ? ,p.flight_date=df.departure_date, p.flight_number= df.flight_number" +
                        "p.flight_date=af.arrival_date, p.flight_number=af.flight_number" +
                        "df.terminal_number= t.terminal_number, af.terminal_number=t.terminal_number";
        PreparedStatement booleanStatement = getPreparedStatement(booleanString);
        booleanStatement.setInt(1, p_id);
        ResultSet rs = booleanStatement.executeQuery(booleanString);
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
