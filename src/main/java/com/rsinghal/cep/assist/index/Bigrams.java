package com.rsinghal.cep.assist.index;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import org.apache.log4j.Logger;

import com.rsinghal.cep.assist.Passage;

import edu.stanford.nlp.util.IterableIterator;

/**
 * Count the bigrams in all passages for entropy based scorers
 * @author Ravi Singhal
 */
public class Bigrams implements Segment {
	private ConcurrentHashMap<String, Integer> unigrams = new ConcurrentHashMap<>(1_000_000, (float) 0.75, 50);
	private ConcurrentHashMap<String, Integer> bigrams = new ConcurrentHashMap<>(1_000_000, (float) 0.75, 50);
	private final Logger log = Logger.getLogger(getClass());
	
	public Bigrams() {
	}

	@Override
	public void close() throws IOException {
		flush();
	}
	
	public void flush() throws IOException {
		// Make space-separated lines
		Stream<String> lines = unigrams.entrySet().stream()
				.map((pair) ->
					pair.getKey() + " " + pair.getValue());
		unigrams= new ConcurrentHashMap<>(1_000_000, (float) 0.75, 50);
		Files.write(
				Paths.get("/mnt/NCDS/sean", "unigrams"),
				new IterableIterator<String>(lines.iterator()),
				StandardOpenOption.CREATE,
				StandardOpenOption.WRITE,
				StandardOpenOption.APPEND);
		// Make space-separated lines
		lines = bigrams.entrySet().stream()
				.map((pair) ->
					pair.getKey() + " " + pair.getValue());
		bigrams =new ConcurrentHashMap<>(1_000_000, (float) 0.75, 50);
		Files.write(
				Paths.get("/mnt/NCDS/sean", "bigrams"),
				new IterableIterator<String>(lines.iterator()),
				StandardOpenOption.CREATE,
				StandardOpenOption.WRITE,
				StandardOpenOption.APPEND);
	}

	@Override
	public void accept(Passage t) {
		if (!t.getTokens().isEmpty()) {
			unigrams.merge(t.getTokens().get(0), 1, (a, b) -> a+b); 
		}
		for (int i=0; i < t.getTokens().size() - 1; i++) {
			String key = t.getTokens().get(i) + " " + t.getTokens().get(i+1);
			bigrams.merge(key, 1, (a, b) -> a+b);
			unigrams.merge(t.getTokens().get(i+1), 1, (a, b) -> a+b);
		}
		// Try to keep it from absorbing all available memory
		if (unigrams.size() > 1_000_000
				|| bigrams.size() > 1_000_000) {
			try {
				flush();
			} catch (IOException failed_flush) {
				log.error(failed_flush);
			}
		}
	}

}
