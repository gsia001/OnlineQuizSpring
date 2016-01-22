package com.ce9001.service;

import java.sql.SQLException;
import java.util.List;

import com.ce9001.viewBean.QuizBean;
import com.ce9001.viewBean.UserBean;

public interface QuizService {
	public boolean createQuiz(QuizBean quiz) throws SQLException;
	public List<QuizBean> viewAllQuiz() throws SQLException;
	public QuizBean viewQuiz(int quizID) throws SQLException;
	public boolean editQuiz(QuizBean quiz) throws SQLException;
	public boolean deleteQuiz(int quizID) throws SQLException;
	public int countQuiz() throws SQLException;
	public boolean insertScore(UserBean user, double score);
}
