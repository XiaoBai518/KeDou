package com.kedou.util;

public class UpLoadErro {

	private String result;
	private String message;
	
	public String getResult() {
		return result;
	}
	public void setResult(Boolean b) {
		if(!b) {
			result = "failed";
		}
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
