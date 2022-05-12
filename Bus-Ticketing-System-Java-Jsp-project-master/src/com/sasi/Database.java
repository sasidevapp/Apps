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
            dataSource.setDriverClass("oracle.jdbc.driver.OracleDriver");
            dataSource.setJdbcUrl("jdbc:oracle:thin:@192.168.57.196:1521:orcl");
            dataSource.setUser("CSPLATFORMDEV");
            dataSource.setPassword("csplatformdev");
            dataSource.setMaxPoolSize(5);
            dataSource.setMinPoolSize(1);
            dataSource.setInitialPoolSize(5);
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
