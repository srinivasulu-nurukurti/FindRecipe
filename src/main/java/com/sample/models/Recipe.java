package com.sample.models;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class Recipe {

	private String name;
	private List<Item> ingredients;

	public Recipe() {
	}

	public Recipe(String name, List<Item> items) {
		setName(name);
		setIngredients(items);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Item> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<Item> ingredients) {
		this.ingredients = ingredients;
	}

	@Override
	public String toString() {
		return "Recipe {" + "name='" + name + '\'' + ", ingredients="
				+ ingredients + "}";
	}
}
