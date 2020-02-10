<%@page import="java.util.*,AllLayout.*,com.sasi.*"%>
<%@ include file="header.jsp"%>
<div class="ticket_selecting_form"></div>

<div id="rs_ticket_result">
</div>

<div id="ticketViewDialog"  title="" style="display: none;">
	    <div class="close-window" style="float:right;padding:10px"><i class="far fa-times-circle "></i></div>
	    <div class="ticket-legend" style="float: right;padding: 25px;">
		     <img src="https://img.icons8.com/ultraviolet/2x/theatre-seat.png" style="height:20px;width:20px;"> Available </img>
		     <img src="https://img.icons8.com/office/2x/theatre-seat.png" style="height:20px;width:20px;"> Booked </img>
		</div>
		<div id="ticketvieweModel" class="clearfix cs-mar cs-viewer-content " style="margin: -3px 0px 0px -1px; width: 100%; height:250px;display:flex;flex-wrap: wrap;">					
		</div>
	</div>
	<div class='ticketInfo' style='display:none;'>
	<div class="ticket_info">
			<h2>Congratulation! Your ticket has been booked.</h2>
		</div>
		<div class="ticket_print_section">
	<div class="rs_shadow single_ticket" style="background-image: url('images/ticket_bg.jpg');">
		<div class="ticket_header">
			<h2>Ticket Details</h2>
		</div>
		<div class="ticket_inner">
			<div class="customer_part">
				
			</div>
			<div class="company_part">
				
			</div>
			<div class="clearfix"></div>
		</div>
		<div class="ticet_footer">
			<span>Have a nice journey.</span>
		</div>
	</div>
</div>
</div>
<script type="text/javascript">
var bookingObj = {};
   $(document).ready(function(){
	   var bookingObj = {"bookedSeat":[1,2,3,5,7,8,9,10],totalSeat:40,busId:'11'};
	   var searchObj = JSON.parse(localStorage.travelInfo);
	   	var searchInfo = {
			"from":searchObj.from,
			"to":searchObj.to,
			"date":searchObj.date,
			"coachType":searchObj.coachType
	};
	$.ajax({
	    type : "POST", 
	    data : {"searchInfo":JSON.stringify(searchInfo)},
	    url  : "${pageContext.request.contextPath}/buses.do?action=getBusInfo",
	    success : function(result){
	    	var data = JSON.parse(result);
	    	formStationDetailsHtml(data);
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
	



function formStationDetailsHtml(data){
	var userDetailHtml = "";
	localStorage.setItem("stationFromName",data.stationFrom);
	localStorage.setItem("stationToName",data.stationTo);
	userDetailHtml = '<h2 class="title">  <span>Buses For ::</span> '+data.stationFrom+'  to '+data.stationTo+'<span class="journeyDate"> :: Journey Date -</span> '+data.journeyData+' </h2>'
						+'<table class="table rs_shadow">'
						+'<tr>'
						+'<th>Serial</th>'
						+'<th>Train No</th>'
						+'<th>Train Name</th>'
						+'<th>Class</th>'
						+'<th>Departure Time</th>'
						+'<th>Unit Fare</th>'
						+'<th>Selection</th>'
						+'</tr>';
					for(var i =0; i<data.busDetails.length; i++){ 
						var j = i+1;
					userDetailHtml	+='<tr>'
									+'<td>'+j+'</td>'
									+'<td>'+data.busDetails[i].busNo+'</td>'
									+'<td>'+data.busDetails[i].name+'</td>'
									+'<td>'+data.busDetails[i].coach+'</td>'
									+'<td>'+data.busDetails[i].time+'</td>'
									+'<td>'+data.busDetails[i].fare+'</td>'
									+'<td>'
									+'<a href="javascript:;" class="btn btn-success rs_search_ticket" busId ="'+data.busDetails[i].busId+'" destinationId="'+data.busDetails[i].destination_id+'"'  
									+' busName="'+data.busDetails[i].name+'" time="'+data.busDetails[i].time+'" busNo="'+data.busDetails[i].busNo+'" fare="'+data.busDetails[i].fare+'"' 
									+'onclick="searchTickets(this)">Search Ticket</a>'
									+'</td>'
									+'</tr>';
					}
					userDetailHtml	+='</table>';
					$("#rs_ticket_result").html(userDetailHtml);
	
}
	   
	   $(document).on('click','.close-window',function(e){
		   $('#ticketvieweModel').empty();
		   $('#ticketViewDialog').css('display','none');
		   e.stopPropagation();
	   });
	   
	   $(document).on('click','.cs-hold-now img',function(e){
		   if($(this).hasClass('cs-seat-booked'))
			   alert('Seat already booked by other, kindly choose available seats');
		   else{
			   if($(this).hasClass('active')){
				   $(this).removeClass('active');
				   $(this).css('background','none');
			   }else{
				   $(this).addClass('active');
				   $(this).css('background','green');
			   }
		   }
		  e.stopPropagation();
	   });
	   
	   $(document).on('click','#bookNow',function(e){
		   var currentBookingSeatNo = [];
		   $('.test').each(function(){
	        	if($(this).hasClass('active'))
	        		currentBookingSeatNo.push($(this).parent().attr('data-seat-no'));
	        });
		   var busId = $(this).attr('data-bus-id');
		   var destinationId = $(this).attr('destinationId');
		   var searchObj = JSON.parse(localStorage.travelInfo);
		   localStorage.setItem("seats",JSON.stringify(currentBookingSeatNo));
		   $.ajax({
			    type     : 'POST',
				url      : "${pageContext.request.contextPath}/book.do?action=bookNow",
				data     : {'busId':busId,'destinationId':destinationId,'seatArr':JSON.stringify(currentBookingSeatNo),"date":searchObj.date},
				success  : function(response){
					$('.close-window').trigger('click');
					$(".ticketInfo").css("display","block");
					showTicketDetails();
			    },
			    error : function(){
			    	alert('Something went wrong');
			    }
		   });
	   });
	   
   });
   
   function searchTickets(e){
	     var seatHtml = '';
	     var count = 1;
	     var busId = e.getAttribute("busId");
	     var destinationId = e.getAttribute("destinationId");
	     var searchObj = JSON.parse(localStorage.travelInfo);
	     localStorage.setItem("busName", e.getAttribute("busName"));
	     localStorage.setItem("busNo", e.getAttribute("busNo"));
	     localStorage.setItem("time", e.getAttribute("time"));
	     localStorage.setItem("fare", e.getAttribute("fare"));
	     $.ajax({
			    type     : 'POST',
				url      : "${pageContext.request.contextPath}/book.do?action=getAvailableSeats",
				data     : {'busId':busId,"journeyDate":searchObj.date,'destinationId':destinationId},
				success  : function(response){
					var bookingObj = JSON.parse(response);
					 var bookedSeat = bookingObj['bookedSeat'];
				     var totalSeat  = bookingObj['totalSeat'];
				     for(var i = 1; i <= totalSeat; i++ ) {
				    	 count = (count > 2) ? 1 : count;
				    	 if(count == 1)
				    		 seatHtml += "<div style='display:flex;padding: 0px 10px 20px 0px;width:100px;height:65px;flex-direction:column;'>";
				    	 if(bookedSeat.indexOf(i) == -1) {
				    		 seatHtml += '<div class="cs-seat-available cs-hold-now" data-seat-no = "'+i+'" data-bus-id = "'+bookingObj['busId']+'" destinationId = "'+bookingObj['destinationId']+'">'
				    	 			+'<img alt="Seat '+i+'" src="https://img.icons8.com/ultraviolet/2x/theatre-seat.png" style="height:40px;width:40px;cursor: pointer;" class="test"><sup style="left: 18px;">'+i+'</sup></div>'; 
				    	 }else{
				    		 seatHtml += '<div class="cs-seat-booked cs-book-now" data-seat-no = "'+i+'" data-bus-id = "'+bookingObj['busId']+'" destinationId = "'+bookingObj['destinationId']+'">'
			  	 			+'<img alt="Seat '+i+'" src="https://img.icons8.com/office/2x/theatre-seat.png" style="height:40px;width:40px;cursor: pointer;"><sup style="left: 18px;">'+i+'</sup></div>'; 
				    	 }
				         if(count == 2)
				        	 seatHtml += "</div>";
				         count ++;
				        
				     }
				     seatHtml += '<button class="btn btn-success rs_search_ticket" id="bookNow" data-bus-id = "'+bookingObj['busId']+'" destinationId = "'+bookingObj['destinationId']+'">Book Now</button>';
				     $('#ticketvieweModel').html(seatHtml);
				     $('#ticketViewDialog').css('display','block');
			    },
			    error : function(){
			    	alert('Something went wrong');
			    }
		   });
	     
 }
   
function showTicketDetails(){
	var html = '';
	var companyPartHtml = '';
	var userName = localStorage.getItem('userName').toUpperCase();
	var busName = localStorage.getItem('busName');
	var busNo = localStorage.getItem('busNo');
	var time = localStorage.getItem('time');
	var fare = localStorage.getItem('fare');
	var stationFromName = localStorage.getItem('stationFromName');
	var stationToName = localStorage.getItem('stationToName');
	var searchObj = JSON.parse(localStorage.travelInfo);
	var seat = JSON.parse(localStorage.seats);
	var seatNumbers = '';
	var b = JSON.parse(localStorage.seats);
	for(var i =0;i<b.length;i++){
		seatNumbers+=","+b[i];
	}
	html+='<h4 class="name">'
					+'<strong>Name of passenger</strong>'+userName
					+'</h4>'
					+'<div class="ticket_col_1">'
					+'<table>'
					+'<tr>'
					+'<td>'
					+'<strong>Bus Name</strong>'
					+'<span>'+busName+'</span>'
					+'<strong>From</strong>'
					+'<span>'+stationFromName+'</span>'
					+'<strong>To</strong>'
					+'<span>'+stationToName+'</span>'
					+'</td>'
					+'<td class="wd_100px text_center">'
					+'<strong>Bus No</strong>'+busNo
					+'</td>'
					+'<td class="wd_100px text_center">'
					+'<strong>Journey Date</strong>'+searchObj.date
					+'</td>'
					+'<td class="wd_100px text_center">'
					+'<strong>Time</strong>'+time
					+'</td>'
					+'</tr>'
					+'</table>'
					+'<table>'
					+'<tr>'
					+'<td>'
					+'<strong>Coach</strong>'
					+'<span>'+searchObj.coachType+'</span>'
					+'</td>'
					+'<td  class="wd_100px text_center">'
					+'<strong>Seat</strong>'+seatNumbers.slice(1,seatNumbers.length)
					+'</td>'
					+'<td  class="text_center">'
					+'<strong>Issue Date</strong>'+searchObj.date
					+'</td>'
					+'<td  class="wd_100px text_center">'
					+'<strong>Fare</strong>'+fare
					+'</td>'
					+'<td width="90"  class="text_center">'
					+'<img class="qr_code" src="images/qr.png" alt="">'
					+'</td>'
					+'</tr>'
					+'</table>';
					
					$('.customer_part').html(html);
					
					companyPartHtml +='<h4>'
									+'<strong>Name of passenger</strong>'+userName
									+'</h4>'
									+'<strong>Bus Name</strong>'
									+'<span>'+busName+'</span>'
									+'<strong>Journey</strong>'
									+'<span>'+stationFromName+' To '+stationToName+'</span>'
									+'<table>'
									+'<tr>'
									+'<td>'
									+'<strong>Seat</strong>'+seatNumbers.slice(1,seatNumbers.length)
									+'</td>'
									+'<td>'
									+'<strong>  Date</strong>'+searchObj.date
									+'</td>'
									+'<td>'
									+'<strong>Time</strong>'+time
									+'</td>'
									+'</tr>'
									+'<tr>'
									+'<td>'
									+'<strong>Fare</strong>'+fare
									+'</td>'
									+'<td>'
									+'<strong>Issue Date</strong>'+searchObj.date
									+'</td>'
									+'<td>'
									+'</td>'
									+'</tr>'
									+'</table>';
					$('.company_part').html(companyPartHtml);
}
</script>