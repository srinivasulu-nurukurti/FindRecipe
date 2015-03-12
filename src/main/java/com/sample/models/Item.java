package com.sample.models;

import javax.xml.bind.annotation.XmlRootElement;

import com.sample.utils.Unit;

@XmlRootElement
public class Item {

	private String item;
	private int amount;
	private Unit unit;

	public Item() {
	}

	public Item(String item, int amount, Unit unit) {
		setItem(item);
		setAmount(amount);
		setUnit(unit);
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	@Override
	public String toString() {
		return "Item{" + "item='" + item + '\'' + ", amount=" + amount
				+ ", unit=" + unit + "}";
	}
}
