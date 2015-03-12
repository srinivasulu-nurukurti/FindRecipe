package com.sample.collection;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.sample.models.FridgeItem;

public final class FridgeRepository {

    private static FridgeRepository repository;
    private final ConcurrentHashMap<String, FridgeItem> fridgeItems = new ConcurrentHashMap<>();

    private FridgeRepository() {}

    public static FridgeRepository instance() {
        if (repository == null) repository = new FridgeRepository();
        return repository;
    }
    
    public synchronized void update(List<FridgeItem> items) {

        // Previous items are removed from the collection and updated with new item
        removeAll();

        // Duplicated items are overridden
        for (FridgeItem item : items)
            fridgeItems.put(item.getItem(), item);
    }

    public synchronized void removeAll() {
        fridgeItems.clear();
    }

    public synchronized List<FridgeItem> get() {
        return new ArrayList<>(fridgeItems.values());
    }

    public synchronized FridgeItem getItem(String name) {
        return fridgeItems.get(name);
    }

    public synchronized boolean isEmpty() {
        return fridgeItems.isEmpty();
    }
}
