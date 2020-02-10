/*
 * FileName: Stations.java
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

import AllLayout.Station;

/**
 * The Class Stations.
 */
public class Stations {

    /** The id. */
    public String id = "";

    /** The name. */
    public String name = "";

    /** The contact. */
    public String contact = "";

    /** The address. */
    public String address = "";

    /**
     * Gets the station name.
     *
     * @param stationId the station id
     * @return the station name
     */
	public String getStationName(String stationId) {
		Connection con = null;
		Statement statement = null;
		ResultSet result = null;
		String returnData = null;
		String queryString = "SELECT NAME FROM STATIONS WHERE STATION_ID = " + stationId;
		try {
			con = Database.getDBConnection();
			statement = con.createStatement();
			result = statement.executeQuery(queryString);
			if (result.next()) {
				System.out.println(result.getString("NAME"));
				returnData = result.getString("NAME");
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
		return returnData;
	}

    /**
     * Gets the station.
     *
     * @param staionId the staion id
     * @return the station
     */
	public Station getStation(String staionId) {
		Station station = new Station();
		String sqlQuery = "SELECT * FROM STATIONS WHERE STATION_ID='" + staionId + "'";
		Connection con = null;
		Statement statement = null;
		ResultSet result = null;
		try {
			con = Database.getDBConnection();
			statement = con.createStatement();
			result = statement.executeQuery(sqlQuery);
			while(result.next()) {
				station.name = result.getString("name");
				station.id = result.getString("station_id");
				station.address = result.getString("address");
				station.contact = result.getString("contact");
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
		
		
		
		return station;
	}

    /**
     * Gets the all.
     *
     * @return the all
     */
	public List<Object> getAll() {
		List<Object> stations = new ArrayList<Object>();

		String sqlQuery = "SELECT * FROM STATIONS ORDER BY NAME ASC";
		Connection con = null;
		Statement statement = null;
		ResultSet result = null;
		try {
			con = Database.getDBConnection();
			statement = con.createStatement();
			result = statement.executeQuery(sqlQuery);
			while(result.next()) {
				Station temp = new Station();
				temp.id = result.getString("station_id");
				temp.name = result.getString("name");
				temp.contact = result.getString("contact");
				temp.address = result.getString("address");
				stations.add(temp);
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

		return stations;
	}

    /**
     * Gets the allstation.
     *
     * @return the allstation
     */
	public List<Station> getAllstation() {
		List<Station> stations = new ArrayList<Station>();

		String sqlQuery = "SELECT * FROM STATIONS ORDER BY NAME ASC";
		Connection con = null;
		Statement statement = null;
		ResultSet result = null;
		try {
			con = Database.getDBConnection();
			statement = con.createStatement();
			result = statement.executeQuery(sqlQuery);
			while (result.next()) {
				Station temp = new Station();
				temp.id = result.getString("station_id");
				temp.name = result.getString("name");
				temp.contact = result.getString("contact");
				temp.address = result.getString("address");
				stations.add(temp);
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

		return stations;
	}

    /**
     * Save.
     *
     * @return the int
     */
	public int Save() {
		Connection con = null;
		Statement statement = null;
		try {
			String query = "INSERT INTO STATIONS(station_id,name, contact,address) "
					+ " VALUES(STATION_ID_SEQ.NEXTVAL,'"
					+ this.name + "','"
					+ this.contact + "','" + this.address + "')";
			con = Database.getDBConnection();
			statement = con.createStatement();
			return statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
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
