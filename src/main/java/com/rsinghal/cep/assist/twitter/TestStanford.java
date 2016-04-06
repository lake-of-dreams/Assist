package com.rsinghal.cep.assist.twitter;

import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;

public class TestStanford {

	public static void main(String[] args) {
		String text = "you are a fool";
		Properties props = new Properties();
		props.setProperty("annotators", "tokenize,cleanxml,ssplit,pos,parse,lemma,ner,regexner,sentiment,truecase,depparse,dcoref,relation,natlog,quote");
		props.setProperty("threads", "10");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		
		Annotation annotation = pipeline.process(text);
		List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
		for (CoreMap sentence : sentences) {
			
		  String sentiment = sentence.get(SentimentCoreAnnotations.SentimentClass.class);
		  System.out.println(sentiment + "\t" + sentence);
		}

	}

}
