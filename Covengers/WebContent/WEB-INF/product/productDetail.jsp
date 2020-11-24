<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
	<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
	<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String ctxPath = request.getContextPath();

	String check = (String)request.getAttribute("check");
	
%>

<jsp:include page="../covengers_header.jsp"></jsp:include>

<style>

.modal-con{
  display:none;
  position:fixed;
  top:10%; left:50%;
  transform: translate(-50%,-50%);
  max-width: 60%;
  min-height: 30%;
  background: white;
}
.modal-con .title{
  font-size:20px; 
  padding: 20px; 
  background : gray;
}
.modal-con .con{
  font-size:15px; line-height:1.3;
  padding: 30px;
}
.modal-con .close{
  display:block;
  font-weight: bold;
  position:absolute;
  width:30px; height:30px;
  border-radius:50%; 
  border: 3px solid white;
  text-align:center; line-height: 30px;
  text-decoration:none;
  color: white; font-size:20px; font-weight: bold;
  right:10px; top:10px;
}
/****************************************************************************************************  */
/* product detail jsp css */
* {
	box-sizing: border-box;
}

.bg {
	/* The image used */
	/* background-image: url("/Covengers/images/orange.jpg"); */
	
	/* Add the blur effect */
	/* filter: blur(8px); */
	/* -webkit-filter: blur(8px); */
	
	/* Full height */
	height: 100%;
	/* Center and scale the image nicely */
	/* background-position: center;
	background-repeat: no-repeat;
	background-size: cover; */
	/* margin-top: -30px; */
}

/* Position text in the middle of the page/image */
#bg-product-dp {
	background-color: rgb(255, 255, 255); /* Fallback color */
	/* background-color: rgba(255, 255, 255, 0.8); */
	/* Black w/opacity/see-through */
	/* color: white; */
	font-weight: bold;
	/* border: 3px solid #f1f1f1; */
	position: absolute;
	top: 58%;
	left: 50%;
	transform: translate(-50%, -50%);
	z-index: 2;
	width: 60%;
	min-width: 700px;
	height: 90%;
	padding: 20px;
	text-align: center;
	border-radius: 20px;
}
/*************************************************************************]] */
div#product-name {
	width: 90%;
	height: 18%;
	margin: 2px auto;
	color: black;
}

div#inner-product-dp {
	width: 90%;
	height: 60%;
	overflow: hidden;
	margin: auto;
}

div.product-img-dp {
	height: 100%;
	width: 450px
	line-height: 1px; 
	padding: 2%;
	border-radius: 4%;
	overflow: auto;
}

div#inner-product-dp img {
	width: 100%;
	height: 100%;
	vertical-align: middle;
}

div.product-detail-dp {
	height: 100%;
	padding: 3%;
	/* border: double 5px gray; */
	border-radius: 4%;
	
	
}

/***************************************************************************  */
/* 상품메뉴 보여주기  */

div#inner-product-menu {
	width: 90%;
	height: 18%;
	margin-top: 10px;
	margin: auto;
	background-color: rgba(0, 0, 0, 0);
	overflow: auto;
	padding: 40px 0;
}

div#product-menu {
	overflow: hidden;
	background-color: rgba(0, 0, 0, 0);
	color: black;
	text-align: center;
	width: 100%;
	height: 100%;
}

/* Style the buttons that are used to open the tab content */
#product-menu button {
	background-color: inherit;
	display: inline-block;
	margin: 0 20px;
	border: none;
	outline: none;
	cursor: pointer;
	padding: 14px 16px;
	transition: 0.3s;
	width: 15%;
}

/* Change background color of buttons on hover */
#product-menu button:hover {
	background-color: #ddd;
}

/* Create an active/current tablink class */
#product-menu button.active {
	background-color: #ccc;
}

/* Style the tab content */
.product-detail {
    display: none;
	color: black;
	width: 100%;
	height: 100%;
	text-align: center;
	overflow: auto;
	
}

.product-detail {
	animation: fadeEffect 0.5s; /* Fading effect takes 1 second */
}

/* Go from zero to full opacity */
@keyframes fadeEffect {
		from {opacity: 0;
	}
	
	to {
		opacity: 1;
	}
}

.detail_title {
	color: gray;
	font-size:13pt;
}
/**************************************************************************  */
button.btn-action {
	display: inline-block;
	text-align: center;
	margin: 5px 15px;
}

.swiper-container {
      width: 100%;
      height: 80%;
      margin-top: 30px;
}

.swiper-slide {
      text-align: center;
      font-size: 18px;
      background: rgba(255,255,255 , 1.0);
      overflow: hidden;

    /*  Center slide text vertically */
      display: -webkit-box;
      display: -ms-flexbox;
      display: -webkit-flex;
      display: flex;
      -webkit-box-pack: center;
      -ms-flex-pack: center;
      -webkit-justify-content: center;
      justify-content: center;
      -webkit-box-align: center;
      -ms-flex-align: center;
      -webkit-align-items: center;
      align-items: center;
    }
/* ***************************************************************************** */
/* .product-detail { display:none; } */
.product-detail::-webkit-scrollbar { width: 0px; height: 0px;}
.product-detail::-webkit-scrollbar-track-piece  { background-color: rgba(0,0,0,0.7);}
.product-detail::-webkit-scrollbar-thumb:vertical {height: 4px; border-radius:8px; color: black;}
</style>

<div class="bg"></div>

<c:if test="${not empty productList }">
	<div style="height: 96%; width:20%; position: absolute; top: 65%; left: 90%; transform: translate(-50%, -50%);" align="center">
		<div style="width: 90%; height: 90%; margin-top: 20px;">
			<div>
				<h3>관련상품</h3>
			</div>
			
			<div class="swiper-container">
				<div class="swiper-wrapper" >
					<c:forEach var="product" items="${productList}">
						<div class="swiper-slide">
							<div style="margin: 20px;">
								<div class="image" style="text-align: center;">
									<a href="<%=ctxPath%>/product/productDetail.com?productcode=${product.productcode}">
										<img src="/Covengers/images/${product.productimg1}" alt="${product.krproductname}" width="100%" height="100%" style="float:center;">
									</a>
									<p style="font-size:10px;"><a href="<%=ctxPath%>/product/productDetail.com?productcode=${product.productcode}">${product.krproductname}</a></p>
								</div>
								<%--<div class="description">
									<p><a href="<%=ctxPath%>/product/productDetail.com?productcode=${product.productcode}">${product.krproductname}</a></p>
								</div> --%>
							
							</div>
							
						</div>
					
					
					</c:forEach>
					
				</div>
				
				
			
			<script src="https://unpkg.com/swiper/swiper-bundle.min.js"></script>
		
			<!-- Initialize Swiper -->
			<script>
				var swiper = new Swiper('.swiper-container', {
			        direction: 'vertical',
			        slidesPerView: 2,
			        spaceBetween: 20,
			        mousewheel: true,
			        freeMode: true,
			        /* loop: true, */
			        autoplay: {
			            delay: 3000,
			            disableOnInteraction: false,
			          },
			       /*  autoHeight: true, */
			        pagination: {
			          el: '.swiper-pagination',
			          clickable: true,
			        },
		    	});
			</script>
			</div>
		</div>
	</div>

</c:if>

<div id="bg-product-dp">
	<div id="product-name">
		<h2>${product.enproductname}</h2>
		<h2>${product.krproductname}</h2>
	</div>
	
	<div id="inner-product-dp">
		<div class="col-md-6 product-img-dp" align="center">
			<img alt="perfume1" src="/Covengers/images/${product.productimg1}">
		</div>

		<div class="col-md-1"></div>
		
		<div class="col-md-5 product-detail-dp">
			<div id="detail" class="product-detail">
				<h3>${product.enproductname}</h3>
				<p><fmt:formatNumber value="${product.saleprice}" pattern="#,###" />원</p>
				<hr style="border: solid 1px black;">
				<p>${product.productdesc1}</p>
				<br>
				
				
				<!-- <span onclick="this.parentElement.style.display='none'">x</span> -->
			</div>

				<div id="info" class="product-detail">
					<h3>필수표기정보</h3>
					<hr style="border: solid 1px black;">
					<p><strong class="detail_title">제조일자</strong><br><br>${product.manufacturedate}</p>
					<hr>
					<p><strong class="detail_title">유통기한</strong><br><br>${product.expiredate}</p>
					<hr>
					<p><strong class="detail_title">원산지</strong><br><br>${product.origin}</p>
					<hr>
					<p><strong class="detail_title">성분표시</strong><br><br>${product.ingredient}</p> 
					<hr>
					<p><strong class="detail_title">사용주의사항</strong><br><br>${product.precautions}</p> 
				</div>

			<div id="review" class="product-detail">
				<div>
					<h3>리뷰</h3>
					<hr style="border: solid 1px black;">
					<c:if test="${reviewList.size() ne 0 }">
					
					<c:forEach var="review" items="${reviewList}">
						
							<c:if test="${fn:length(review.rcontents) <= 40 }">
								<span style="color: #cc66ff;">${review.rcontents }</span>
							</c:if>
							
							<c:if test="${fn:length(review.rcontents) > 40 }">
								<c:set var="contents" value="${ fn:substring(review.rcontents, 0, 40)}"></c:set>
								<c:set var="contents" value="${fn:replace(contents,'<br>', ' ') }"></c:set>
								<span style="color: #cc66ff;">${contents }...</span>
							</c:if>
						<br>
						<span>작성자 : ${review.email }</span>
						<br><br>
					</c:forEach>
					</c:if>
					<c:if test="${reviewList.size() eq 0 }">
						<span>등록된 리뷰가 없습니다...</span>
						<br>
						<span>첫번째 리뷰 등록자가 되어보세요!</span>
						
					</c:if>
					
					
					
					
				</div>
			</div>
			
			<div id="order" class="product-detail">
			
				<div id="select-product-option" style="position: relative; font-family: Arial;">
					<h3>주문하기</h3>
					<hr style="border: solid 1px black;">
					<div style="margin-bottom: 10px;">
						<div class="col-md-5" >
						
							<label style="font-size: 14pt; display: inline-block; width: 200px;">가격</label>
							<br>
							<span id="showPrice"><fmt:formatNumber value="${product.saleprice}" pattern="#,###" /></span>원
						</div>
										
						<div class="col-md-7">
							<label style="font-size: 14pt; display: inline-block; width: 200px;">옵션선택 </label>
							<br>
							 <select id="selected-option" name="selected-option">
							 <c:if test="${optionListSize ne 0 }">
								<c:forEach var="option" items="${optionList}" varStatus="status">	
									<c:choose>
										<c:when test="${status.index eq 0}">
																				
											<option value="${option.optioncode}" selected>${option.optionname} - (+<fmt:formatNumber value="${option.addprice}" pattern="#,###" />원)</option>								
										</c:when>
										<c:otherwise>
										
											<option value="${option.optioncode}">${option.optionname} - (+<fmt:formatNumber value="${option.addprice}" pattern="#,###" />원)</option>								
										</c:otherwise>
									</c:choose>
									
								</c:forEach>							 
							 </c:if>
							 
							 <c:if test="${optionListSize eq 0 }">
							 	<option  selected disabled >선택가능 옵션없음</option>
							 </c:if>
								
							</select> 
						</div>
					</div>
					
					<br>
					<br>
					<br>
					<br>
					

					<c:if test="${optionListSize eq 0 }">
						<div>
						 	<p style="font-size: 14pt;">죄송합니다.<br>현재 상품 준비중에 있습니다.</p>
						 	<br>
						 </div>
						 <hr>
					 	<div align="center">
						 	<button class="btn-action" type="button" value="back" onclick="shopping()">뒤로가기</button>						 	
					 	</div>
					 </c:if>
					 <c:if test="${optionListSize ne 0 }">
					 	<div class="col-md-5"></div>
						<div class="col-md-7">
							<label style="font-size: 14pt; display: inline-block; width: 200px;">수량선택 </label>
							<br>
							<button type="button" id="down" onclick="doDown();"> - </button><input type="text" value="1" id="orderQuantity" name="orderQuantity" readonly size="5" ></input><button type="button" id="up" onclick="doUp();"> + </button>
						</div>
						
						<br>
						<br>
						<br>
						<br>
						
						
						<div>						
							<label style="font-size: 14pt; display: inline-block; width: 200px;">총 가격 </label>
							<br>
							<span id="totalPrice"><fmt:formatNumber value="${product.saleprice}" pattern="#,###" /></span>원
						</div>
						<br>
						<div align="center">
							<button class="btn btn-default btn-action" type="button" value="cart" onClick="goCart()"><strong>장바구니 넣기</strong></button>
							<button class="btn btn-default btn-action" type="button" value="order" onclick="goOrder()"><strong>바로 결제</strong></button>
						</div>
						
					 </c:if>
					
					
					
				</div>
				
				<!-- 카트로 보내어 주는 폼태그 -->
				<form name="orderDetail">
					<!-- 로그인 유저는 나중에 불러오면 되니까 일단 스킵ㅂ -->
					<input type="hidden" id="userno" name="userno" value="${sessionScope.loginuser.userno}">
					<input type="hidden" id="productcode" name="productcode" value="${product.productcode}" />
					<input type="hidden" id="poqty" name="poqty" />
					<input type="hidden" id="pprice" name="pprice" />
					<input type="hidden" id="optioncode" name="optioncode" />
				</form>

				
			
			</div>
		</div>

	</div>
	<div id="inner-product-menu" align="center">
		<div id="product-menu">
			<button class="tablinks" onclick="openDetail(event, 'detail')" id="defaultOpen">상세정보</button>
			<button class="tablinks" onclick="openDetail(event, 'info')">필수표기정보</button>
			<button class="tablinks" onclick="openDetail(event, 'review')">리뷰보기</button>
			<button class="tablinks" onclick="openDetail(event, 'order')">주문하기</button>
		</div>
	</div>

<!-- 장바구니에 상품 추가된후 띄워주는 모달창! -->

	<div class="modal-con modal1">
	<a href="javascript:;" class="close">&times;</a>
	<div class="con title">
		<span>::상품 추가 완료::</span>
		
	</div>
		<br>
		<br>
		<p>장바구니에 상품을 넣었습니다!</p>
		<br>
		<button type="button" style="margin: 35% 30px 30px 30px; color: black;" onclick="shopping()">쇼핑계속하기</button>
		<button type="button" style="margin: 35% 30px 30px 30px; color: black;" onclick="cart()">장바구니로이동</button>
			
	</div>
<!---------------------------------------------------------------------------------------------------------------------->

	<script type="text/javascript">
	
		function openModal(modalname){
		  document.get
		  $("#modal").fadeIn(300);
		  $("."+modalname).fadeIn(300);
		}

		$(".close").on('click',function(){
		  $(".modal-con").fadeOut(300);
		  history.back();
		});
		
		
		$(document).ready(function(){
		
			 
			 if(<%= check %> != null) {
				 openModal('modal1');		  
			 }
			 
			showPrice();
			
			
			$("select[id=selected-option]").change(function(){
				showPrice();
			});
			
		});
		
		document.getElementById("defaultOpen").click();
		
		
		// 선택되어진 옵션과 비교해서 가격을 보여주는 메소드
		function showPrice() {
			
			var index = $("#selected-option option").index( $("#selected-option option:selected"));
			var optionListSize = ${optionListSize};
			var price = ${product.saleprice};
			var addpriceList = new Array();
			if (optionListSize != 0) {
				
				<c:forEach items="${optionList}" var="option">
					addpriceList.push(${option.addprice});
				</c:forEach>
				
				for(var i = 0; i < optionListSize; i ++) {
					if (index == 0) {
						
						$("span#showPrice").html(price);
						$("input:text[name=orderQuantity]").val(1);
						checkTotalPrice();
						return;
					}
					else if (index == i) {
						price = price + addpriceList[i];
						$("span#showPrice").html(price);
						$("input:text[name=orderQuantity]").val(1);
						checkTotalPrice();
						return;
					}
				}
			} else {
				return;
			}
			
			
			
		}
		
		// 상품 디테일 창에서 각각의 탭을 눌렀을 경우 나타나게 해주는 메소드
		function openDetail(evt, contents) {
			// Declare all variables
			var i, productDetail, tablinks;

			// 탭 전부 숨겨버리기
			productDetail = document.getElementsByClassName("product-detail");
			for (i = 0; i < productDetail.length; i++) {
				productDetail[i].style.display = "none";
			}

			// 액티브 클래스 넣어주기
			tablinks = document.getElementsByClassName("tablinks");
			for (i = 0; i < tablinks.length; i++) {
				tablinks[i].className = tablinks[i].className.replace(" active", "");
			}

			// Show the current tab, and add an "active" class to the button that opened the tab
			document.getElementById(contents).style.display = "block";
			evt.currentTarget.className += " active";
		}
		
		// 물건 수량을 1개씩 줄일 수 있는 메서드
		function doDown() {
			
			var quantity = (Number)($("input:text[name=orderQuantity]").val());
			if (quantity == 1) {
				return;
			} else  {
				$("input:text[name=orderQuantity]").val(quantity - 1);
			}
			
			
			checkTotalPrice();
			
			
		}
		
		// 물건 수량을 1개씩 늘릴 수 있는 메서드
		function doUp() {
			
			var quantity = (Number)($("input:text[name=orderQuantity]").val());
			
			$("input:text[name=orderQuantity]").val(quantity + 1);
			
			
			checkTotalPrice();
			
			
		}
		
		// 전체 가격을 페이지 상에 보여주는 메서드
		function checkTotalPrice() {
			var price = (Number) ($("span#showPrice").text());  // 상품의 옵션별 단일가격
			var quantity = (Number) ($("input:text[name=orderQuantity]").val());  // 상품선택 갯수
			
			$("span#totalPrice").html(price * quantity);
			
		}
		
		
		// 장바구니로 보내주는 메소드
		function goCart() {
			
			
				var frm = document.orderDetail;
				frm.poqty.value = $("input#orderQuantity").val();
				frm.pprice.value = $("span#showPrice").text();
				frm.optioncode.value = $("select#selected-option option:selected").val();
				
				frm.action = "<%= ctxPath%>/product/productPutCart.com";
				frm.method = "POST";
				frm.submit();
			
			
			
			
		}
		
		function goOrder() {
			
			
			var frm = document.orderDetail;
			frm.poqty.value = $("input#orderQuantity").val();
			frm.pprice.value = $("span#showPrice").text();
			frm.optioncode.value = $("select#selected-option option:selected").val();
			
			frm.action = "<%= ctxPath%>/payment/cardSlash.com";
			frm.method = "POST";
			frm.submit();
		}
		
		/* 모달창에서 쇼핑 계속하기 버튼을 눌렀을경우 상품 디피 창으로 이동되는 메소드*/
		function shopping() {
			history.back();
			
		}
		
		/* 모달창에서 장바구니로이동 버튼을 눌렀을경우 상품 디피 창으로 이동되는 메소드*/
		function cart() {
			location.href="<%= ctxPath %>/product/myCart.com";
			
		}
		
	</script>
</div>


