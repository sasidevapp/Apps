/*
 * FileName: Booking.java
 * Author  : sasi1901
 * 
 * Using JRE 1.8.0_212
 *  
 * REVISION         DATE            NAME     DESCRIPTION
 * 511.101       Feb 10, 2020       Sasi   Initial Code  
 */
package com.sasi;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sasi.action.HeaderAction;

/**
 * The Class Booking.
 */
public class Booking {

    /** The status. */
    public String id;

    /** The destination id. */
    public String destination_id;

    /** The booking date. */
    public String booking_date;

    /** The journey date. */
    public String journey_date;

    /** The train id. */
    public String train_id;

    /** The seat numbers. */
    public String seat_numbers;

    /** The passenger id. */
    public String passenger_id;

    /** The number of seat. */
    public String number_of_seat;

    /** The status. */
    public String status;
	
    /**
     * Find by user.
     *
     * @param userId the user id
     * @return the json array
     */
	public JsonArray FindByUser(String userId) {
		Connection con = null;
		Statement statement = null;
		ResultSet result = null;
		JsonArray bookedObjects = new JsonArray();
		JsonObject jsObj;
		try {
			Stations stations = new Stations();
			String sql = "SELECT B.NAME,B.TYPE,BK.BOOKING_DATE,BK.JOURNEY_DATE,D.STATION_FROM,D.STATION_TO,BK.NUMBER_OF_SEAT FROM BOOKING BK LEFT JOIN BUSES B "
					+ "ON(B.BUS_ID=BK.BUS_ID) " + "LEFT JOIN DESTINATIONS D "
					+ "ON (BK.DESTINATION_ID = D.DESTINATION_ID) " + "WHERE BK.PASSENGER_ID = " + userId;
			con = Database.getDBConnection();
			statement = con.createStatement();
			result = statement.executeQuery(sql);
			while (result.next()) {
				jsObj = new JsonObject();
				jsObj.addProperty("busName", result.getString("NAME"));
				jsObj.addProperty("type", result.getString("TYPE"));
				jsObj.addProperty("bookingDate", result.getString("BOOKING_DATE"));
				jsObj.addProperty("journeyDate", result.getString("JOURNEY_DATE"));
				jsObj.addProperty("fromStation", stations.getStationName(result.getString("STATION_FROM")));
				jsObj.addProperty("toStation", stations.getStationName(result.getString("STATION_TO")));
				jsObj.addProperty("numberOfSeat", result.getString("NUMBER_OF_SEAT"));
				bookedObjects.add(jsObj);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				Database.close(con, statement, result);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return bookedObjects;
	}
	

    /**
     * Book now.
     *
     * @param destinationId the destination id
     * @param busId the bus id
     * @param date the date
     * @param totalSeat the total seat
     * @param userId the user id
     */
	public void BookNow(String destinationId, String busId, String date, List<Object> totalSeat, String userId) {
		String seatsToBook= "";
		Connection con = null;
		Statement statement = null;
		ResultSet result = null;
		int count = 0;
		try {
			con = Database.getDBConnection();
			statement = con.createStatement();
			for (Object tkt : totalSeat) {
				seatsToBook += "," + tkt;
				count++;
			}
			String sqlBooking = "INSERT INTO booking(booking_id,destination_id,booking_date,journey_date,bus_id,seat_numbers,passenger_id,number_of_seat,status)"
					+ " VALUES(BOOKING_ID_SEQ.NEXTVAL,'" + destinationId + "','" + date + "','" + date + "','" + busId
					+ "','"
					+ seatsToBook.substring(1, seatsToBook.length()) + "','" + userId + "','" + count + "','SUCCESS')";
		
			statement.executeUpdate(sqlBooking, Statement.RETURN_GENERATED_KEYS);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				Database.close(con, statement, result);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
    /**
     * Gets the available seats.
     *
     * @param destinationId the destination id
     * @param busId the bus id
     * @param date the date
     * @return the available seats
     */
	public JsonObject getAvailableSeats(String destinationId, String busId, String date) {
		JsonObject jsObj = new JsonObject();
		List<Object> bookedSeats = new ArrayList<>();
		String sql = "SELECT SEAT_NUMBERS FROM BOOKING WHERE DESTINATION_ID = '" + destinationId
				+ "'  AND JOURNEY_DATE ='" + date + "' AND BUS_ID = '" + busId + "'";
		Connection con = null;
		Statement statement = null;
		Statement totalSeatStatement = null;
		ResultSet seatResult = null;
		ResultSet result = null;
		try {
			con = Database.getDBConnection();
			statement = con.createStatement();
			result = statement.executeQuery(sql);
			while (result.next()) {
				String[] tempSeat = result.getString("seat_numbers").split(",");
				for (String seat : tempSeat) {
					bookedSeats.add(Integer.parseInt(seat));
				}
			}
			totalSeatStatement = con.createStatement();
			seatResult = totalSeatStatement.executeQuery("SELECT TOTAL_SEAT FROM BUSES WHERE BUS_ID = '" + busId + "'");
			jsObj.add("bookedSeat",
					new JsonParser().parse(new HeaderAction().listToJsonArray(bookedSeats)).getAsJsonArray());
			if (seatResult.next())
				jsObj.addProperty("totalSeat", seatResult.getInt("total_seat"));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				Database.close(null, totalSeatStatement, seatResult);
				Database.close(con, statement, result);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return jsObj;
	}
}
