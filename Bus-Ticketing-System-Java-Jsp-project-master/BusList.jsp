<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ page import="com.sasi.Helper,com.sasi.Buses,java.util.ArrayList,AllLayout.Bus,java.util.Iterator" %>   
<%@ include file="header.jsp" %>
<%
	Buses buses = new Buses();

	if(request.getParameter("delete") != null){
		String busId = (String) request.getParameter("delete");
		buses.Delete(busId);
	}

	ArrayList<Bus> buslist = new ArrayList<Bus>();
	buslist = buses.getAll();
	Iterator trnIt = buslist.iterator();
%>
<div class="text-right">
	<a class="btn btn-success" href="Add.jsp">Create Bus</a>
</div>
<br>
<div class="box successfully_purschase_ticket">
	<h2 class="box_title">All Bus List</h2>
	<table class="table table-bordered">
		<tr>
			<td wide="50">Train Code</td>
			<td>Name</td>
			<td>Coach</td>
			<td>Total Seat</td>
			<td>Actions</td>
		</tr>
		<%
		while(trnIt.hasNext()){
			Bus bus = (Bus) trnIt.next();
			
			%>
			<tr>
				<td><%= bus.code %></td>
				<td><%= bus.type %></td>
				<td><%= bus.name %></td>
				<td><%= bus.totalSeat %></td>
				<td><a href="?delete=<%= bus.id %>" class="btn btn-sm btn-danger">Delete</a></td>
			</tr>
			<%
		}
		%>
		
	</table>
</div>
<%@ include file="footer.jsp" %>