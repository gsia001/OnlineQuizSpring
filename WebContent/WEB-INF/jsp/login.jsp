<%@include file="include.jsp"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
   
    <title>
        Login
    </title>
<%@include file="css.jsp"%>
    <style type="text/css">
body {
	background: url("${pageContext.request.contextPath}/images/background.jpg");
}
.user-icon {
	top:153px; /* Positioning fix for slide-in, got lazy to think up of simpler method. */
	background: rgba(65,72,72,0.75) url('${pageContext.request.contextPath}/resource/images/user-icon.png') no-repeat center;	
}

.pass-icon {
	top:201px;
	background: rgba(65,72,72,0.75) url('${pageContext.request.contextPath}/resource/images/pass-icon.png') no-repeat center;
}


</style>
</head>
<body>

<%@ include file="header.jsp" %>
<%-- 
<div style="position:absolute;left:500px;top:90px">
Don`t have an account, click here to <a href='${pageContext.request.contextPath}/register'>Register</a>
</div>
--%>
<div id="wrapper">

	<form:form name="login-form" class="login-form" action="login" modelAttribute="loginBean" method="post">
	
		<div class="header">
		<h1>Login </h1>
		<span></span>
		</div>
	
		<div class="content">
		<div style="color: #FF0000;">${errorMessage}</div>
		<form:input name="username" class="input username" placeholder="Username" path="" />
		<div class="user-icon"></div><br/>
		<form:password name="password" class="input password" placeholder="Password" path=""  />
		<div class="pass-icon"></div>		
		</div>

		<div class="footer">
		<input type="submit" name="submit" value="Login" class="button" />
		
		</div>
	
	</form:form>

</div>
<div class="gradient"></div>


</body>
</html>
