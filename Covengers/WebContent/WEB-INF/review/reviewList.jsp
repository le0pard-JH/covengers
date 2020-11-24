<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
	<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
	String ctxPath = request.getContextPath();
%>

<jsp:include page="../covengers_header.jsp"></jsp:include>



<style type="text/css">

	div#container {
      /* border: solid 1px yellow;  */
      width: 100%;
      margin: 30px auto;
      text-align: center; 
    }
	
	div#miniContainer {
      /* border: solid 1px red; */ 
      width: 70%;
      margin: 0 auto;
      text-align: center;
      display: inline-block;
    }

	button.mybtn:hover {
   	  	font-weight: bold;
    }
   
    button.mybtn {
      	border: none;
   	  	margin: 10px 10px;
   	  	padding: 7px 7px;
   	  	background-color: #eee;
   	  	color: #444;
   	  	border-radius: 5%;
   	  	float: right;
   	}
   	
   	div.board {
   		background-color: #f2f2f2;
   		width: 90%;
   		height: 40px;
   		cursor: pointer;
   		margin: 0 auto;
   		padding: 5px;
   	}
   	
   	div.board:hover {
   		background-color: #ccc;
   	}
   	
</style>

<script>
	$(document).ready(function(){
		$("div.board").click(function(){
			location.href = "<%= ctxPath%>/product/showOneReview.com?reviewno=" + $(this).find('.reviewno').val();
		});
	})

</script>
 

	<div id="container">
		<div id="miniContainer">
		
			<h1>리뷰보기</h1>
	      	<hr style="border: solid 1px gray;">	
			
			<c:if test="${reviewList.size() eq 0}">
				<h2>등록된 리뷰가 없습니다.</h2>
			</c:if>
			
			<c:if test="${reviewList.size() ne 0}">
				<c:forEach var="review" items="${reviewList}">
					<div class="board well" style="border:solid 1px gray; margin-bottom:10px;">
						<input class="reviewno" name="reviewno" type="hidden" value="${review.reviewno}" />
						<label style="color: #cc66ff;">${review.email}</label>&nbsp;&nbsp;&nbsp;
						<label style="color: #cc66ff;">${review.fk_productcode}</label>&nbsp;&nbsp;&nbsp;
						
						<c:if test="${fn:length(review.rcontents) <= 16 }">
							<label style="color: #cc66ff;">${review.rcontents }</label>&nbsp;&nbsp;&nbsp;
						</c:if>
						
						<c:if test="${fn:length(review.rcontents) > 16 }">
							<c:set var="contents" value="${ fn:substring(review.rcontents, 0, 16)}"></c:set>
							<c:set var="contents" value="${fn:replace(contents,'<br>', ' ') }"></c:set>
							<label style="color: #cc66ff;">${contents }...</label>&nbsp;&nbsp;&nbsp;
						</c:if>

						<label>작성일 : ${review.rdate}</label>
					</div>
				</c:forEach>
			</c:if>
	
			<div>${pageBar}</div>
		
		</div>
	</div>

</body>
</html>