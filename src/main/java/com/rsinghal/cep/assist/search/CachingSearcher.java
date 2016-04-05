package com.rsinghal.cep.assist.search;

import java.util.List;
import com.google.gson.reflect.TypeToken;
import com.rsinghal.cep.assist.Environment;
import com.rsinghal.cep.assist.Passage;

public class CachingSearcher extends Searcher {
	private final Searcher searcher;
	private final String engine_name;

	public CachingSearcher(Environment env, Searcher searcher, String engine_name) {
		super(env);
		this.searcher = searcher;
		this.engine_name = engine_name;
	}
	
	public List<Passage> query(String query) {
		return env.computeIfAbsent(
				"search:" + engine_name +":"+ query,
				k -> searcher.query(query),
				new TypeToken<List<Passage>>(){}.getType()
				);
	}
}
