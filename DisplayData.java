package com.abb.nlp;

public class DisplayData {
private String sen;
private String tokens[];
public DisplayData(String sen, String[] tokens) {
	super();
	this.sen = sen;
	this.tokens = tokens;
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

}
