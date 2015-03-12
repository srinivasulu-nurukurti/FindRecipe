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
    	
    	List<Item> RECIPE_ONE_ITEMS = Lists.newArrayList(
    			(new Item()),(new Item()));
    			
        List<Recipe> RECIPE_ONE = Lists.newArrayList(
                new Recipe("cheese sand wich ", RECIPE_ONE_ITEMS ));
        
        service.add(RECIPE_ONE);
        assertThat(RecipesRepository.instance().get()).hasSize(1);
    }

//    @Test
//    public void addItems_shouldPopulateTheRepository() throws IOException {
//        String csv = "bread,10,slices,25/12/2014\n" +
//                     "cheese,10,slices,25/12/2014";
//
//        FridgeRepository.instance().removeAll();
//
//        service.addItems(csv);
//        assertThat(FridgeRepository.instance().get()).hasSize(2);
//    }
}
