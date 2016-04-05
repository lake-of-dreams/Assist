package com.rsinghal.cep.assist.scorers;

import com.rsinghal.cep.assist.Answer;
import com.rsinghal.cep.assist.Passage;
import com.rsinghal.cep.assist.Phrase;

public class AnswerInPassage extends PassageScorer {
	@Override
	public double scorePassage(Phrase q, Answer a, Passage p)
	{
		return p.text.contains(a.text) ?
				1 : 0;
	}
}
