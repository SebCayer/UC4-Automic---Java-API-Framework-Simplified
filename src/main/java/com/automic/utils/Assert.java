package com.automic.utils;

import com.google.common.base.Strings;

public class Assert {

	public static void isTrue(boolean expression, String message) {
		if (!expression) {
			throw new IllegalArgumentException(message);
		}
	}

	public static void isTrue(boolean expression) {
		isTrue(expression, "<Assertion failed> - this expression must be true");
	}

	public static void notNull(Object object, String message) {
		if (object == null) {
			throw new IllegalArgumentException(message);
		}
	}

	public static void notNull(Object object) {
		notNull(object, "<Assertion failed> - this argument is required; it must not be null");
	}

	public static void hasLength(String text, String message) {
		if (Strings.isNullOrEmpty(text)) {
			throw new IllegalArgumentException(message);
		}
	}

	public static void hasLength(String text) {
		hasLength(text,
				"<Assertion failed> - this String argument must have length; it must not be null or empty");
	}
}
