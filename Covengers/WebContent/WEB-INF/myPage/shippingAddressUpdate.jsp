<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
    
<!-- <!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" /> -->
<title>배송지변경</title>

<jsp:include page="../covengers_header.jsp"></jsp:include>

<style type="text/css">
    
    span.necesitado {
	    color: red;
	    font-weight: bold;
	    font-size: 13pt;
	}
    
    .mylabel{
        width: 100px;
    }
    
</style>

<!-- <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script> -->
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script type="text/javascript">

	$(document).ready(function(){
		
        // == 요청사항 select란에 띄워주기 == //
		
        var isSelected = false;
        
        $("input#deliveryRequest").hide();
        
		 $("#select option").each(function(){
		    if($(this).val()=="${svo.deliveryRequest}"){

		      $(this).attr("selected","selected");  
		      isSelected = true;
		    }
		 });
		 
	    if(!isSelected){
			$("input#deliveryRequest").val("${svo.deliveryRequest}");
	    	$("input#deliveryRequest").show();
			$("#select option:eq(5)").attr("selected","selected");
		}
			    
	    $("select#select").change(function(){ //셀렉트 체인지 됐을때
			
			var deliveryRequest = "";
		
			if($(this).val() == "기타"){ // 직접입력을 선택했을 때
				$("input#deliveryRequest").val("");
				$("input#deliveryRequest").show(); // 입력란 보여주기
			
			}
			else{ // 다른걸 선택했을 때
				$("input#deliveryRequest").hide(); // 입력란 숨기기
				$("input#deliveryRequest").val("");
				deliveryRequest = $("select#select").val();
				$("input#deliveryRequest").val(deliveryRequest);
  			}
		
	});
	    
		$("button#btnDelete").click(function(){
			shipno = $(this).siblings("input[name=shipNo]").val();
			$.ajax({
				url: "<%= request.getContextPath() %>/member/shippingAddressDelete.com",
				data: {"shipNo" : shipno},
				type: "post",
				dataType: "json",
				success:function(json){
					if(json.isDeleted){
						alert("배송지 삭제 성공");
						opener.location.reload();
						self.close();
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
		
		$("button#searchAddress").click(function () {
            new daum.Postcode({
                oncomplete: function (data) {
                    // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                    // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                    // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                    var addr = ""; // 주소 변수
                    var extraAddr = ""; // 참고항목 변수

                    //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                    if (data.userSelectedType === "R") {
                        // 사용자가 도로명 주소를 선택했을 경우
                        addr = data.roadAddress;
                    } else {
                        // 사용자가 지번 주소를 선택했을 경우(J)
                        addr = data.jibunAddress;
                    }

                    // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
                    if (data.userSelectedType === "R") {
                        // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                        // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                        if (data.bname !== "" && /[동|로|가]$/g.test(data.bname)) {
                            extraAddr += data.bname;
                        }
                        // 건물명이 있고, 공동주택일 경우 추가한다.
                        if (data.buildingName !== "" && data.apartment === "Y") {
                            extraAddr += extraAddr !== "" ? ", " + data.buildingName : data.buildingName;
                        }
                        // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                        if (extraAddr !== "") {
                            extraAddr = " (" + extraAddr + ")";
                        }
                        // 조합된 참고항목을 해당 필드에 넣는다.
                        document.getElementById("extraAddress").value = extraAddr;
                    } else {
                        document.getElementById("extraAddress").value = "";
                    }

                    // 우편번호와 주소 정보를 해당 필드에 넣는다.
                    document.getElementById("postcode").value = data.zonecode;
                    document.getElementById("address").value = addr;
                    // 커서를 상세주소 필드로 이동한다.
                    document.getElementById("detailAddress").focus();
                },
            }).open();
		}); // end of $("button#searchAddress").click(function () {}------------------
		
            $("input#mobile").keyup(function() { 
            	$(this).val( $(this).val().replace(/[^\d]/g, "").replace(/(\d{3})(\d{4})(\d{4})/,"$1-$2-$3"));
            	
            	var mobile = $(this).val();
            	if(mobile.trim() == ""){
            		$(this).val("");
            	}
            	else{
                    // 010-7681-0219
                    $(this).blur(function () {
                    	mobile = $(this).val();
                    	if(mobile.length != 13){
                             $(this).val("");
                    	}
                    });
            	}
            });
         
		
	});// end of $(document).ready(function(){});-----------------------------

	
	function goSaveUpdateAddress(){
		
		var bFlagRequiredInfo = false;

        $(".requiredInfo").each(function () {
            var data = $(this).val(); //선택자(필수입력란)의 값
            if (data == "") {
                //선택자(필수입력란)의 값이 공백일 때
                bFlagRequiredInfo = true;
                alert("필수 입력사항을 모두 입력해주세요.");
                return false;
            }
        });
	
        if(!bFlagRequiredInfo){
			var frm = document.shippingAddressFrm;
	        frm.action = "ShippingAddressUpdateEnd.com";
	        frm.method = "post";
	        frm.submit();
        }
	}

</script>



	<form name="shippingAddressFrm">
		<fieldset>
			<div class="addressCard">
				<c:if test="${svo.status eq '1'}">
					<div style="font-size: 15pt; color: green; font-weight: bold;">기본배송지</div>
				</c:if>
				<input type="hidden" name="shipNo" value="${svo.shipNo}"/>
				<input type="hidden" name="userno" value="${svo.userno}"/>
				<label class="mylabel">수취인명</label><span class="necesitado">*</span>
				<input name="receiverName" id="receiverName" class="myinput equiredInfo" value="${svo.receiverName}"/><br>
				<label class="mylabel">배송지이름</label><span class="necesitado">*</span>
				<input name="siteName"  id="siteName"  class="myinput equiredInfo" value="${svo.siteName}" /><br>
				<label class="mylabel">우편번호</label><span class="necesitado">*</span>
				<input name="postcode"  id="postcode" class="myinput equiredInfo" maxlength="5" value="${svo.postcode}"/><button type="button" id="searchAddress" class="btn btn-default">우편번호찾기</button><br/>
				<label class="mylabel">주소</label><span class="necesitado">*</span>
				<input name="address"  id="address"  class="myinput equiredInfo" value="${svo.address}" /><br>
				<label class="mylabel">상세주소</label><span class="necesitado equiredInfo">*</span>
				<input name="detailAddress"  id="detailAddress"  class="myinput" value="${svo.detailAddress}"/><br>
				<label class="mylabel">참고항목</label>
				<input name="extraAddress"  id="extraAddress"  class="myinput" value="${svo.extraAddress}"/><br>
				<label class="mylabel">연락처</label><span class="necesitado equiredInfo">*</span>
				<input name="mobile" class="myinput" id="mobile" maxlength="" value="${svo.mobile}" /><br>
				<label class="mylabel">요청사항</label>
				
				<select id="select">
				            <option id="0" value="" disabled selected>요청사항선택</option>
				            <option id="1">문 앞</option>
				            <option id="2">직접받고 부재시 문 앞</option>
				            <option id="3">경비실</option>
				            <option id="4">택배함</option>
				            <option id="5">기타</option>
					</select>
				
				<input name="deliveryRequest"  id="deliveryRequest" class="myinput"/><br>
				<input type="checkbox" name="status" value="1"/><span>기본배송지로 저장</span><br>
				<button id="btnUpdate" type="button" onclick="goSaveUpdateAddress()">저장</button>
				<button id="btnDelete" type="button">삭제</button>
			</div>
		</fieldset>
	</form>


	
</body>
</html>