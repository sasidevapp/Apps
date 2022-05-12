/*
 * FileName: User.java
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

import javax.servlet.http.HttpSession;

/**
 * The Class User.
 */
public class User {

    /** The id. */
    public String id = "";

    /** The name. */
    public String name = "";

    /** The email. */
    public String email = "";

    /** The phone. */
    public String phone = "";

    /** The password. */
    public String password = "";

    /** The rule. */
    public String rule = "";

    /** The address. */
    public String address = "";

    /**
     * Instantiates a new user.
     *
     * @param argId the arg id
     */
    public User(String argId) {
        SetUserFromId(argId);
    }


    /**
     * Instantiates a new user.
     */
    public User() {
    }

    /**
     * Sets the user from id.
     *
     * @param userId the arg id
     */
    public void SetUserFromId(String userId) {
        Connection con = null;
        Statement statement = null;
        ResultSet result = null;
        String sqlArg = "SELECT * FROM REDBUS_USERS WHERE USER_ID='" + userId + "'";
        try {
            con = Database.getDBConnection();
            statement = con.createStatement();
            result = statement.executeQuery(sqlArg);
            while(result.next()) {
                name = result.getString("name");
                id = result.getString("user_id");
                email = result.getString("email");
                phone = result.getString("phone");
                rule = result.getString("rule");
                address = result.getString("address");
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
     * Checks if is email or phone exist.
     *
     * @param phoneOrEmail the phone or email
     * @return true, if is email or phone exist
     */
    public boolean isEmailOrPhoneExist(String phoneOrEmail) {
        Connection con = null;
        Statement statement = null;
        ResultSet result = null;
        boolean isExist = false;
        String queryString =
            "SELECT * FROM REDBUS_USERS WHERE phone = '" + phoneOrEmail + "' OR email = '" + phoneOrEmail
            + "'";
        try {
            con = Database.getDBConnection();
            statement = con.createStatement();
            result = statement.executeQuery(queryString);
            if(result.next()) {
                isExist = true;
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

        return isExist;
    }

    /**
     * Do login.
     *
     * @param argUser the arg user
     * @param argPass the arg pass
     * @return the long
     */
    public long doLogin(String argUser,String argPass) {
        Connection con = null;
        Statement statement = null;
        ResultSet result = null;
        int returnData = 0;
        String queryString = "SELECT USER_ID from REDBUS_USERS WHERE phone = '" + argUser + "' OR email = '"
            + argUser + "' AND password = '" + argPass + "'";
        try {
            con = Database.getDBConnection();
            statement = con.createStatement();
            result = statement.executeQuery(queryString);
            if(result.next()) {
                System.out.println(result.getInt("USER_ID"));
                returnData = result.getInt("USER_ID");
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
     * Sets the user session.
     *
     * @param sessionArg the session arg
     */
    public void SetUserSession(HttpSession sessionArg) {
        sessionArg.setAttribute("user_id", id);
    }

    /**
     * Resiter user.
     *
     * @return true, if successful
     */
    public boolean ResiterUser() {
        long userId =InsertNew();
        System.out.println(userId);
        if(userId != 0) {
            return true;
        }
        return false;
    }

    /**
     * Insert new.
     *
     * @return the long
     */
    private long InsertNew() {
        Connection con = null;
        Statement statement = null;
        long lastUserId = 0;
        if(isEmailOrPhoneExist(email) || isEmailOrPhoneExist(phone)) {
            return lastUserId;
        }
        String sqlQquery = "INSERT INTO REDBUS_USERS "
            + "(user_id,name,email,phone,password,address,rule) " + " VALUES(USER_ID_SEQ.NEXTVAL,'" + name
            + "','" + email + "','" + phone + "','" + password + "','" + address + "','"
            + rule + "')";
        try {
            con = Database.getDBConnection();
            statement = con.createStatement();
            lastUserId = statement.executeUpdate(sqlQquery, Statement.RETURN_GENERATED_KEYS);

        } catch (Exception e) {
            System.out.println("User.InsertNew: "+e.getMessage());
        } finally {
            try {
                Database.close(con, statement, null);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return lastUserId;
    }

    /**
     * Check regis validation.
     *
     * @return the string
     */
    public String CheckRegisValidation() {
        String msg = null;
        if (name.equals("")) {
            msg = "User Full Name Required!";
        } else if (email.equals("")) {
            msg = "Email is Required!";
        } else if (password.equals("")) {
            msg = "Password is Required!";
        } else if (phone.equals("")) {
            msg = "Phone is Required!";
        }
        return msg;
    }

}
