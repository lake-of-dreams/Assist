package com.rsinghal.cep.assist.scorers;

import com.rsinghal.cep.assist.Answer;
import com.rsinghal.cep.assist.Question;

/**
 * Returns 1.0 if the answer text is found in the question and 0.0 otherwise
 * @author Ravi Singhal
 *
 */
public class AnswerInQuestionScorer extends AnswerScorer {
	
	@Override
	public double scoreAnswer(Question q, Answer a) {
		String qtext = q.text.toLowerCase();
		String atext = a.text.toLowerCase();
		
		if (qtext.contains(atext))
			return 1.0;
		else
			return 0.0;		
	}
	
}
