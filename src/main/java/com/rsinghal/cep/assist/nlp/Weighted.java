package com.rsinghal.cep.assist.nlp;

/**
 * Simple immutable wrapper to express weight or probability
 * @author Ravi Singhal
 *
 * @param <T>
 */
public class Weighted<T> {
	public final T item;
	public final double weight;
	public Weighted(T item, double weight) {
		this.item = item;
		this.weight = weight;
	}
}
