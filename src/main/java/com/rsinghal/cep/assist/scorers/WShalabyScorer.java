/**
*
* @author Ravi SInghal
*/ 

package com.rsinghal.cep.assist.scorers;

import com.rsinghal.cep.assist.Answer;
import com.rsinghal.cep.assist.Passage;
import com.rsinghal.cep.assist.Phrase;

public class WShalabyScorer extends PassageScorer {

	@Override
	/** Detect if the question matches the answer, score it appropriately
	 * This is to ease machine learning*/
	// TODO: Don't reassign for every passage
	public double scorePassage(Phrase q, Answer a, Passage p) {
		return 0.0;
	}
}
