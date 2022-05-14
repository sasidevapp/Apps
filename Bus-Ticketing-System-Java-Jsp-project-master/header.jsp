<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.sasi.Helper,com.sasi.User" %>
<%
boolean isLogin = false;
if(session.getAttribute("isUserLogin") != null)
	isLogin = (boolean) session.getAttribute("isUserLogin");

%>

<!doctype html>
<html lang="en">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/assets/owl.carousel.min.css"/>
    <link rel="stylesheet" href="css/assets/owl.theme.default.css"/>
    <link rel="stylesheet" href="css/main.css"/>
    <script src="js/jquery-1.11.2.min.js"></script>
    <script src="https://kit.fontawesome.com/a076d05399.js"></script>
  </head>
  <body>
  	<div class="header">
  		<div class="container">
  			<div class="header_content ">
  				Red Bus
  			</div>
  			<div class="main_nav">
  				<ul class="nav nav-pills pull-left">
  					<% if(isLogin){ 
  						String userId = (String) session.getAttribute("user_id");
  						User user = new User(userId);
  						if(user.rule.equals("admin")){
  							%>
  							<li  class="nav-item"><a href="<%= Helper.baseUrl %>Dashboard.jsp" class="nav-link">Dashboard</a></li>
							<li  class="nav-item"><a href="<%= Helper.baseUrl %>BusList.jsp" class="nav-link">Buses</a></li>
							<li  class="nav-item"><a href="<%= Helper.baseUrl %>StationList.jsp" class="nav-link">Stations</a></li>
							<li  class="nav-item"><a href="<%= Helper.baseUrl %>Destinations.jsp" class="nav-link">Destinations</a></li>
							<li class="nav-item"><a href="<%= Helper.baseUrl %>Logout" class="nav-link">Logout</a></li>
							<li class="nav-item"><span class="nav-link" style="position:relative;left:300px"><strong>Welcome <%= user.name.toUpperCase() %>!!!</strong></span></li>
  							<%
  						}
  						else{
  							%>
  							<li  class="nav-item"><a href="<%= Helper.baseUrl %>Dashboard.jsp" class="nav-link">Dashboard</a></li>
  							<li  class="nav-item"><a href="<%= Helper.baseUrl %>Purchase.jsp" class="nav-link">Book Tickets</a></li>
  							<li class="nav-item"><a href="<%= Helper.baseUrl %>Logout" class="nav-link">Logout</a></li>
  							<li class="nav-item"><span class="nav-link" style="position:relative;left:550px"><strong>Welcome <%= user.name.toUpperCase() %>!!!</strong></span></li>
  						<%
  						}
  					}
  					else{
  					%>
  						<li class="nav-item"><a href="<%= Helper.baseUrl %>Register.jsp" class="nav-link">Register</a></li>
  						<li class="nav-item"><a href="<%= Helper.baseUrl %>Login.jsp" class="nav-link">Login</a></li>
  					<% } %>
  				</ul>
  			</div>
  		</div>
  	</div>
  	<section class="main_contents">
  		<div class="container">
  		<div class="main_contents_inner" >
