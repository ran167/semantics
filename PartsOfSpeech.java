package com.abb.nlp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.chunker.ChunkerME;
import opennlp.tools.chunker.ChunkerModel;
import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.tokenize.WhitespaceTokenizer;
 

public class PartsOfSpeech {
 
    public static PosDisplayData pos(String sentence) {
 
        InputStream tokenModelIn = null;
        InputStream posModelIn = null;
        PosDisplayData p=null;
        try {
           // String sentence = "John is 27 years old.";
            // tokenize the sentence
            tokenModelIn = new FileInputStream("C:/OpenNLP_models/en-token.bin");
            TokenizerModel tokenModel = new TokenizerModel(tokenModelIn);
            Tokenizer tokenizer = new TokenizerME(tokenModel);
            String tokens[] = tokenizer.tokenize(sentence);
 
            // Parts-Of-Speech Tagging
            // reading parts-of-speech model to a stream 
            posModelIn = new FileInputStream("C:/OpenNLP_models/en-pos-maxent.bin");
            // loading the parts-of-speech model from stream
            POSModel posModel = new POSModel(posModelIn);
            // initializing the parts-of-speech tagger with model 
            POSTaggerME posTagger = new POSTaggerME(posModel);
            // Tagger tagging the tokens
            String tags[] = posTagger.tag(tokens);
            // Getting the probabilities of the tags given to the tokens
            double probs[] = posTagger.probs();
             p=new PosDisplayData(sentence, tokens, tags, probs);
//            System.out.println("Token\t:\tTag\t:\tProbability\n---------------------------------------------");
//            for(int i=0;i<tokens.length;i++){
//                System.out.println(tokens[i]+"\t:\t"+tags[i]+"\t:\t"+probs[i]);
//            }
            
        }
        catch (IOException e) {
            // Model loading failed, handle the error
            e.printStackTrace();
        }
        finally {
            if (tokenModelIn != null) {
                try {
                    tokenModelIn.close();
                }
                catch (IOException e) {
                }
            }
            if (posModelIn != null) {
                try {
                    posModelIn.close();
                }
                catch (IOException e) {
                }
            }
        }
        return p;
    }
public void chunker(){
    String sentence = "Obama was great president";       
    WhitespaceTokenizer whitespaceTokenizer= WhitespaceTokenizer.INSTANCE; 
    String[] tokens = whitespaceTokenizer.tokenize(sentence); 
   try{
    //Generating the POS tags 
    //Load the parts of speech model 
    File file = new File("C:/OpenNLP_models/en-pos-maxent.bin"); 
    POSModel model = new POSModelLoader().load(file);     
    
    //Constructing the tagger 
    POSTaggerME tagger = new POSTaggerME(model);        
    
    //Generating tags from the tokens 
    String[] tags = tagger.tag(tokens);    
  
    //Loading the chunker model 
    InputStream inputStream = new FileInputStream("C:/OpenNLP_models/en-chunker.bin"); 
    ChunkerModel chunkerModel = new ChunkerModel(inputStream);  
    
    //Instantiate the ChunkerME class 
    ChunkerME chunkerME = new ChunkerME(chunkerModel);
     
    //Generating the chunks 
    String result[] = chunkerME.chunk(tokens, tags); 
    for (String s : result) 
        System.out.println(s);     
   }catch(Exception e){
	   e.printStackTrace();
   }
        
 }   //chunker
}
