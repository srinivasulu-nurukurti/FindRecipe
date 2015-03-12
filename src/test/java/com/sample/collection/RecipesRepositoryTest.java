package com.sample.collection;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.sample.models.Item;
import com.sample.models.Recipe;
import com.sample.utils.Unit;

public class RecipesRepositoryTest {

	private static final List<Item> RECIPE_ONE_ITEMS = Lists.newArrayList(
			(new Item("bread" ,  8, Unit.SLICES)),
			(new Item("cheese", 10, Unit.SLICES)));
			
    private static final List<Recipe> RECIPE_ONE = Lists.newArrayList(
            new Recipe("cheese sand wich ", RECIPE_ONE_ITEMS ));
            		

//    private static final List<Recipe> RECIPE_TWO = Lists.newArrayList(
//            new Recipe("guava", 8300, Unit.GRAMS, FORMATTER.parseLocalDate("25/03/2014")),
//            new Recipe("rice", 500, Unit.ML, FORMATTER.parseLocalDate("25/12/2015")));
//
//    private static final List<Recipe> RECIPE_THREE = Lists.newArrayList(
//            new Recipe("guava", 8300, Unit.GRAMS, FORMATTER.parseLocalDate("25/03/2014")),
//            new Recipe("rice", 500, Unit.ML, FORMATTER.parseLocalDate("25/12/2015")),
//            new Recipe("rice", 700, Unit.ML, FORMATTER.parseLocalDate("22/12/2015")));

    private RecipesRepository repository;

    @Before
    public void init() {
        repository = RecipesRepository.instance();
    }

    @Test
    public void update_shouldRemoveItems_BeforeAddingNewOnes() {
        repository.update(RECIPE_ONE);
        List<Recipe> recipeList = repository.get();
        assertThat(recipeList).hasSize(1).containsAll(RECIPE_ONE);
        
        List<Item> itemsInRecipe = recipeList.get(0).getIngredients();
        assertThat(itemsInRecipe).hasSize(2).containsAll(RECIPE_ONE_ITEMS);
        

//        repository.update(ITEMS_TWO);
//        List<FridgeItem> itemsTwo = repository.get();
//        assertThat(itemsTwo).hasSize(2).containsAll(ITEMS_TWO);
    }

//    @Test
//    public void update_shouldOverrideDuplicatedItems() {
//        repository.update(ITEMS_THREE);
//        List<FridgeItem> items = repository.get();
//        assertThat(items).hasSize(2).contains(ITEMS_THREE.get(0)).contains(ITEMS_THREE.get(2));
//    }
//
//    @Test
//    public void removeAll_shouldClearTheRepository() {
//        repository.update(ITEMS_ONE);
//        assertThat(repository.isEmpty()).isFalse();
//        assertThat(repository.get()).hasSize(2);
//
//        repository.removeAll();
//        assertThat(repository.isEmpty()).isTrue();
//        assertThat(repository.get()).isEmpty();
//    }
}
