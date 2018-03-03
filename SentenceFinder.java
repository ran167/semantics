package com.abb.mynlps;

import java.io.FileInputStream;
import java.io.InputStream;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;

public class SentenceFinder {
	private  static String sentences[] ;
public static String[] readSentence(String sentence){
	 
	   try{    
	 //Loading sentence detector model 
	      InputStream inputStream = new FileInputStream("C:/OpenNLP_models/en-sent.bin"); 
	      SentenceModel model = new SentenceModel(inputStream); 
	       
	      //Instantiating the SentenceDetectorME class 
	      SentenceDetectorME detector = new SentenceDetectorME(model);  
	    
	      //Detecting the sentence
	      sentences = detector.sentDetect(sentence); 
	   
	      //Printing the sentences 
//	      for(String sent : sentences)        
//	         System.out.println(sent);  
	   }catch(Exception e){
		   e.printStackTrace();
	   }
	   return sentences;
}
}
