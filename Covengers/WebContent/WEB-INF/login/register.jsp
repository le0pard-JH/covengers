<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<% String ctxPath = request.getContextPath(); %>

<jsp:include page="../covengers_header.jsp"></jsp:include>
        <title>회원가입페이지</title>
        <style>
            body {
                /* border: solid 1px gray; */
                margin: 0;
                padding: 0;
                font-family: Arial, "MS Trebuchet", sans-serif;
                word-break: break-all;
            }
            #container {
                /* border: soild 1px blue; */
                width: 60%;
                margin: 0 auto;
            }
            ul {
                list-style-type: none;
            }
            ul > li {
                line-height: 40px;
            }
            #marca {
                margin-top: 60px;
                text-align: center;
            }
            input.myinput {
                border: none;
                border-bottom: solid 1px lightgray;
                outline: none;
                font-size: 15pt;
                margin-bottom: 10px;
            }
            form {
                width: 600px;
                margin: 0 auto;
                padding: 20px 30px;
            }
            .pequenoTitle {
                font-weight: bold;
                font-size: 13pt;
            }
            .error {
                font-size: 11pt;
                color: red;
                margin-bottom: 10px;
            }
            button {
                font-weight: bold;
            }
            legend {
                font-size: 16pt;
                font-weight: bold;
                text-align: center;
                width: 120px;
                border: none;
            }
            fieldset {
                border: solid 1px lightgray;
                border-radius: 20px;
                padding-bottom: 20px;
                padding-right: 10px;
            }
            #like {
                border: solid 1px lightgray;
                border-radius: 10px;
                width: 95%;
                padding-left: 5%;
            }
            
            span.necesitado {
                color: red;
                font-weight: bold;
                font-size: 13pt;
            }
            
            select {
			    width: 130px;  /* 너비설정 */
			    height: 40px;
			    border: none;
			    font-size: 13pt;
			    border-bottom: solid 1px lightgray;
                outline: none;

            }
            
        </style>
      <!--   <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script> -->
        <script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
        <script>
            var isExistRcode = false;

            $(document).ready(function () {
                $(".error").hide();
                $("input#emailtext").hide(); 
                $("input#name").focus();
                
                $("input#name").blur(function () {
                    var name = $(this).val().trim();

                    if (name == "") {
                        $(this).next().show();
                    } else {
                        $(this).next().hide();
                    }
                });
                
				var emailtext = "";
                var emailid = "";
      			var email = "";
                
                
      			$("input#emailid").keyup(function(){ // 이메일아이디 입력창에 입력할때
      				
    				emailid = $(this).val(); //변수에 값 넣어주기
    				
      				if(emailid == ""){ // 이메일아이디 태그에 값 없을때
    					$("#emailCheck").text("이메일 주소를 입력해주세요.");
						$("#emailCheck").show();
    				}
      				else{
      					if($("select#select").val() != null){
      						//$("input#emailtext").val("");
      						emailtext = $("select#select").val();
      						email = $(this).val()+"@"+emailtext;
      						
      						$("input#email").val(email);
	    					emailDuplicateCheck();
      					}
      					if($("input#emailtext").val() != ""){
      						emailtext = $("input#emailtext").val();
      						
      						email = emailid+"@"+emailtext;
	    					$("input#email").val(email);
	    					 emailDuplicateCheck();
      					}
      				}
      			});
      			
				$("select#select").change(function(){ //셀렉트 체인지 됐을때
      				
      				if($(this).val() == "directly"){ // 직접입력을 선택했을 때
      					$("input#emailtext").show(); // 입력란 보여주기
      					$("#emailCheck").hide();
      				}
      				else{ // 다른걸 선택했을 때
    					$("input#emailtext").hide(); // 입력란 숨기기
    					$("input#emailtext").val("");
    					emailtext = $("select#select").val(); //선택된 태그의 값을 emailtext변수에 넣기
    					
	      					if(emailid != ""){
	          				email = emailid+"@"+emailtext;

	    					$("input#email").val(email);
	    					emailDuplicateCheck();
	          			}
    				}
				
    			});

					$("input#emailtext").keyup(function(){
	      				
	      				emailtext = $("input#emailtext").val();
	      				
	      				if(emailtext == ""){
	      					$("#emailCheck").text("이메일 주소를 올바르게 입력해주세요.");
							$("#emailCheck").show();
	      				}
	      				else{
	      					$("#emailCheck").hide();
	      					emailtext = $("input#emailtext").val();
	      				}
	      		
	      				if(emailid != "" && emailtext != ""){
	          				email = emailid+"@"+emailtext;

	    					$("input#email").val(email);
	    					 emailDuplicateCheck();
	          			}
	      			});
				
             

                $("input#password").blur(function () {
                    var pwd = $(this).val().trim();

                    if (pwd == "") {
                        $(this).next().text("비밀번호를 입력해주세요.");
                        $(this).next().show();
                    } else {
                        var regExp = new RegExp(/^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).*$/g);
                        // 숫자/문자/특수문자/ 포함 형태의 8~15자리 이내의 암호 정규표현식 객체 생성

                        var bool = regExp.test(pwd);

                        if (!bool) {
                            $(this).next().text("비밀번호는 영문자,숫자,특수기호가 혼합된 8~15 글자로 입력하세요.");
                            $(this).next().show();
                        } else {
                            $(this).next().hide();
                        }
                    }
                });

                $("input#passwordCheck").blur(function () {
                    var pwd = $("input#password").val();
                    var pwdcheck = $(this).val().trim();

                    if (pwdcheck == "") {
                        $(this).next().text("비밀번호 확인을 진행해주세요.");
                        $(this).next().show();
                    } else {
                        if (pwd != pwdcheck) {
                            $(this).next().text("비밀번호가 일치하지 않습니다.");
                            $(this).next().show();
                            $("input#password").focus();
                        } else {
                            $(this).next().hide();
                        }
                    }
                });

                $("input#birthday").keyup(function(){
                	$(this).val( $(this).val().replace(/[^\d]/g, "").replace(/(\d{4})(\d{2})(\d{2})/,"$1.$2.$3") );
                	
                	var birthday = $(this).val();
                	if(birthday == ""){
               		 $(this).next().text("생년월일을 올바르게 입력해주세요.(생년월일을 8자리로 입력해주세요! ) 예) 19960716 ");
                        $(this).next().show();
                        $(this).val("");
               	}
               	else{
               		$(this).next().hide();
                       // 010-7681-0219
                       $(this).blur(function () {
                    	birthday = $(this).val();
                       	if(birthday.length != 10){

                       		 $(this).next().text("생년월일을 올바르게 입력해주세요.(생년월일을 8자리로 입력해주세요! ) 예) 19960716 ");
                                $(this).next().show();
                                $(this).val("");
                       	}
                       });
               	}
                });

             

                
                $("input#mobile").keyup(function() { 
                	$(this).val( $(this).val().replace(/[^\d]/g, "").replace(/(\d{3})(\d{4})(\d{4})/,"$1-$2-$3"));
                	
                	var mobile = $(this).val();
                	if(mobile == ""){
                		 $(this).next().text("전화번호 11자리(또는 10자리)를 입력해주세요.");
                         $(this).next().show();
                         $(this).val("");
                	}
                	else{
                		$(this).next().hide();
                        // 010-7681-0219
                        $(this).blur(function () {
                        	mobile = $(this).val();
                        	if(mobile.length != 13){

                        		 $(this).next().text("전화번호 11자리(또는 10자리)를 입력해주세요.");
                                 $(this).next().show();
                                 $(this).val("");
                        	}
                        });
                	}
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
                });

                $("input:checkbox[name=taste]").change(function(){
                	if($("input:checkbox[name=taste]:checked").length >= 2) {
                		$(":checkbox[name=taste]:not(:checked)").attr("disabled", true);
                	}
                	else{
                		$("input:checkbox[name=taste]").removeAttr("disabled");
                	}
                });
                
                $("input#Rcode").blur(function () {
                    var Rcode = $(this).val();
                    if (Rcode != null) {
                        //db상에 해당 이메일이 있는지 확인
                        // 이메일이 입력되었고, 형식맞을 때
                        // 이메일 중복체크
                        var flagEmailDuplicateClick = true;

                        //if(isExist)
                        $.ajax({
                            url: "<%=ctxPath%>/member/emailDuplicateCheck.com", // url은 데이터를 보내줄 곳이다.
                            data: { email: $("input#Rcode").val() }, //data는 /MyMVC/member/emailDuplicateCheck.up으로 전송해야할 데이터를 말한다.
                            type: "post",
                            //   dataType:"json",    //JSON : Javascript Standard Object Notation. / dataType은 /MyMVC/member/idDuplicateCheck.up에서 실행된 결과물을 받아오는 데이터 타입을 이야기한다.
                            // dataType:"json"을 생략하면 웹페이지에 띄워지는  {"isExists":true}는 JSON(자바스크립트객체)으로 인식되지 않고 단순 문자열로 인식된다.
                            success: function (text) {
                                // (변수명은 뭐라고써도 노상관쓰~~)

                                var json = JSON.parse(text); // JSON.parse(text); 은 JSON 형식으로 되어진 문자열을 자바스크립트 객체로 변환해주는 것이다.
                                // 조심할 것은 text 는 반드시 JSON 형식으로 되어진 문자열이어야 한다.

                                if (json.isExists) {
                                    // 입력한 email이 존재할때
                                    // 있을 경우: 가입자와 해당 이메일주소를 가진 사람에게 10000포인트씩 더해주기
                                    // memberDAO에 update, memberVO

                                    $("div#RcodeCheck").text("회원가입 완료시 10,000 포인트가 즉시 지급됩니다.");
                                    $("div#RcodeCheck").show().css({ color: "green" });
                                    isExistRcode = true;
                                } else {
                                    // 입력한 userid가 DB 테이블에 존재하지 않는 경우 : 에러띄워주기
                                    $("div#RcodeCheck").text("해당 회원이 존재하지 않습니다. 확인후 다시 입력해주세요.").css({ color: "red" });
                                    $("div#RcodeCheck").show();
                                    $("div#Rcode").val("");
                                }
                            },
                            error: function (request, status, error) {
                                alert("code: " + request.status + "\n" + "message: " + request.responseText + "\n" + "error: " + error);
                            },
                        });
                    } else {
                    }
                });

                if (isExistRcode == true) {
                }
            }); // end of $(document).ready(function(){});------------------------------------

            function goRegister() {
                // == 약관동의 체크했는지 확인 == //
                var checkboxCheckedLength = $("input:checkbox[id=agree]:checked").length;

                if (checkboxCheckedLength == 0) {
                    alert("약관에 동의하지 않으면 회원가입이 불가합니다.");
                    return;
                }

                ////// 필수입력사항에 모두 입력이 됐는지 검사하기 /////

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

                if (isExistRcode) {
                    // 추천인으로 입력한 email이 존재할때
                    // 있을 경우: 가입자와 해당 이메일주소를 가진 사람에게 10000포인트씩 더해주기
                    // memberDAO에 update, memberVO

                    $("input#point").val("10000"); // 가입회원에게 10000포인트 더해주기

                    $.ajax({
                        url: "<%=ctxPath%>/member/pointUpdate.com", // url은 데이터를 보내줄 곳이다.
                        data: {
                            Rcode: $("input#Rcode").val(),
                        }, //data는 /MyMVC/member/emailDuplicateCheck.up으로 전송해야할 데이터를 말한다.
                        type: "post",
                        //   dataType:"json",    //JSON : Javascript Standard Object Notation. / dataType은 /MyMVC/member/idDuplicateCheck.up에서 실행된 결과물을 받아오는 데이터 타입을 이야기한다.
                        // dataType:"json"을 생략하면 웹페이지에 띄워지는  {"isExists":true}는 JSON(자바스크립트객체)으로 인식되지 않고 단순 문자열로 인식된다.
                        success: function (text) {
                            // (변수명은 뭐라고써도 노상관쓰~~)

                            var json = JSON.parse(text); // JSON.parse(text); 은 JSON 형식으로 되어진 문자열을 자바스크립트 객체로 변환해주는 것이다.
                            // 조심할 것은 text 는 반드시 JSON 형식으로 되어진 문자열이어야 한다.
                        },
                        error: function (request, status, error) {
                            alert("code: " + request.status + "\n" + "message: " + request.responseText + "\n" + "error: " + error);
                        },
                    });
                } 

                if (!bFlagRequiredInfo) {
                    var frm = document.registerFrm;
                    frm.action = "register.com"; //자기가 자기한테 보내는중
                    frm.method = "post";
                    frm.submit();
                }
            } // end of function goRegister()--------------------------------------------------------
            
            function emailDuplicateCheck() {
                var email = $("input#email").val();
            	
            	if (email == "") {
                    $("div#emailCheck").text("이메일은 필수입력사항 입니다.").css({ color: "red" });
                    $("div#emailCheck").show();
                } else {

                    var regExp = new RegExp(/^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i);

                    var bool = regExp.test(email);
                    
                    if (!bool) {
                    	$("div#emailCheck").text("이메일 형식이 맞지 않습니다.").css({ color: "red" });
                    	$("div#emailCheck").show();
                    	$("input#email").val("");
                    	
                    } else {
                        // 이메일이 입력되었고, 형식맞을 때
                        // 이메일 중복체크
                        var flagEmailDuplicateClick = true;

                        //if(isExist)
                        $.ajax({
                            url: "<%=ctxPath%>/member/emailDuplicateCheck.com", // url은 데이터를 보내줄 곳이다.
                            data: { email: $("input#email").val() }, //data는 /MyMVC/member/emailDuplicateCheck.up으로 전송해야할 데이터를 말한다.
                            type: "post",
                            //   dataType:"json",    //JSON : Javascript Standard Object Notation. / dataType은 /MyMVC/member/idDuplicateCheck.up에서 실행된 결과물을 받아오는 데이터 타입을 이야기한다.
                            // dataType:"json"을 생략하면 웹페이지에 띄워지는  {"isExists":true}는 JSON(자바스크립트객체)으로 인식되지 않고 단순 문자열로 인식된다.
                            success: function (text) {
                                // (변수명은 뭐라고써도 노상관쓰~~)

                                var json = JSON.parse(text); // JSON.parse(text); 은 JSON 형식으로 되어진 문자열을 자바스크립트 객체로 변환해주는 것이다.
                                // 조심할 것은 text 는 반드시 JSON 형식으로 되어진 문자열이어야 한다.
								
                                if (json.isExists) {
                                    // 입력한 userid가 이미 사용중일때(중복)
                                    $("div#emailCheck").text("해당 이메일은 이미 사용중인 이메일 입니다. 다른 이메일 주소를 입력해주세요.").css({ color: "red" });
                                    $("input#email").val("");
                                    $("div#emailCheck").show();
                                } else {
                                    // 입력한 userid가 DB 테이블에 존재하지 않는 경우(사용가능)
                                    $("div#emailCheck").text("해당 이메일은 사용가능합니다.").css({ color: "green" });
                                    $("div#emailCheck").show();
                                }
                            },
                            error: function (request, status, error) {
                                alert("code: " + request.status + "\n" + "message: " + request.responseText + "\n" + "error: " + error);
                            },
                        });
                        $("div#emailCheck").next().hide();
                    }
                }
            }
            
        </script>
    <!-- </head>
    <body> -->
        <h2 id="marca">Nombre de marca</h2>
        <div id="container">
            <form name="registerFrm">
                <fieldset>
                    <legend>회원가입</legend>
                    <ul>
                        <li>
                            <label class="pequenoTitle">성명&nbsp;</label><span class="necesitado">*</span> <input type="text" name="name" id="name" class="myinput requiredInfo" size="41px" />
                            <div class="error">성명은 필수입력사항 입니다.</div>
                        </li>
                        <li>
                            <label class="pequenoTitle">이메일&nbsp;</label><span class="necesitado">*</span><br>
                            <input type="text" id="emailid" class="myinput requiredInfo" size="10px" placeholder="이메일 입력"/>
                            <span>@</span>
                            <input type="text" id="emailtext" class="myinput" size="10px"/>
                            <select id="select">
					            <option value="" disabled selected>E-Mail 선택</option>
					            <option value="naver.com" id="naver.com">naver.com</option>
					            <option value="gmail.com" id="gmail.com">gmail.com</option>
					            <option value="hanmail.net" id="hanmail.net">hanmail.net</option>
					            <option value="nate.com" id="nate.com">nate.com</option>
					            <option value="directly" id="textEmail">직접 입력하기</option>
					        </select>
					        <input type="hidden" name="email" id="email"/>
                            <div class="error" id="emailCheck"></div>
                        </li>
                        <li>
                            <label class="pequenoTitle">비밀번호&nbsp;</label><span class="necesitado">*</span> <input type="password" name="password" id="password" class="myinput requiredInfo" size="41px" />
                            <div class="error"></div>
                        </li>
                        <li>
                            <label class="pequenoTitle">비밀번호확인&nbsp;</label><span class="necesitado">*</span> <input type="password" name="passwordCheck" id="passwordCheck" class="myinput requiredInfo" size="41px" />
                            <div class="error"></div>
                        </li>
                        <li>
                            <label class="pequenoTitle">생년월일</label> <input type="text" name="birthday" id="birthday" class="myinput" size="41px" maxlength="10" placeholder="예) 19960716" />
                            <div class="error"></div>
                        </li>
                         
                        <li>
                        	<label class="pequenoTitle">성별</label><br>
                            <select id="gender" name="gender" aria-label="성별">
	                            <option value="" selected>성별</option>
	                            <option value="1">남자</option>
	                            <option value="2">여자</option>
	                            <option value="3">선택 안함</option>
                          	</select>
                        </li>
                        
                        <li>
                            <label class="pequenoTitle">핸드폰번호</label> <input type="text" name="mobile" id="mobile" class="myinput" placeholder=" - 없이 입력" maxlength="13" size="41px" />
                            <div class="error"></div>
                        </li>
                        <li>
                            <label class="pequenoTitle">주소</label><br />
                            <input type="text" name="postcode" id="postcode" class="myinput" size="5pt" maxlength="5" placeholder="우편번호" />
                            <button type="button" id="searchAddress" class="btn btn-default">우편번호찾기</button>
                            <input type="text" name="address" id="address" class="myinput" size="41px" placeholder="주소" /> <input type="text" name="detailAddress" id="detailAddress" class="myinput" size="41px" placeholder="상세주소" />
                            <input type="text" name="extraAddress" id="extraAddress" class="myinput" size="41px" placeholder="참고항목" />
                            <div class="error"></div>
                        </li>
                        <li>
                            <label class="pequenoTitle">취향정보</label><span>(2개까지 선택가능)</span>
                            <div id="like">
                                <label for="dulce">달콤한향</label><input type="checkbox" name="taste" id="dulce" value="달콤"  />&nbsp;&nbsp; 
                                <label for="limpio">상쾌한향</label><input type="checkbox" name="taste" id="limpio" value="상쾌" />&nbsp;&nbsp; 
                                <label for="acido">상큼한향</label><input type="checkbox" name="taste" id="acido" value="상큼" />&nbsp;&nbsp; 
                                <label for="pesado">무거운향</label><input type="checkbox" name="taste" id="pesado" value="무거운" />&nbsp;&nbsp; 
                                <label for="fresco">시원한향</label><input type="checkbox" name="taste" id="fresco" value="시원" />&nbsp;&nbsp;
                            </div>
                            <!-- <div class="error">취향은 두개까지 선택가능합니다.</div> -->
                        </li>
                        <li>
                            <label class="pequenoTitle">추천인코드</label> <input type="text" name="rcode" id="Rcode" class="myinput" size="41px" />
                            <div class="error" id="RcodeCheck"></div>
                        </li>
                        <li><label for="agree" class="pequenoTitle">이용약관동의</label>&nbsp;<input type="checkbox" id="agree" /> <iframe src="../iframeAgree/agree.html" width="95%" height="150px" class="box"></iframe></li>
                    </ul>
                    <div id="buttons" style="text-align: center;">
                        <button type="button" class="btn btn-default" onclick="goRegister()">회원가입</button>
                        <button type="button" class="btn btn-default" onclick="location.href='http://localhost:9090/Covengers/main.com'">취소</button>
                    </div>
                    <input type="hidden" name="point" id="point" value="0" />
                </fieldset>
            </form>
        </div>
    </body>
</html>