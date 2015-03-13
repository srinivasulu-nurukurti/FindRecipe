package com.sample.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;

import java.io.IOException;

import javax.ws.rs.WebApplicationException;

import org.junit.Before;
import org.junit.Test;

import com.sample.collection.FridgeRepository;


public class FridgeServiceTest {

    private FridgeService service;

    @Before
    public void init()
    {
        service = new FridgeService();
        FridgeRepository.instance().removeAll();
    }

    @Test(expected = WebApplicationException.class)
    public void addItems_shouldThrowAnException_WhenCsvIsEmpty() throws IOException {
        service.addItems("");
        fail("Adding items should have failed!");
    }

    @Test(expected = WebApplicationException.class)
    public void addItems_shouldThrowAnException_WhenCsvIsBlank() throws IOException {
        service.addItems("   ");
        fail("Adding items should have failed!");
    }

    @Test(expected = WebApplicationException.class)
    public void addItems_shouldThrowAnException_WhenCsvIsInvalid() throws IOException {
        String invalidCsv = "bread,10,slices,25/12/2014\n" +
                            "cheese,10,slices,25/12/2014, unexpected-column\n"+
                            "bread,10,slices,25/13/2014";
                            
                            
        service.addItems(invalidCsv);
        fail("Adding items should have failed!");
    }

    @Test
    public void addItems_shouldPopulateTheRepository() throws IOException {
        String csv = "bread,10,slices,25/12/2014\n" +
                     "cheese,10,slices,25/12/2014";

        FridgeRepository.instance().removeAll();

        service.addItems(csv);
        assertThat(FridgeRepository.instance().get()).hasSize(2);
    }
}
