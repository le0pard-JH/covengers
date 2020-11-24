<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String ctxPath = request.getContextPath();
%>	

<jsp:include page="../covengers_header.jsp"></jsp:include>

<style>
/* 메인상단 이미지 */
/* The hero image */
div.hero-head-image {
	/* Use "linear-gradient" to add a darken background effect to the image (photographer.jpg). This will make the text easier to read */
	background-image: linear-gradient(rgba(0, 0, 0, 0.5), rgba(0, 0, 0, 0.5)),
		url("/Covengers/images/orange.jpg");
	/* Set a specific height */
	/* Position and center the image to scale nicely on all screens */
	background-position: center;
	background-repeat: no-repeat;
	background-size: cover;
	position: relative;
	margin: 0 auto;
	overflow: hidden;
	width: 100%;
	height: 600px;	
	top: -140px;
}

/* Place text in the middle of the image */
.hero-head-text {
	text-align: center;
	position: absolute;
	top: 75%;
	left: 50%;
	width: 100%;
	height: 20%;
	transform: translate(-50%, -50%);
	color: white;
	background-color: rgba(176, 176, 181, 0.5);
	opacity: 1;
}

/* 상품 보여주기 */
.display {
	position: relative;
	width: 60%;
	margin: 0 auto;
	margin-top: 40px;
}
.productDisplay{
	height : 780px;
	margin-bottom: 30px;
}

.image {
	opacity: 1;
	display: block;
	width: 100%;
	height: 400px;
	transition: .5s ease;
	backface-visibility: hidden;
}


.middle1 {
	transition: .5s ease;
	opacity: 0;
	position: absolute;
	top: 30%;
	left: 50%;
	transform: translate(-50%, -50%);
	-ms-transform: translate(-50%, -50%);
	text-align: center;
	width: 85%;
}

.productDisplay:hover .middle1 {
	opacity: 1;
}

.text {
	background-color: rgba(176, 176, 181, 0.8);
	color: white;
	font-size: 16px;
	opacity: 1; padding : 16px 32px;
	text-decoration: none;
	padding: 16px 32px;
}

div.description {
	text-align: center;
}


.middle1 a:hover {
	text-decoration: none;
	color: white;
}

.middle1 a:visited {
	color: white;
}


a:hover {
	text-decoration: none;
}


</style>



<div class="hero-head-image" >
	<div class="hero-head-text">
		<h2>
			<em>${product.encategoryname}</em>
		</h2>
		<p>
			<em>${product.krcategoryname}</em>
		</p>
	</div>
</div>


<div class="display">


	<c:forEach var="productVO" items="${productList}" varStatus="status">
		<div class="col-md-4 productDisplay" align="center">
			<div class="image" style="text-align: center;">
				<img src="/Covengers/images/${productVO.productimg1}" alt="${productVO.enproductname}" width="100%" height="100%" style="float:center;">
			</div>
			
			<div class="middle1">
				<a class="text" href="<%=ctxPath%>/product/productDetail.com?productcode=${productVO.productcode}">자세히보기</a>
			</div>
			<hr>
			<div class="description">
				<h4><a href="<%=ctxPath%>/product/productDetail.com?productcode=${productVO.productcode}">${productVO.enproductname}</a></h4>
				<br>
				<h6><a href="<%=ctxPath%>/product/productDetail.com?productcode=${productVO.productcode}">${productVO.krproductname}</a></h6>
				<br>
				<p style="color: gray; font-size:14pt; "> <fmt:formatNumber value="${productVO.saleprice}" pattern="#,###" />원</p>
				<br>
				<p>${productVO.productdescshort}</p>
			</div>
		</div>
	
	</c:forEach>

	
</div>
	
</div>
</body>
</html>