package com.ce9001.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.ce9001.dao.QuizDao;

import com.ce9001.viewBean.QuizBean;
import com.ce9001.viewBean.UserBean;

public class JavaQuizDaoImpl implements QuizDao {

	DataSource dataSource;

	public DataSource getDataSource() {
		return this.dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public boolean createQuiz(QuizBean quiz) throws SQLException {
		int rowCount = countQuiz() + 1;
		PreparedStatement st = dataSource.getConnection().prepareStatement("");
		if (quiz.getQuestionType().equalsIgnoreCase("MCQ")) {
			String delimiter = "~@";
			String choice = quiz.getChoice1() + delimiter + quiz.getChoice2() + delimiter + quiz.getChoice3()
					+ delimiter + quiz.getChoice4();
			int[] updateNum = { 1, 2, 3, 4, 5, 6, 7 };
			int[] insertNum = { 7, 1, 2, 3, 4, 5, 6 };

			st = dataSource.getConnection().prepareStatement(
					"INSERT INTO quiz(QuizID,QuizQuestion,QuizAnswer,QuizCategory,OutputResult,Marks,QuestionType) values (?,?,?,?,?,?,?)");

			st.setInt(1, rowCount);
			st.setString(2, quiz.getQuizQuestion());
			st.setString(3, quiz.getQuizAnswer());
			st.setString(4, quiz.getQuizCategory());
			st.setString(5, choice);
			st.setInt(6, quiz.getMarks());
			st.setString(7, quiz.getQuestionType());

		}
		if (quiz.getQuestionType().equalsIgnoreCase("Short Answer")) {
			st = dataSource.getConnection().prepareStatement(
					"INSERT INTO quiz(QuizID,QuizQuestion,QuizAnswer,QuizCategory,Marks,QuestionType) values (?,?,?,?,?,?)");
			st.setInt(1, rowCount);
			st.setString(2, quiz.getQuizQuestion());
			st.setString(3, quiz.getQuizAnswer());
			st.setString(4, quiz.getQuizCategory());
			st.setInt(5, quiz.getMarks());
			st.setString(6, quiz.getQuestionType());
		}
		if (quiz.getQuestionType().equalsIgnoreCase("Essays")) {

			if (!(quiz.getOutputResult().isEmpty())) {
				st = dataSource.getConnection().prepareStatement(
						"INSERT INTO quiz(QuizID,QuizQuestion,QuizAnswer,QuizCategory,OutputResult,Marks,QuestionType) values (?,?,?,?,?,?,?)");
				st.setString(5, quiz.getOutputResult());

			} else {
				st = dataSource.getConnection().prepareStatement(
						"INSERT INTO quiz(QuizID,QuizQuestion,QuizAnswer,QuizCategory,CheckValue,Marks,QuestionType) values (?,?,?,?,?,?,?)");
				st.setString(5, quiz.getCheckValue());
			}

			st.setInt(1, rowCount);
			st.setString(2, quiz.getQuizQuestion());
			st.setString(3, quiz.getQuizAnswer());
			st.setString(4, quiz.getQuizCategory());

			st.setInt(6, quiz.getMarks());
			st.setString(7, quiz.getQuestionType());
		}
		st.executeUpdate();
		return true;
	}

	@Override
	public List<QuizBean> viewAllQuiz() throws SQLException {
		List<QuizBean> quizs = new ArrayList<QuizBean>();
		try {
			String query = "SELECT * FROM quiz";
			PreparedStatement pstmt = dataSource.getConnection().prepareStatement(query);

			ResultSet resultSet = pstmt.executeQuery();
			while (resultSet.next()) {

				QuizBean quiz = new QuizBean();
				quiz.setQuizID((resultSet.getInt("QuizID")));
				quiz.setQuestionType((resultSet.getString("QuestionType")));
				quiz.setQuizQuestion((resultSet.getString("QuizQuestion")));
				quiz.setQuizAnswer(resultSet.getString("QuizAnswer"));
				quiz.setOutputResult(resultSet.getString("OutputResult"));
				quiz.setCheckValue(resultSet.getString("CheckValue"));
				quiz.setMarks(resultSet.getInt("Marks"));
				quizs.add(quiz);
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return quizs;
	}

	@Override
	public QuizBean viewQuiz(int quizID) throws SQLException {
		QuizBean quiz = new QuizBean();
		try {
			System.out.println("here1");
			String query = "SELECT * FROM quiz WHERE QuizID = ?";
			PreparedStatement pstmt = dataSource.getConnection().prepareStatement(query);
			pstmt.setInt(1, quizID);
			ResultSet resultSet = pstmt.executeQuery();
			while (resultSet.next()) {

				System.out.println("here2");
				quiz.setQuizID((resultSet.getInt("QuizID")));
				quiz.setQuestionType((resultSet.getString("QuestionType")));
				quiz.setQuizQuestion((resultSet.getString("QuizQuestion")));
				quiz.setQuizAnswer(resultSet.getString("QuizAnswer"));
				quiz.setQuizCategory(resultSet.getString("QuizCategory"));
				quiz.setOutputResult(resultSet.getString("OutputResult"));
				quiz.setCheckValue(resultSet.getString("CheckValue"));
				quiz.setMarks(resultSet.getInt("Marks"));

			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return quiz;
	}

	@Override
	public boolean editQuiz(QuizBean quiz) throws SQLException {
		PreparedStatement st = dataSource.getConnection().prepareStatement("");
		if (quiz.getQuestionType().equalsIgnoreCase("MCQ")) {
			System.out.println("edditQuzi");
			String delimiter = "~@";
			String choice = quiz.getChoice1() + delimiter + quiz.getChoice2() + delimiter + quiz.getChoice3()
					+ delimiter + quiz.getChoice4();
			st = dataSource.getConnection().prepareStatement(
					"UPDATE quiz SET QuizQuestion = ?,QuizAnswer = ?,QuizCategory = ?,OutputResult = ?,Marks = ?,QuestionType = ? WHERE QuizID = ?");
			st.setInt(7, quiz.getQuizID());
			st.setString(1, quiz.getQuizQuestion());
			st.setString(2, quiz.getQuizAnswer());
			st.setString(3, quiz.getQuizCategory());
			st.setString(4, choice);
			st.setInt(5, quiz.getMarks());
			st.setString(6, quiz.getQuestionType());

		}
		if (quiz.getQuestionType().equalsIgnoreCase("Short Answer")) {
			st = dataSource.getConnection().prepareStatement(
					"UPDATE quiz SET QuizQuestion = ?, QuizAnswer = ?, QuizCategory = ?, Marks = ?, QuestionType = ? WHERE QuizID = ?");
			st.setInt(6, quiz.getQuizID());
			st.setString(1, quiz.getQuizQuestion());
			st.setString(2, quiz.getQuizAnswer());
			st.setString(3, quiz.getQuizCategory());
			st.setInt(4, quiz.getMarks());
			st.setString(5, quiz.getQuestionType());
		}
		if (quiz.getQuestionType().equalsIgnoreCase("Essays")) {

			if (!(quiz.getOutputResult().isEmpty())) {
				st = dataSource.getConnection().prepareStatement(
						"UPDATE quiz SET QuizQuestion = ?, QuizAnswer = ?, QuizCategory = ?, OutputResult = ?, Marks = ?, QuestionType = ? WHERE QuizID = ?");
				st.setString(4, quiz.getOutputResult());
			} else {
				st = dataSource.getConnection().prepareStatement("UPDATE quiz SET QuizQuestion = ?, QuizAnswer = ?, QuizCategory = ?, CheckValue = ?, Marks = ?, QuestionType = ? WHERE QuizID = ?");
				st.setString(4, quiz.getCheckValue());
			}

			st.setInt(7, quiz.getQuizID());
			st.setString(1, quiz.getQuizQuestion());
			st.setString(2, quiz.getQuizAnswer());
			st.setString(3, quiz.getQuizCategory());
			st.setInt(5, quiz.getMarks());
			st.setString(6, quiz.getQuestionType());
		}
		st.executeUpdate();
		return true;
	}

	@Override
	public boolean deleteQuiz(int quizID) throws SQLException {
		boolean success = false;
		try {
			String query = "DELETE FROM quiz WHERE QuizID = ?";
			PreparedStatement pstmt = dataSource.getConnection().prepareStatement(query);
			pstmt.setInt(1, quizID);
			int st = pstmt.executeUpdate();
			if (st == 1) {

				success = true;

			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return success;
	}

	@Override
	public int countQuiz() throws SQLException {
		int rowCount = 0;
		try {
			String query = "SELECT count(*) FROM quiz";
			PreparedStatement pstmt = dataSource.getConnection().prepareStatement(query);

			ResultSet resultSet = pstmt.executeQuery();

			resultSet.next();

			rowCount = resultSet.getInt(1);

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return rowCount;
	}

	@Override
	public boolean insertScore(UserBean user, double score) {
		int success = 0;
		try {
			String query = "INSERT INTO studentquiz(StudentID,QuizBatchID,StudentScore) values (?,?,?)";
			PreparedStatement pstmt = dataSource.getConnection().prepareStatement(query);

			pstmt.setInt(1, user.getId());
			pstmt.setInt(2, 1);
			pstmt.setDouble(3, score);

			success = pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return true;

	}
}
