<%@include file="include.jsp"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
   
    <title>
        View Quiz
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

span.dropt {border-bottom: thin dotted; background: #ffeedd;}

span.dropt:hover {text-decoration: none; background: #ffffff; z-index: 6; }

span.dropt span {position: absolute; left: -9999px;
  margin: 20px 0 0 0px; padding: 3px 3px 3px 3px;
  
    border-style:solid; border-color:black; border-width:1px; z-index: 6;}
    
    span.dropt:hover span {left: 2%; background: #ffffff;} 
    
    span.dropt span {position: absolute; left: -9999px;
      margin: 4px 0 0 0px; padding: 3px 3px 3px 3px; 
      
        border-style:solid; border-color:black; border-width:1px;}
        
        span.dropt:hover span {margin: 60px 0 0 20px; background: #ffffff; z-index:6;} 
</style>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>

</head>
<body>

<%@ include file="header.jsp" %>

<div id="wrapper">
		<div class="login-form">
		<div class="header">
		<br>
		<h1>View Quiz </h1>
		<span></span>
		</div>





				<div class="content">
<div style="display:inline-block; position:absolute; top:0;">				
<form name="viewQuiz-form" class="viewQuiz-form" action="viewQuiz" method="post">
<select name="viewType" id="viewType" class="input category" onchange="document.viewQuiz-form.submit();" >
		<option <c:if test="${viewType == 'question'}"> selected="selected" </c:if> value="question" >Question</option>
		<option <c:if test="${viewType == 'list'}"> selected="selected" </c:if>  value="list">List</option>
			
		</select>
		</form>	
		</div>			
<c:if test="${viewType == 'question'}">
<div style="display:inline-block; position:absolute; top:0; right:0;">


		<span class="dropt"><span>edit</span><a href="editQuiz?id=${quizs.getQuizID()}"><img height="50" width="50" src="${pageContext.request.contextPath}/resource/images/editing.jpg" onmouseover="this.src='${pageContext.request.contextPath}/resource/images/editing_over.jpg'" onmouseout="this.src='${pageContext.request.contextPath}/resource/images/editing.jpg'"  /></a></span>
<a href="deleteQuiz?id=${quizs.getQuizID()}" onclick="return confirm('Are you sure you want to delete')"><img height="50" width="50" src="${pageContext.request.contextPath}/resource/images/delete.png" onmouseover="this.src='${pageContext.request.contextPath}/resource/images/delete_over.png'" onmouseout="this.src='${pageContext.request.contextPath}/resource/images/delete.png'"  /></a>
</div>
</c:if>

	
		<c:if test="${viewType == 'list'}">
		<c:forEach items="${quizs}" var="quiz">
		
			<a href="${pageContext.request.contextPath}/viewQuiz?Counter=${quiz.getQuizID()}&viewType=question" style="text-decoration: none"><div style="width: auto; padding: 10px; border: 5px solid gray; margin: 0;"><pre>${quiz.getQuizQuestion()}</pre></div></a><br>

				
		                   

		</c:forEach>
		</c:if>
		<c:if test="${viewType == 'question'}">
				Question: <pre>${quizs.getQuizQuestion()}</pre><br>
				<c:if test="${quizs.getQuestionType() == 'Essays'}">
				Code Segment: <pre>${quizs.getQuizCode()}</pre><br>
				</c:if>
				Answer: <pre><c:out value="${quizs.getQuizAnswer()}" /></pre><br>
				<c:if test="${quizs.getQuestionType() == 'MCQ'}">
				  Choice 1: <pre>${choice1}</pre><br>
				  Choice 2: <pre>${choice2}</pre><br>
				  Choice 3: <pre>${choice3}</pre><br>
				  Choice 4: <pre>${choice4}</pre><br>
				</c:if>
				Category: <pre>${quizs.getQuizCategory()}</pre><br>
				<c:if test="${quizs.getQuestionType() == 'Essays'}">
				
				Results: <pre>${result}</pre><br>
				</c:if>
				Marks: <pre>${quizs.getMarks()}</pre><br>
				</c:if>
				</div>
				
<c:if test="${viewType == 'question'}">
			<div class="footer">
			 <c:if test="${quizs.getQuizID()  < totalNum}">
<form name="viewQuiz-form" class="viewQuiz-form" action="viewQuiz?Counter=${quizs.getQuizID()+1}&viewType=question" method="post">

 <input type="submit" name="action" value="Next" class="button" />

</form>
 </c:if> 
		<c:if test="${quizs.getQuizID() > 1}">
<form name="viewQuiz-form" class="viewQuiz-form" action="viewQuiz?Counter=${quizs.getQuizID()-1}&viewType=question" method="post">

 <input type="submit" name="action" value="Previous" class="button" />

</form>
 </c:if>


	
 </div>
</c:if>



	</div></div>


</body>
</html>
