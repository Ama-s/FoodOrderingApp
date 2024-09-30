package com.ama.FoodOrdering.services;

import com.ama.FoodOrdering.entities.MenuItem;

import java.util.List;

public interface MenuService {
    public List<MenuItem> showDailyMenu();

    public MenuItem getDailySuggestion();
}
