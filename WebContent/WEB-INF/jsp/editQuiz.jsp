<%@include file="include.jsp"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
   
    <title>
        Edit Quiz
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

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script type="text/javascript">
	//$('#quizQuestion').keydown(function(e) {
		
</script>
<script>

	$(document).ready(function() {

	$('#quizQuestion').keydown(function(e) {

					var keyCode = e.keyCode;

					if (keyCode === 9) {
						// get caret position/selection

						var start = this.selectionStart;

						var end = this.selectionEnd;

						var $this = $(this);

						var value = $this.val();

						// set textarea value to: text before caret + tab + text after caret

						$this.val(value.substring(0, start) + "\t"

						+ value.substring(end));

						// put caret at right position again (add one for the tab)

						this.selectionStart = this.selectionEnd = start + 1;

						// prevent the focus lose

						e.preventDefault();

					}
				});
	$('#codeSegment').keydown(function(e) {

		var keyCode = e.keyCode;

		if (keyCode === 9) {
			// get caret position/selection

			var start = this.selectionStart;

			var end = this.selectionEnd;

			var $this = $(this);

			var value = $this.val();

			// set textarea value to: text before caret + tab + text after caret

			$this.val(value.substring(0, start) + "\t"

			+ value.substring(end));

			// put caret at right position again (add one for the tab)

			this.selectionStart = this.selectionEnd = start + 1;

			// prevent the focus lose

			e.preventDefault();

		}
	});
				var $radios = $('input:radio[name=questionType]');
				var $radios2 = $('input:radio[name=radios]');
				var choice1 = $("#choice1");
				var choice2 = $("#choice2");
				var choice3 = $("#choice3");
				var choice4 = $("#choice4");
				var codeSegment = $("#codeSegment");
				var radios = $("#radios");
				var result = $("#result");
				
				if ($radios.is(':checked') === false) {

					$radios.filter('[value=MCQ]').prop('checked', true);
					choice1.show();
					choice2.show();
					choice3.show();
					choice4.show();
					codeSegment.hide();
					radios.hide();
					result.hide();

				}
				
				<c:if test="${questionType == 'MCQ'}">
				choice1.show();
				choice2.show();
				choice3.show();
				choice4.show();
				codeSegment.hide();
				radios.hide();
				result.hide();
				</c:if>
				<c:if test="${questionType == 'Short Answer'}">
				choice1.hide();
				choice2.hide();
				choice3.hide();
				choice4.hide();
				codeSegment.hide();
				radios.hide();
				result.hide();
				</c:if>
				<c:if test="${questionType == 'Essays'}">
				choice1.hide();
				choice2.hide();
				choice3.hide();
				choice4.hide();
				codeSegment.show();
				radios.show();
				result.show();
				</c:if>

				$(".aboveage1")
						.click(
								function() {

									if ($('input[name=questionType]:checked')
											.val() == "MCQ") {


										choice1.show();
										choice2.show();
										choice3.show();
										choice4.show();
										codeSegment.hide();
										radios.hide();
										result.hide();
									} else if ($(
											'input[name=questionType]:checked')
											.val() == "Essays") {

										choice1.hide();
										choice2.hide();
										choice3.hide();
										choice4.hide();
										codeSegment.show();
										radios.show();
										result.show();
									} else if ($(
									'input[name=questionType]:checked')
									.val() == "Short Answer") {


										choice1.hide();
										choice2.hide();
										choice3.hide();
										choice4.hide();
										codeSegment.hide();
										radios.hide();
										result.hide();
									}

								});

			});
</script>

</head>
<body>

<%@ include file="header.jsp" %>

<div id="wrapper">

	<form name="CreateQuiz-form" class="login-form" action="checkcreateQuiz" method="post">
	
		<div class="header">
		<h1>Edit Quiz </h1>
		<span></span>
		</div>
	
		<div class="content">
		<div style="color: #FF0000;">${errorMessage}</div>
		<input type="hidden" name="quizCycle" value="update" />
		<input type="hidden" name="quizID" value="${quizs.getQuizID()}" />
		<input type="radio" name="questionType" value="MCQ" class="aboveage1" <c:if test="${questionType == 'MCQ'}">CHECKED</c:if> /> MCQ
		 <input type="radio" name="questionType" value="Short Answer" class="aboveage1" <c:if test="${questionType == 'Short Answer'}">CHECKED</c:if> /> Short Answer
		 <input type="radio" name="questionType" value="Essays" class="aboveage1" <c:if test="${questionType == 'Essays'}">CHECKED</c:if> /> Essays
		 <div class="content" id ="parent1">
		 <textarea id="quizQuestion" name="quizQuestion" rows="5" class="input quizQuestion" placeholder="Question" ><c:if test="${not empty quizQuestion}">${quizQuestion}</c:if></textarea>
		 
		<div class="user-icon"></div><br>
		<textarea id="codeSegment" name="codeSegment" rows="5" class="input codeSegment" placeholder="Code Segment" ><c:if test="${not empty codeSegment}">${codeSegment}</c:if></textarea>
		<div class="user-icon"></div><br>
		<input id="choice1" name="choice1" class="input choice1" value="<c:if test="${not empty choice1}">${choice1}</c:if>" placeholder="Choice 1" /><br/>
		<input id="choice2" name="choice2" class="input choice2" value="<c:if test="${not empty choice2}">${choice2}</c:if>" placeholder="Choice 2" /><br/>
		<input id="choice3" name="choice3" class="input choice3" value="<c:if test="${not empty choice3}">${choice3}</c:if>" placeholder="Choice 3" /><br/>
		<input id="choice4" name="choice4" class="input choice4" value="<c:if test="${not empty choice4}">${choice4}</c:if>" placeholder="Choice 4" /><br/>
		<textarea id="answer" name="answer" rows="5" class="input answer" placeholder="Answer" ><c:if test="${not empty answer}">${answer}</c:if></textarea>
		<%-- <input name="answer" class="input answer" placeholder="Answer" value="<c:if test="${not empty answer}">${answer}</c:if>"/> --%>
		<br><br>
		<div id="radios">
		 Expected Result: <INPUT id="radios" TYPE="radio" NAME="radios" VALUE="expectedResult" <c:if test="${radios == 'expectedResult'}">CHECKED</c:if> />
		  Condition Value: <INPUT id="radios" TYPE="radio" NAME="radios" VALUE="conditionValue" <c:if test="${radios == 'conditionValue'}">CHECKED</c:if> />
		  <div class="user-icon"></div><br><br>
		  </div>
		<textarea id="result" name="result" rows="5" class="input result" placeholder="Output Result" ><c:if test="${not empty result}">${result}</c:if></textarea>
		<div class="user-icon"></div><br><br>
		<select name="category" id="category" class="input category" >
		<option value="">Category</option>
			
		<c:forEach items="${lists}" var="list">
		
			<option <c:if test="${list == category}">selected</c:if> value = "${list}">${list}</option>
		                   

		</c:forEach>
		</select><br/>

		 <input id="mark" name="mark" type="text" class="input mark" placeholder="Mark" value="<c:if test="${not empty mark}">${mark}</c:if>" />
		<div class="user-icon"></div>	
		</div>
		</div>

		<div class="footer">		
		<input type="submit" class="button" name="submit" value="Edit" />
		</div>
	
	</form>

</div>
<div class="gradient"></div>


</body>
</html>
