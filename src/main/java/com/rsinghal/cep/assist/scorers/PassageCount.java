package com.rsinghal.cep.assist.scorers;
import com.rsinghal.cep.assist.Answer;
import com.rsinghal.cep.assist.Question;


/**
 * @author Ravi Singhal
 */
public class PassageCount extends AnswerScorer {
	public double scoreAnswer(Question q, Answer a) {
		return a.passages.size();
	}
}

