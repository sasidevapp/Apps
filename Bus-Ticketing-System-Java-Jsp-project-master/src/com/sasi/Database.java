/*
 * FileName: Database.java
 * Author  : sasi1901
 * 
 * Using JRE 1.8.0_212
 * 
 * REVISION         DATE            NAME     DESCRIPTION
 * 511.101       Feb 10, 2020       Sasi   Initial Code
 */
package com.sasi;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * The Class Database.
 */
public class Database {

    /** The connection pool. */
    private static ComboPooledDataSource connectionPool = createConnectionPool();

    /**
     * Creates the connection pool.
     *
     * @return the combo pooled data source
     */
    private static ComboPooledDataSource createConnectionPool() {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        try {
        	dataSource.setDriverClass("org.postgresql.Driver");
            dataSource.setJdbcUrl("jdbc:postgresql://ec2-52-4-104-184.compute-1.amazonaws.com:5432/ddshttahk9btj7?sslmode=require");
            dataSource.setUser("tlznodimqwirfj");
            dataSource.setPassword("82710d527913abe9d8e10657fe5a491bd2e076029d7abe3c1cb9ff9d924578f4");
            dataSource.setMaxPoolSize(5);
            dataSource.setMinPoolSize(1);
            dataSource.setInitialPoolSize(5);
            dataSource.setMaxIdleTimeExcessConnections(300); // seconds
            dataSource.setIdleConnectionTestPeriod(300); // seconds
            dataSource.setMaxIdleTime(600); // seconds
            dataSource.setCheckoutTimeout(15000); // milliseconds
            dataSource.setAcquireIncrement(5);
            dataSource.setPreferredTestQuery("SELECT 1 FROM DUAL");
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        return dataSource;
    }

    /**
     * Gets the DB connection.
     *
     * @return the DB connection
     * @throws SQLException the SQL exception
     */
    public static Connection getDBConnection() throws SQLException {
        return connectionPool.getConnection();
    }

    /**
     * Close.
     *
     * @param con the con
     * @param stmt the stmt
     * @param rs the rs
     * @throws SQLException the SQL exception
     */
    public static void close(Connection con, Statement stmt, ResultSet rs) throws SQLException {
        if (rs != null) {
            rs.close();
        }

        if (stmt != null) {
            stmt.close();
        }

        if (con != null && !con.isClosed()) {
            con.close();
        }
    }
}
