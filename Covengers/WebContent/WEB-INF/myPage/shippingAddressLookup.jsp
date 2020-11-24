<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String ctxPath = request.getContextPath();
	//    /Covengers
%>
<!-- <!DOCTYPE html>
<html>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" /> -->




<title>배송지 목록</title>

<!-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script> -->

<jsp:include page="../covengers_header.jsp"></jsp:include>
<style type="text/css">
	
	.myinput {
		border: none;
		outline: none;
		font-size: 15pt;
		margin-bottom: 10px;
		width: 90%;
	}
	
	 .addressCard {
		border: solid 1px lightgray;
		width: 600px;
		margin: 30px auto;
		padding: 20px 30px;
	}
	
</style>

<script type="text/javascript">
	$(document).ready(function(){
		
		var shipno = "";
		
		$("button.btnUpdate").click(function(){ // 수정버튼 클릭시
			
			shipno = $(this).siblings("input[name=shipNo]").val();
			userno = $(this).siblings("input[name=userno]").val();
		//	alert(shipno);
			
		//	shipno = $(this).siblings("input[name=shipNo]").val();
		
			goUpdateAddress(shipno, userno);
			
		});
		
		$("button.btnDelete").click(function(){ //삭제버튼 클릭시
			shipno = $(this).siblings("input[name=shipNo]").val();
			$.ajax({
				url: "<%= request.getContextPath() %>/member/shippingAddressDelete.com",
				data: {"shipNo" : shipno},
				type: "post",
				dataType: "json",
				success:function(json){
					if(json.isDeleted){
						alert("배송지 삭제 성공");
						location.reload();
					}
					else{
						alert("배송지 삭제 실패");
					}
				},
				error: function(request, status, error){
	                alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
	             }
			});
		});

		
		$("button#btnAddAddress").click(function(){ //배송지 추가 버튼 클릭시
			var numberOfShippingAddress = ${numberOfShippingAddress}
			
			alert(numberOfShippingAddress);
			
			if(numberOfShippingAddress >= 10){
				alert("배송지는 10개까지 등록 가능합니다.");
				return;
			}
			else{
				var url = "<%=ctxPath%>/member/shippingAddressAdd.com";
			      
			    window.open(url, "shippingAddressEdit", "left=350px, top=100px, width=800px, height=650px");
			}
		});
		
	});// end of $(document).ready(function(){})--------------------------------------------------------
	
	function goUpdateAddress(shipno, userno){		
		//정보수정하기 팝업창 띄우기
	      var url = "<%=ctxPath%>/member/shippingAddressUpdate.com?shipNo="+shipno+"&userno="+userno;
	      
	      window.open(url, "shippingAddressEdit", "left=350px, top=100px, width=800px, height=650px");
	      
	}
	
	function goCheckUser(email){
		
	}
</script>



	<div id=container>
		<c:if test="${not empty addressList}">
			<h3>배송지목록</h3>
			<c:forEach var="svo" items="${addressList}">
					<div class="addressCard">
						<c:if test="${svo.status eq '1'}">
							<div style="font-size: 15pt; color: green; font-weight: bold;">기본배송지</div>
						</c:if>
						<input type="hidden" name="shipNo" value="${svo.shipNo}"/>
						<label>수취인명</label>
						<input name="receiverName" class="myinput" value="${svo.receiverName}" readonly/><br>
						<label>배송지이름</label>
						<input name="siteName" class="myinput" value="${svo.siteName}" readonly/><br>
						<label>우편번호</label>
						<input name="postcode" class="myinput" value="${svo.postcode}" readonly/><br>
						<label>주소</label><br>
						<input name="address" class="myinput" value="${svo.address}" readonly/><br>
						<input name="detailAddress" class="myinput" value="${svo.detailAddress}" readonly/><br>
						<input name="extraAddress" class="myinput" value="${svo.extraAddress}" readonly/><br>
						<label>연락처</label><br>
						<input name="mobile" class="myinput" value="${svo.mobile}" readonly/><br>
						<label>요청사항</label>
						<input name="deliveryRequest" class="myinput" value="${svo.deliveryRequest}" readonly/><br>
						<input type ="hidden" name="status" value="${svo.status}"/>
						<input type ="hidden" name="userno" value="${svo.userno}"></input>
						<button class="btnUpdate" type="button">수정</button>
						<button class="btnDelete" type="button">삭제</button>
					</div>
			</c:forEach>
		</c:if>
		<c:if test="${empty addressList}">
			<div>등록된 배송지가 없습니다.</div>
		</c:if>
		<button type="button" id="btnAddAddress">배송지 추가</button>
	</div>

</body>
</html>