package com.sample.services;

import com.sample.collection.RecipesRepository;
import com.sample.models.Recipe;
import com.sample.utils.Message;
import com.sample.utils.MsgHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;

import static javax.ws.rs.core.Response.Status;

/**
 * This class is responsible to fetch recipe items
 * and to add new recipes
 * 
 */
@Path("/recipes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RecipesService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecipesService.class);

    /*
     * fetching available recipes
     */
    @GET
    public List<Recipe> get() {
        return RecipesRepository.instance().get();
    }

    /*
     * Adding new recipes
     */
    @POST @Path("/add")
    public Message add(List<Recipe> recipes) {
        LOGGER.info("Adding recipes: ", recipes);

        try {
        	
        	if(recipes == null || recipes.size()==0)
        		throw new IllegalArgumentException("No Recipes specified! ");
        	
            RecipesRepository.instance().update(recipes);
            
        } catch(IllegalArgumentException e) {
            throw new WebApplicationException(Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build());
        }

        return MsgHelper.suggestRecipe();
    }

    @GET @Path("/suggestion")
    public Message suggestion() {
        return MsgHelper.suggestRecipe();
    }
}
