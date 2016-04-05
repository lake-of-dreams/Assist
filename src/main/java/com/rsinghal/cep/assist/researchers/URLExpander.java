package com.rsinghal.cep.assist.researchers;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import com.google.gson.reflect.TypeToken;
import com.rsinghal.cep.assist.Answer;
import com.rsinghal.cep.assist.Environment;
import com.rsinghal.cep.assist.Passage;
import com.rsinghal.cep.assist.Phrase;

import crawlercommons.fetcher.BaseFetchException;
import crawlercommons.fetcher.http.SimpleHttpFetcher;
import crawlercommons.fetcher.http.UserAgent;
import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.extractors.ArticleExtractor;


/** Fill in the full text of an answer from it's URL, if it has one */
public class URLExpander extends Researcher {
	private SimpleHttpFetcher fetcher;
			
	private Environment env;
	
	public URLExpander(Environment env) {
		this.env = env;
		fetcher = new SimpleHttpFetcher(3,
				new UserAgent(
						"Assist",
						"ravi.singhal21@gmail.com",
						"https://github.com/rsinghal-foundary/Assist",
						"Mozilla/5.0",
						"05 April 2016"));

		//fetcher.setConnectionTimeout(2000);
		//fetcher.setSocketTimeout(2000);
		fetcher.setMaxRetryCount(1);
	}
	
	/**
	 * Get a page from the Internet and clean it.
	 */
	private String fetch(String key) {
		try {
			byte[] payload = fetcher.fetch(key.substring(4)).getContent();
			InputStreamReader isr = new InputStreamReader(
					new ByteArrayInputStream(payload));
			return ArticleExtractor.INSTANCE.getText(isr);
		} catch (BaseFetchException | BoilerpipeProcessingException e) {
			// TODO Auto-generated catch block
			System.err.println("Can't connect to " + key);
			return "";
		}
	}
	
	public Answer answer(Phrase q, Answer a) {
		a.passages.replaceAll( p -> {
			if (p.reference.startsWith("http") && p.reference.contains(".htm")) {
				/* This is roundabout because I really want to avoid
				 * committing to a character set. (So I don't use String.)
				 */
				// Download
				String payload = env.computeIfAbsent("url:"+p.reference,
						this::fetch,
						new TypeToken<String>(){}.getType());
				if (!payload.isEmpty()) {
					// Parse
					p = new Passage(
							"live-url",
							p.title,
							payload,
							p.reference);
					a.log(this, "Filled in passage from %s", p.reference);
				}
			}
			return p;
		});
		return a;
	}
}
