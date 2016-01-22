package com.ce9001.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ce9001.delegate.QuizDelegate;

import com.ce9001.viewBean.QuizBean;

@Controller
public class QuizController {
	@Autowired
	private QuizDelegate quizDelegate;

	@RequestMapping(value = "/viewQuiz")
	public ModelAndView viewQuiz(HttpServletRequest request, HttpServletResponse response, QuizBean quizBean) {

		ModelAndView model = new ModelAndView("viewQuiz");

		String counter = request.getParameter("Counter");
		String viewType = request.getParameter("viewType");

		if (viewType == null || viewType.equalsIgnoreCase("question")) {
			if (counter == null) {
				counter = "1";
				viewType = "question";
				request.setAttribute("viewType", viewType);
			}

			try {
				QuizBean quizs = quizDelegate.viewQuiz(Integer.valueOf(counter));
				int totalNum = quizDelegate.countQuiz();
				String result = "";
				String choice1 = "";
				String choice2 = "";
				String choice3 = "";
				String choice4 = "";
				String radios = "";
				String code = null;
				String correct = null;
				String outputResult = null;
				String question = quizs.getQuizQuestion();
				String questionType = quizs.getQuestionType();
				if (!(questionType.equalsIgnoreCase("Short Answer"))) {
					if (questionType.equalsIgnoreCase("Essays")) {

						if (quizs.getCheckValue() != null) {
							String[] dynamicValue = quizs.getCheckValue().split(";");
							question = question.replace("?", dynamicValue[0]);
						}

						String[] spiltCode = question.split("<FileName>");
						question = spiltCode[2];
						String[] spiltQuestion = question.split("<Question>");
						question = spiltQuestion[1];
						code = spiltQuestion[0] + spiltQuestion[2];
						correct = quizs.getQuizAnswer();
						outputResult = quizs.getOutputResult();
						quizs.setQuizQuestion(question);
						quizs.setQuizAnswer(correct);
						quizs.setOutputResult(outputResult);
						quizs.setQuizCode(code);
					}

					if (quizs.getCheckValue() == null) {
						result = quizs.getOutputResult();
						if (questionType.equalsIgnoreCase("MCQ")) {
							String[] results = result.split("~@");
							choice1 = results[0];
							choice2 = results[1];
							choice3 = results[2];
							choice4 = results[3];
						}
						radios = "expectedResult";
					} else {
						result = quizs.getCheckValue();
						radios = "conditionValue";
					}
				}

				request.setAttribute("quizs", quizs);
				request.setAttribute("totalNum", totalNum);
				request.setAttribute("viewType", viewType);
				request.setAttribute("choice1", choice1);
				request.setAttribute("choice2", choice2);
				request.setAttribute("choice3", choice3);
				request.setAttribute("choice4", choice4);
				request.setAttribute("result", result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (viewType.equalsIgnoreCase("list")) {
			try {
				List<QuizBean> quizs = quizDelegate.viewAllQuiz();
				int totalNum = quizDelegate.countQuiz();

				request.setAttribute("quizs", quizs);
				request.setAttribute("totalNum", totalNum);
				request.setAttribute("viewType", viewType);
			} catch (Exception e) {

			}
		}
		
		model.addObject("quizBean", quizBean);
		return model;
	}

	@RequestMapping(value = "/deleteQuiz")
	public ModelAndView deleteQuiz(HttpServletRequest request, HttpServletResponse response, QuizBean quizBean) {
		ModelAndView model = new ModelAndView("redirect:viewQuiz");
		String id = request.getParameter("id");
		try {
			boolean success = quizDelegate.deleteQuiz(Integer.valueOf(id));
		} catch (Exception e) {

		}

		model.addObject("quizBean", quizBean);
		return model;
	}

	@RequestMapping(value = "/editQuiz")
	public ModelAndView editQuiz(HttpServletRequest request, HttpServletResponse response, QuizBean quizBean) {

		ModelAndView model = new ModelAndView("editQuiz");

		String id = request.getParameter("id");
		// String quizCycle = request.getParameter("quizCycle");

		try {
			QuizBean quizs = quizDelegate.viewQuiz(Integer.valueOf(id));
			int totalNum = quizDelegate.countQuiz();
			String questionType = quizs.getQuestionType();
			String quizQuestion = quizs.getQuizQuestion();
			String answer = quizs.getQuizAnswer();
			String category = quizs.getQuizCategory();
			int mark = quizs.getMarks();
			String result = "";
			String choice1 = "";
			String choice2 = "";
			String choice3 = "";
			String choice4 = "";
			String radios = "";
			if (!(questionType.equalsIgnoreCase("Short Answer"))) {
				if (quizs.getCheckValue() == null) {
					result = quizs.getOutputResult();
					if (questionType.equalsIgnoreCase("MCQ")) {
						String[] results = result.split("~@");
						choice1 = results[0];
						choice2 = results[1];
						choice3 = results[2];
						choice4 = results[3];
					}
					radios = "expectedResult";
				} else {
					result = quizs.getCheckValue();
					radios = "conditionValue";
				}
			}

			request.setAttribute("quizs", quizs);
			request.setAttribute("totalNum", totalNum);
			request.setAttribute("questionType", questionType);
			request.setAttribute("quizQuestion", quizQuestion);
			request.setAttribute("answer", answer);
			request.setAttribute("category", category);
			request.setAttribute("result", result);
			request.setAttribute("mark", mark);
			request.setAttribute("choice1", choice1);
			request.setAttribute("choice2", choice2);
			request.setAttribute("choice3", choice3);
			request.setAttribute("choice4", choice4);
			request.setAttribute("radios", radios);
			List lists = new ArrayList<String>();
			lists.add("Elementary Programming");
			lists.add("Selections");
			lists.add("Loops");
			lists.add("Methods");
			lists.add("Arrays");
			lists.add("Objects & Classes");
			lists.add("Strings");
			lists.add("Exception handling and text I/O");
			request.setAttribute("lists", lists);
		} catch (Exception e) {

		}
		model.addObject("quizBean", quizBean);
		return model;
	}

	@RequestMapping(value = "/createQuiz")
	public ModelAndView createQuiz(HttpServletRequest request, HttpServletResponse response, QuizBean quiz) {
		List lists = new ArrayList<String>();
		lists.add("Elementary Programming");
		lists.add("Selections");
		lists.add("Loops");
		lists.add("Methods");
		lists.add("Arrays");
		lists.add("Objects & Classes");
		lists.add("Strings");
		lists.add("Exception handling and text I/O");

		request.setAttribute("lists", lists);
		ModelAndView model = new ModelAndView("createQuiz");

		return model;
	}

	@RequestMapping(value = "/checkcreateQuiz")
	public ModelAndView addQuiz(HttpServletRequest request, HttpServletResponse response) {
		QuizBean quiz = new QuizBean();
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {

		}
		HttpSession session = request.getSession();
		String questionType = request.getParameter("questionType").trim();
		String quizQuestion = request.getParameter("quizQuestion");
		String answer = request.getParameter("answer").trim();
		String category = request.getParameter("category").trim();
		String mark = request.getParameter("mark").trim();
		String result = request.getParameter("result").trim();
		String codeSegment = request.getParameter("codeSegment").trim();
		String choice1 = request.getParameter("choice1").trim();
		String choice2 = request.getParameter("choice2").trim();
		String choice3 = request.getParameter("choice3").trim();
		String choice4 = request.getParameter("choice4").trim();
		String radios = request.getParameter("radios").trim();
		String quizCycle = request.getParameter("quizCycle").trim();
		String quizID = request.getParameter("quizID").trim();
		StringBuilder sb = new StringBuilder();
		String message = "";
		if (quizQuestion.isEmpty()) {
			message = "Quiz Question cannot be blank <br>";
			sb.append(message);
		}
		if (answer.isEmpty()) {
			message = "Quiz Answer cannot be blank <br>";
			sb.append(message);
		}
		if (category.isEmpty()) {
			message = "Category cannot be blank <br>";
			sb.append(message);
		}
		if (mark.isEmpty()) {
			message = "Mark cannot be blank <br>";
			sb.append(message);
		} else {
			boolean isInteger = isInteger(mark);
			if (!isInteger) {
				message = "Mark must be a number <br>";
				sb.append(message);
			}
		}
		if (questionType.equalsIgnoreCase("MCQ")) {

			if (choice1.isEmpty()) {
				message = "Choice 1 cannot be blank <br>";
				sb.append(message);
			}
			if (choice2.isEmpty()) {
				message = "Choice 2 cannot be blank <br>";
				sb.append(message);
			}
			if (choice3.isEmpty()) {
				message = "Choice 3 cannot be blank <br>";
				sb.append(message);
			}
			if (choice4.isEmpty()) {
				message = "Choice 4 cannot be blank <br>";
				sb.append(message);
			}

		}
		if (questionType.equalsIgnoreCase("Essays")) {

			if (codeSegment.isEmpty()) {
				message = "Code Segment cannot be blank <br>";
				sb.append(message);
			}
			if (result.isEmpty()) {
				message = "Result cannot be blank <br>";
				sb.append(message);
			}
		}

		if (sb.length() > 0)

		{
			List lists = new ArrayList<String>();
			lists.add("Elementary Programming");
			lists.add("Selections");
			lists.add("Loops");
			lists.add("Methods");
			lists.add("Arrays");
			lists.add("Objects & Classes");
			lists.add("Strings");
			lists.add("Exception handling and text I/O");

			request.setAttribute("lists", lists);

			request.setAttribute("errorMessage", sb);
			request.setAttribute("questionType", questionType);
			request.setAttribute("quizQuestion", quizQuestion);
			request.setAttribute("answer", answer);
			request.setAttribute("category", category);
			request.setAttribute("mark", mark);
			request.setAttribute("result", result);
			request.setAttribute("codeSegment", codeSegment);
			request.setAttribute("choice1", choice1);
			request.setAttribute("choice2", choice2);
			request.setAttribute("choice3", choice3);
			request.setAttribute("choice4", choice4);
			request.setAttribute("radios", radios);
			ModelAndView model = new ModelAndView("createQuiz");

			return model;

		} else {

			if (radios.equals("expectedResult"))
				quiz.setOutputResult(result);
			else
				quiz.setCheckValue(result);
			quiz.setChoice1(choice1);
			quiz.setChoice2(choice2);
			quiz.setChoice3(choice3);
			quiz.setChoice4(choice4);
			quiz.setMarks(Integer.valueOf(mark));

			quiz.setQuestionType(questionType);
			quiz.setQuizAnswer(answer);
			quiz.setQuizCategory(category);
			quiz.setQuizQuestion(quizQuestion);
			quiz.setQuizID(Integer.valueOf(quizID));
			StringBuilder sbQuestion = new StringBuilder();
			if (codeSegment.contentEquals("public class")) {
				String[] spiltCode = codeSegment.split("public class");
				String[] spiltFileName = spiltCode[0].split("{");
				String fileName = spiltFileName[0];
				sbQuestion.append("<FileName>" + fileName + "<FileName>");
				sbQuestion.append("<Question>" + quizQuestion + "<Question>");
				sbQuestion.append(codeSegment);
				quiz.setQuizQuestion(sbQuestion.toString());
			}

		}

		try {
			if (quizCycle.equalsIgnoreCase("create"))
				quizDelegate.createQuiz(quiz);
			if (quizCycle.equalsIgnoreCase("update"))
				quizDelegate.editQuiz(quiz);
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("quizCycle", quizCycle);
		request.setAttribute("questionType", questionType);
		request.setAttribute("quizQuestion", quizQuestion);
		request.setAttribute("codeSegment", codeSegment);
		request.setAttribute("answer", answer);
		request.setAttribute("category", category);
		request.setAttribute("result", result);
		request.setAttribute("mark", mark);
		request.setAttribute("choice1", choice1);
		request.setAttribute("choice2", choice2);
		request.setAttribute("choice3", choice3);
		request.setAttribute("choice4", choice4);

		quiz.setQuizQuestion(quizQuestion);
		session.setAttribute("quest", quiz);
		ModelAndView model = new ModelAndView("createQuizSuccess");

		return model;
	}

	public static boolean isInteger(String s) {

		boolean isValidInteger = false;

		try

		{

			Integer.parseInt(s);

			// s is a valid integer

			isValidInteger = true;

		}

		catch (NumberFormatException ex)

		{

			// s is not an integer

		}

		return isValidInteger;

	}
}
