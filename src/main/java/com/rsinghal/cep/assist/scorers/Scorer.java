package com.rsinghal.cep.assist.scorers;

import java.util.List;

import com.rsinghal.cep.assist.Answer;
import com.rsinghal.cep.assist.Question;

public interface Scorer {
	public void scoreQuestion(Question q, List<Answer> answers);
}
