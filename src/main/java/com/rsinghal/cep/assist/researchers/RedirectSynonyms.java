package com.rsinghal.cep.assist.researchers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;

import com.rsinghal.cep.assist.Answer;
import com.rsinghal.cep.assist.Database;
import com.rsinghal.cep.assist.Environment;
import com.rsinghal.cep.assist.Question;
import com.rsinghal.cep.assist.Score;
import com.rsinghal.cep.assist.scorers.Merge;

/**
 * Create a bunch of new answers with the same passages based on "synonyms"
 * made from Wikipedia redirects.
 * 
 * @author Ravi Singhal
 */
public class RedirectSynonyms extends Researcher {
	private final Database db;
	private final PreparedStatement s;
	
	public RedirectSynonyms(Environment env) {
		db = env.db;
		s = db.prep("SELECT source from wiki_redirects where target = ?;");
		Score.register("IS_WIKI_REDIRECT", 0.0, Merge.Min);
	}

	@Override
	public List<Answer> question(Question q, List<Answer> answers) {
		// For logging 
		int synonym_count = 0;
		List<Answer> new_answers = new ArrayList<Answer>();
		for (Answer a : answers) {
			try {
				s.setString(1, a.text);
				ResultSet results = s.executeQuery();
				while (results.next()) {
					synonym_count++;
					Answer new_answer = new Answer(
							new ArrayList<>(a.passages),
							a.scores.clone(),
							StringEscapeUtils.unescapeXml(results.getString("source")));
					a.scores.put("IS_WIKI_REDIRECT", 1.0);
					new_answers.add(new_answer);
				}
			} catch (SQLException e) {
				// Just don't make any synonyms.
				return answers;
			}
		}
		
		log.info("Found " + synonym_count + " synonyms for " + answers.size() +
				" candidate answers using Wikipedia redirects.");
		return new_answers;
	}
}
