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
	
	div#questionDiv, div#answerDiv {
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
	
	.bWhite {
		background-color: white !important;
	}
	
	
</style>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.css" /> 
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.js"></script>

<script>

	$(document).ready(function() {
		
		// 기존에 선택한 카테고리가 나타나도록 함. 
		$("select#category").val("${qqvo.qCategory}");
		
		// 관리자 계정으로 들어올 시, 질문 부분의 "삭제" 버튼은 보이지 않게 함.
		/* if ("${loginuser.status}" == "3") { */
		if ("${status}" == "3") {
			$("button#deleteQuestion").hide();
		}
		
		
		// 회원이 수정버튼을 누르면 직성되어 있는 form 그대로 update 된다. 
		$("button#editQuestion").click(function() {
			var questionFrm = $("form[name=questionFrm]").serialize();
			
			$.ajax({
				url: "<%= ctxPath%>/board/updateQuestion.com",
				data: questionFrm,
				type: "post",
				success:function(){
					swal("수정이 완료되었습니다.");
				},
				error: function(request, status, error){
					alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
				}
			});
		});
		
		// 질문 부분의 "삭제" 버튼을 누르면 해당 게시물이 삭제된다.
		$("button#deleteQuestion").click(function() {
			var frm = document.questionFrm;
			frm.action = "<%= ctxPath%>/board/deleteQuestion.com";
			frm.method = "post";
			frm.submit();
		});
		
		// "목록으로" 버튼을 누르면 QnA 목록으로 넘어간다.
		$("button#goListBtn").click(function() {
			location.href = "<%= ctxPath%>/board/qnaList.com";
		});
		
		// 관리자가 답변 부분의 "등록" 버튼을 누르면 해당 내용이 저장(insert)되도록 한다. 
		$("button#registAnswer").click(function() {
			
			var answer = $("textarea[name=answer]").val().trim();
			var qnaNo = $("input[name=qnaNo]").val();
			
			if (answer == "") {
				swal("답변 내용을 입력하세요!!");
				return;
			}
			
			$.ajax({
				url: "<%= ctxPath%>/board/registerAnswer.com",
				data: {"answer":answer, "qnaNo":qnaNo},
				type: "post",
				success:function(){
					location.reload();
				//	swal("등록이 완료되었습니다.");
				},
				error: function(request, status, error){
					alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
				}
			});
		});
		
		// 관리자가 답변 부분의 "수정" 버튼을 누르면 해당 내용이 수정(update)되도록 한다.
		$("button#editAnswer").click(function() {
			
			var answer = $("textarea[name=answer]").val();
			var qnaNo = $("input[name=qnaNo]").val();
			
			$.ajax({
				url: "<%= ctxPath%>/board/editAnswer.com",
				data: {"answer":answer, "qnaNo":qnaNo},
				type: "post",
				success:function(){
					swal("수정이 완료되었습니다.");
				},
				error: function(request, status, error){
					alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
				}
			});
		});
		
		// 관리자가 답변 부분의 "삭제" 버튼을 누르면 해당 답변이 삭제되도록 한다. 
		$("button#deleteAnswer").click(function() {
			
			var qnaNo = $("input[name=qnaNo]").val();
			
			$.ajax({
				url: "<%= ctxPath%>/board/deleteAnswer.com",
				data: {"qnaNo":qnaNo},
				type: "post",
				success:function(){
					location.reload();
				//	swal("답변이 삭제되었습니다.");
				},
				error: function(request, status, error){
					alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
				}
			});
		});
		
		
	});// end of $(document).ready(function() {});----------------------
	
</script>

<body>

	<div id="container">
	
		<div id="miniContainer">
			<div style="text-align: center;">
				<h1>1 : 1 문의</h1>
				<hr style="border: solid 1px gray;">
			</div>	
	<!-- 질문 부분 -->	
			<div id="questionDiv"  class="form-group" style="border:solid 1px gray; margin-bottom:10px;"> 
		   		<!-- 답변이 달리지 않았다면 수정가능하게 함. -->
		   <%-- <c:if test="${qavo eq null and loginuser.status ne 3}">  --%>
				<c:if test="${qavo eq null and status ne 3}">
					<form name="questionFrm">
						<input name="qnaNo" type="hidden" value="${qqvo.qnaNo}" />
						<ul>
							<li>
								<span style="font-size: 15pt;">"<span style="color: #cc66ff;">${qqvo.qUserName}</span>"님의 문의입니다.</span> 
								<span style="float: right;">작성일 : ${qqvo.qDateString}</span>
							</li>
							<li>
								제목:<input id="subject" name="subject" class="form-control" type="text" value="${qqvo.qSubject}"/>
							</li>
							<li>
								문의유형:
								<select id="category" name="category" class="form-control">
									<c:forEach var="category" items="${cateList}">
										<c:choose>
											<c:when test="${category eq 'GOODS'}">
												<option>상품</option>
											</c:when>
											<c:when test="${category eq 'DELIVERY'}">
												<option>배송</option>
											</c:when>
											<c:when test="${category eq 'PAYMENT'}">
												<option>결제</option>
											</c:when>
											<c:otherwise>
												<option>기타</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</select>
							</li>
							<li>
								문의 내용: 
								<textarea id="content" name="content" class="form-control" cols="40" rows="8">${qqvo.qContent}</textarea><br>
							</li>
						</ul>
						<button id="editQuestion" type="button" class="mybtn">수정</button>
						<button id="deleteQuestion" type="button" class="mybtn">삭제</button>
					</form>
				</c:if>
				
				<!-- 답변이 달렸거나 관리자가 들어올 시, 수정이나 삭제를 할 수 없음 -->
		   <%-- <c:if test="${qavo ne null or sessionScope.loginuser.status eq 3}">  --%>
				<c:if test="${qavo ne null or status eq 3}"> 
					<input name="qnaNo" type="hidden" value="${qqvo.qnaNo}" />
					<ul>
						<li>
							<span style="font-size: 15pt;">"<span style="color: #cc66ff;">${qqvo.qUserName}</span>"님의 문의입니다.</span> 
							<span style="float: right;">작성일 : ${qqvo.qDateString}</span>
						</li>
						<li>
							제목: <input id="subject" name="subject" class="form-control bWhite" type="text" value="${qqvo.qSubject}" readonly/>
						</li>
						<li>
							문의유형:
							<select id="category" name="category" class="form-control bWhite" disabled="disabled">
								<option>${qqvo.qCategory}</option>
							</select>
					   <%-- <select id="category" name="category" disabled>
								<c:forEach var="category" items="${cateList}">
									<c:choose>
										<c:when test="${category eq 'GOODS'}">
											<option>상품</option>
										</c:when>
										<c:when test="${category eq 'DELIVERY'}">
											<option>배송</option>
										</c:when>
										<c:when test="${category eq 'PAYMENT'}">
											<option>결제</option>
										</c:when>
										<c:otherwise>
											<option>기타</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select> --%>
						</li>
						<li>
							문의 내용:
							<textarea id="content" name="content" class="form-control bWhite" cols="40" rows="8" readonly>${qqvo.qContent}</textarea>
						</li>
					</ul>
					<!-- <button id="deleteQuestion" type="button" class="mybtn">삭제</button> -->
				</c:if>
			</div>
	
	<!-- 답변 부분 -->
			<div id="answerDiv" class="form-group" style="border:solid 1px gray; margin-bottom:10px;">
					답변 : 
					<!-- 답변이 달렸는데 회원이 들어왔다면 -->
					<c:if test="${qavo ne null and status ne 3}">
						<ul>
							<li>
								<span style="float: right;">작성일 : ${qavo.qAdate}</span><br>
							</li>
							<li>
								<textarea id="answer" name="answer" class="form-control bWhite" cols="40" rows="8" disabled="disabled">${qavo.qAnswer}</textarea>
							</li>
						</ul>
					</c:if>
					
				<form id="answerFrm"> 
					<!-- 답변이 달리지 않은 상태로 관리자가 들어왔다면 -->
					<c:if test="${qavo eq null and status eq 3}">
						<textarea id="answer" name="answer" class="form-control" cols="40" rows="8" ></textarea><br>
						<button id="registAnswer" type="button" class="mybtn">등록</button>
					</c:if>
	
					<!-- 답변이 이미 달린 상태로 관리자가 들어왔다면 -->
					<c:if test="${qavo ne null and status eq 3}">
						<input name="fk_qnaNo" type="hidden" value="${qavo.fk_qnaNo}" /> 
						<ul>
							<li>
								<span style="float: right;">작성일 : ${qavo.qAdate}</span>
								<span>관리자ID : ${qavo.qAdminid}</span>
							</li>
							<li>
								<textarea id="answer" name="answer" class="form-control" cols="40" rows="8" >${qavo.qAnswer}</textarea>
							</li>
						</ul>
						<button id="editAnswer" type="button" class="mybtn">수정</button>
						<button id="deleteAnswer" type="button" class="mybtn">삭제</button>
					</c:if>
				</form>
			</div>
			
			<button id="goListBtn" type="button" class="mybtn">목록으로</button>
		</div>
	</div>
</body>
</html>