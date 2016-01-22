package com.ce9001.controller;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ce9001.delegate.QuizDelegate;
import com.ce9001.viewBean.ChangePasswordBean;
import com.ce9001.viewBean.UserBean;
import com.ce9001.viewBean.ExamBean;
import com.ce9001.viewBean.QuizBean;

@Controller
public class ExamController {

	@Autowired
	private QuizDelegate quizDelegate;

	@RequestMapping(value = "/takeExam")
	public ModelAndView takeExam(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = null;

		model = new ModelAndView("quizDetails");
		request.getSession().setAttribute("currentExam", null);

		String exam = request.getParameter("test");
		request.getSession().setAttribute("exam", exam);
		request.getSession().setAttribute("totalNumberOfQuizQuestions", 2);

		request.getSession().setAttribute("quizDuration", 10);

		request.getSession().setAttribute("min", 1);

		request.getSession().setAttribute("sec", 0);


		return model;
	}

	@RequestMapping(value = "/exam")
	public ModelAndView startExam(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = null;

		boolean finish = false;
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (Exception e) {

		}
		HttpSession session = request.getSession();

		try {
			if (session.getAttribute("currentExam") == null) {
				session = request.getSession();
				String selectedExam = (String) request.getSession().getAttribute("exam");
				List<QuizBean> quizList = quizDelegate.viewAllQuiz();
				ExamBean newExam = new ExamBean(quizList, selectedExam);
				session.setAttribute("currentExam", newExam);
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss a");
				Date date = new Date();
				String started = dateFormat.format(date);
				session.setAttribute("started", started);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		ExamBean exam = (ExamBean) request.getSession().getAttribute("currentExam");

		if (exam.currentQuestion == 0) {
			/// exam.currentQuestion++;
			exam.setQuestion(exam.currentQuestion);
			QuizBean q = exam.questionList.get(exam.currentQuestion);
			String[] choicesNum = { "1", "2", "3", "4" };
			session.setAttribute("quest", q);
			session.setAttribute("choicesNum", choicesNum);
			exam.currentQuestion++;
		}

		String action = request.getParameter("action");
		int minute = -1;

		int second = -1;

		if (request.getParameter("minute") != null) {

			minute = Integer.parseInt(request.getParameter("minute"));
			second = Integer.parseInt(request.getParameter("second"));
			request.getSession().setAttribute("min", request.getParameter("minute"));
			request.getSession().setAttribute("sec", request.getParameter("second"));

		}

		String radio = request.getParameter("answer");
		int selectedRadio = -1;
		exam.selections.put(exam.currentQuestion - 1, radio);

		if ("Next".equals(action)) {
			exam.setQuestion(exam.currentQuestion);
			QuizBean q = exam.questionList.get(exam.currentQuestion);
			session.setAttribute("quest", q);
			session.setAttribute("answer", exam.selections.get(exam.currentQuestion));
			exam.currentQuestion++;
		} else if ("Previous".equals(action)) {
			exam.currentQuestion -= 2;
			exam.setQuestion(exam.currentQuestion);
			QuizBean q = exam.questionList.get(exam.currentQuestion);
			session.setAttribute("quest", q);
			session.setAttribute("answer", exam.selections.get(exam.currentQuestion));
			exam.currentQuestion++;
		} else if ("Finish Exam".equals(action) || (minute == 0 && second == 0)) {
			finish = true;
			double result = exam.calculateResult(exam);
			UserBean user = (UserBean) session.getAttribute("user");
			quizDelegate.insertScore(user, result);
			request.setAttribute("result", result);
			request.setAttribute("noOfQuestion", exam.getTotalNumberOfQuestions());
			request.getSession().setAttribute("currentExam", null);
			model = new ModelAndView("result");

		}

		if (finish != true) {
			model = new ModelAndView("exam");
		}

		return model;
	}
}
