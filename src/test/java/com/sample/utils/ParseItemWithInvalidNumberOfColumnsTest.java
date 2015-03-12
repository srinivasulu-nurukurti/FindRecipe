package com.sample.utils;

import static org.assertj.core.api.Fail.fail;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ParseItemWithInvalidNumberOfColumnsTest {

	@Parameters
	public static Collection<Object[]> itemsWithInvalidNumberOfColumns() {

		Object[][] data = new Object[][] {
				new Object[] { new String[] { "bread,10,slices" } },
				new Object[] { new String[] { "bread,10,slices,25/12/2014,25/12/2014" } } };

		return Arrays.asList(data);
	}

	public String[] item;

	public ParseItemWithInvalidNumberOfColumnsTest(String[] item) {
		this.item = item;
	}

	@Test(expected = IllegalArgumentException.class)
	public void parseFridgeItem_ShouldThrowException_WhenNumberOfColumnsDoNotMatch() {
		ItemParser.parseFridgeItem(item);
		fail("Adding items should have failed!");
	}
}
