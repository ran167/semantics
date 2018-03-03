package com.abb.nlp;

public class PosDisplayData {
	private String sen;
	private String tokens[];
	private String tags[];
	private double probs[];
	public PosDisplayData(String sen, String[] tokens, String[] tags, double[] probs) {
		super();
		this.sen = sen;
		this.tokens = tokens;
		this.tags = tags;
		this.probs = probs;
	}
	public String getSen() {
		return sen;
	}
	public void setSen(String sen) {
		this.sen = sen;
	}
	public String[] getTokens() {
		return tokens;
	}
	public void setTokens(String[] tokens) {
		this.tokens = tokens;
	}
	public String[] getTags() {
		return tags;
	}
	public void setTags(String[] tags) {
		this.tags = tags;
	}
	public double[] getProbs() {
		return probs;
	}
	public void setProbs(double[] probs) {
		this.probs = probs;
	}
	
}
