package io.anymobi.domain.dto.exception;

public class ExceptionDto {

	private String msg;
	
	private String cause;
	
	private ExceptionType type;
	
	public ExceptionDto(String msg, String cause, ExceptionType type) {
		super();
		this.msg = msg;
		this.cause = cause;
		this.type = type;
	}

	@Override
	public String toString() {
		
		return msg + ", " + type.getTitle() + ": " + cause;
	}

	public String getMsg() {
		return msg;
	}

	public String getCause() {
		return cause;
	}

	public ExceptionType getType() {
		return type;
	}
}
