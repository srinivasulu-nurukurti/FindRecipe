package com.sample.utils;

import javax.xml.bind.annotation.XmlRootElement;

import com.sample.models.Recipe;

@XmlRootElement
public class Message {

    private String message;
    private Recipe recipe;

    public Message(String message) {
        this(message, null);
    }

    public Message(String message, Recipe recipe) {
        setMessage(message);
        setRecipe(recipe);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
