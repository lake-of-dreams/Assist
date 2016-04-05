package com.rsinghal.cep.assist.scorers;

import com.rsinghal.cep.assist.Answer;
import com.rsinghal.cep.assist.Passage;
import com.rsinghal.cep.assist.Phrase;

/**
 * Take advantage of the Scorer dimension reduction for Lucene passages
 */
public class LuceneEcho extends PassageScorer {

	@Override
	public double scorePassage(Phrase q, Answer a, Passage p) {
		return p.scores.get("LUCENE_SCORE");
	}
	
}
