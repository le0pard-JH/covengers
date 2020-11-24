<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%
    String ctxPath = request.getContextPath();
    %>
    
    
<script type="text/javascript"> 

	$(document).ready(function(){
		
		////////////////////////////////////////////////////////////////////////////////////////////////////////

		// 카테고리 코드 확인
		$("input:text[id=categorycode]").blur(function(){
			var categorycode = $(this).val();
			
			var regExp = /^[0-9]{3}$/g;  // 숫자만 가능   3자리
			checkInput("categorycode", categorycode, regExp);
			
		});
		////////////////////////////////////////////////////////////////////////////////////////////////////
		// 카테고리 영문 이름 확인
		$("input:text[id=encategoryname]").blur(function(){
			var encategoryname = $(this).val();
			
			var regExp = /^([a-zA-Z])[a-zA-Z0-9]+$/g;  
			checkInput("encategoryname", encategoryname, regExp);
			
		});
		////////////////////////////////////////////////////////////////////////////////////////////////////
		// 카테고리 국문 이름  확인
		$("input:text[id=krcategoryname]").blur(function(){
			var krcategoryname = $(this).val();
			
			var regExp = /^([가-힣])[가-힣0-9]+$/g;  
			checkInput("krcategoryname", krcategoryname, regExp);
			
		});
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		//상품 카테고리 등록에서 상품 종류 코드를 옵션으로 선택할시 발생하는 이벤트~
		$("select#selectedKindcode").change(function(){
			$("form#insertCategory input").val("");
			$("form#insertCategory span.error").text("");
			
			var selectedKindOption = $("select#selectedKindcode option:selected").val();
			
			if(selectedKindOption != 0) {
				$.ajax({
					url:"<%= ctxPath%>/admin/selectedKindCategory.com",
					type:"POST",
					data:{"selectedKindOption":selectedKindOption},
					dataType:"JSON",
					success:function(json){
						$("select#selectedKindcode").parent().find(".error").text("");
						
						if(json.result != null) {
							$( 'input#categorycode' ).val(Number(json.result) + 1);
						} else {
							
							$( 'input#categorycode' ).attr( 'placeholder', "등록제품없음:) ex)101(띄어쓰기 불가)" );
						}
					},
					error: function(request, status, error){
			        	alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
			        } 
				});
			} else {
				$("select#selectedKindcode").parent().find(".error").text("옵션을 선택해 주세요!");
				$( 'input#categorycode' ).attr( 'placeholder', "ex)101(띄어쓰기 불가)" );
				return;
			}
			
		});
		////////////////////////////////////////////////////////////////////////////////////////////////////////
		// 카테고리 - 등록 버튼을 눌렀을 경우
		$("button#categorySubmit").click(function(){
			var flag = false;
			
			// 옵션태그 선택 여부 확인
			
			var selectedKindOption = $("select#selectedKindcode option:selected").val();
			if (selectedKindOption == 0) {
				$("select#selectedKindcode").parent().find(".error").text("옵션을 선택해 주세요!");
				flag = true;
			}else {
				$("select#selectedKindcode").parent().find(".error").text("");
			}
			
			// 각각의 input태그를 돌려서 비어있는 값이 있는지 확인한다.
			
			$("form#insertCategory input:text").each(function(index, item){
				// console.log("바보야");
				if($(this).val().trim() == "") {  // 만약 공백을 입력한 인풋태그가 있는경우
					if ($(this).parent().find(".error").html() == "") {  // 에러메세지 창에 아무런 에러메세지가 없다면
						$(this).parent().find(".error").text("빈칸을 입력해주세요");  // 빈칸을 입력해 달리고 글을 보여준다.
						flag = true;
					}
				}
			});
			
			var error = $("form#insertCategory span.error").text();
			// console.log(errorList)
			// console.log(typeof(errorList));  -> String
			
			
			if(error.length != 0) {  // 문자열로 나오기 때문에 문자열의 길이를 구해준다. 아무것도 없으면 0 이 나온당~
				flag = true;   // 깃발을 올린다.
			}
			
			
			if (!flag) {  // 에러메세지가 아무것도 발견되지 않았을 경우

				var selectedKindOption = $("select#selectedKindcode option:selected").val();
				if (selectedKindOption == 0) {
					$("select#selectedKindcode").parent().find(".error").text("옵션을 선택해 주세요!");
					return;
				}else {
					$("select#selectedKindcode").parent().find(".error").text("");
				}
				
				$.ajax({
					url:"<%= ctxPath%>/admin/insertCategoryJSON.com",
					type:"POST",
					data:{"categorycode":$("input#categorycode").val()
						, "fk_kindcode":selectedKindOption
						, "encategoryname":$("input#encategoryname").val() 
						, "krcategoryname":$("input#krcategoryname").val()},
					dataType:"JSON",
					success:function(json){
						if (json.result == 2) {
							$("span.error").text("");
							$("input#categorycode").parent().find(".error").text("같은 값의 키 코드가 있습니다.");
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
							$("input#encategoryname").parent().find(".error").text("같은 이름이 있습니다.");
							return;
						}else if (json.result == 4) {
							$("span.error").text("");
							$("input#krcategoryname").parent().find(".error").text("같은 이름이 있습니다.");
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

<!-- --------------------------------상품 카테고리 등록------------------------- -->
		<div class="panel panel-default divRegister">
			<div class="panel-heading">
				<h4 class="panel-title">
					<a class="register" data-toggle="collapse" data-parent="#accordion" href="#collapse2">상품 카테고리 등록</a>
				</h4>
			</div>
			
			<div id="collapse2" class="panel-collapse collapse">
				<form name="registerCategoryFrm" class="registerFrm" autocomplete="off" id="insertCategory">
					<div class="form-group">
						<label >상품 종류 코드</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="error"></span>
						<select id="selectedKindcode" name="selectedKindcode" class="form-control">
							<option value="0">== 옵션선택 ==</option>
							<c:forEach var="kindvo" items="${kindList}" varStatus="status">
								<option value="${kindvo.kindcode }">${ kindvo.kindcode} (+${kindvo.krkindname })</option>
							</c:forEach>
						</select>
					</div>
					<div class="form-group">
						<label for="categorycode">상품 카테고리 코드</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="error"></span>
						<input type="text" class="form-control firstInput" id="categorycode" placeholder="ex)101(띄어쓰기 불가)" name="categorycode" maxlength="3" />
					</div>
					<div class="form-group">
						<label for="encategoryname">상품 카테고리 영문</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="error"></span>
						<input type="text" class="form-control" id="encategoryname" placeholder="ex)floral(띄어쓰기 불가)" maxlength="30" name="encategoryname" />
					</div>
					<div class="form-group">
						<label for="krcategoryname">상품 카테고리 국문</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="error"></span>
						<input type="text" class="form-control" id="krcategoryname" placeholder="ex)플로랄(띄어쓰기 불가)" maxlength="30" name="krcategoryname" />
					</div>
				</form>
				<div class="btnGroup" align="center">
					<button type="button" id="categorySubmit"class="btn btn-default">등록</button>
					<button type="button" class="btn btn-default" onclick="reset()">취소</button>
				</div>
			</div>
		</div>


