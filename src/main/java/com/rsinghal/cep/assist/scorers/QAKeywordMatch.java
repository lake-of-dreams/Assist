package com.rsinghal.cep.assist.scorers;

import java.util.List;

import com.rsinghal.cep.assist.Answer;
import com.rsinghal.cep.assist.Question;
import com.rsinghal.cep.assist.StringUtils;

/*Author : Ricky Sanders
 * 
 * Checks the Question against the answer to remove
 * answers that closely match the question
 * 
 */

public class QAKeywordMatch extends AnswerScorer {
	public double scoreAnswer(Question q, Answer a){
		List<String> questionTextArray = StringUtils.tokenize(q.text);
		List<String> answerTextArray = StringUtils.tokenize(a.text);
		int count = 0;
		for (String word : questionTextArray)
			if (answerTextArray.contains(word))
				count += 1;
		return (count / (double)questionTextArray.size());
	}
}
