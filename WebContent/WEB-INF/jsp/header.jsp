<div id='cssmenu'>
<ul>
   <li class=''><a href='${pageContext.request.contextPath}'><span>Home</span></a></li>
   <c:if test='${empty sessionScope.user}'>
   <li><a href='${pageContext.request.contextPath}/login'><span>Login</span></a></li>
   </c:if>
   <c:if test='${not empty sessionScope.user}'>
   <li><a href='${pageContext.request.contextPath}/logout'><span>Logout</span></a></li>
   </c:if>
   <%--
   <li><a href='${pageContext.request.contextPath}/register'><span>Register</span></a></li>
   
   <li class='#'><a href='#'><span>Submit a Question</span></a></li>
   <li class='#'><a href='#'><span>Feedback</span></a></li>
   <li><a href='#'><span>Contribute</span></a></li>
   <li><a href='#'><span>Contact us</span></a></li>
   --%>
</ul>
</div>