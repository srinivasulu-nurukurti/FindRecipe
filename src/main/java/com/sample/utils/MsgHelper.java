package com.sample.utils;

import com.sample.collection.FridgeRepository;
import com.sample.collection.RecipesRepository;
import com.sample.models.FridgeItem;
import com.sample.models.Item;
import com.sample.models.Recipe;
import com.sample.models.RecipePack;

import org.joda.time.Days;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MsgHelper {

    private MsgHelper() {}

    public static Message suggestRecipe() {
        return FridgeRepository.instance().isEmpty() ? fridgeIsEmpty()
                : RecipesRepository.instance().isEmpty() ? takeout()
                : findRecipe();
    }

    private static Message fridgeIsEmpty() {
        return new Message("Fridge is empty");
    }

    private static Message takeout() {
        return new Message("Order Takeout");	
    }

    private static Message suggestedRecipe(Recipe recipe) {
        return new Message("Suggestion for dinner", recipe); 	 	
    }

    private static Message findRecipe() {
        List<RecipePack> recipesList = new ArrayList<>();
        for (Recipe recipe : RecipesRepository.instance().get()) {
            boolean foundRecipe = true;
            RecipePack recipeItem = new RecipePack(recipe);

            for (Item ingredient : recipe.getIngredients()) {
                FridgeItem fridgeItem = FridgeRepository.instance().getItem(ingredient.getItem());
                if (!isValid(fridgeItem, ingredient, recipeItem)) {
                    foundRecipe = false;
                    break;
                }
            }

            if (foundRecipe)
                recipesList.add(recipeItem);
        }

        return recipesList.isEmpty() ? takeout()
                : recipesList.size() == 1 ? suggestedRecipe(recipesList.get(0).getRecipe())
                : findBestMatch(recipesList);
    }

    private static boolean isValid(FridgeItem item, Item ingredient, RecipePack recipeItem) {
        if (item == null
                || !item.getUnit().equals(ingredient.getUnit())
                || item.getAmount() < ingredient.getAmount())
            return false;

        LocalDate today = new LocalDate();
        LocalDate useBy = new LocalDate(item.getUseBy());
        if (useBy.isBefore(today))
            return false;

        recipeItem.getClosestDays().add(Days.daysBetween(today, useBy).getDays());
        return true;
    }

    private static Message findBestMatch(List<RecipePack> recipeItemList) {
        Iterator<RecipePack> it = recipeItemList.iterator();
        return suggestedRecipe(findBestMatch(it.next(), it).getRecipe());
    }

    private static RecipePack findBestMatch(RecipePack current, Iterator<RecipePack> it) {
        if (!it.hasNext())
            return current;

        RecipePack next = it.next();

        Iterator<Integer> currentIt = current.getClosestDays().iterator();
        Iterator<Integer> nextIt = next.getClosestDays().iterator();

        while (currentIt.hasNext()) {
            if (!nextIt.hasNext())
                return findBestMatch(next, it);

            int result = currentIt.next().compareTo(nextIt.next());
            if (result == -1)
                return findBestMatch(current, it);
            if (result == 1)
                return findBestMatch(next, it);
        }

        return findBestMatch(current, it);
    }
}
