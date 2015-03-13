package com.sample.collection;

import static org.assertj.core.api.Assertions.assertThat;

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
            		
    
    private static final List<Item> RECIPE_TWO_ITEMS = Lists.newArrayList(
			(new Item("guava", 8300, Unit.GRAMS)),
			(new Item("rice", 1000, Unit.ML)));
			
    private static final List<Recipe> RECIPE_TWO = Lists.newArrayList(
            new Recipe("rice and guava ", RECIPE_TWO_ITEMS ));
    
    private static final List<Item> RECIPE_THREE_ITEMS = Lists.newArrayList(
			(new Item("guava", 8300, Unit.GRAMS)),
			(new Item("rice", 500, Unit.ML)),
			(new Item("rice", 700, Unit.ML)));
			
    private static final List<Recipe> RECIPE_THREE = Lists.newArrayList(
            new Recipe("rice and guava ", RECIPE_THREE_ITEMS ),
            new Recipe("rice and guava ", RECIPE_TWO_ITEMS )
    		);


    private RecipesRepository repository;

    @Before
    public void init() {
        repository = RecipesRepository.instance();
    }

    /**
     * Below method tests adding of items to repository and ensures only added 
     * items are available and clear old items
     */
    @Test
    public void update_shouldRemoveItems_BeforeAddingNewOnes() {
        repository.update(RECIPE_ONE);
        List<Recipe> recipeList = repository.get();
        assertThat(recipeList).hasSize(1).containsAll(RECIPE_ONE);
        
        List<Item> itemsInRecipe = recipeList.get(0).getIngredients();
        assertThat(itemsInRecipe).hasSize(2).containsAll(RECIPE_ONE_ITEMS);
        
        repository.update(RECIPE_TWO);
        List<Recipe> recipeListTwo = repository.get();
        assertThat(recipeListTwo).hasSize(1).containsAll(RECIPE_TWO);
        
        List<Item> itemsInRecipeTwo = recipeListTwo.get(0).getIngredients();
        assertThat(itemsInRecipeTwo).hasSize(2).containsAll(RECIPE_TWO_ITEMS);
        
    }

    
    /**
     * To test duplicate items insertion and only latest items
     * are available 
     */
    @Test
    public void update_shouldOverrideDuplicatedItems() {
        repository.update(RECIPE_THREE);
        List<Recipe> items = repository.get();
        assertThat(items).hasSize(1).contains(RECIPE_THREE.get(1));
        
        List<Item> itemsInRecipe = items.get(0).getIngredients();
        assertThat(itemsInRecipe).hasSize(2).contains(RECIPE_TWO_ITEMS.get(1));
        
    }

    
    /**
     * To test remove functionality of repository
     */
    @Test
    public void removeAll_shouldClearTheRepository() {
        repository.update(RECIPE_THREE);
        assertThat(repository.isEmpty()).isFalse();
        assertThat(repository.get()).hasSize(1);

        repository.removeAll();
        assertThat(repository.isEmpty()).isTrue();
        assertThat(repository.get()).isEmpty();
    }
}
