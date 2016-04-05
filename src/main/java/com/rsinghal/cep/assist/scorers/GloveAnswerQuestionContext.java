package com.rsinghal.cep.assist.scorers;

import java.util.List;
import java.util.stream.Collectors;

import com.rsinghal.cep.assist.Answer;
import com.rsinghal.cep.assist.Phrase;
import com.rsinghal.cep.assist.Question;
import com.rsinghal.cep.assist.nlp.DenseVectors;

/**
 * Returns the total context similarity between the answer and question.
 * The algorithm it uses is simply the mean of the word vectors (not really a
 * great solution, better with short questions / answers)
 */
public class GloveAnswerQuestionContext extends AnswerScorer {
	
	@Override
	public double scoreAnswer(Question q, Answer a) {
		List<float[]> qtokens = q.memo(Phrase.simpleTokens)
				.stream().map(DenseVectors::vectorFor)
				.filter(v -> v.isPresent())
				.map(v ->v.get())
				.collect(Collectors.toList());
		List<float[]> atokens = a.memo(Phrase.simpleTokens)
				.stream().map(DenseVectors::vectorFor)
				.filter(v -> v.isPresent())
				.map(v -> v.get())
				.collect(Collectors.toList());
		
		return DenseVectors.sim(DenseVectors.mean(atokens), DenseVectors.mean(qtokens));	
	}
	
}
