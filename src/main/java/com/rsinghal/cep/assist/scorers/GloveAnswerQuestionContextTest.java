package com.rsinghal.cep.assist.scorers;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.rsinghal.cep.assist.Answer;
import com.rsinghal.cep.assist.Question;

public class GloveAnswerQuestionContextTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testScoreAnswer() {
		GloveAnswerQuestionContext scorer = new GloveAnswerQuestionContext();
		assertEquals(scorer.scoreAnswer(new Question("frog"), new Answer("toad")), 0.73, 0.01);
		assertEquals(scorer.scoreAnswer(new Question("frog"), new Answer("maple")), 0.23, 0.01);
		assertEquals(scorer.scoreAnswer(
				new Question("Who was Marilyn Monroe's second husband?"),
				new Answer("Joe Dimaggio")), 0.26, 0.01);
		assertEquals(scorer.scoreAnswer(
				new Question("Who was Marilyn Monroe's ^&^*()(*&$%^% 7868769987 jhgkjhgbnvbnuyr second husband?"),
				new Answer("Joe Dimaggio")), 0.26, 0.01);
		assertEquals(scorer.scoreAnswer(
				new Question("Who was Marilyn Monroe's second husband?"),
				new Answer("husband")), 0.71, 0.01);
		assertEquals(scorer.scoreAnswer(
				new Question("34986 **(&)(*& uiuytiuytiuyti"),
				new Answer("iuyoiuyoiuyhjjkhg")), 0.0, 0.01);
		assertEquals(scorer.scoreAnswer(
				new Question("democracy"),
				new Answer("")), 0.0, 0.01);
	}

}
