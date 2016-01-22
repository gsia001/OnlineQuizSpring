package com.ce9001.quiz;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import java.util.Arrays;

import java.util.Formatter;

import java.util.List;

import java.util.Locale;

import javax.tools.Diagnostic;

import javax.tools.DiagnosticCollector;

import javax.tools.JavaCompiler;

import javax.tools.JavaFileObject;

import javax.tools.StandardJavaFileManager;

import javax.tools.ToolProvider;

public class JavaQuizChecker {



	public static List<String> check(String file) {

		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

		StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);

		Iterable<? extends JavaFileObject> compilationUnits =

		fileManager.getJavaFileObjectsFromStrings(Arrays.asList(file));

		DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();

		compiler.getTask(null, fileManager, diagnostics, null, null, compilationUnits).call();

		List<String> messages = new ArrayList<String>();

		Formatter formatter = new Formatter();

		for (Diagnostic diagnostic : diagnostics.getDiagnostics()) {

			messages.add(diagnostic.getKind() + ":\t Line [" + diagnostic.getLineNumber() + "] \t Position ["
					+ diagnostic.getPosition() + "]\t" + diagnostic.getMessage(Locale.ROOT) + "\n");
		}

		return messages;

	}



	public static List<String> runProcess(String command) throws Exception {
		
		ArrayList<String> result = new ArrayList<String>();
		File dir = new File("C:/Users/GuoLong/workspace/OnlineQuiz/src/com/ce9001/quiz/");
		Process pro = Runtime.getRuntime().exec(command, null, dir);
		if(!(pro.getInputStream().toString().isEmpty()))
		{
			String line = null;

			BufferedReader in = new BufferedReader(new InputStreamReader(pro.getInputStream()));

			while ((line = in.readLine()) != null) {

				result.add(command + " " + line);

			}
		}
		if(!(pro.getErrorStream().toString().isEmpty())){
			String line = null;

			BufferedReader in = new BufferedReader(new InputStreamReader(pro.getErrorStream()));

			while ((line = in.readLine()) != null) {
				
				result.add(command + " " + line);

			}
		}
		pro.waitFor();
		result.add(command + " exitValue() " + pro.exitValue());
		return result;
	}
}
