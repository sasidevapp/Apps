/*
 * FileName: Helper.java
 * Author  : sasi1901
 * 
 * Using JRE 1.8.0_212
 * 
 * REVISION         DATE            NAME     DESCRIPTION
 * 511.101       Feb 10, 2020       Sasi   Initial Code
 */
package com.sasi;

import java.util.HashMap;
import java.util.Map;

/**
 * The Class Helper.
 */
public class Helper {

    /** The base url. */
    public static final String baseUrl = "http://localhost:8082/RedBus/";

    /**
     * Bus type.
     *
     * @return the map
     */
    public static Map<String, String> BusType() {
        Map<String, String> coach = new HashMap<>();
        coach.put("SLEEPER", "SLEEPER");
        coach.put("SEATER", "SEATER");
        coach.put("SEMI-SLEEPER", "SEMI-SLEEPER");
        return coach;
    }
}
