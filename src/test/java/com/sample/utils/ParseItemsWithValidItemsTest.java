package com.sample.utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.sample.models.FridgeItem;

@RunWith(Parameterized.class)
public class ParseItemsWithValidItemsTest {

	@Parameters
	public static Collection<Object[]> validItems() {

		Object[][] data = new Object[][] {
				new Object[] { new String[] { "bread", "   10", "  slices",
						"1/12/2014" } },
				new Object[] { new String[] { "banana", "3", "of  ",
						"15/5/2014" } },
				new Object[] { new String[] { "watermelon", "3", "OF",
						"15/12/2014" } },
				new Object[] { new String[] { "butter", "250", "GRAMS",
						"02/03/2014 " } },
				new Object[] { new String[] { "peanut butter", "250", "grams ",
						"2/3/2014" } },
				new Object[] { new String[] { "vanilla extract", "20", " ml",
						"26/12/2013" } }
						};

		return Arrays.asList(data);
	}

	public String[] item;

	public ParseItemsWithValidItemsTest(String[] item) {
		this.item = item;
	}

	@Test
	public void parseFridgeItem() {

		FridgeItem fridgeItem = ItemParser.parseFridgeItem(item);

		assertThat(fridgeItem.getItem()).isNotEmpty();
		assertThat(fridgeItem.getAmount()).isGreaterThan(0);
		assertThat(fridgeItem.getUnit()).isNotNull();
		assertThat(fridgeItem.getUseBy()).isNotNull();
	}

}
