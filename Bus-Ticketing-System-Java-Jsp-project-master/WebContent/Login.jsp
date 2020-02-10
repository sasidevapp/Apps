<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ page import="com.sasi.Helper" %>
<%@ page import="com.sasi.User" %>
<%@ include file="header.jsp" %>
<div class="signpage">
	<!-- <form class="register_form form_login"> -->
		
		<div class="row">
			<div class="col-xs-12 col-sm-8">
			<div class="owl-carousel home_page_slider">
			  <div class="item">
			  	<div class="rs_single_slide" style="background-image:url(images/slide1.jpg)">
			  	</div>
			  </div>
			  <div  class="item">
			  	<div class="rs_single_slide" style="background-image:url(images/slide2.jpg)">
			  	</div>
			  </div>
			  <div  class="item">
			  	<div class="rs_single_slide" style="background-image:url(images/slide3.jpg)">
			  	</div>
			  </div>
			  <div  class="item">
			  	<div class="rs_single_slide" style="background-image:url(images/slide4.jpg)">
			  	</div>
			  	
			  </div>
			</div>
			</div>
			<div class="col-xs-12 col-sm-4">
				<div class="alert alert-danger" style="display:none"><p class="alertMsg"></p></div>
				
				<div class="rs_form_box">
					<h3 class="form_section_title">
						Login
					</h3>
					<div class="input-group">
						<label>Mobile*</label>
						<input type="text" name="phone" class="form-controller" id="phone">
					</div>
					<div class="input-group">
						<label>Password</label>
						<input type="password" name="password" class="form-controller" id="password">
					</div>
				</div>
				<div class="text-center">
					<div class="rs_btn_group">
						<button class="btn btn-default login" name="submit" type="submit">Login</button>
						<a href="<%= Helper.baseUrl %>Register.jsp" class="btn btn-default">Register</a>
					</div>
				</div>
			</div>
			
		</div>
	<!-- </form> -->
</div>
<script type="text/javascript">
$(document).ready(function(){
	$('.login').on('click',function(){
		var phone = $("#phone").val();
		var password =$("#password").val(); 
		$.ajax({
		    type : "POST", 
		    url  : "${pageContext.request.contextPath}/login.do?action=doLogin",
		    data : {"phone":phone,
		    		"password":password
		    		},		
		    success : function(result){
		    	if(result!=null&result!="")
		    		result = JSON.parse(result);  
		    	if(result.msg!=null && result.msg!=""){
		    		$(".alert-danger").css("display","block");
		    		$(".alertMsg").text(result.msg);
		    	}
		    	else{
		    		location.href ="<%= Helper.baseUrl %>Dashboard.jsp";
		    	}
		    },
		    error : function(xhr, errmsg) {
		    	alert("No values found..!!");
		    }
		}); 
	});
});
</script>
<%@ include file="footer.jsp" %>