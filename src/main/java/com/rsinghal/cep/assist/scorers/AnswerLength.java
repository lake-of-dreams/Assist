package com.rsinghal.cep.assist.scorers;

import com.rsinghal.cep.assist.Answer;
import com.rsinghal.cep.assist.Question;

/**
 * Return the length of the candidate text in chars.
 * @author Ravi Singhal
 */
public class AnswerLength extends AnswerScorer {
	
	public double scoreAnswer(Question q, Answer a) {
		return a.text.length();
	}

}
