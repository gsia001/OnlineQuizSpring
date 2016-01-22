package com.ce9001.delegate;

import java.sql.SQLException;
import java.util.List;

import com.ce9001.lookup.QuizLookUp;
import com.ce9001.service.QuizService;
import com.ce9001.viewBean.QuizBean;
import com.ce9001.viewBean.UserBean;

public class QuizDelegate {

	private QuizService quizService;

	
	public QuizService getQuizService()
	{
			return this.quizService;
	}
	
	public void setQuizService(QuizService quizService)
	{
			this.quizService = quizService;
	}
	

	
	public boolean createQuiz(QuizBean quiz) throws SQLException
	{
		return quizService.createQuiz(quiz);
	}
	
	public List<QuizBean> viewAllQuiz() throws SQLException
	{
		return quizService.viewAllQuiz();
	}
	
	public QuizBean viewQuiz(int quizID) throws SQLException
	{
		return quizService.viewQuiz(quizID);
	}
	
	public boolean editQuiz(QuizBean quiz) throws SQLException
	{
		return quizService.editQuiz(quiz);
	}

	public boolean deleteQuiz(int quizID) throws SQLException
	{
		return quizService.deleteQuiz(quizID);
	}
	
	public int countQuiz() throws SQLException
	{
		return quizService.countQuiz();
	}
	public boolean insertScore(UserBean user, double score) 
	{
		return quizService.insertScore(user,score);
	}
}
