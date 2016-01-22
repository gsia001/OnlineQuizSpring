package com.ce9001.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ce9001.dao.QuizDao;
import com.ce9001.service.QuizService;
import com.ce9001.viewBean.QuizBean;
import com.ce9001.viewBean.UserBean;

public class JavaQuizServiceImpl implements QuizService {

	private QuizDao quizDao;

	public QuizDao getQuizDao()
	{
			return this.quizDao;
	}

	public void setQuizDao(QuizDao quizDao)
	{
			this.quizDao = quizDao;
	}
	
	@Override
	public boolean createQuiz(QuizBean quiz) throws SQLException
	{
		return quizDao.createQuiz(quiz);
	}
	
	@Override
	public List<QuizBean> viewAllQuiz() throws SQLException
	{
		return quizDao.viewAllQuiz();
	}
	
	@Override
	public QuizBean viewQuiz(int quizID) throws SQLException
	{
		return quizDao.viewQuiz(quizID);
	}
	
	@Override
	public boolean editQuiz(QuizBean quiz) throws SQLException
	{
		return quizDao.editQuiz(quiz);
	}
	
	@Override
	public boolean deleteQuiz(int quizID) throws SQLException
	{
		return quizDao.deleteQuiz(quizID);
	}
	
	@Override
	public int countQuiz() throws SQLException
	{
		return quizDao.countQuiz();
	}
	public boolean insertScore(UserBean user, double score)
	{
		return quizDao.insertScore(user, score);
	}
}
