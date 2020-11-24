<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%
    String ctxPath = request.getContextPath();
    %>
    
    
<script type="text/javascript"> 

	$(document).ready(function(){
	////////////////////////////////////////////////////////////////////////////////////////////////////////
		// 옵션이름
		$("input:text[id=optionname]").blur(function(){
			if($(this).val() == "") {
				$(this).parent().find(".error").text("빈칸을 입력해 주세용");
				return;
			} else {
				$(this).parent().find(".error").text("");
			}
			
		});
		////////////////////////////////////////////////////////////////////////////////////////////////////////

		// 옵션 추가가격 확인
		$("input:text[id=addprice]").blur(function(){
			var addprice = $(this).val();
			
			var regExp = /^[0-9]+$/g;  
			checkInput("addprice", addprice, regExp);
			
		});
		////////////////////////////////////////////////////////////////////////////////////////////////////
		// 옵션 추가가격 확인
		$("input:text[id=qty]").blur(function(){
			var qty = $(this).val();
			
			var regExp = /^[0-9]+$/g;  
			checkInput("qty", qty, regExp);
			
		});
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		// 옵션 등록에서 상품 카테고리를 선택했을 때 발생하는 이벤트~
		$("select#selectedProductcode").change(function(){
			
			$("form#insertOption input").val("");
			$("form#insertOption span.error").text("");
			
			var selectedProductcode = $("select#selectedProductcode option:selected").val();
			
			if(selectedProductcode != 0) {  // 옵션 선택이 아닌 다른 것을 선택 한 경우
				$.ajax({
					url:"<%= ctxPath%>/admin/getOptioncode.com",
					type:"POST",
					data:{"selectedProductcode":selectedProductcode
						// , "optioncode":$("input#optioncode").val().trim()
						// , "optionname":$("input#optionname").val().trim()
						// , "addprice":$("input#addprice").val().trim()
						},
					dataType:"JSON",
					success:function(json){
						$("select#selectedProductcode").parent().find(".error").text("");
						
						if(json.result.length != 0) {
							$( 'input#optioncode' ).val(json.result);
							
						} else {	
							$( 'input#optioncode' ).val("오류발생ㅎㅎ;");
						}
					},
					error: function(request, status, error){
			        	alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
			        } 
				});
			} else {
				$("select#insertOption").parent().find(".error").text("옵션을 선택해 주세요!");
				$( 'input#optioncode' ).attr( 'placeholder', "ex)PF-101-001-S01" );
				return;
			}
			
		});
		////////////////////////////////////////////////////////////////////////////////////////////////////////
		// 카테고리 - 등록 버튼을 눌렀을 경우
		$("button#optionSubmit").click(function(){
			
			var flag = false;
			
			// 옵션태그 선택 여부 확인
			var selectedProductcode = $("select#selectedProductcode option:selected").val();
			
			if (selectedProductcode == 0) {
				$("select#selectedProductcode").parent().find(".error").text("옵션을 선택해 주세요!");
				flag = true;
			}else {
				$("select#selectedProductcode").parent().find(".error").text("");
			}
			
			// 각각의 input태그를 돌려서 비어있는 값이 있는지 확인한다.
			$("form#insertOption input:text").each( function(index, item){
				if($(this).val().trim() == "") {  // 만약 공백을 입력한 인풋태그가 있는경우
					
					if ($(this).parent().find(".error").html() == "") {  // 에러메세지 창에 아무런 에러메세지가 없다면
						$(this).parent().find(".error").text("빈칸을 입력해주세요");  // 빈칸을 입력해 달리고 글을 보여준다.
						flag = true;
					} 
				}
			});
			
			if ($("form#insertOption input#qty").val().trim() == '0') {
				$("form#insertOption input#qty").parent().find(".error").text("0이 될 수 없습니다.");
				$("form#insertOption input#qty").val("1");
				flag = true;
			}
			
			
			var error = $("form#insertCategory span.error").text();
			// console.log(errorList)
			// console.log(typeof(errorList));  -> String
			
			
			if(error.length != 0) {  // 문자열로 나오기 때문에 문자열의 길이를 구해준다. 아무것도 없으면 0 이 나온당~
				flag = true;   // 깃발을 올린다.
			}
			
			
			if (!flag) {  // 에러메세지가 아무것도 발견되지 않았을 경우

				$.ajax({
					url:"<%= ctxPath%>/admin/insertOptionJSON.com",
					type:"POST",
					data:{"optioncode":$("input#optioncode").val().trim()
						, "fk_productcode":selectedProductcode
						, "optionname":$("input#optionname").val().trim()
						, "addprice":$("input#addprice").val().trim()
						, "qty":$("input#qty").val().trim()},
					dataType:"JSON",
					success:function(json){
						
						if (json.result == 2) {
							$("span.error").text("");
							$("input#optioncode").parent().find(".error").text("같은 값의 키 코드가 있습니다.");
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
						} else if (json.result == 3){
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
					<a class="register" data-toggle="collapse" data-parent="#accordion" href="#collapse4">상품 옵션 등록</a>
				</h4>
			</div>
			
			<div id="collapse4" class="panel-collapse collapse">
				<form name="registerOptionFrm" class="registerFrm" autocomplete="off" id="insertOption">
					<div class="form-group">
						<label >상품 코드</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="error"></span>
						<select id="selectedProductcode" name="selectedProductcode" class="form-control">
							<option value="0">== 옵션선택 ==</option>
							<c:forEach var="productvo" items="${productList}" varStatus="status">
								<option value="${productvo.productcode }">${productvo.productcode} (+ ${productvo.krproductname })</option>
							</c:forEach>
						</select>
					</div>
					<div class="form-group">
						<label for="optioncode">상품 옵션 코드</label>
						<input type="text" disabled class="form-control firstInput" id="optioncode" placeholder="ex)PF-101-001-S01(자동입력)" name="optioncode" maxlength="14" />
					</div>
					<div class="form-group">
						<label for="optionname">옵션 이름</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="error"></span>
						<input type="text"  class="form-control" id="optionname" placeholder="50ml/라벤더향/지리산 쑥 등" maxlength="30" name="optionname" />
					</div>
					<div class="form-group">
						<label for="addprice">옵션 추가 가격</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="error"></span>
						<input type="text" class="form-control" id="addprice" placeholder="ex)0, 30000, 40000(숫자만)" maxlength="20" name="addprice" />
					</div>
					<div class="form-group">
						<label for="qty">수량</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="error"></span>
						<input type="text" class="form-control" id="qty" placeholder="ex)10, 30, 100(숫자만)" maxlength="8" name="qty" value="1"/>
					</div>
				</form>
				<div class="btnGroup" align="center">
					<button type="button" id="optionSubmit"class="btn btn-default">등록</button>
					<button type="button" class="btn btn-default" onclick="reset()">취소</button>
				</div>
			</div>
		</div>


