package io.anymobi.common.enums;


public enum ExceptionType {

	SQL("Code"), SERVER("Exception");
	
	final private String title;
	
	private ExceptionType(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}
}
