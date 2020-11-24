<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
	<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
	String ctxPath = request.getContextPath();
%>

<jsp:include page="../covengers_header.jsp"></jsp:include>


<style type="text/css">

	div#show-one-review {
      /* border: solid 1px yellow;  */
      width: 100%;
      margin: 30px auto;
      text-align: center; 
    }
	
	div#review-contents {
      /* border: solid 1px red; */ 
      width: 70%;
      margin: 0 auto;
      text-align: center;
      display: inline-block;
    }

	
   	
</style>

<script>
	$(document).ready(function(){
		$(".filledstar").hide();
		$(".emptystar").show();
		
		for(var i = 0 ; i < ${review.rgrade}; i++) {
			$("div#star-rating span").eq(i).find(".emptystar").hide();
			$("div#star-rating span").eq(i).find(".filledstar").show();
		}
	});

</script>
 

	<div id="show-one-review">
		<div id="review-contents" >
		
			<h1> ${review.krproductname} 리뷰 </h1>
	      	<hr style="border: solid 1px gray;">	
			<span>${review.email}</span>
			<br>
			<span>${review.rdate}</span>
			<br>
			<div id="rcontents" style="border: black solid 1px; width: 60%; height: 300px; display: inline-block;" align="center">
				<span id="contents">${review.rcontents}</span>
			</div>
			
			<div id="star-rating" style="margin: 20px;">
			<c:forEach begin="1" end="5" varStatus="status">
				<span id="grade${status.count}"   style="display: inline-block; width: 30px; height: 30px;">
					<img class="emptystar" src="<%= ctxPath %>/images/whitestar.png" width="30px" height="30px">
					<img class="filledstar" src="<%= ctxPath %>/images/blackstar.png" width="30px" height="30px">
				</span>
			</c:forEach>
			<span id="result"></span>
			
		</div>
	
		
		</div>
	</div>

</body>
</html>