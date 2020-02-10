/*
 * FileName: Buses.java
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

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import AllLayout.Bus;

/**
 * The Class Buses.
 */
public class Buses {

    /** The name. */
    public String name;

    /** The id. */
    public String id;

    /** The code. */
    public String code;

    /** The type. */
    public String type;

    /** The total seat. */
    public String total_seat;

    /** The total seat. */
	public int totalSeat;

    /**
     * Instantiates a new buses.
     */
	public Buses(){
        this.total_seat = this.name = this.id = this.code = this.total_seat = "";
		this.totalSeat = 0;
	}

    /**
     * Instantiates a new buses.
     *
     * @param trnId the trn id
     */
	public Buses(String trnId) {
		Connection con = null;
		Statement statement = null;
		ResultSet result = null;
		try {
			String sql = "SELECT * FROM BUSES WHERE BUS_ID='" + trnId + "'";
			con = Database.getDBConnection();
			statement = con.createStatement();
			result = statement.executeQuery(sql);
			while(result.next()) {
				this.name = result.getString("name");
				this.id = result.getString("bus_id");
				this.type = result.getString("type");
				this.code = result.getString("bus_no");
				this.total_seat = result.getString("total_seat");
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
	}

    /**
     * Gets the all.
     *
     * @return the all
     */
	public ArrayList<Bus> getAll() {
		ArrayList<Bus> trains = new ArrayList<Bus>();
		Connection con = null;
		Statement statement = null;
		ResultSet result = null;
		try {
			String sqlQuery = "SELECT * FROM BUSES";
			con = Database.getDBConnection();
			statement = con.createStatement();
			result = statement.executeQuery(sqlQuery);
			while(result.next()) {
				Bus temp = new Bus();
				temp.id = result.getString("bus_id");
				temp.name = result.getString("name");
				temp.code = result.getString("bus_no");
				temp.type = result.getString("type");
				temp.totalSeat = Integer.parseInt(result.getString("total_seat"));
				trains.add(temp);
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
		return trains;
	}

    /**
     * Delete.
     *
     * @param trnId the trn id
     */
	public void Delete (String trnId) {
		Connection con = null;
		Statement statement = null;
		try {
			String sql = "DELETE FROM BUSES WHERE BUS_ID = '" + trnId + "'";
			con = Database.getDBConnection();
			statement = con.createStatement();
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				Database.close(con, statement, null);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/* Search Destinations Buses */
	
    /**
     * Search bus from to.
     *
     * @param from the from
     * @param to the to
     * @param coach the coach
     * @param date the date
     * @return the json array
     */
	public JsonArray SearchBusFromTo(String from, String to, String coach, String date) {
		JsonArray buses = new JsonArray();
		String sql = null;
		Connection con = null;
		Statement statement = null;
		ResultSet result = null;
		if(coach != null && !coach.equals("any")) {
			sql = "SELECT D.*,B.TYPE as coach,B.BUS_ID as busId,B.NAME,B.BUS_NO FROM BUSES B"
					+ " INNER JOIN DESTINATIONS D ON " + " B.BUS_ID = D.BUS_ID" + " WHERE D.STATION_FROM = '" + from
					+ "' AND D.STATION_TO = '" + to + "' AND B.TYPE = '" + coach + "'" + " ORDER BY B.NAME ASC";
		}else {
			sql = "SELECT D.*,B.TYPE as coach,B.BUS_ID as busId,B.NAME,B.BUS_NO FROM BUSES B"
					+ " INNER JOIN DESTINATIONS D ON " + " B.BUS_ID = D.BUS_ID" + " WHERE D.STATION_FROM = '" + from
					+ "' AND D.STATION_TO = '" + to + "' ORDER BY B.NAME ASC";
		}
		
		try {
			con = Database.getDBConnection();
			statement = con.createStatement();
			result = statement.executeQuery(sql);
			while(result.next()) {
				JsonObject tempBus = new JsonObject();
				tempBus.addProperty("name", result.getString("NAME"));
				tempBus.addProperty("destination_id", result.getString("DESTINATION_ID"));
				tempBus.addProperty("coach", result.getString("coach"));
				tempBus.addProperty("busId", result.getString("busId"));
				tempBus.addProperty("busNo", result.getString("BUS_NO"));
				tempBus.addProperty("time", result.getString("TIME"));
				tempBus.addProperty("fare", result.getString("FARE"));
				buses.add(tempBus);
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
		
		return buses;
	}
	
    /**
     * Save.
     *
     * @return the int
     */
	public int Save() {
		String sqlQquery = "";
		Connection con = null;
		Statement statement = null;
		try {
			sqlQquery = "INSERT INTO BUSES(bus_id,name,bus_no,total_seat,type)" + " VALUES(BUS_ID_SEQ.NEXTVAL,'"
					+ this.name + "','"
					+ this.code + "','" + Integer.toString(this.totalSeat) + "','" + this.type + "')";
			con = Database.getDBConnection();
			statement = con.createStatement();
			return statement.executeUpdate(sqlQquery, Statement.RETURN_GENERATED_KEYS);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				Database.close(con, statement, null);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}
}