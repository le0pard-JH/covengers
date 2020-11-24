	<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
	<%
		String ctxPath = request.getContextPath();
	%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>코벤져스 향수 오져따리</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/jquery-ui-1.11.4.custom/jquery-ui.css" />



<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js" ></script>
<script type="text/javascript" src="/Covengers/js/jquery-3.3.1.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%= ctxPath%>/jquery-ui-1.11.4.custom/jquery-ui.js"></script>
<script src="http://malsup.github.io/min/jquery.form.min.js"></script>
<link rel="stylesheet" href="https://unpkg.com/swiper/swiper-bundle.min.css">


<style type="text/css">
.sticky {
	position: fixed;
	top: 0;
	width: 100%;
}

/* Add some top padding to the page content to prevent sudden quick movement (as the navigation bar gets a new position at the top of the page (position:fixed and top:0) */
.sticky+.content {
	padding-top: 60px;
}

/* The navigation menu */
body, html {
	height: 100%;
	font-family: Arial, Helvetica, sans-serif;
}

.navbar {
	overflow: hidden;
	background-color: #333;
	height: 12%;
	width: 100%;
}

/* Navigation links */
.navbar a {
	float: left;
	font-size: 20px;
	color: white;
	text-align: center;
	padding: 14px 16px;
	text-decoration: none;
}

/* The subnavigation menu */
.subnav {
	float: left;
	overflow: hidden;
}

/* Subnav button */
.subnav .subnavbtn {
	font-size: 20px;
	border: none;
	outline: none;
	color: white;
	padding: 14px 16px;
	background-color: inherit;
	font-family: inherit;
	margin: 0;
}

/* Add a red background color to navigation links on hover */
.navbar a:hover, .subnav:hover .subnavbtn {
	background-color: gray;
}

/* Style the subnav content - positioned absolute */
.subnav-content {
	display: none;
	position: absolute;
	left: 0;
	background-color: #333;
	width: 100%;
	z-index: 1;
}

/* Style the subnav links */
.subnav-content a {
	float: left;
	color: white;
	text-decoration: none;
}

/* Add a grey background color on hover */
.subnav-content a:hover {
	background-color: #333;
	color: white;
}

/* When you move the mouse over the subnav container, open the subnav content */
.subnav:hover .subnav-content {
	display: block;
}

div#member_menu {
	margin-top: 10px;
	display: inline-block;
	color: white;
	float: right;
}

div#member_menu a {
	margin: 0 20px;
	opacity: 0.7;
}

button#btnTop {
	display: none; /* Hidden by default */
	position: fixed; /* Fixed/sticky position */
	bottom: 20px; /* Place the button at the bottom of the page */
	right: 30px; /* Place the button 30px from the right */
	z-index: 99; /* Make sure it does not overlap */
	border: none; /* Remove borders */
	outline: none; /* Remove outline */
	background-color: red; /* Set a background color */
	color: white; /* Text color */
	cursor: pointer; /* Add a mouse pointer on hover */
	padding: 15px; /* Some padding */
	border-radius: 10px; /* Rounded corners */
	font-size: 18px; /* Increase font size */
}

#btnTop:hover {
	background-color: #555; /* Add a dark-grey background on hover */
}
</style>
<script type="text/javascript">
	
</script>
</head>
<body>

		<div class="navbar" id="header">
			<a href="<%=request.getContextPath()%>/">메인페이지</a>
			<div class="subnav">
				<button class="subnavbtn">
					향수<i class="fa fa-caret-down"></i>
				</button>
				<div class="subnav-content">
					<a href="<%=ctxPath%>/product/productDisplay.com?categorycode=101"">플로랄</a>
					<a href="<%=ctxPath%>/product/productDisplay.com?categorycode=102">시트리스</a>
					<a href="<%=ctxPath%>/product/productDisplay.com?categorycode=103"">우디</a>
					<a href="<%=ctxPath%>/product/productDisplay.com?categorycode=104"">스파이시</a>
					<a href="<%=ctxPath%>/product/productDisplay.com?categorycode=105"">프루티</a>
				</div>
			</div>
			<div class="subnav">
				<button class="subnavbtn">
					디퓨저<i class="fa fa-caret-down"></i>
				</button>
				<div class="subnav-content">
					<a href="<%=ctxPath%>/product/productDisplay.com?categorycode=201"">실내용</a>
					<a href="<%=ctxPath%>/product/productDisplay.com?categorycode=202">차량용</a>
				</div>
			</div>
			<div class="subnav">
				<button class="subnavbtn">
					인센스<i class="fa fa-caret-down"></i>
				</button>
				<div class="subnav-content">
					<a href="<%=ctxPath%>/product/productDisplay.com?categorycode=301"">향초</a>
					<a href="<%=ctxPath%>/product/productDisplay.com?categorycode=302">향</a>
				</div>
			</div>
			<a href="<%= ctxPath %>/admin/productRegister.com">아이템좀 추가해보장!</a>
			<div id="member_menu">
				<span><a data-toggle="modal" data-target="#loginModal">Login</a></span> 
				<span><a href="<%= ctxPath%>/member/register.com">회원가입</a></span>
				<span><a href="<%= ctxPath %>/product/myCart.com">장바구니</a></span>
			</div>
		</div>
	

	
	
	
	<div class="modal fade login" id="loginModal">
    <div class="modal-dialog login animated">
		      <div class="modal-content">
		         <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                </div>
                <div class="modal-body">
                    <div class="box">
                         <div class="content">
                            <div class="social">
                                <a class="circle github" href="#">
                                    <i class="fa fa-github fa-fw"></i>
                                </a>
                                <a id="google_login" class="circle google" href="#">
                                    <i class="fa fa-google-plus fa-fw"></i>
                                </a>
                                <a id="facebook_login" class="circle facebook" href="#">
                                    <i class="fa fa-facebook fa-fw"></i>
                                </a>
                            </div>
                            <div class="division">
                                <div class="line l"></div>
                                  <span>or</span>
                                <div class="line r"></div>
                            </div>
                            <div class="error"></div>
                            <div class="form loginBox">
                                <form accept-charset="UTF-8" name="loginFrm">
                                <input id="email" class="form-control" type="text" placeholder="Email" name="email">
                                <input id="password" class="form-control" type="password" placeholder="Password" name="pwd">
                                <input class="btn btn-default btn-login" type="button" value="Login" onclick="login()">
                                </form>
                            </div>
                         </div>
                    </div>
                    <div class="box">
                        <div class="content registerBox" style="display:none;">
                         <div class="form">
                            <form method="" html="{:multipart=>true}" data-remote="true" action="" accept-charset="UTF-8">
                            <input id="email" class="form-control" type="text" placeholder="Email" name="email">
                            <input id="password" class="form-control" type="password" placeholder="Password" name="password">
                            <input id="password_confirmation" class="form-control" type="password" placeholder="Repeat Password" name="password_confirmation">
                            <input class="btn btn-default btn-register" type="button" value="Create account" name="commit">
                            </form>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <div class="forgot login-footer">
                        <span>Looking to
                             <a href="javascript: showRegisterForm();">create an account</a>
                        ?</span>
                    </div>
                    <div class="forgot register-footer" style="display:none">
                         <span>Already have an account?</span>
                         <a href="<%=ctxPath%>/member/LoginAction2.com">Login</a>
                    </div>
                </div>
		      </div>
    </div>
</div>