package com.rsinghal.cep.assist.researchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.rsinghal.cep.assist.Answer;
import com.rsinghal.cep.assist.Environment;
import com.rsinghal.cep.assist.Passage;
import com.rsinghal.cep.assist.Phrase;
import com.rsinghal.cep.assist.Question;
import com.rsinghal.cep.assist.nlp.ClueType;
import com.rsinghal.cep.assist.nlp.DBPediaCandidateType;
import com.rsinghal.cep.assist.nlp.Relatedness;
import com.rsinghal.cep.assist.nlp.SupportCandidateType;

import edu.stanford.nlp.util.Pair;


public class TagLAT extends Researcher {
	private final DBPediaCandidateType dbpedia;
	private final Relatedness syn;
	
	public TagLAT(Environment env) {
		dbpedia = new DBPediaCandidateType(env);
		syn = new Relatedness(env);
	}
	
	public List<Answer> pull(Question q, List<Answer> answers) {
		return pull(q, answers, 0);
	}
	
	public List<Answer> pull(Question q, List<Answer> answers, int depth) {
		return question(q, chain.pull(q, answers), depth);
	}
	
	
	/**
	 * Find the possible lexical types of a candidate, and label the answer.
	 */
	public List<Answer> question(Question q, List<Answer> answers, int depth) {
		int have_any_types = 0;
		
		int dbpedia_types = 0;
		int support_types = 0;
		
		List<Answer> suggestions = new ArrayList<>();
		
		for (Answer a: answers) {
			
			// Handle DBPedia types
			
			a.lexical_types = dbpedia.viaDBPedia(a.text);
			for (String type: a.lexical_types) {
				a.log(this, "DBPedia says it's a %s", type);
			}
			if (a.lexical_types.isEmpty())
				a.log(this, "DBPedia has no type information for it.");
			dbpedia_types += a.lexical_types.size(); 
			
			// Handle Support types
			
			for (Passage p: a.passages) {
				List<Pair<String, String>> types = p.memo(SupportCandidateType::extract);
				for (Pair<String, String> name_and_type : types) {
					Phrase name = new Phrase(name_and_type.first);
					Phrase type = new Phrase(name_and_type.second);
					if (syn.implies(a, name)) {
						a.log(this, "Passage %s says it's a %s.", p.reference, type);
						a.lexical_types.add(type.text);
						support_types++;
					} else if (syn.implies(type, new Phrase(q.memo(ClueType::fromClue)))) {
						Answer suggestion = new Answer(name.text);
						suggestion.lexical_types = Arrays.asList(type.text);
						suggestion.log(this, "Found it's a %s, while reading about %s in %s", type, a, p.reference);
						if (!(suggestions.contains(suggestion)
								|| answers.contains(suggestion))) {
							log.info("Suggesting " + name);
							suggestions.add(suggestion);
						}
						
					}
				}
			}
			if (!a.lexical_types.isEmpty()) have_any_types++;
		}
		
		// This is the chain magic:
		// We can pull the new suggestions through the pipeline and merge them!
		List<Answer> new_answers = new ArrayList<>();
		if (!suggestions.isEmpty() && depth < 3)
			new_answers.addAll(pull(q, suggestions, depth+1));
		new_answers.addAll(answers);
		

		//System.out.println(text + " could be any of " + types);
		log.info("Found " + (dbpedia_types+support_types) + " types for "
				+ have_any_types + " candidates. ("+ support_types +" by reading) "
				+ (answers.size() - have_any_types) + " candidates are unknown.");
		return new_answers;
	}

}

