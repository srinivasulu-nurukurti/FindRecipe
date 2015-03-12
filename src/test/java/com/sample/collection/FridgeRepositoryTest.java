package com.sample.collection;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.sample.collection.FridgeRepository;
import com.sample.models.FridgeItem;
import com.sample.utils.Unit;

public class FridgeRepositoryTest {

    private static final DateTimeFormatter FORMATTER = DateTimeFormat.forPattern("d/M/yyyy");

    private static final List<FridgeItem> ITEMS_ONE = Lists.newArrayList(
            new FridgeItem("bread", 8, Unit.SLICES, FORMATTER.parseLocalDate("25/03/2014")),
            new FridgeItem("cheese", 10, Unit.SLICES, FORMATTER.parseLocalDate("13/12/2014")));

    private static final List<FridgeItem> ITEMS_TWO = Lists.newArrayList(
            new FridgeItem("guava", 8300, Unit.GRAMS, FORMATTER.parseLocalDate("25/03/2014")),
            new FridgeItem("rice", 500, Unit.ML, FORMATTER.parseLocalDate("25/12/2015")));

    private static final List<FridgeItem> ITEMS_THREE = Lists.newArrayList(
            new FridgeItem("guava", 8300, Unit.GRAMS, FORMATTER.parseLocalDate("25/03/2014")),
            new FridgeItem("rice", 500, Unit.ML, FORMATTER.parseLocalDate("25/12/2015")),
            new FridgeItem("rice", 700, Unit.ML, FORMATTER.parseLocalDate("22/12/2015")));

    private FridgeRepository repository;

    @Before
    public void init() {
        repository = FridgeRepository.instance();
    }

    @Test
    public void update_shouldRemoveItems_BeforeAddingNewOnes() {
        repository.update(ITEMS_ONE);
        List<FridgeItem> itemsOne = repository.get();
        assertThat(itemsOne).hasSize(2).containsAll(ITEMS_ONE);

        repository.update(ITEMS_TWO);
        List<FridgeItem> itemsTwo = repository.get();
        assertThat(itemsTwo).hasSize(2).containsAll(ITEMS_TWO);
    }

    @Test
    public void update_shouldOverrideDuplicatedItems() {
        repository.update(ITEMS_THREE);
        List<FridgeItem> items = repository.get();
        assertThat(items).hasSize(2).contains(ITEMS_THREE.get(0)).contains(ITEMS_THREE.get(2));
    }

    @Test
    public void removeAll_shouldClearTheRepository() {
        repository.update(ITEMS_ONE);
        assertThat(repository.isEmpty()).isFalse();
        assertThat(repository.get()).hasSize(2);

        repository.removeAll();
        assertThat(repository.isEmpty()).isTrue();
        assertThat(repository.get()).isEmpty();
    }
}
