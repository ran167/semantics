package com.abb.nlp.copy;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

public class SenTokenizer {
	public  void Tokenizer(String s) throws FileNotFoundException
    {
   //InputStream modelIn = new FileInputStream("en-token.bin");
InputStream modelIn = new FileInputStream("C:/OpenNLP_models/en-token.bin"); 
	     
  // InputStream modelIn=getClass().getResourceAsStream("en-token.bin");
       try {
             TokenizerModel model = new TokenizerModel(modelIn);
             TokenizerME tokenizer = new TokenizerME(model);
             String tokens[] = tokenizer.tokenize(s);
              
             for(int i=0; i<tokens.length;i++)
               {
                   System.out.println(tokens[i]);
               }
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
   }
	public static void main(String ar[])throws Exception{
		new SenTokenizer().Tokenizer("Sample tokenizer program using java");
	}
}
