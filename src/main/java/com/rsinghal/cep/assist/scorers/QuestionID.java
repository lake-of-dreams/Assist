package com.rsinghal.cep.assist.scorers;

import com.rsinghal.cep.assist.Answer;
import com.rsinghal.cep.assist.Question;

/**
 * A bogus scorer whose purpose is to collate answers to the same question
 * @author Ravi Singhal
 */
public class QuestionID extends AnswerScorer {

	@Override
	public double scoreAnswer(Question q, Answer a) {
		return q.text.hashCode();
	}

}
