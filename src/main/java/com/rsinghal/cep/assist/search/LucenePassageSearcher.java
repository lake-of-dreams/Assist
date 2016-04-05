package com.rsinghal.cep.assist.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;

import com.rsinghal.cep.assist.Environment;
import com.rsinghal.cep.assist.Passage;
import com.rsinghal.cep.assist.Score;
import com.rsinghal.cep.assist.scorers.Merge;

/**
 * @author Ravi Singhal
 */
public class LucenePassageSearcher extends Searcher {
	private final IndexSearcher lucene;
	private final Environment env;
	
	public LucenePassageSearcher(Environment env) {
		super(env);
		this.lucene = env.lucene;
		this.env = env;
		Score.register("LUCENE_SCORE", -1, Merge.Mean);
		Score.register("LUCENE_RANK", -1, Merge.Mean);
	}
	
	public List<Passage> query(String question_text) {
		List<Passage> results = new ArrayList<>();
		try {
			ScoreDoc[] hits = env.simpleLuceneQuery(question_text, MAX_RESULTS);
			// This isn't range based because we need the rank
			for (int i=0; i < hits.length; i++) {
				Document doc = lucene.doc(hits[i].doc, Collections.singleton("docno"));
				results.add(new com.rsinghal.cep.assist.Passage(
						"lucene", 			// Engine
						"",	// Title
						"", // Text
						doc.get("docno"))   // Reference
						.score("LUCENE_RANK", (double) i)           // Rank
						.score("LUCENE_SCORE", (double) hits[i].score)	// Source
						);
			}
		} catch (IOException e) {
			System.out.println("Failed to query Lucene. Is the index in the correct location?");
			e.printStackTrace();
		}
		
		// Fill any missing full text from sources
		return fillFromSources(results);
	}

}
