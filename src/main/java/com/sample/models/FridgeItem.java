package com.sample.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sample.utils.Unit;

import org.joda.time.LocalDate;

import java.util.Date;

public class FridgeItem extends Item {

	private Date useBy;

	public FridgeItem(String item, int amount, Unit unit, LocalDate useBy) {
		setItem(item);
		setAmount(amount);
		setUnit(unit);
		setUseBy(useBy.toDate());
	}

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "d/M/yyyy", locale = "en_AU", timezone = "Australia/Sydney")
	public Date getUseBy() {
		return useBy;
	}

	public void setUseBy(Date useBy) {
		this.useBy = useBy;
	}

	@Override
	public String toString() {
		return "FridgeItem {" + super.toString() + ", useBy=" + useBy + "}";
	}
}
