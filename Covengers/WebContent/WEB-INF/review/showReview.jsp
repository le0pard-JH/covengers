<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String ctxPath = request.getContextPath();
%>

<jsp:include page="../covengers_header.jsp"></jsp:include>

<style type="text/css">


</style>

<script type="text/javascript">
	$(document).ready(function(){
		
		$(".filledstar").hide();
		$("span#result").hide();

		
		$("div#star-rating span").click(function(){
			var index = $(this).index() + 1;
			
			$(".filledstar").hide(); 
			$(".emptystar").show(); 
			
			for (var i = 0; i < index; i++) {
				$("div#star-rating span").eq(i).find(".emptystar").hide();
				$("div#star-rating span").eq(i).find(".filledstar").show();
			}
			
			$("span#result").hide().html(index);
			
		});
	
	});
	
	function resetReview() {
		$("textarea#rcontent").val("");
		$(".filledstar").hide();
		$(".emptystar").show();
	}
	
	function writeReview() {
		
		if($("textarea#rcontent").val().trim() == "") {
			alert("내용이 비어있습니다!");
			return;
		}
		
		if ($("span#result").text().trim() == "") {
			alert("별점을 선택해 주세용!");
			return;
		}
		
		
		
		var frm = document.goReview;
		frm.orderno.value = "${orderno}"; 
		frm.productcode.value = "${product}";  
		frm.userno.value = "${loginuser}";  
		frm.rcontent.value = $("textarea#rcontent").val();
		frm.rgrade.value = $("span#result").text();
		
		
		frm.action = "<%=ctxPath%>/product/writeReview.com";
		frm.method = "POST";
		frm.submit();
	}

</script>



<div style="height: 80%; width: 80%; margin: auto" align="center" id="review">

	<div>
		<div>
			<span>작성자 : ${loginuser}<%-- ${loginuser.email} --%></span>
			
			
		
		</div>
		
		<div>
			<span>상품 : ${product}<%-- ${product.krproductname } (${product.enproductname}) --%></span>
		</div>
		
		<div>
			<textarea id="rcontent" name="rcontent" rows="15" cols="60" style="resize:none;" placeholder="리뷰 내용을 써주세여~"></textarea>
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
		
		<div>
			<button type="button" onclick="writeReview();">리뷰등록</button>
			<button type="button" onclick="resetReview();">취소</button>
			
			
		</div>
	
	</div>

</div>

<div>
	<form name="goReview">
		<input type="hidden" name="orderno" />
		<input type="hidden" name="productcode" />
		<input type="hidden" name="userno" />
		<input type="hidden" name="rcontent" />
		<input type="hidden" name="rgrade" />
		
		
	</form>
</div>