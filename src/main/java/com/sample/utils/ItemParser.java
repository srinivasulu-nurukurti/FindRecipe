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
		System.out.println(item.trim());
		return item.trim();
	}

	private static int getAmount(String amount) {

		int amt;
		if (amount == null || amount.trim().length() == 0)
			throw new IllegalArgumentException(" Expected amount for item ");

		try {
			amt = Integer.parseInt(amount.trim());
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage());
		}
		
		System.out.println(amt);
		return amt;
	}

	private static Unit getUnit(String unit) {

		if (unit == null || unit.trim().length() == 0)
			throw new IllegalArgumentException(
					" Expected no of units for item ");
		
		System.out.println(Unit.valueOf(unit.trim().toUpperCase()));
		
		return Unit.valueOf(unit.trim().toUpperCase());
	}

	private static LocalDate getDate(String date) {

		if (date == null || date.trim().length() == 0)
			throw new IllegalArgumentException(" Expected date for item ");

		DateTimeFormatter formatter = DateTimeFormat.forPattern("d/M/yyyy");
		
		System.out.println(formatter.parseLocalDate(date.trim()));
		
		return formatter.parseLocalDate(date.trim());
	}

}
