package com.sample.utils;

import org.junit.Test;

public class ParseItemWithNullArgumentTest {

	@Test(expected = NullPointerException.class)
    public void parseFridgeItem_ShouldThrowException_WhenItemArgumentIsNull() {
		ItemParser.parseFridgeItem(null);
    }   
}
