<%@include file="include.jsp"%>
<%@ page language="java" import="com.ce9001.quiz.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Quiz</title>
 <style type="text/css">
body {
	background: url("${pageContext.request.contextPath}/images/background.jpg");
}
pre {
 width:1000px;
white-space: pre-wrap;

word-wrap: break-word;

}
</style>

<script language="javascript">
	window.history.forward(1);
	document.onkeypress= keyCatcher;

	function keyCatcher() {

	var e = event.srcElement.tagName;

	if (event.keyCode == 8 && e != "INPUT" && e != "TEXTAREA") {

	event.cancelBubble = true;

	event.returnValue = false;

	}

	}
	var time;

	var min = '${sessionScope.min}';

	var sec = '${sessionScope.sec}';

	function customSubmit(someValue) {

		document.questionForm.minute.value = min;

		document.questionForm.second.value = sec;

		document.questionForm.submit();

	}

	function examTimer() {

		if (parseInt(sec) > 0) {

			document.getElementById("showtime").innerHTML = "Time Remaining :"
					+ min + " Minute ," + sec + " Seconds";

			sec = parseInt(sec) - 1;

			time = setTimeout("examTimer()", 1000);

		}

		else {

			if (parseInt(min) == 0 && parseInt(sec) == 0) {

				document.getElementById("showtime").innerHTML = "Time Remaining :"
						+ min + " Minute ," + sec + " Seconds";

				alert("Time Up");

				document.questionForm.minute.value = 0;

				document.questionForm.second.value = 0;

				document.questionForm.submit();

			}

			if (parseInt(sec) == 0) {

				document.getElementById("showtime").innerHTML = "Time Remaining :"
						+ min + " Minute ," + sec + " Seconds";

				min = parseInt(min) - 1;

				sec = 59;

				time = setTimeout("examTimer()", 1000);

			}

		}

	}
</script>
</head><br/>
<body onload="examTimer()">
<%

response.setHeader("Pragma","no-cache");

response.setHeader("Cache-Control","no-store");

response.setHeader("Expires","0");

response.setDateHeader("Expires",-1);

%>
<div style="position:absolute;left:50px;top:20px">

Current Question ${sessionScope.quest.getQuestionNumber()+1} / ${sessionScope.currentExam.getTotalNumberOfQuestions()}
</div>

<div id="showtime" style="position:absolute;left:800px;top:20px"></div>


<form action="exam" method="post" name="questionForm">

 <div style="position:absolute;width:1000px;word-wrap: normal;padding:25px;
  border: 1px solid green ;left:100px;top:60px">
  Question: <pre>${sessionScope.quest.getQuizQuestion()}</pre>
  <c:if test="${sessionScope.quest.getQuestionType() == 'MCQ'}">
  <pre>${sessionScope.choicesNum[0]} ${sessionScope.quest.getChoice1()}</pre>
  <pre>${sessionScope.choicesNum[1]} ${sessionScope.quest.getChoice2()}</pre>
  <pre>${sessionScope.choicesNum[2]} ${sessionScope.quest.getChoice3()}</pre>
  <pre>${sessionScope.choicesNum[3]} ${sessionScope.quest.getChoice4()}</pre>
  </c:if>
  <c:if test="${sessionScope.quest.getQuestionType() == 'Essays'}">
  <span><pre>${sessionScope.quest.getQuizCode()}</pre></span>
  </c:if>
 <%--
 <c:forEach var="choice" items="${sessionScope.quest.questionOptions}" varStatus="counter">
 <input type="radio" name="answer" value="${counter.count}" >${choice}  <br/>
 </c:forEach> <br/> 
 --%>
 <%-- </div> 
 
  <div style="position:absolute;width:1000px;padding:25px;
  left:100px;top:500px">  --%>
 <textarea name="answer" rows="7" cols="50"><c:out value="${sessionScope.answer}" /></textarea><br/>

 <c:if test="${sessionScope.quest.getQuestionNumber() > 0}">

 <input type="submit" name="action" value="Previous" onclick="customSubmit()" />

 </c:if>


 <c:if test="${sessionScope.quest.getQuestionNumber()  < sessionScope.currentExam.getTotalNumberOfQuestions()-1}">

 <input type="submit" name="action" value="Next" onclick="customSubmit()" />

 </c:if> 

 
 <input type="submit" name="action" value="Finish Exam" onclick="customSubmit()" />


<input type="hidden" name="minute"/> 

<input type="hidden" name="second"/>
  </div>

 </form>



</body>
</html>