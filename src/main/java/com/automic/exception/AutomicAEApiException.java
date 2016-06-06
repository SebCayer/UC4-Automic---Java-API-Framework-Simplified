package com.automic.exception;

public class AutomicAEApiException extends Exception {

	public static final String ERROR_CODE_VERSION_NOT_SUPPORTED = "VERSION_NOT_SUPPORTED";
	public static final String ERROR_CONNECTION_MESSAGE_BOX = "ERROR_CONNECTION_MESSAGE_BOX";
	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public AutomicAEApiException(String code, String message) {
		super(message);
		this.code = code;
	}

	public AutomicAEApiException(String message) {
		super(message);
	}

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

}
