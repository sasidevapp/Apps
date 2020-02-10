<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.*,com.sasi.*,AllLayout.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ include file="header.jsp" %>
<div class="signpage">
	<div class="register_form" style="max-width:400px;">
		<div class="rs_form_box">
			<h3 class="form_section_title">
				 PURCHASE TICKET
			</h3>
			<div class="form-group">
				<label>Station From :</label>
				<select class="form-control fromStation" name="from" id="from_where_select">
				</select>
			</div>
			
			<div class="form-group">
				<label>Journey Date :</label>
				<select class="form-control jorneyDate" name="journey_date" id="from_where_select">
					<%
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY");
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DAY_OF_YEAR, 0);

					for(int i = 0; i< 10; i++){
					    cal.add(Calendar.DAY_OF_YEAR, 1);
					    String tempDtate = dateFormat.format(cal.getTime());
					    %>
					    <option value="<%= tempDtate %>"><%= tempDtate %></option>
					    <%
					}
					%>
					
				</select>
			</div>
			
			<div class="form-group">
				<label>Station To :</label>
				<select class="form-control toStation" name="to" id="from_where_select">
				</select>
			</div>
			
			<div class="form-group">
				<label>Coach Type :</label>
				<select class="form-control coachType" name="coach" id="from_where_select" >
				<option value="any">Any Coach</option>
					<option value="Sleeper">Sleeper</option>
					<option value="SemiSleeper">Semi Sleeper</option>
					<option value="Seater">Seater</option>
				</select>
			</div>

		</div>
		<div class="text-center">
			<div class="rs_btn_group">
				<button class="btn btn-default searchBuses" type="submit">Search</button>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
$(document).ready(function(){
	$.ajax({
	    type : "POST", 
	    url  : "${pageContext.request.contextPath}/purchase.do?action=getStationInfo",
	    success : function(result){
	    	var data = JSON.parse(result);
	    	formStationDetailsHtml(data.stationInfo);
	    },
	    error : function(xhr, errmsg) {
	    	alert("No values found..!!");
	    }
	}); 
	$(".searchBuses").on('click',function(){
		var from = $(".fromStation").val();
		var to = $(".toStation").val();
		var date = $('.jorneyDate').val();
		var coachType = $('.coachType').val();
		var travelInfo = {
				"from":from,
				"to":to,
				"date":date,
				"coachType":coachType
		};
		localStorage.setItem("travelInfo",JSON.stringify(travelInfo));
		location.href ="<%= Helper.baseUrl %>TicketDisplay.jsp";
	});
	
});


function formStationDetailsHtml(data){
	var userDetailHtml = "";
	for(var i=0;i<data.length;i++){
		 userDetailHtml += '<option value="'+data[i].id+'" name="'+data[i].name+'">'+data[i].name+'</option>';
	}
$('.fromStation').append(userDetailHtml);
$('.toStation').append(userDetailHtml);
}
</script>
<%@ include file='footer.jsp' %>