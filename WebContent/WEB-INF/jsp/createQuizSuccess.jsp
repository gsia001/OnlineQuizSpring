<%@include file="include.jsp"%>
<%@ page language="java" import="com.ce9001.quiz.*" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>
        Create Quiz
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
		<div class="login-form">
		<div class="header">
		<c:if test="${quizCycle == 'create'}">
		<h1>Quiz Created Successfully </h1>
		</c:if>
		<c:if test="${quizCycle == 'update'}">
		<h1>Quiz Updated Successfully </h1>
		</c:if>
		<span></span>
		</div>
		<c:if test="${questionType == 'MCQ'}">
		<div class="content">
		<c:if test="${not empty questionType}">
		Question Type: ${questionType}
		<div class="user-icon"></div><br><br>
		</c:if>
		
  		${sessionScope.quest.getQuizQuestion()}
		<c:if test="${not empty quizQuestion}">
		Question: 
<pre>
${quizQuestion}
</pre><br/><br/>
		</c:if>

		<c:if test="${not empty choice1}">
		Choice 1: ${choice1}
		<div class="user-icon"></div><br><br>
		</c:if>
		<c:if test="${not empty choice2}">
		Choice 2: ${choice2}
		<div class="user-icon"></div><br><br>
		</c:if>
		<c:if test="${not empty choice3}">
		Choice 3: ${choice3}
		<div class="user-icon"></div><br><br>
		</c:if>
		<c:if test="${not empty choice4}">
		Choice 4: ${choice4}
		<div class="user-icon"></div><br><br>
		</c:if>
		<c:if test="${not empty answer}">
		Answer: ${answer}
		<div class="user-icon"></div><br><br>
		</c:if>
		<c:if test="${not empty category}">
		Category: ${category}
		<div class="user-icon"></div><br><br>
		</c:if>
		<c:if test="${not empty mark}">
		Mark: ${mark}
		<div class="user-icon"></div>
		</c:if>	
		</div>
		</c:if>
		
		<c:if test="${questionType == 'Short Answer'}">
		<div class="content">
		<c:if test="${not empty questionType}">
		Question Type: ${questionType}
		<div class="user-icon"></div><br><br>
		</c:if>
  		

		<c:if test="${not empty quizQuestion}">
		Question:
<pre>
${quizQuestion}
</pre><br/><br/>
		<div class="user-icon"></div><br><br>
		</c:if>
		

		<c:if test="${not empty answer}">
		Answer: ${answer}
		<div class="user-icon"></div><br><br>
		</c:if>
		<c:if test="${not empty category}">
		Category: ${category}
		<div class="user-icon"></div><br><br>
		</c:if>
		<c:if test="${not empty mark}">
		Mark: ${mark}
		<div class="user-icon"></div>
		</c:if>	
		</div>
		</c:if>
		
		<c:if test="${questionType == 'Essays'}">
		<div class="content">
		<c:if test="${not empty questionType}">
		Question Type: ${questionType}
		<div class="user-icon"></div><br><br>
		</c:if>
		<c:if test="${not empty quizQuestion}">
		Question:
<pre>
${quizQuestion}
</pre><br/><br/>
		<div class="user-icon"></div><br><br>
		</c:if>
		<c:if test="${not empty codeSegment}">
		Code Segment: ${codeSegment}
		<div class="user-icon"></div><br><br>
		</c:if>
		<c:if test="${not empty answer}">
		Answer: ${answer}
		<div class="user-icon"></div><br><br>
		</c:if>
		<c:if test="${not empty result}">
		Output Result: ${result}
		<div class="user-icon"></div><br><br>
		</c:if>
		<c:if test="${not empty category}">
		Category: ${category}
		<div class="user-icon"></div><br><br>
		</c:if>
		<c:if test="${not empty mark}">
		Mark: ${mark}
		<div class="user-icon"></div>
		</c:if>	
		</div>
		</c:if>
		

		
		<div class="footer">
		<c:if test="${quizCycle == 'create'}">
		<button class="button" onclick="location.href='${pageContext.request.contextPath}/createQuiz'">Create another quiz</button>
		</c:if>
		<c:if test="${quizCycle == 'update'}">
		<button class="button" onclick="location.href='${pageContext.request.contextPath}/viewQuiz'">Edit another quiz</button>
		</c:if>		
		
		</div>
</div>

</div>
<div class="gradient"></div>


</body>
</html>


