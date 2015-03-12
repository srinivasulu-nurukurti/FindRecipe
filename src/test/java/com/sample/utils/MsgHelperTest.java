package com.sample.utils;

import static com.google.common.collect.Lists.newArrayList;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Before;
import org.junit.Test;

import com.sample.collection.FridgeRepository;
import com.sample.collection.RecipesRepository;
import com.sample.models.FridgeItem;
import com.sample.models.Item;
import com.sample.models.Recipe;
import com.sample.utils.Message;
import com.sample.utils.MsgHelper;
import com.sample.utils.Unit;

public class MsgHelperTest {

    private static final DateTimeFormatter FORMATTER = DateTimeFormat.forPattern("d/M/yyyy");

    private static final List<FridgeItem> FRIDGE_ITEMS = newArrayList(
            new FridgeItem("bread", 8, Unit.SLICES, FORMATTER.parseLocalDate("12/3/2025")),
            new FridgeItem("banana", 12, Unit.OF, new LocalDate()),
            new FridgeItem("orange", 250, Unit.GRAMS, new LocalDate().plusDays(1)),
            new FridgeItem("kiwi", 500, Unit.GRAMS, new LocalDate().plusDays(2)),
            new FridgeItem("avocado", 50, Unit.OF, new LocalDate().minusDays(1)),
            new FridgeItem("cheese", 10, Unit.SLICES, FORMATTER.parseLocalDate("3/12/2024")));

    private static final List<Item> ITEMS_EXCEEDING_AMOUNT_IN_THE_FRIDGE = newArrayList(
            new Item("bread", 8, Unit.SLICES),
            new Item("cheese", 112, Unit.SLICES));
            //
    private static final Recipe RECIPE_EXCEEDING_AMOUNT_IN_THE_FRIDGE =
            new Recipe("Recipe One", ITEMS_EXCEEDING_AMOUNT_IN_THE_FRIDGE);

    private static final List<Item> ITEMS_PAST_USE_BY_DATE = newArrayList(
            new Item("avocado", 8, Unit.OF),
            new Item("cheese", 3, Unit.SLICES));
            //
    private static final Recipe RECIPE_PAST_USE_BY_DATE =
            new Recipe("Recipe Two", ITEMS_PAST_USE_BY_DATE);

    private static final List<Item> ITEMS_UNITS_DO_NOT_MATCH = newArrayList(
            new Item("kiwi", 8, Unit.OF),
            new Item("orange", 8, Unit.GRAMS));
            //
    private static final Recipe RECIPE_UNITS_DO_NOT_MATCH =
            new Recipe("Recipe Three", ITEMS_UNITS_DO_NOT_MATCH);

    private static final List<Item> ITEMS_OK_ONE = newArrayList(
            new Item("kiwi", 8, Unit.GRAMS),
            new Item("cheese", 2, Unit.SLICES));
            //
    private static final Recipe RECIPE_OK_ONE =
            new Recipe("Recipe Four", ITEMS_OK_ONE);

    private static final List<Item> ITEMS_OK_TWO = newArrayList(
            new Item("kiwi", 8, Unit.GRAMS),
            new Item("orange", 8, Unit.GRAMS),
            new Item("cheese", 2, Unit.SLICES));
            //
    private static final Recipe RECIPE_OK_TWO =
            new Recipe("Recipe Five", ITEMS_OK_TWO);

    private static final List<Item> ITEMS_OK_THREE = newArrayList(
            new Item("orange", 8, Unit.GRAMS),
            new Item("cheese", 2, Unit.SLICES));
            //
    private static final Recipe RECIPE_OK_THREE =
            new Recipe("Recipe Six", ITEMS_OK_THREE);

    @Before
    public void init() {
        FridgeRepository.instance().removeAll();
        RecipesRepository.instance().removeAll();
    }

    @Test
    public void getRecipe_shouldReturnTheBestMatch_whenIngredientsAreInTheFridge() {
        FridgeRepository.instance().update(FRIDGE_ITEMS);

        RecipesRepository.instance().update(newArrayList(RECIPE_OK_ONE, RECIPE_OK_TWO));
        Message response = MsgHelper.suggestRecipe();
        assertThat(response.getMessage()).containsIgnoringCase("suggestion");
        assertThat(response.getRecipe()).isEqualTo(RECIPE_OK_TWO);

        RecipesRepository.instance().update(newArrayList(RECIPE_OK_TWO, RECIPE_OK_THREE));
        response = MsgHelper.suggestRecipe();
        assertThat(response.getMessage()).containsIgnoringCase("suggestion");
        assertThat(response.getRecipe()).isEqualTo(RECIPE_OK_TWO);

        RecipesRepository.instance().update(newArrayList(RECIPE_OK_ONE, RECIPE_UNITS_DO_NOT_MATCH));
        response = MsgHelper.suggestRecipe();
        assertThat(response.getMessage()).containsIgnoringCase("suggestion");
        assertThat(response.getRecipe()).isEqualTo(RECIPE_OK_ONE);
    }

    @Test
    public void getRecipe_shouldReturnTakeout_whenItemInTheFridgePastUseByDate() {
        FridgeRepository.instance().update(FRIDGE_ITEMS);

        RecipesRepository.instance().update(newArrayList(RECIPE_PAST_USE_BY_DATE));
        Message response = MsgHelper.suggestRecipe();
        assertThat(response.getMessage()).containsIgnoringCase("takeout");
        assertThat(response.getRecipe()).isNull();
    }

    @Test
    public void getRecipe_shouldReturnTakeout_whenThereAreNotEnoughIngredientsInTheFridge() {
        FridgeRepository.instance().update(FRIDGE_ITEMS);

        RecipesRepository.instance().update(newArrayList(RECIPE_EXCEEDING_AMOUNT_IN_THE_FRIDGE));
        Message response = MsgHelper.suggestRecipe();
        assertThat(response.getMessage()).containsIgnoringCase("takeout");
        assertThat(response.getRecipe()).isNull();
    }

    @Test
    public void getRecipe_shouldReturnFridgeEmptyMessage_whenThereAreNoItemsOnTheFridge() {
        assertThat(FridgeRepository.instance().isEmpty()).isTrue();

        Message response = MsgHelper.suggestRecipe();
        assertThat(response.getMessage()).containsIgnoringCase("fridge")
                .containsIgnoringCase("empty");
        assertThat(response.getRecipe()).isNull();
    }

    @Test
    public void getRecipe_shouldReturnTakeOutMessage_whenFridgeHasItemsButThereAreNoRecipes() {
        FridgeRepository.instance().update(FRIDGE_ITEMS);
        assertThat(FridgeRepository.instance().isEmpty()).isFalse();
        assertThat(RecipesRepository.instance().isEmpty()).isTrue();

        Message response = MsgHelper.suggestRecipe();
        assertThat(response.getMessage()).containsIgnoringCase("takeout");
        assertThat(response.getRecipe()).isNull();
    }
}
