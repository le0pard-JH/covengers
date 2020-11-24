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
		
		
		// 상품 코드 확인
		$("input:text[id=productcode]").blur(function(){
			var productcode = $(this).val();
			
			var regExp = /^[A-Z]{2}-[0-9]{3}-[0-9]{3}$/g;  // 숫자만 가능   3자리
			checkInput("productcode", productcode, regExp);
			
		});
		////////////////////////////////////////////////////////////////////////////////////////////////////
		// 상품 영문 이름 확인
		$("input:text[id=enproductname]").blur(function(){
			var enproductname = $(this).val();
			
			var regExp = /^([a-zA-Z])[a-zA-Z0-9\s]+$/g;  // 
			checkInput("enproductname", enproductname, regExp);
			
		});
		////////////////////////////////////////////////////////////////////////////////////////////////////
		// 상품 국문 이름  확인
		$("input:text[id=krproductname]").blur(function(){
			var krproductname = $(this).val();
			
			var regExp = /^([가-힣])[가-힣0-9\s]+$/g;  // 한글만 입력 가능함!
			checkInput("krproductname", krproductname, regExp);
			
		});
		////////////////////////////////////////////////////////////////////////////////////////////////////
		// 원가 확인
		$("input:text[id=price]").blur(function(){
			var price = $(this).val();
			
			var regExp = /^([1-9])[0-9]+$/g;  
			checkInput("price", price, regExp);
			
		});
		////////////////////////////////////////////////////////////////////////////////////////////////////
		// 상품 판매가 확인
		$("input:text[id=saleprice]").blur(function(){
			var saleprice = $(this).val();
			
			var regExp = /^([1-9])[0-9]+$/g;   
			checkInput("saleprice", saleprice, regExp);
			
		});
		////////////////////////////////////////////////////////////////////////////////////////////////////
		// 원산지
		$("input:text[id=origin]").blur(function(){
			var origin = $(this).val();
			
			var regExp = /^([a-zA-Z가-힣])[a-zA-Z가-힣0-9\s]+$/g;
			checkInput("origin", origin, regExp);
			
		});
		////////////////////////////////////////////////////////////////////////////////////////////////////////
		/* // 상품이미지
		$("input:text[id=productimg1]").blur(function(){
			
			if($(this).val() == "") {
				$(this).parent().find(".error").text("사진을 선택해 주세용");
				return;
			} else {
				$(this).parent().find(".error").text("");
			}
		}); */
		////////////////////////////////////////////////////////////////////////////////////////////////////////
		// 상품 간단 설명
		$("textarea[id=productdescshort]").blur(function(){

			if($(this).val() == "") {
				$(this).parent().find(".error").text("빈칸을 입력해 주세용");
				return;
			} else {
				$(this).parent().find(".error").text("");
			}
			
		});
		////////////////////////////////////////////////////////////////////////////////////////////////////////
		// 제조년월일
		$("input[id=manufacturedate]").blur(function(){
			if($(this).val() == "") {
				$(this).parent().find(".error").text("날짜를 선택해 주세용");
				return;
			} else {
				$(this).parent().find(".error").text("");
			}
			
		});
		////////////////////////////////////////////////////////////////////////////////////////////////////////
		// 유통기한
		$("input[id=expiredate]").blur(function(){
			if($(this).val() == "") {
				$(this).parent().find(".error").text("날짜를 선택해 주세용");
				return;
			} else {
				$(this).parent().find(".error").text("");
			}
			
		});
		////////////////////////////////////////////////////////////////////////////////////////////////////////
		// 디테일설명1
		$("textarea[id=productdesc1]").blur(function(){
			if($(this).val() == "") {
				$(this).parent().find(".error").text("빈칸을 입력해 주세용");
				return;
			} else {
				$(this).parent().find(".error").text("");
			}
			
		});
		////////////////////////////////////////////////////////////////////////////////////////////////////////
		// 성분표시
		$("textarea[id=ingredient]").blur(function(){
			if($(this).val() == "") {
				$(this).parent().find(".error").text("빈칸을 입력해 주세용");
				return;
			} else {
				$(this).parent().find(".error").text("");
			}
			
		});
		////////////////////////////////////////////////////////////////////////////////////////////////////////
		// 사용주의사항
		$("textarea[id=precautions]").blur(function(){
			if($(this).val() == "") {
				$(this).parent().find(".error").text("빈칸을 입력해 주세용");
				return;
			} else {
				$(this).parent().find(".error").text("");
			}
			
		});
		////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		
		
		// 상품 등록 에서 종류 선택 옵션을 바꾸었을시 발생하는 메서드
		$("select#selectedKindcodeInProduct").change(function(){
			var selectedKindcodeInProduct = $("select#selectedKindcodeInProduct option:selected").val();
			$("form#insertProduct input#productcode").val("");
			$("form#insertProduct span.error").html("");
			
			var html = "<option value='0'>==옵션선택==</option>";
			
			$( 'input#productcode' ).attr( 'placeholder', "ex)PF-101-001(자동입력)" );
		
			if(selectedKindcodeInProduct != 0) {
				$("select#selectedKindcodeInProduct").parent().find(".error").text("");
				var cnt = 1;
				<c:forEach items="${categoryList}" var="category">
					
					if ('${category.kindvo.kindcode}' == selectedKindcodeInProduct) {
						
						html += "<option value='" + ${category.categorycode} + "'>${category.krcategoryname} (${category.categorycode})</option>";
						cnt++;
					} 
				</c:forEach>
				$("select#selectedCategorycodeInProduct").html(html);
				
			} else {
				$("select#selectedCategorycodeInProduct").html(html);
				$("select#selectedKindcodeInProduct").parent().find(".error").text("옵션을 선택해 주세요!");
				return;
			}
			
		});
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// 상품 등록 에서 카테고리 선택 옵션을 바꾸었을시 발생하는 메서드
		$("select#selectedCategorycodeInProduct").change(function(){
			
			$("form#insertProduct input#productcode").val("");
			// $("form#insertProduct span.error").text("");
			
			var selectedCategorycodeInProduct = $("select#selectedCategorycodeInProduct option:selected").val();
			console.log(selectedCategorycodeInProduct);
			if(selectedCategorycodeInProduct != 0) {
				$.ajax({
					url:"<%= ctxPath%>/admin/selectedKindCategory.com",
					type:"POST",
					data:{"selectedCategoryOption":selectedCategorycodeInProduct},
					dataType:"JSON",
					success:function(json){
						$("select#selectedCategorycodeInProduct").parent().find(".error").text("");
						
						if(json.result.length > 9) {
							var result =  Number(json.result.substr(7,3)) + 1 + ""
							
							if (result.length == 1) {
								result = "00" + result;
							} else if (result.length == 2) {
								result = "0" + result;
							} else if (result.length == 3 && !(result != 999)){
								result = result;
							} else if (result.length == 3 && result != 999) {
								result = "999";
							}
							
							
							$( 'input#productcode' ).val(json.result.substr(0, 7) + result);
						} else if (json.result.length == 7){
							$( 'input#productcode' ).val(json.result + "001");
						}
					},
					error: function(request, status, error){
			        	alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
			        } 
				});
				
			} else {
				$("select#selectedCategorycodeInProduct").parent().find(".error").text("옵션을 선택해 주세요!");
				return;
			}
			
		});
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		$("button#productSubmit").click(function(){
			var flag = false;
			
			// 옵션태그 선택 여부 확인
			var selectedKindcodeInProduct = $("select#selectedKindcodeInProduct option:selected").val();
			
			if(selectedKindcodeInProduct == 0) {
				$("select#selectedKindcodeInProduct").parent().find(".error").text("옵션을 선택해 주세요!");
				flag = true;
			} else {
				$("select#selectedKindcodeInProduct").parent().find(".error").text("");
			}
			
			var selectedCategorycodeInProduct = $("select#selectedCategorycodeInProduct option:selected").val();
			
			if(selectedCategorycodeInProduct == 0) {
				$("select#selectedCategorycodeInProduct").parent().find(".error").text("옵션을 선택해 주세요!");
				flag = true;
			} else {
				$("select#selectedCategorycodeInProduct").parent().find(".error").text("");				
			}
			
			
			
			// 각각의 input태그를 돌려서 비어있는 값이 있는지 확인한다.
			$("form#insertProduct input:text").each(function(index, item){
				// console.log("바보야");
				if($(this).val().trim() == "") {  // 만약 공백을 입력한 인풋태그가 있는경우
					if ($(this).parent().find(".error").html() == "") {  // 에러메세지 창에 아무런 에러메세지가 없다면
						$(this).parent().find(".error").text("빈칸을 입력해주세요");  // 빈칸을 입력해 달리고 글을 보여준다.
						flag = true;
					}
				} 
			});
			
			$("form#insertProduct input#productimg1").each(function(index, item){
				// console.log("바보야");
				if($(this).val().trim() == "") {  // 만약 공백을 입력한 인풋태그가 있는경우
					if ($(this).parent().find(".error").html() == "") {  // 에러메세지 창에 아무런 에러메세지가 없다면
						$(this).parent().find(".error").text("파일을 선택해 주세용");  // 빈칸을 입력해 달리고 글을 보여준다.
						flag = true;
					}
				} else {
					$(this).parent().find(".error").text("");
				}
			});
			
			$("form#insertProduct input#manufacturedate").each(function(index, item){
				// console.log("바보야");
				if($(this).val().trim() == "") {  // 만약 공백을 입력한 인풋태그가 있는경우
					if ($(this).parent().find(".error").html() == "") {  // 에러메세지 창에 아무런 에러메세지가 없다면
						$(this).parent().find(".error").text("날짜를 입력해주세요");  // 빈칸을 입력해 달리고 글을 보여준다.
						flag = true;
					}
				}
			});
			
			$("form#insertProduct input#expiredate").each(function(index, item){
				// console.log("바보야");
				if($(this).val().trim() == "") {  // 만약 공백을 입력한 인풋태그가 있는경우
					if ($(this).parent().find(".error").html() == "") {  // 에러메세지 창에 아무런 에러메세지가 없다면
						$(this).parent().find(".error").text("날짜를 입력해주세요");  // 빈칸을 입력해 달리고 글을 보여준다.
						flag = true;
					}
				} 
			});
			// 각각의 textarea 태그를 돌려서 비어있는 값이 있는지 확인한다.
			$("form#insertProduct textarea").each( function(index, item){
				
				if($(this).val().trim() == "") {  // 만약 공백을 입력한 인풋태그가 있는경우
					if ($(this).parent().find(".error").html() == "") {  // 에러메세지 창에 아무런 에러메세지가 없다면
						$(this).parent().find(".error").text("빈칸을 입력해주세요");  // 빈칸을 입력해 달리고 글을 보여준다.
						flag = true;
					}
				} 
			});
			
			var error = $("form#insertProduct span.error").text();
			
			
			if(error.length != 0) {  // 문자열로 나오기 때문에 문자열의 길이를 구해준다. 아무것도 없으면 0 이 나온당~
				flag = true;   // 깃발을 올린다.
			}
			
			if (!flag) {  // 에러메세지가 아무것도 발견되지 않았을 경우
				
				
				$("#insertProduct").ajaxSubmit({
					url:"<%= ctxPath%>/admin/insertProductJSON.com",
					type:"POST",
					data:{"productcode":$("input#productcode").val().trim()
						, "fk_kindcode":$("select#selectedKindcodeInProduct option:selected").val().trim()
						, "fk_categorycode":$("select#selectedCategorycodeInProduct option:selected").val().trim()
						, "enproductname":$("input#enproductname").val().trim()
						, "krproductname":$("input#krproductname").val().trim()
						, "productimg1":$("input#productimg1").val().trim()
						, "productimg2":$("input#productimg2").val().trim()
						, "price":$("input#price").val().trim()
						, "saleprice":$("input#saleprice").val().trim()
						, "origin":$("input#origin").val().trim()
						, "productdescshort":$("textarea#productdescshort").val().trim()
						, "manufacturedate":$("input#manufacturedate").val()
						, "expiredate":$("input#expiredate").val()
						, "productdesc1":$("textarea#productdesc1").val().trim()
						, "productdesc2":$("textarea#productdesc2").val().trim()
						, "ingredient":$("textarea#ingredient").val().trim()
						, "precautions":$("textarea#precautions").val().trim()},
					dataType:"JSON",
					contentType: false,
		            processData: false,
					success:function(json){
						if (json.result == 2) {
							$("span.error").text("");
							$("input#productcode").parent().find(".error").text("같은 값의 키 코드가 있습니다.");
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
							$("input#enproductname").parent().find(".error").text("같은 이름이 있습니다.");
							return;
						}else if (json.result == 4) {
							$("span.error").text("");
							$("input#krproductname").parent().find(".error").text("같은 이름이 있습니다.");
							return;
						} else {
							$("span.error").text("");
							alert("내부적 문제로 인한 등록실패!");
							$("form#insertProduct")[0].reset();
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
	
	function updateProductFrm() {
		
		var frm = document.updateProductFrm;
	    frm.action = "<%=request.getContextPath()%>/admin/updateProduct.com";
		frm.method = "post";
		frm.submit();
		
	}
	
		
		
	
</script>

<div id="registerProduct">

	<div id="registerProductHeader" align="center">
		<span><h2>상품 정보 수정 </h2></span>
	</div>
	
	<hr style ="border: 2px solid black; background-color: black;">

	<div class="panel-group" id="accordion">
	
		
		<!-- ---------------------------------------------------------------------------------------------------- -->
		<!-- ---------------------------------------------------------------------------------------------------- -->
<div class="panel panel-default divRegister" style="max-height: 600px; overflow: auto;">
	<div class="panel-heading" >
		<h4 class="panel-title">
			<a class="register" data-toggle="collapse" data-parent="#accordion" href="#collapse3">상품 수정</a>
		</h4>
	</div>
	
	<div id="collapse3" class="panel-collapse collapse in" >
		<form name="updateProductFrm" class="registerFrm" autocomplete="off" id="insertProduct" enctype="multipart/form-data" method="post">
			<div class="form-group">
				<label >상품 종류 코드</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="error"></span>
				<select id="selectedKindcodeInProduct" name="selectedKindcodeInProduct" class="form-control">
					<option value="0">== 옵션선택 ==</option>
					<c:forEach var="kindvo" items="${kindList}">
						<option value="${kindvo.kindcode}">${ kindvo.kindcode} (+${kindvo.krkindname })</option>
					</c:forEach>
				</select>
			</div>

			<div class="form-group">
				<label for="enproductname">상품 영문</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="error"></span>
				<input type="text" class="form-control" id="enproductname" maxlength="50" name="enproductname" value="${pvo.enproductname}"/>
			</div>
			<div class="form-group">
				<label for="krproductname">상품 국문</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="error"></span>
				<input type="text" class="form-control" id="krproductname" maxlength="50" name="krproductname" value="${pvo.krproductname}"/>
			</div>
			<div class="form-group">
				<label for="productimg1">상품 이미지1</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="error"></span>
				<input type="file" class="form-control" id="productimg1" name="productimg1" />
			</div>
			<div class="form-group">
				<label for="productimg2">상품 이미지2(선택)</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="file" class="form-control" id="productimg2"  name="productimg2" />
			</div>
			<div class="form-group">
				<label for="price">상품 원가</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="error"></span>
				<input type="text" class="form-control" id="price" maxlength="20" name="price" value="${pvo.enproductname}" />
			</div>
			<div class="form-group">
				<label for="saleprice">상품 판매가</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="error"></span>
				<input type="text" class="form-control" id="saleprice" maxlength="20" name="saleprice" value="${pvo.price}" />
			</div>
			<div class="form-group">
				<label for="origin">원산지</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="error"></span>
				<input type="text" class="form-control" id="origin" maxlength="50" name="origin" value="${pvo.origin}" />
			</div>
			<div class="form-group">
				<label for="productdescshort">상품 간단 설명</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="error"></span>
				<textarea class="form-control" id="productdescshort" placeholder="${pvo.productdescshort}" maxlength="100" name="productdescshort" rows="3" style="resize: none;"></textarea>
			</div>
			
			<div class="form-group">
				<label for="manufacturedate">제조년월일</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="error"></span>
				<input type="date" class="form-control" id="manufacturedate" name="manufacturedate" value="${pvo.manufacturedate}" />
			</div>
			<div class="form-group">
				<label for="expiredate">유통기한</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="error"></span>
				<input type="date" class="form-control" id="expiredate" name="expiredate" value="${pvo.expiredate}" />
			</div>
			<div class="form-group">
				<label for="productdesc1">상품 디테일 설명1</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="error"></span>
				<textarea class="form-control" id="productdesc1" placeholder="${pvo.productdesc1}" name="productdesc1" maxlength="1000" rows="5" style="resize: none;"></textarea>
			</div>
			<div class="form-group">
				<label for="productdesc2">상품 디테일 설명2(선택)</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<textarea class="form-control" id="productdesc2" placeholder="${pvo.productdesc2}" name="productdesc2" maxlength="1000" rows="5" style="resize: none;"></textarea>
			</div>
			<div class="form-group">
				<label for="ingredient">성분표시</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="error"></span>
				<textarea class="form-control" id="ingredient" placeholder="${pvo.ingredient}" name="ingredient" maxlength="1000" rows="5" style="resize: none;"></textarea>
			</div>
			<div class="form-group">
				<label for="precautions">사용주의사항</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="error"></span>
				<textarea class="form-control" id="precautions" placeholder="${pvo.precautions}" name="precautions" maxlength="1000" rows="5" style="resize: none;"></textarea>
			</div>
		</form>
		<div class="btnGroup" align="center">
			<button type="button" id="productSubmit"class="btn btn-default" onclick="updateProductFrm">수정</button>
			<button type="button" class="btn btn-default" onclick="reset()">취소</button>
		</div>
	</div>
</div>		

		<!-- ---------------------------------------------------------------------------------------------------- -->

	</div>
	
	

</div>


</body>
</html>