<%@include file="include.jsp"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
   
    <title>
        Change Password
    </title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <style type="text/css">
body {
	background: url("${pageContext.request.contextPath}/images/background.jpg");
}
.user-icon {
	top:153px; /* Positioning fix for slide-in, got lazy to think up of simpler method. */
	background: rgba(65,72,72,0.75) url('${pageContext.request.contextPath}/images/user-icon.png') no-repeat center;	
}

.pass-icon {
	top:201px;
	background: rgba(65,72,72,0.75) url('${pageContext.request.contextPath}/images/pass-icon.png') no-repeat center;
}


</style>
</head>
<body>

<%@ include file="header.jsp" %>



<div id="wrapper">

	<form:form name="login-form" class="login-form" action="changePassword" modelAttribute="changePasswordBean" method="post">
	
		<div class="header">
		<h1>Change Password </h1>
		<span></span>
		</div>
	
		<div class="content">
		<div style="color: #FF0000;">${errorMessage}</div>
		<form:input type="hidden" name="userName" value="${userName}" path="" />
		<form:input name="newPassword" type="password" class="input newPassword" placeholder="New Password" path="" /><br/>
		<div class="user-icon"></div>
		<form:input name="confirmNewPassword" type="password" class="input confirmNewPassword" placeholder="Confirm New Password" path="" />
		<div class="pass-icon"></div>		
		</div>

		<div class="footer">
		<input type="submit" name="submit" value="Change Password" class="button" />
		
		</div>
	
	</form:form>

</div>
<div class="gradient"></div>


</body>
</html>
