package com.sample.utils;

import static org.assertj.core.api.Fail.fail;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ParseItemWithInvalidFieldTest {

	@Parameters
	public static Collection<Object[]> itemsWithInvalidField() {

		Object[][] data = new Object[][] {
				new Object[] {new String[] { "bread", "10", "slices", "1/12/2014" }},
				new Object[] {new String[] { "cheese", "xx", "slices", "25/12/2014" }},
				new Object[] {new String[] { "butter", "250", "grams", "32/12/2014" }},
				new Object[] {new String[] { "mixed salad", "150", "grammar", "26/12/2013" }}};

		return Arrays.asList(data);
	}
	
	public String[] item;

	public ParseItemWithInvalidFieldTest(String[] item) {
		this.item = item;
	}

	@Test(expected = IllegalArgumentException.class)
	public void parseFridgeItem_ShouldThrowException_WhenItemIsNotValid() {
		ItemParser.parseFridgeItem(item);
		fail("Adding items should have failed!");
	}

}
