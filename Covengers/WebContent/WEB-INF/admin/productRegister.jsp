<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% 
	String ctxPath = request.getContextPath();
%>

<jsp:include page="../headNavigation.jsp"></jsp:include>

<style type="text/css"> 
	div#registerProduct, .divRegister {
		width: 60%;
		margin: 10px auto;
		
	}
	
	div#registerProductHeader, form.registerFrm {
		margin: 30px 20px 30px 20px ;
	}
	
	.btnGroup {
		margin: 0 20px 20px 20px;
	}
	
	button {
		margin: 0 30px;
	}
	
	span.error {
		color: red;
		font-weight: bold;
	}
	
</style>
	

<script type="text/javascript">
	$(document).ready(function (){		
		// 태그를 눌렀을시 안에 있는 속성들을 전부 초기치로 리셋시켜주는 메소드~
		// function reset ()과 같아야 하기때문에 반드시 확인 해주세용
		$("a.register").click(function(){
			$("form#insertKind")[0].reset();
			$("form#insertCategory")[0].reset();
			$("form#insertProduct")[0].reset();
			$("form#insertOption")[0].reset();
			$("span.error").text("");
			
		});
	
	});
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 인풋태그 유효성검사 
	function checkInput(colname, value, regExp) {
			$("input#"+ colname).parent().find(".error").text("");
			var bool = regExp.test(value);
			
			if(value == "") {
				$("input#"+colname).parent().find(".error").text("");
				// $(this).parent().find(".error").show().text("빈칸을 채워주세요.");
				return;
			}
			
			
			if (!bool) {
				// 입력하지 않았거나 공백만 입력했을 경우
				$("input#"+colname).parent().find(".error").text("바른 형식의 입력이 필요합니다.");
			} else {
				// 공백이 아닌 글자를 입력했을 경우
				$("input#"+colname).parent().find(".error").text("");
				// $(":input").prop("disabled", false);
			}
			
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	function reset () {
		$("form#insertKind")[0].reset();
		$("form#insertCategory")[0].reset();
		$("form#insertProduct")[0].reset();
		$("form#insertOption")[0].reset();
		$("span.error").text("");
		return;
	}	
	
		
		
	
</script>

<div id="registerProduct">

	<div id="registerProductHeader" align="center">
		<span><h2>상품 등록 </h2></span>
	</div>
	
	<hr style ="border: 2px solid black; background-color: black;">

	<div class="panel-group" id="accordion">
	
		<jsp:include page="kindInsert.jsp"></jsp:include>
		<jsp:include page="categoryInsert.jsp"></jsp:include>
		<jsp:include page="productInsert.jsp"></jsp:include>
		<jsp:include page="optionInsert.jsp"></jsp:include>
		
	</div>
	
	

</div>


</body>
</html>