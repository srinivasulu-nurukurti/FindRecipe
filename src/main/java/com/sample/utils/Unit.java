package com.sample.utils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Unit {
	OF, GRAMS, ML, SLICES;

	@JsonValue
	public String toJson() {
		return name().toLowerCase();
	}

	@JsonCreator
	public static Unit fromJson(String text) {
		return valueOf(text.toUpperCase());
	}
}