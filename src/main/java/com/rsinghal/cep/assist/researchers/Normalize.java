package com.rsinghal.cep.assist.researchers;

import java.util.List;

import com.rsinghal.cep.assist.Answer;
import com.rsinghal.cep.assist.Question;
import com.rsinghal.cep.assist.Score;

public class Normalize extends Researcher {

	@Override
	public List<Answer> question(Question q, List<Answer> candidates) {
		return Score.normalizeGroup(candidates);
	}

}
