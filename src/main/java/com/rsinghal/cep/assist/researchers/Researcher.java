package com.rsinghal.cep.assist.researchers;

import java.util.ArrayList;
import java.util.List;

import com.rsinghal.cep.assist.Answer;
import com.rsinghal.cep.assist.Log;
import com.rsinghal.cep.assist.Phrase;
import com.rsinghal.cep.assist.Question;

/** Researchers can modify questions and have the guarantee of running
 * sequentially. They also do not return double's because they are not expected
 * to do scoring. If they do, they can use score() themselves. Consider using
 * Scorer instead for that, which is parallelizable.
 */
abstract public class Researcher {
	/**
	 * The empty researcher does nothing.
	 */
	public static final Researcher NIL = new Researcher() {
		public List<Answer> pull(Question q, List<Answer> answers){return answers;}
	};
	
	/**
	 * The previous item in the research chain
	 */
	protected Researcher chain = NIL;
	
	/**
	 * Output to the user. (This is a multi-user app so each pipeline needs to
	 * know where to push new results.)
	 */
	protected Log log = Log.NIL;
	
	/**
	 * Join together segments of a (recursive) Researcher pipeline.
	 * The idea of it is that you can "pull" a question through it by passing
	 * it to pull() of the last Researcher segment.
	 * 
	 * @param segments  Pipe segments, which will be mutated (for the chain)
	 * @return  The last Researcher in the line
	 */
	public static Researcher pipe(Log output, Researcher... segments) {
		Researcher prev = NIL;
		for (Researcher link : segments) {
			link.chain = prev;
			link.log = output.kid(link.getClass());
			prev = link;
		}
		return prev;
	}
	
	/**
	 * Wrapper method to pull questions through the research chain
	 */
	public List<Answer> pull(Question q, List<Answer> candidates) {
		return question(q, chain.pull(q, candidates));
	}

	/** Default implementation of research for a question.
	 * Simply calls research_answer for every Answer
	 * Override this if you need more power.
	 * @param question
	 * @throws Exception 
	 */
	public List<Answer> question(Question q, List<Answer> candidates) {
		List<Answer> outs = new ArrayList<>();
		for (Answer in : candidates)
			outs.add(answer(q, in));
		return outs;
	}
	
	/** Default implementation for researching an answer.
	 * Does nothing by default. You don't need to override this if you don't
	 * use it.
	 * @param q TODO
	 * @param answer
	 * 
	 * @return TODO
	 */
	public Answer answer(Phrase q, Answer a) {
		return a;
	}
}