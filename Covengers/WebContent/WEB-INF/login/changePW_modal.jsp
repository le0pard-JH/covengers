<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><!doctype html>
<% String ctxPath = request.getContextPath(); %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

<script type="text/javascript">

    $(document).ready(function(){
		
		
    	
    });
    
    function changePW() {
    	
    	ps1 = $("input#password").val();
    	ps2 = $("input#confirmpassword").val();
    	
    	if (ps1 != ps2) {
    		alert("false!");
    	} else {
    		
    		$.ajax({
        		url:"<%= ctxPath%>/member/updatePassword2.com",
        		data:{"email":$("input#email").val(), "password":$("input#password").val()}, 
        		type:"post",
        		dataType:"json"
        	});
    		
    		alert("sucess");
    		location.href="<%=ctxPath%>/main.com"
    	}
    	
    	
    	
    }
    
    function searchPW() {
    	
    	var frm = document.pwForm;
	    frm.action = "<%=request.getContextPath()%>/member/searchPwpage.com";
		frm.method = "post";
		frm.submit();
    	
    }
    
  
</script>


<head>
    <title>Login/Register Modal by Creative Tim</title>

	<meta charset="utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />


	<style>body{padding-top: 60px;}</style>

    <link href="../assets/css/bootstrap.css" rel="stylesheet" />

	<link href="../assets/css/login-register.css" rel="stylesheet" />
	<link rel="stylesheet" href="http://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css">

	<script src="../assets/js/jquery-1.10.2.js" type="text/javascript"></script>
	<script src="../assets/js/bootstrap.js" type="text/javascript"></script>
	<script src="../assets/js/login-register.js" type="text/javascript"></script>

</head>
<body>
    <div class="container">
    <form action="" name="pwForm">
        <div class="row">
            <div class="col-sm-4"></div>
            <div class="col-sm-4">
            	 <h3>Please fill in the password and password confirmation box.</h3>
                 <input id="email" class="email" type="hidden" name="email" value="${email}">
                 <input id="name" class="name" type="hidden" name="name" value="${name}">
            	 <input id="password" class="form-control" type="password" placeholder="password" name="password"><br>
            	 <input id="confirmpassword" class="form-control" type="password" placeholder="password confirm" name="confirmpassword"><br>
            	 
                 <a class="btn big-login" data-toggle="modal" href="javascript:void(0)" onclick="changePW();">CHANGE PASSWORD</a><br>
            <div class="col-sm-4"></div>
        </div>
	</form>
		 <div class="modal fade login" id="loginModal">
		      <div class="modal-dialog login animated">
    		      <div class="modal-content">
    		         <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title">Login with</h4>
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
                                    <input id="password1" class="form-control" type="password" placeholder="Password" name="pwd">
                                    <input class="btn btn-default btn-login" type="button" value="Login" onclick="login()">
                                    </form>
                                </div>
                             </div>
                        </div>
                        <div class="box">
                            <div class="content registerBox" style="display:none;">
                             <div class="form">
                                <form html="{:multipart=>true}" data-remote="true" action="" accept-charset="UTF-8">
                                <input id="email" class="form-control" type="text" placeholder="Email" name="email">
                                <input id="password" class="form-control" type="password" placeholder="Password" name="password" >
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
                            </span>
                        </div>
                        <div class="forgot register-footer" style="display:none">
                             <span>Already have an account?</span>
                             <a href="<%=ctxPath%>/member/LoginAction2.com">Login</a>
                        </div>
                    </div>
    		      </div>
		      </div>
		  </div>
    </div>
    
<!--  -->


</body>
</html>
