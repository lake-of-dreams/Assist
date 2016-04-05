package com.rsinghal.cep.assist.scorers;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.rsinghal.cep.assist.Answer;
import com.rsinghal.cep.assist.Passage;
import com.rsinghal.cep.assist.Phrase;
import com.rsinghal.cep.assist.Question;

public class WordProximity extends PassageScorer {
	Set<String> q_words = new HashSet<String>();
	
	@Override
	public void scoreQuestion(Question q, List<Answer> answers) {
		q_words.clear();
		q_words.addAll(Arrays.asList(q.text.split("\\W+")));
		super.scoreQuestion(q, answers);
	}
	
	@Override
	public double scorePassage(Phrase q, Answer a, Passage p) {
		double distance = 1;
		double average_log_distance = 0;
		
		for (String w : p.text.split("\\W+")) {
			if (q_words.contains(w)) {
				average_log_distance += Math.log(distance);
				distance = 1;
			} else {
				distance++;
			}
		}

		// This result is given as log(interval). Does that matter?
		return average_log_distance;
	}
	
}
