package com.rsinghal.cep.assist.scorers;

import com.rsinghal.cep.assist.Answer;
import com.rsinghal.cep.assist.Passage;
import com.rsinghal.cep.assist.Phrase;

public class PassageQuestionLengthRatio extends PassageScorer {
	
	public double scorePassage(Phrase q, Answer a, Passage p) {
		String qs = q.text;
		//String qst= q.text; //processes question, stopwords, punctuation removed
		//String as= a.candidate_text;
		//String ps=p.text; // text is guaranteed to have content
	    //ps.tokenize();
		
		int pl = p.text.length();
		int ql = qs.length();
		double sc=pl/ql;
		return sc;
	}

}
