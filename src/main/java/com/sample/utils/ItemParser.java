package com.sample.utils;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.sample.models.FridgeItem;

public final class ItemParser {

	public static FridgeItem parseFridgeItem(String[] columns) {

		if (columns.length != 4)
			throw new IllegalArgumentException(
					" Expected 4 fields for item, instead of: "
							+ columns.length);

		return new FridgeItem(getItem(columns[0]), getAmount((columns[1])),
				getUnit((columns[2])), getDate((columns[3])));
	}

	private static String getItem(String item) {
		if (item == null || item.trim().length() == 0)
			throw new IllegalArgumentException(" Expected item name ");
		return item.trim();
	}

	private static int getAmount(String amount) {

		if (amount == null || amount.trim().length() == 0)
			throw new IllegalArgumentException(" Expected amount for item ");
		return Integer.parseInt(amount.trim());
	}

	private static Unit getUnit(String unit) {

		if (unit == null || unit.trim().length() == 0)
			throw new IllegalArgumentException(
					" Expected no of units for item ");
		return Unit.valueOf(unit.trim().toUpperCase());
	}

	private static LocalDate getDate(String date) {

		if (date == null || date.trim().length() == 0)
			throw new IllegalArgumentException(" Expected date for item ");

		DateTimeFormatter formatter = DateTimeFormat.forPattern("d/M/yyyy");
		return formatter.parseLocalDate(date.trim());
	}
	
}


