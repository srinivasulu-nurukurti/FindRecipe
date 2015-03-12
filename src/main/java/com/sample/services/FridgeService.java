package com.sample.services;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sample.collection.FridgeRepository;
import com.sample.models.FridgeItem;
import com.sample.utils.CSVParser;
import com.sample.utils.Message;
import com.sample.utils.MsgHelper;

/**
 * This service is used to add and get fridge items 
 * 
 */
@Path("/fridge")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FridgeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FridgeService.class);

    /**
     * To get items from fridge
     * @return
     */
    @GET
    public List<FridgeItem> get() {
        return FridgeRepository.instance().get();
    }

    /**
     * To insert items into fridge
     * Input comes as CSV format and need to be parsed before adding to fridge
     * @param items
     * @return
     * @throws IOException
     */
    @POST @Path("/add")
    @Consumes(MediaType.TEXT_PLAIN)
    public Message addItems(String items) throws IOException {
       
        try {
            if (items.trim().isEmpty()) 
            	throw new IllegalArgumentException("No items specified! ");

            // parsing CSV data, each line contains a item content
            List<FridgeItem> fridgeItems = CSVParser.parseFridgeItems(items);
            FridgeRepository.instance().update(fridgeItems);
            
        } catch(IllegalArgumentException e) {
        	LOGGER.error(e.getMessage());
            throw new WebApplicationException(Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build());
        }

        // to send response back either as order taken or available recipes
        return MsgHelper.suggestRecipe();
    }
}
