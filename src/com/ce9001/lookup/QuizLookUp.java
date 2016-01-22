package com.ce9001.lookup;

import com.ce9001.service.QuizService;
import com.ce9001.service.impl.JavaQuizServiceImpl;

public class QuizLookUp {
	public QuizService getQuizService(String serviceType){
		
		   
		
		      if(serviceType.equalsIgnoreCase("Java")){
		    	  System.out.println("lookup");
		               return new JavaQuizServiceImpl();
		               
		      }
		      else
		    	  	return null;
		      
		      
		      
	}
}
