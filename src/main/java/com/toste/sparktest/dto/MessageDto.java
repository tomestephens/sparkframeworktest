package com.toste.sparktest.dto;

public class MessageDto {
	private String message;

	public MessageDto(String message, String... args) {
		this.message = String.format(message, (Object[])args);
	}

	public MessageDto(Exception e) {
		this.message = e.getMessage();
	}

	public String getMessage() {
		return this.message;
	}
}