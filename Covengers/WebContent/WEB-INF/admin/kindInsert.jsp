<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%
    String ctxPath = request.getContextPath();
    %>
    
    
<script type="text/javascript">
	$(document).ready(function(){
		//////////////////////////////////////////////////////////////////////////////////////////////////
		// 상품종류 확인
		$("input:text[id=kindcode]").blur(function(){
			var kindcode = $(this).val();
			
			var regExp = /^[A-Z]{2}$/g;  // 대문자영어만 입력할 수 있음 2자리 한정
			checkInput("kindcode", kindcode, regExp);
			
			$.ajax({
				url:"<%= ctxPath%>/admin/kindCheck.com",
				type:"POST",
				data:{"kindcode":$("input:text[id=kindcode]").val().trim()},
				dataType:"JSON",
				success:function(json){
					if ($("input:text[id=kindcode]").val().trim() != "") {  // 해당 인풋 태그가 비어있지 않은 경우
						if(json.result != null && $("input#kindcode").parent().find(".error").text() == "") {
							$("input#kindcode").parent().find(".error").text("사용중인 코드입니다.");
						} else if (json.result == null && $("input#kindcode").parent().find(".error").text() == ""){
							$("input#kindcode").parent().find(".error").text("사용가능");
						}
					} else {  // 빈칸인 경우
						$("input#kindcode").parent().find(".error").text("");
					}
					
				},
				error: function(request, status, error){
		        	alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
		        } 
			});
			

			
		
		});
		////////////////////////////////////////////////////////////////////////////////////////////////////
		// 상품 종류 영문 이름 확인
		$("input:text[id=enkindname]").blur(function(){
			var enkindname = $(this).val();
	
			var regExp = /^([a-zA-Z])[a-zA-Z0-9]+$/g;  
			checkInput("enkindname", enkindname, regExp);
			
			
			
		});
		///////////////////////////////////////////////////////////////////////////////////////////////////////
		// 상품 종류 국문 확인
		$("input:text[id=krkindname]").blur(function(){
			var krkindname = $(this).val();
			
			var regExp = /^([가-힣])[가-힣0-9]+$/g;  
		
			checkInput("krkindname", krkindname, regExp);
			
			
		});
		////////////////////////////////////////////////////////////////////////////////////////////////////////
		// 상품 종류 등록을 눌렀을 시 발생하는 이벤트
		$("button#kindSubmit").click(function(){
			var flag = false;  
			
			// 각각의 input태그를 돌려서 비어있는 값이 있는지 확인한다.
			
			$("form#insertKind input:text").each(function(index, item){
				// console.log("바보야");
				if($(this).val().trim() == "") {  // 만약 공백을 입력한 인풋태그가 있는경우
					if ($(this).parent().find(".error").html() == "") {  // 에러메세지 창에 아무런 에러메세지가 없다면
						$(this).parent().find(".error").text("빈칸을 입력해주세요");  // 빈칸을 입력해 달리고 글을 보여준다.
						flag = true;
					}
				} else {
					if ($(this).parent().find(".error").html() == "사용가능") {
						$(this).parent().find(".error").text("");
					}
					
				}
				
			});
			
			var error = $("form#insertKind span.error").text();
			// console.log(errorList)
			// console.log(typeof(errorList));  -> String
			
			
			if(error.length != 0) {
				flag = true;   // 깃발을 올린다.
			}
			
			
			if (!flag) {  // 에러메세지가 아무것도 발견되지 않았을 경우
				$.ajax({
					url:"<%= ctxPath%>/admin/insertKindJSON.com",
					type:"POST",
					data:{"kindcode":$("input#kindcode").val()
						, "enkindname":$("input#enkindname").val() 
						, "krkindname":$("input#krkindname").val()},
					dataType:"JSON",
					success:function(json){
						if (json.result == 2) {
							$("span.error").text("");
							$("input#kindcode").parent().find(".error").text("같은 값의 키 코드가 있습니다.");
							return;
						} else if (json.result == 1) {
							$("span.error").text("");
							alert("등록에 성공하셨습니다.");
							$("form#insertKind")[0].reset();
							$("form#insertCategory")[0].reset();
							$("form#insertProduct")[0].reset();
							$("form#insertOption")[0].reset();
							location.reload(true);
							return;
						}else if (json.result == 3) {
							$("span.error").text("");
							$("input#enkindname").parent().find(".error").text("같은 이름이 있습니다.");
							return;
						}else if (json.result == 4) {
							$("span.error").text("");
							$("input#krkindname").parent().find(".error").text("같은 이름이 있습니다.");
							return;
						} else {
							$("span.error").text("");
							alert("내부적 문제로 인한 등록실패!");
							$("form#insertOption")[0].reset();
							return;
						}
					},
					error: function(request, status, error){
			        	alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
			        } 
				});
				
			} else {  // 에러메세지가 떠 있을 경우
				alert("입력값이 올바르지 않습니다~");
				return;
			}
			
		});
	});
</script>

<!-- --------------------------------상품종류등록------------------------- -->
		<div class="panel panel-default divRegister">
			<div class="panel-heading">
				<h4 class="panel-title">
					<a class="register" data-toggle="collapse" data-parent="#accordion" href="#collapse1">상품 종류 등록</a>
				</h4>
			</div>
			
			<div id="collapse1" class="panel-collapse collapse">
				<form name="registerKindFrm" class="registerFrm" autocomplete="off" id="insertKind">
					<div class="form-group">
						<label for="kindcode">상품 종류 코드</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="error"></span>
						<input type="text" class="form-control firstInput" id="kindcode" placeholder="ex)DF(띄어쓰기 불가)" name="kindcode" maxlength="2" />
					</div>
					<div class="form-group">
						<label for="enkindname">상품 종류 영문</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="error"></span>
						<input type="text" class="form-control" id="enkindname" placeholder="ex)diffuser(띄어쓰기 불가)" maxlength="30" name="enkindname" />
					</div>
					<div class="form-group">
						<label for="krkindname">상품 종류 국문</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="error"></span>
						<input type="text" class="form-control" id="krkindname" placeholder="ex)디퓨져(띄어쓰기불가)" maxlength="30" name="krkindname" />
					</div>
				</form>
				<div class="btnGroup" align="center">
					<button type="button" id="kindSubmit"class="btn btn-default">등록</button>
					<button type="button" class="btn btn-default" onclick="reset()">취소</button>
				</div>
			</div>
		</div>