package com.abb.nlp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;

public class SenTokenizer {
	public  String[] Tokenizer(String s) throws FileNotFoundException
    {
		System.out.println("&&&&&&&&&&&&&&&&TOKEN METHOD");
   //InputStream modelIn = new FileInputStream("en-token.bin");
InputStream modelIn = new FileInputStream("C:/OpenNLP_models/en-token.bin"); 
String tokens[] =null;
  // InputStream modelIn=getClass().getResourceAsStream("en-token.bin");
       try {
             TokenizerModel model = new TokenizerModel(modelIn);
             TokenizerME tokenizer = new TokenizerME(model);
             tokens = tokenizer.tokenize(s);
              
//             for(int i=0; i<tokens.length;i++)
//               {
//                   System.out.println("**********"+tokens[i]);
//               }
           }
           catch (IOException e) {
             e.printStackTrace();
           }
           finally {
             if (modelIn != null) {
               try {
                 modelIn.close();
               }
               catch (IOException e) {
               }
             } 
           }       
       return tokens;
   }
	
	public static List<String> getAllEntites(String sentence){
		System.out.println("@@@@@@@@@@@@@@@@sentence"+sentence);
		List<String> namelist=new ArrayList<String>();
		try{
		//Loading the tokenizer model 
	      InputStream inputStreamTokenizer = new 
	         FileInputStream("C:/OpenNLP_models/en-token.bin");
	      TokenizerModel tokenModel = new TokenizerModel(inputStreamTokenizer); 
	       
	      //Instantiating the TokenizerME class 
	      TokenizerME tokenizer = new TokenizerME(tokenModel); 
	       
	      //Tokenizing the sentence in to a string array 
	      String tokens[] =   tokenizer.tokenize(sentence); 
	    
	     
	       
	      //Loading the NER-person model 
	      InputStream inputStreamNameFinder = new 
	         FileInputStream("C:/OpenNLP_models/en-ner-person.bin");       
	      TokenNameFinderModel model = new TokenNameFinderModel(inputStreamNameFinder);
	      
	      //Instantiating the NameFinderME class 
	      NameFinderME nameFinder = new NameFinderME(model);       
	      
	      //Finding the names in the sentence 
	      Span nameSpans[] = nameFinder.find(tokens);        
	      
	      //Printing the names and their spans in a sentence 
	      for(Span s: nameSpans)        
	         namelist.add(tokens[s.getStart()]);      
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println(namelist);
		return namelist;
	}
}
