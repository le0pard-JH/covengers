<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
	String ctxPath = request.getContextPath();
%>
<jsp:include page="../covengers_header.jsp"></jsp:include>
<style type="text/css">

	div#container {
      /* border: solid 1px yellow;  */
      width: 1200px;
      margin: 30px auto;
      text-align: center; 
    }
	
	div#miniContainer {
      /* border: solid 1px red; */ 
      width: 800px;
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
   	  	background-color: #c44dff;
   	  	color: white;
   	  	border-radius: 5%;
   	  	/* float: right; */
   	  	
   	}
	
	div#reset {
		width: 80%; 
		margin: 0 auto;
		text-align: left;
		background-color: #eee;
		padding: 20px 20px;
	}
	
	ul {
		list-style: none;
		line-height: 50px;
		margin: 0;
		padding: 0;
	}

</style>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>

	$(document).ready(function() {

		func_selectCategory();
		
		// 질문 유형을 고르면 func_selectCategory이 수행됨
		$("select#category").change(function() {
			func_selectCategory();
		});
		
		
		// "등록" 버튼을 누르면 form을 제출함
		$("button#submit").click(function() {
			
			var subject = $("input#subject").val().trim();
			
			if (subject == "") {
				alert("제목을 입력하세요!!");
				return;
			}
			
			var content = $("textarea#content").val().trim();
			
			if (content == "") {
				alert("문의 내용을 입력하세요!!");
				return;
			}
			
			var frm = document.questionFrm;
			frm.action = "<%= ctxPath%>/board/qnaRegister.com";
			frm.method = "post";
			frm.submit();
		});
		
		
	});// end of $(document).ready(function() {});-----------------------------------

	
	// === 선택한 카테고리에 따른 설명이 textarea에 제공되게 하는 function === //
	function func_selectCategory() {
		
		var category = $("select#category").val();
		
		if (category == '상품') {
			category = 'GOODS';
		}else if(category == '배송') {
			category = 'DELIVERY';
		}else if(category == '결제') {
			category = 'PAYMENT';
		}else {
			category = 'ETC';
		}
		
		$.ajax({
			url:"<%= ctxPath%>/board/selectCategoryExplain.com",
			data:{"category":category},
	     // type:"get",
			dataType:"json",
			success:function(json){

				$("textarea#content").val(json.explain);
				
			},
	        error: function(request, status, error){
	            alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
	        }
		});
		
	}// end of function func_selectCategory() {}------------------------------------------------------------------	
	
</script>
<body>

	<div id="container" style="width: 1200px; margin: 0 auto;">
		<div id="miniContainer">
			<div style="text-align: center;">
				<h1>1 : 1 문의</h1>
			   	<hr style="border: solid 1px gray;">	
			</div>
			<div id="reset" class="form-group">
				
				<%-- <form name="questionFrm">
					"<span style="color: #cc66ff;">${name}</span>"님의 문의입니다. <br>
					제목: <input id="subject" name="subject" type="text" /> &nbsp;&nbsp;&nbsp;&nbsp;
					
					문의유형:
					<select id="category" name="category">
						<c:forEach var="category" items="${cateList}">
							<option>
								<c:choose>
									<c:when test="${category eq 'GOODS'}">
										상품
									</c:when>
									<c:when test="${category eq 'DELIVERY'}">
										배송
									</c:when>
									<c:when test="${category eq 'PAYMENT'}">
										결제
									</c:when>
									<c:otherwise>
										기타
									</c:otherwise>
								</c:choose>
							</option>
						</c:forEach>
					</select>
					<br>
					<input name="userno" type="hidden" value="${userno}" />
					<input name="username" type="hidden" value="${name}" />
					
					문의 내용: <br>
					<textarea id="content" name="content" cols="40" rows="8"></textarea>
					
				</form> --%>

				<form name="questionFrm">
					<ul>
						<li>
							<span style="font-size: 15pt;">"<span style="color: #cc66ff;">${name}</span>"님의 문의입니다.</span> 
						</li>
						<li>
							제목: <input id="subject" name="subject" class="form-control" type="text" /> 
						</li>
						<li>
							문의유형:
							<select id="category" name="category" class="form-control">
								<c:forEach var="category" items="${cateList}">
									<option>
										<c:choose>
											<c:when test="${category eq 'GOODS'}">
												상품
											</c:when>
											<c:when test="${category eq 'DELIVERY'}">
												배송
											</c:when>
											<c:when test="${category eq 'PAYMENT'}">
												결제
											</c:when>
											<c:otherwise>
												기타
											</c:otherwise>
										</c:choose>
									</option>
								</c:forEach>
							</select>
						</li>
						<li>
							문의 내용: 
						</li>
						<li>
							<textarea id="content" name="content" class="form-control" rows="8"></textarea>
						</li>
					</ul>	
					<input name="userno" type="hidden" value="${userno}" />
					<input name="username" type="hidden" value="${name}" />
					<button id="submit" class="mybtn">등록</button>
				</form>
			</div>
		</div>
	</div>

</body>
</html>