<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.sasi.Helper,com.sasi.*,java.sql.ResultSet,AllLayout.*" %>
<%@ include file="header.jsp" %>

<div class="dashboard">
	<div class="box personal_info">
		<h2 class="box_title">Personal Information</h2>
		<table class="table table-bordered userdetails">
		</table>
	</div>
	<div class="box successfully_purschase_ticket">
		<h2 class="box_title">Successful Purchase Information</h2>
		<table class="table table-bordered bookingdetails">
			<tr>
				<td>Bus Name</td>
				<td>Type</td>
				<td>Purchase Date</td>
				<td>Journey Date</td>
				<td>From</td>
				<td>To</td>
				<td>Total Seat</td>
			</tr>
		</table>
	</div>
</div>
<script type="text/javascript">
$(document).ready(function(){
	$.ajax({
	    type : "POST", 
	    url  : "${pageContext.request.contextPath}/dashboard.do?action=getDashboardDetails",
	    success : function(result){
	    	var data = JSON.parse(result);
	    	formUserDetail(data.userDetails);
	    	formBookingDetailsHtml(data.bookingHistory);
	    },
	    error : function(xhr, errmsg) {
	    	alert("No values found..!!");
	    }
	}); 
});

function formUserDetail(data){
	localStorage.setItem("userName",data.name);
	var userDetailHtml = '<tr>'
		+'<td><strong>Full Name</strong></td>'
		+'<td>'+data.name+'</td>'
		+'</tr>'
	    +'<tr>'
	    +'<td><strong>Email Address</strong></td>'
	    +'<td>'+data.email+'</td>'
	    +'</tr>'
	    +'<tr>'
	    +'<td><strong>Cell Phone Number</strong></td>'
	    +'<td>'+data.phone+'</td>'
	    +'</tr>'
	    +'<tr>'
	    +'<td><strong>Address</strong></td>'
	    +'<td>'+data.address+'</td>'
	    +'</tr>';
$('.userdetails').html(userDetailHtml);
}

function formBookingDetailsHtml(data){
	var userDetailHtml = "";
	for(var i=0;i<data.length;i++){
		 userDetailHtml += '<tr>'
			+'<td>'+data[i].busName+'</td>'
			+'<td>'+data[i].type+'</td>'
			+'<td>'+data[i].bookingDate+'</td>'
			+'<td>'+data[i].journeyDate+'</td>'
			+'<td>'+data[i].fromStation+'</td>'
			+'<td>'+data[i].toStation+'</td>'
			+'<td>'+data[i].numberOfSeat+'</td>'
		    +'</tr>';
	}
$('.bookingdetails').append(userDetailHtml);
}
</script>
<%@ include file="footer.jsp" %>
