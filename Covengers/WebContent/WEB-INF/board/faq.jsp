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
	

 	div.item {
		border: solid 1px #f2f2f2;
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

.accordion {
  background-color: #eee;
  color: #444;
  cursor: pointer;
  padding: 18px;
  width: 100%;
  border: none;
  text-align: left;
  outline: none;
  font-size: 15px;
  transition: 0.4s;
}

.active, .accordion:hover {
  background-color: #ccc;
}

.panel {
  padding: 0 18px;
  background-color: white;
  max-height: 0;
  overflow: hidden;
  transition: max-height 0.2s ease-out;
}

</style>

</head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script type="text/javascript">

	$(document).ready(function() {
			
		// 관리자가 "등록" 버튼을 누르면 FaqRegisterAction으로 넘어감
		$("button#registerFaq").click(function() {
 			location.href="<%= ctxPath%>/board/faqRegister.com";
		});


		// 관리자가 각 게시물의 "수정" 버튼을 누르면 FaqRegisterAction으로 넘어감.
		$("button.editFaq").click(function() {
			
			// 수정으로 들어갈 때, requestFaqNo에 수정할 faqNo를 넣어보낸다.
			var faqNo = $(this).parent().siblings("input.faqNo").val();
			$("input[name=requestFaqNo]").val(faqNo);
			
			var frm = document.requestFrm;
			frm.action = "<%= ctxPath%>/board/faqUpdate.com";
			frm.submit();
		});
		
		
		// 관리자가 각 게시물의 "삭제" 버튼을 누르면 FaqDeleteAction으로 넘어감.
		$("button.deleteFaq").click(function() {
			
			// 삭제로 들어갈 때, requestFaqNo에 삭제할 faqNo를 넣어보낸다.
			var faqNo = $(this).parent().siblings("input.faqNo").val();
			$("input[name=requestFaqNo]").val(faqNo);
			
			var frm = document.requestFrm;
			frm.action = "<%= ctxPath%>/board/faqDelete.com";
			frm.method = "post";
			frm.submit();
			
		});// end of $("button.deleteFaq").click(function() {});-------------------------
		
		
		// 관리자가 "전체삭제" 버튼을 누르면 모든 게시물이 삭제됨.
		$("button#deleteAllFaq").click(function() {
			location.href= "<%= ctxPath%>/board/faqDeleteAll.com";
		});
		
		
		
		
		// 아코디언 효과
		var acc = document.getElementsByClassName("accordion");
		var i;

		for (i = 0; i < acc.length; i++) {
		  acc[i].addEventListener("click", function() {
		    this.classList.toggle("active");
		    var panel = this.nextElementSibling;
		    if (panel.style.maxHeight) {
		      panel.style.maxHeight = null;
		    } else {
		      panel.style.maxHeight = panel.scrollHeight + "px";
		    } 
		  });
		}
		
		
		
	});// end of $(document).ready(function() {});-----------------------------

</script>

<body>
<%-- 
	<div id="container">
		<div id="miniContainer">
		
			<h1>FAQ</h1>
	      	<hr style="border: solid 1px gray;">
			
			<!-- 로그인 유저가 일반 회원이라면  -->
			<c:if test="${sessionScope.loginuser.status ne 3}">
				<c:forEach var="faq" items="${faqList}" varStatus="status">
					<div class="item">
						<div>Q${status.count}. ${faq.faqQuestion}</div>
						<hr>
						<div>${faq.faqAnswer}</div>
					</div>		
				</c:forEach>
			</c:if>
			
			<!-- 로그인 유저가 관리자라면 -->
			<c:if test="${sessionScope.loginuser.status eq 3}">
				<form name="requestFrm">
					<input type="hidden" name="requestFaqNo" value="" />
				</form>
				<c:forEach var="faq" items="${faqList}" varStatus="status">
					<div class="item">
						<input type="hidden" class="faqNo" value="${faq.faqNo}" />
						<div>Q${status.count}. ${faq.faqQuestion}</div>
						<hr>
						<div>${faq.faqAnswer}</div>
						<button class="editFaq">수정</button>
						<button class="deleteFaq">삭제</button>
					</div>		
				</c:forEach>
				<button id="registerFaq" type="button">등록 </button>
				<button id="deleteAllFaq" type="button">전체삭제</button>
			</c:if>
		
		</div>
	</div>
 --%>
 
 	<div id="container">
		<div id="miniContainer">
 			
 			<h1>F A Q</h1>
			<hr style="border: solid 1px gray;">
	
			<!-- 로그인 유저가 일반 회원이라면  -->
			<%-- <c:if test="${sessionScope.loginuser.status ne 3}"> --%>
			<c:if test="${loginStatus ne 3}">
				<c:forEach var="faq" items="${faqList}" varStatus="status">
					<div class="item">
						<button class="accordion mybtn">Q${status.count}. ${faq.faqQuestion}</button>
						<div class="panel">
						  <p>${faq.faqAnswer}</p>
						</div>
					</div>		
				</c:forEach>
			</c:if>
			
			<!-- 로그인 유저가 관리자라면 -->
			<%-- <c:if test="${sessionScope.loginuser.status eq 3}"> --%>
			<c:if test="${loginStatus eq 3}">
				<form name="requestFrm">
					<input type="hidden" name="requestFaqNo" value="" />
				</form>
				<c:forEach var="faq" items="${faqList}" varStatus="status">
					<div class="item">
						<input type="hidden" class="faqNo" value="${faq.faqNo}" />
						<button class="accordion">Q${status.count}. ${faq.faqQuestion}</button>
						<div class="panel">
						  	<p>${faq.faqAnswer}</p>
							<button class="deleteFaq mybtn">삭제</button>
							<button class="editFaq mybtn">수정</button>
						</div>
					</div>		
				</c:forEach>
				<button id="deleteAllFaq" type="button" class="mybtn">전체삭제</button>
				<button id="registerFaq" type="button" class="mybtn">등록 </button>
			</c:if>
		
		</div>
	</div>

</body>
</html>