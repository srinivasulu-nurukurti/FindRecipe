package com.sample.utils;

import au.com.bytecode.opencsv.CSVReader;
import com.sample.models.FridgeItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public final class CSVParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(CSVParser.class);

    private CSVParser() {}

    public static List<FridgeItem> parseFridgeItems(String items) throws IOException, IllegalArgumentException {

        List<FridgeItem> fridgeItems = new ArrayList<>();
        int currentRow = 0;

        try (CSVReader reader = new CSVReader(new StringReader(items))) {
            String[] nextItem;
            while((nextItem = reader.readNext()) != null) {
                currentRow++;
                // parsing each line as item
                FridgeItem fridgeItem = ItemParser.parseFridgeItem(nextItem);
                fridgeItems.add(fridgeItem);
            }
        } catch (IllegalArgumentException | NullPointerException e) {
            String message = "Failed to parse line " + currentRow + ". " + e.getMessage();
            LOGGER.error(message);
            throw new IllegalArgumentException(message, e);
        }

        return fridgeItems;
    }
}
