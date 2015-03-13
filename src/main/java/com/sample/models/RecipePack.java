package com.sample.models;

import java.util.TreeSet;

public class RecipePack {
	final Recipe recipe;
	final TreeSet<Integer> closestDays = new TreeSet<>();

	public RecipePack(Recipe recipe) {
		this.recipe = recipe;
	}

	public Recipe getRecipe(){
		return recipe;
	}
	
	public TreeSet<Integer> getClosestDays(){
		return closestDays;
	}
	
	@Override
	public String toString() {
		return "RecipeFound{" + "recipe=" + recipe + ", closestDays="
				+ closestDays + "}";
	}
}