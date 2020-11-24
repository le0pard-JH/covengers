<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<%
	String ctxpath = request.getContextPath();
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
   	  	background-color: #eee;
   	  	color: #444;
   	  	border-radius: 5%;
   	  	float: right;
   	}
   	
   	.form-group {
   		width: 600px;
   		margin: 0 auto;
   	}

</style>    
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>

	$(document).ready(function() {
		
		// 넘어온 fvo가 빈값이 아니라면 각 항목에 값을 넣어준다.
		if ("${fvo}" != "") {
			$("input[name=question]").val("${fvo.faqQuestion}");
			$("textarea[name=answer]").val("${fvo.faqAnswer}");
			$("input[name=faqNo]").val("${fvo.faqNo}");
		}
		
		// 처음 들어왔을 때엔 경고창을 보여주지 않는다.
		$("span#questionAlert").hide();
		$("span#answerAlert").hide();
		
		// 등록(저장) 버튼을 클릭하면
		$("button#submit").click(function() {
			
			// 경고창을 숨겼다가
			$("span#questionAlert").hide();
			$("span#answerAlert").hide();

			// 각 항목의 값을 받아.
			var question = $("input[name=question]").val();
			var answer = $("textarea[name=answer]").val();
			
			// 만약 비어있는 항목이 있다면 경고창을 띄우며 focus를 넣어준다.
			if (question.trim() == "") {
				$("span#questionAlert").show();
				$("input[name=question]").val("");
				$("input[name=question]").focus();
				return false;
			}
			
			if (answer.trim() == "") {
				$("span#answerAlert").show();
				$("textarea[name=answer]").val("");
				$("textarea[name=answer]").focus();
				return false;
			}
			
			if ("${fvo}" == "") {
			// 넘어온 fvo가 ""(빈값)이라면 신규작성 action으로 가고 
				var frm = document.faqFrm;
				frm.action = "<%= ctxpath%>/board/faqRegister.com";
				frm.method = "post";
				frm.submit();
			}else{
			// 넘어온 fvo가 ""(빈값)이 아니라면 수정 action으로 간다.
				var frm = document.faqFrm;
				frm.action = "<%= ctxpath%>/board/faqUpdate.com";
				frm.method = "post";
				frm.submit();
			}
		});
		
	});// end of $(document).ready(function() {});-------------------------

</script>
<body>
	
	<div id="container">
		<div id="miniContainer">
			<h1>FAQ 작성</h1>
		    <hr style="border: solid 1px gray;">
			<form name="faqFrm">
		      	<div class="form-group">
			      	<input name="faqNo" type="hidden" value=""/>
			    	<label for="question">질문:</label>
			      	<input type="text" class="form-control" id="question" name="question" />
			      	<span id="questionAlert" style="color:red;">질문을 작성해주세요!!</span><br>
			      	
			      	<label for="comment">답변:</label>
			      	<textarea class="form-control" rows="5" id="answer" name="answer"></textarea>
			      	<span id="answerAlert" style="color:red;">답변을 작성해주세요!!</span><br>
		      		
		      		<button id="submit" class="mybtn">등록(저장)</button>
			    </div>
			    
	      	</form>
	      	
      	</div>
	</div>
</body>
</html>