package com.ce9001.viewBean;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.ce9001.delegate.QuizDelegate;
import com.ce9001.quiz.JavaQuizChecker;

public class ExamBean {
	Document dom;
	public List<QuizBean> quizList;
	public int currentQuestion = 0;

	public Map<Integer, String> selections = new LinkedHashMap<Integer, String>();
	public ArrayList<QuizBean> questionList = null;

	public ExamBean(List<QuizBean> getQuizList, String test)
			throws SAXException, ParserConfigurationException, IOException, URISyntaxException, SQLException {
		// dom=CreateDOM.getDOM(test);
		try {
			quizList = getQuizList;
			questionList = new ArrayList<QuizBean>(quizList.size());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void setQuestion(int i) {
		int number = i;
		String options = null;
		String question = null;
		String code = null;
		String correct = null;
		String outputResult = null;
		question = quizList.get(i).getQuizQuestion();
		String questionType = quizList.get(i).getQuestionType();
		String cloneQuestion = question;
		QuizBean q = new QuizBean();
		if (questionType.equalsIgnoreCase("MCQ")) {
			String choice = quizList.get(i).getOutputResult();
			String[] choices = choice.split("~@");

			q.setChoice1(choices[0]);
			q.setChoice2(choices[1]);
			q.setChoice3(choices[2]);
			q.setChoice4(choices[3]);
			correct = quizList.get(i).getQuizAnswer();
		}

		if (questionType.equalsIgnoreCase("Essays")) {

			if (quizList.get(i).getCheckValue() != null) {
				String[] dynamicValue = quizList.get(i).getCheckValue().split(";");
				question = question.replace("?", dynamicValue[0]);
			}

			String[] spiltCode = question.split("<FileName>");
			question = spiltCode[2];
			String[] spiltQuestion = question.split("<Question>");
			question = spiltQuestion[1];
			code = spiltQuestion[0] + spiltQuestion[2];
			correct = quizList.get(i).getQuizAnswer();
			outputResult = quizList.get(i).getOutputResult();
		}

		q.setQuestionNumber(number);
		q.setOriginalQuestion(cloneQuestion);
		q.setQuizQuestion(question);
		q.setQuizAnswer(correct);
		q.setOutputResult(outputResult);
		q.setCheckValue(quizList.get(i).getCheckValue());
		q.setQuizCode(code);
		q.setMarks(quizList.get(i).getMarks());
		q.setQuestionType(questionType);
		questionList.add(number, q);

	}

	public ArrayList<QuizBean> getQuestionList() {
		return this.questionList;
	}

	public int getCurrentQuestion() {
		return currentQuestion;
	}

	public Map<Integer, String> getSelections() {
		return this.selections;
	}

	public double calculateResult(ExamBean exam) {
		double totalCorrect = 0;
		double totalCorrect2 = 0;
		Map<Integer, String> userSelectionsMap = exam.selections;
		List<String> userSelectionsList = new ArrayList<String>(quizList.size());
		for (Map.Entry<Integer, String> entry : userSelectionsMap.entrySet()) {
			userSelectionsList.add(entry.getValue());
		}
		List<QuizBean> questionList = exam.questionList;
		List<String> userAnswersList = new ArrayList<String>(quizList.size());
		List<String> correctAnswersList = new ArrayList<String>(quizList.size());
		List<String> outputResultList = new ArrayList<String>(quizList.size());
		List<String> checkValueResultList = new ArrayList<String>(quizList.size());
		int questionNo = 0;
		String userChoice = "";
		for (QuizBean question : questionList) {
			correctAnswersList.add(question.getQuizAnswer());
			outputResultList.add(question.getOutputResult());
			checkValueResultList.add(question.getCheckValue());
			if (question.getQuestionType().equalsIgnoreCase("MCQ")) {
				if ((userSelectionsList.get(questionNo).trim().length() != 0)) {
					if (Integer.valueOf(userSelectionsList.get(questionNo)) == 1)
						userChoice = question.getChoice1();
					else if (Integer.valueOf(userSelectionsList.get(questionNo)) == 2)
						userChoice = question.getChoice2();
					else if (Integer.valueOf(userSelectionsList.get(questionNo)) == 3)
						userChoice = question.getChoice3();
					else if (Integer.valueOf(userSelectionsList.get(questionNo)) == 4)
						userChoice = question.getChoice4();
					if (question.getQuizAnswer().equalsIgnoreCase(userChoice))
						totalCorrect2 += question.getMarks();
				}
			}
			questionNo++;

		}

		int userAttempt = userSelectionsList.size();

		for (int i = 0; i < userSelectionsList.size(); i++) {
			if (questionList.get(i).getQuestionType().equalsIgnoreCase("Essays")) {
				String[] fileName = questionList.get(i).getOriginalQuestion().split("<FileName>");
				String[] instFileName = fileName;
				String[] checkValue = null;
				int loopCode = 0;
				boolean condition = false;
				boolean remainNumofQuestion = false;
				if (questionList.get(i).getCheckValue() == null) {
					loopCode = 1;
					condition = true;
				} else {
					checkValue = questionList.get(i).getCheckValue().split(";");
					loopCode = checkValue.length;
				}

				if (userAttempt > 0) {

					userAttempt--;
				} else
					remainNumofQuestion = true;
				double marks = 0;
				for (int numOfWrite = 0; numOfWrite < loopCode; numOfWrite++) {
					if (!remainNumofQuestion) {
						if (condition)
							writeToFile(fileName[1], userSelectionsList.get(i), questionList.get(i).getQuizCode());
						else
							writeToFile(fileName[1], userSelectionsList.get(i),
									questionList.get(i).getOriginalQuestion(), checkValue[numOfWrite]);
					}
					List<String> compile = JavaQuizChecker.check(
							"C:/Users/GuoLong/workspace/OnlineQuiz/src/com/ce9001/quiz/" + fileName[1] + ".java");
					if (compile.isEmpty() && !remainNumofQuestion) {
						try {
							List<String> compileResult = getCompileResult(fileName[1]);
							if (compileResult.contains("javac " + fileName[1] + ".java exitValue() 0")) {
								List<String> outputResult = getOutputResult(fileName[1]);
								// List<String> instcompileResult

								for (String a : outputResult) {
									if (a.compareToIgnoreCase("java " + fileName[1] + " exitValue() 0") == 0) {
										outputResult.remove("java " + fileName[1] + " exitValue() 0");
										if (condition)
											writeToFile(instFileName[1], questionList.get(i).getQuizAnswer(),
													questionList.get(i).getQuizCode());
										else
											writeToFile(instFileName[1], questionList.get(i).getQuizAnswer(),
													questionList.get(i).getOriginalQuestion(), checkValue[numOfWrite]);
										List<String> instCompileResult = getCompileResult(instFileName[1]);
										List<String> instOutputResult = getOutputResult(instFileName[1]);
										instOutputResult.remove("java " + fileName[1] + " exitValue() 0");
										boolean check = true;

										if (outputResultList.get(i) != null) {
											marks += compareResult(userSelectionsList.get(i), correctAnswersList.get(i),
													outputResult.toString(), outputResultList.get(i), fileName[1],
													check, questionList.get(i).getMarks());

										} else {
											check = false;
											marks += compareResult(userSelectionsList.get(i), correctAnswersList.get(i),
													outputResult.toString(), instOutputResult.toString(), fileName[1],
													check, questionList.get(i).getMarks());
										}

									}
								}

							} else {
								System.out.println("Compilation error");
							}
						} catch (Exception e) {

							System.out.println(e.getMessage());

						}
					}
				}
				if (marks - 1 == loopCode)
					totalCorrect = questionList.get(i).getMarks();
				else
					totalCorrect = (double) questionList.get(i).getMarks() * (marks / (double) loopCode);
				totalCorrect2 += totalCorrect;
			}
		}

		DecimalFormat df2 = new DecimalFormat(".##");
		totalCorrect2 = Double.valueOf(df2.format(totalCorrect2));
		return totalCorrect2;
	}

	public double compareResult(String userAnswer, String correctAnswer, String userOutput, String expectedOutput,
			String fileName, boolean check, int marks) {
		int divideValue = 2;
		String checkName = "[java " + fileName + " " + expectedOutput + "]";
		if (!check)
			checkName = expectedOutput;
		double totalCorrect = 0;
		if (userAnswer.equalsIgnoreCase(correctAnswer) && userOutput.compareTo(checkName) == 0) {
			totalCorrect = 1;
		} else {
			if (userOutput.compareTo(checkName) == 0) {
				if (!(userAnswer.contentEquals(expectedOutput)))
					totalCorrect = 1;
			} else {
				String[] partialAnswer = correctAnswer.split(";");
				String[] userAnswerSplit = userAnswer.split(";");
				for (int checkAns = 0; checkAns < partialAnswer.length; checkAns++) {
					for (int checkUserAns = 0; checkUserAns < partialAnswer.length; checkUserAns++) {
						if (partialAnswer[checkAns].compareTo(userAnswerSplit[checkUserAns]) == 0) {

							totalCorrect = 0.5;

						}
					}
				}
			}

		}
		return totalCorrect;
	}

	public int getTotalNumberOfQuestions() {

		return quizList.size();

	}

	public void writeToFile(String fileName, String userSelections, String quizQuestion, String... checkValue) {
		try {

			File file = new File("C:/Users/GuoLong/workspace/OnlineQuiz/src/com/ce9001/quiz/" + fileName + ".java");

			// if file doesnt exists, then create it

			if (!file.exists()) {

				file.createNewFile();

			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			if (checkValue != null && checkValue.length > 0) {
				String[] spiltCode = quizQuestion.split("<FileName>");
				quizQuestion = spiltCode[2];
				String[] spiltQuestion = quizQuestion.split("<Question>");
				quizQuestion = spiltQuestion[0] + spiltQuestion[2];
				quizQuestion = quizQuestion.replace("?", checkValue[0]);
			}
			String[] studetAnswerMerge = quizQuestion.split("//Answer//");
			String studentAnswer = studetAnswerMerge[0] + userSelections + studetAnswerMerge[1];
			bw.write(studentAnswer);

			bw.close();

		} catch (IOException e) {

			e.printStackTrace();

		}
	}

	public List<String> getCompileResult(String fileName) {
		List<String> compileResult = null;
		try {
			compileResult = JavaQuizChecker.runProcess("javac " + fileName + ".java");

		} catch (Exception e) {

			System.out.println(e.getMessage());

		}
		return compileResult;

	}

	public List<String> getOutputResult(String fileName) {
		List<String> outputResult = null;
		try {
			outputResult = JavaQuizChecker.runProcess("java " + fileName);

		} catch (Exception e) {

			System.out.println(e.getMessage());

		}
		return outputResult;

	}
}
