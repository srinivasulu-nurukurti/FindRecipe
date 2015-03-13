package com.sample.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.WebApplicationException;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.sample.collection.RecipesRepository;
import com.sample.models.Item;
import com.sample.models.Recipe;
import com.sample.utils.Unit;


public class RecipesServiceTest {

	private RecipesService service;

    @Before
    public void init()
    {
        service = new RecipesService();
        RecipesRepository.instance().removeAll();
    }

    @Test(expected = WebApplicationException.class)
    public void addRecipes_shouldThrowAnException_WhenListIsEmpty() throws IOException {
        service.add(new ArrayList<Recipe>());
        fail("Adding recipes should have failed!");
    }

    @Test(expected = WebApplicationException.class)
    public void addRecipes_shouldThrowAnException_WhenListIsNull() throws IOException {
        service.add(null);
        fail("Adding items should have failed!");
    }

    @Test
    public void addRecipe_shouldPopulateTheRepository() {
    	
    	List<Item> RECIPE_ITEMS = Lists.newArrayList(
    			(new Item("bread" ,  8, Unit.SLICES)),
    			(new Item("cheese", 10, Unit.SLICES)));
    			
        List<Recipe> RECIPE = Lists.newArrayList(
                new Recipe("cheese sand wich ", RECIPE_ITEMS ));
        
        service.add(RECIPE);
        assertThat(RecipesRepository.instance().get()).hasSize(1);
    }

}
