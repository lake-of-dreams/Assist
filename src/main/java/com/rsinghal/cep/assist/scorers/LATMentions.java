package com.rsinghal.cep.assist.scorers;

import com.rsinghal.cep.assist.Answer;
import com.rsinghal.cep.assist.Question;
import com.rsinghal.cep.assist.scorers.AnswerScorer;

/**
 * Return how many unique LAT's there are for an answer. 
 * @author Ravi Singhal
 *
 */
public class LATMentions extends AnswerScorer {
	
	@Override
	public double scoreAnswer(Question q, Answer a) {
		return a.lexical_types.size();
	}
}
