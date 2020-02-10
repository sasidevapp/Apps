/*
 * FileName: Destination.java
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
import java.util.HashMap;
import java.util.List;

/**
 * The Class Destination.
 */
public class Destination {

    /** The id. */
    public String id;

    /** The station from. */
    public String station_from;

    /** The station to. */
    public String station_to;

    /** The train id. */
    public String busId;

    /** The time. */
    public String time;

    /** The status. */
    public String status;

    /** The fare. */
    public String fare;

    /**
     * Instantiates a new destination.
     */
	public Destination() {
		id = station_from = station_to = busId = time = status = fare = "";
	}

    /**
     * Instantiates a new destination.
     *
     * @param argId the arg id
     */
	public Destination(String argId) {
		String sql = "SELECT * FROM DESTINATIONS WHERE destination_id = '" + argId + "'";
		Connection con = null;
		Statement statement = null;
		ResultSet result = null;
		try {
			con = Database.getDBConnection();
			statement = con.createStatement();
			result = statement.executeQuery(sql);
			if(result.next()) {
				this.id = result.getString("destination_id");
				this.station_from = result.getString("station_from");
				this.station_to = result.getString("station_to");
				this.busId = result.getString("bus_id");
				this.time = result.getString("time");
				this.status = result.getString("status");
				this.fare = result.getString("fare");
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
     * @param stationFrom the station from
     * @param trainId the train id
     * @return the all
     */
	public List<HashMap<String, String>> getAll(String stationFrom, String trainId) {
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		 String queryString = null;
		Connection con = null;
		Statement statement = null;
		ResultSet result = null;
		try {
			if (trainId == null) {
				queryString = "SELECT * FROM DESTINATIONS WHERE station_from ='" + stationFrom + "'";
			} else {
				queryString = "SELECT * FROM DESTINATIONS WHERE station_from ='" + stationFrom + "' AND bus_id = '"
						+ trainId + "'";
			}
			con = Database.getDBConnection();
			statement = con.createStatement();
			result = statement.executeQuery(queryString);
			
			while(result.next()) {
				HashMap<String,String> tempData = new HashMap<String,String>();
				tempData.put("station_from", result.getString("station_from"));
				tempData.put("id", result.getString("destination_id"));
				tempData.put("station_to", result.getString("station_to"));
				tempData.put("bus_id", result.getString("bus_id"));
				tempData.put("time", result.getString("time"));
				tempData.put("status", result.getString("status"));
				tempData.put("fare", result.getString("fare"));
				list.add(tempData);
				
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
		 return list;
	}

    /**
     * Gets the all.
     *
     * @param stationFrom the station from
     * @return the all
     */
	public List<HashMap<String, String>> getAll(String stationFrom) {
		return this.getAll(stationFrom,null);
	}


    /**
     * Delete.
     */
	public void Delete() {
		String querySting = "DELETE FROM DESTINATIONS WHERE destination_id='" + this.id + "'";
		Connection con = null;
		Statement statement = null;
		try {
			con = Database.getDBConnection();
			statement = con.createStatement();
			statement.executeUpdate(querySting);
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

	
    /**
     * Save.
     *
     * @return the int
     */
	public int Save() {
		String sqlQury = "INSERT INTO DESTINATIONS"
				+ " (destination_id,station_from,station_to,bus_id,time,status,fare) "
				+ " VALUES(DESTINATION_ID_SEQ.NEXTVAL,'" + this.station_from + "','" + this.station_to + "','"
				+ this.busId + "','" + time + "','" + status + "','" + fare + "')";
		Connection con = null;
		Statement statement = null;
		try {
			con = Database.getDBConnection();
			statement = con.createStatement();
			return statement.executeUpdate(sqlQury, Statement.NO_GENERATED_KEYS);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				Database.close(con, statement, null);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}
}
