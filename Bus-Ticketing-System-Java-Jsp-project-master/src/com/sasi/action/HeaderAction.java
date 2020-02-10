/*
 * FileName: HeaderAction.java
 * Author  : sasi1901
 * 
 * Using JRE 1.8.0_212
 *  
 * REVISION         DATE            NAME     DESCRIPTION
 * 511.101       Feb 10, 2020       Sasi   Initial Code  
 */
package com.sasi.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.sasi.Booking;
import com.sasi.Buses;
import com.sasi.Stations;
import com.sasi.User;

/**
 * The Class HeaderAction.
 */
public class HeaderAction extends CommonDispatchAction {

    /**
     * Gets the dashboard details.
     *
     * @param actionMapping the action mapping
     * @param actionForm the action form
     * @param httpServletRequest the http servlet request
     * @param httpServletResponse the http servlet response
     * @return the dashboard details
     * @throws IOException Signals that an I/O exception has occurred.
     */
	public ActionForward getDashboardDetails(ActionMapping actionMapping, ActionForm actionForm,
        HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
		try {
			PrintWriter writer;
			HttpSession session = httpServletRequest.getSession();
			String userId = (String) session.getAttribute("userId");
			JsonObject dashObj = new JsonObject();
			JsonArray bookedObjects = getBookingDetails(userId);
			JsonObject userObj = getUserDetails(userId);
			dashObj.add("userDetails", userObj);
			dashObj.add("bookingHistory", bookedObjects);
			writer = httpServletResponse.getWriter();
			writer.append(dashObj.toString());
			writer.close();
        } catch (IOException | SQLException exception) {
			exception.printStackTrace();
		}
		return null;
	}

    /**
     * Gets the booking details.
     *
     * @param userId the user id
     * @return the booking details
     * @throws SQLException the SQL exception
     */
	private JsonArray getBookingDetails(String userId) throws SQLException {
        return new Booking().FindByUser(userId);
	}

    /**
     * Gets the user details.
     *
     * @param userId the user id
     * @return the user details
     */
	private JsonObject getUserDetails(String userId) {
		User user = new User(userId);
		JsonObject userObj = new JsonObject();
		userObj.addProperty("name", user.name);
		userObj.addProperty("email", user.email);
		userObj.addProperty("phone", user.phone);
		userObj.addProperty("password", user.password);
		userObj.addProperty("address", user.address);
		return userObj;
	}

    /**
     * Convert object to json.
     *
     * @param object the object
     * @return the json object
     */
	public JsonObject convertObjectToJson(Object object) {
		String jsonContent = "";
		JsonParser parser = new JsonParser();
		JsonElement jsonEle = null;
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();
		JsonObject jsonObj = null;

		if (Serializable.class.isAssignableFrom(object.getClass()))
			jsonContent = gson.toJson(object);
		else
			jsonContent = gson.toJson(object, new TypeToken<Object>() {
			}.getType());
		jsonEle = parser.parse(jsonContent);
		if (jsonEle.isJsonObject())
			jsonObj = new JsonParser().parse(jsonContent).getAsJsonObject();
		return jsonObj;

	}

    /**
     * List to json array.
     *
     * @param listObj the list obj
     * @return the string
     */
	public String listToJsonArray(List<Object> listObj) {
		String jsonContent = "";
		GsonBuilder gsonBuilder = new GsonBuilder();

		Gson gson = gsonBuilder.create();
		jsonContent = gson.toJson(listObj, new TypeToken<Object>() {
		}.getType());
		return jsonContent;
	}

    /**
     * Gets the station info.
     *
     * @param actionMapping the action mapping
     * @param actionForm the action form
     * @param httpServletRequest the http servlet request
     * @param httpServletResponse the http servlet response
     * @return the station info
     */
	public ActionForward getStationInfo(ActionMapping actionMapping, ActionForm actionForm,
			HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		PrintWriter writer;
		try {
			Stations stations = new Stations();
			JsonObject stationInfo = new JsonObject();
			List<Object> allStations = stations.getAll();
			stationInfo.add("stationInfo", new JsonParser().parse(listToJsonArray(allStations)).getAsJsonArray());
			writer = httpServletResponse.getWriter();
			writer.append(stationInfo.toString());
			writer.close();
		} catch (IOException exception) {
			exception.printStackTrace();
		}
		return null;
	}

    /**
     * Gets the bus info.
     *
     * @param actionMapping the action mapping
     * @param actionForm the action form
     * @param request the request
     * @param httpServletResponse the http servlet response
     * @return the bus info
     */
	public ActionForward getBusInfo(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) {
		PrintWriter writer;
		try {
            JsonArray buses;
			JsonObject busDetails = new JsonObject();
            JsonObject searchInfo;
			searchInfo = new JsonParser().parse(request.getParameter("searchInfo")).getAsJsonObject();
			Buses trnObj = new Buses();
			String stationTo = searchInfo.get("to").getAsString();
			String stationFrom = searchInfo.get("from").getAsString();
			String sCoach = searchInfo.get("coachType").getAsString();
            String journeyDate = searchInfo.get("date").getAsString();
			Stations stationFromObj = new Stations();
			if (stationTo != null || stationFrom != null) {
                buses = trnObj.SearchBusFromTo(stationFrom, stationTo, sCoach, journeyDate);
				busDetails.add("busDetails", buses);
				busDetails.addProperty("stationFrom", stationFromObj.getStationName(stationFrom));
				busDetails.addProperty("stationTo", stationFromObj.getStationName(stationTo));
                busDetails.addProperty("journeyData", journeyDate);
			}
			writer = httpServletResponse.getWriter();
			writer.append(busDetails.toString());
			writer.close();
		} catch (IOException exception) {
			exception.printStackTrace();
		}
		return null;
	}

    /**
     * Gets the available seats.
     *
     * @param actionMapping the action mapping
     * @param actionForm the action form
     * @param request the request
     * @param httpServletResponse the http servlet response
     * @return the available seats
     */
	public ActionForward getAvailableSeats(ActionMapping actionMapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse httpServletResponse) {
		PrintWriter writer;
		try {
			String busId = request.getParameter("busId");
			String journeyDate = request.getParameter("journeyDate");
			String destinationId = request.getParameter("destinationId");
			JsonObject bookingObj = new Booking().getAvailableSeats(destinationId, busId, journeyDate);
			bookingObj.addProperty("busId", busId);
			bookingObj.addProperty("destinationId", destinationId);
			writer = httpServletResponse.getWriter();
			writer.append(bookingObj.toString());
			writer.close();
		} catch (IOException exception) {
			exception.printStackTrace();
		}
		return null;
	}

    /**
     * Book now.
     *
     * @param actionMapping the action mapping
     * @param actionForm the action form
     * @param request the request
     * @param httpServletResponse the http servlet response
     * @return the action forward
     * @throws IOException Signals that an I/O exception has occurred.
     */
	public ActionForward bookNow(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws IOException {
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String busId = request.getParameter("busId");
		JsonArray seatArr = new JsonParser().parse(request.getParameter("seatArr")).getAsJsonArray();
		String destinationId = request.getParameter("destinationId");
		String journeyDate = request.getParameter("date");
		List<Object> seatList = jsonArrayToList(seatArr, Integer.class);
		new Booking().BookNow(destinationId, busId, journeyDate, seatList, userId);
		return null;
	}

    /**
     * Json array to list.
     *
     * @param jsonArray the json array
     * @param typeClass the type class
     * @return the list
     */
	public List<Object> jsonArrayToList(JsonArray jsonArray, Class<?> typeClass) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		List<Object> objectList = new LinkedList<Object>();

		Gson gson = gsonBuilder.create();
		if (jsonArray != null && jsonArray.size() > 0)
			for (JsonElement jsEle : jsonArray)
				objectList.add(gson.fromJson(jsEle, typeClass));

		return objectList;
	}
}
