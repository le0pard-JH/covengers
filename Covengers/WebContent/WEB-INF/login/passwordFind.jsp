<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    String ctxPath = request.getContextPath();
    //    /MyMVC
%>    
    
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/style.css" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>

<style>
	#div_userid {
		width: 70%;
		height: 15%;
		margin-bottom: 5%;
		margin-left: 10%;
		position: relative;
	}
	
	#div_email {
		width: 70%;
		height: 15%;
		margin-bottom: 5%;
		margin-left: 10%;
		position: relative;
	}
	
	#div_findResult {
		width: 70%;
		height: 15%;
		margin-bottom: 5%;
		margin-left: 10%;		
		position: relative;
	}
	
	#div_btnFind {
		width: 70%;
		height: 15%;
		margin-bottom: 5%;
		margin-left: 10%;
		position: relative;
	}
</style>

<script type="text/javascript">
	
	$(document).ready(function(){
		
		// 찾기
		$("button#btnFind").click(function(){
			
			var frm = document.pwdFindFrm;
			frm.action = "<%= ctxPath%>/member/searchPwpage2.com";
			frm.method = "POST";
			frm.submit();

		});// end of $("button#btnFind").click(function(){})--------------------
		
		

		
		// 인증하기 
		$("button#btnConfirmCode").click(function(){
			
			var frm = document.verifyCertificationFrm;
			
			frm.userid.value = $("input#userid").val();
			frm.userCertificationCode.value = $("input#input_confirmCode").val();
			
			frm.action = "<%= ctxPath%>/member/verifyCertification.com";
			frm.method = "POST";
			frm.submit();
		});
		
	});// end of $(document).ready(function(){})---------------
	
</script>

<form name="pwdFindFrm">
	
	<input type="hidden" id="email" name="email" value="${email}"/>
	<input type="hidden" id="name" name="name" value="${name}"/>
    
   <div id="div_findResult" align="center">
   	  
   	  <c:if test="${sendMailSuccess == true}">
   	    <span style="font-size: 10pt;">인증코드가 ${email}로 발송되었습니다.</span><br>
   	    <span style="font-size: 10pt;">인증코드를 입력해주세요.</span><br>
   	    <input type="text" name="input_confirmCode" id="input_confirmCode" required />
   	    <br><br>
   	    <button type="button" class="btn btn-info" id="btnConfirmCode">인증하기</button>
   	  </c:if>
   	  
   	  <c:if test="${ sendMailSuccess == false}">
   	  	<span style="color: red;">메일발송이 실패했습니다.</span>
   	  </c:if>

   	  
   </div> 
          
   <div id="div_btnFind" align="center">
   	  <button type="button" class="btn btn-success" id="btnFind">찾기</button>
   </div>
</form>

 
<form name="verifyCertificationFrm">
	<input type="hidden" name="userid" />
	<input type="hidden" name="userCertificationCode" />
	<input type="hidden" id="email" name="email" value="${email}"/>
	<input type="hidden" id="name" name="name" value="${name}"/>
</form>


    