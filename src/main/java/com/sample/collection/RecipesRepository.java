package com.sample.collection;

import com.sample.models.Recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class RecipesRepository {

    private static RecipesRepository repository;
    private final ConcurrentHashMap<String, Recipe> recipes = new ConcurrentHashMap<>();

    private RecipesRepository() {}

    public static RecipesRepository instance() {
        if (repository == null) repository = new RecipesRepository();
        return repository;
    }

    public synchronized void update(List<Recipe> recipeList) {

        // Previous items are removed from the collection every time we update it.
        removeAll();

        // Duplicated items are overridden
        for (Recipe recipe : recipeList)
            recipes.put(recipe.getName(), recipe);
    }

    public synchronized void removeAll() {
        recipes.clear();
    }

    public synchronized List<Recipe> get() {
        return new ArrayList<>(recipes.values());
    }

    public synchronized boolean isEmpty() {
        return recipes.isEmpty();
    }
}
