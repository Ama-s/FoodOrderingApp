package com.ama.FoodOrdering.controller;


import com.ama.FoodOrdering.entities.MenuItem;
import com.ama.FoodOrdering.services.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/menu")
// this is the root url for menu

public class MenuController {
    @Autowired
    MenuService menuService;

    @PostMapping("/addItem")
    public ResponseEntity<MenuItem> addMenuItem(@RequestBody MenuItem menuItem){
        // The @RequestBody annotation converts the JSON to a MenuItem object.

        try {
            MenuItem newMenuItem = menuService.addMenuItem(menuItem);
            return new ResponseEntity<>(newMenuItem, HttpStatus.CREATED);
        } catch (ChangeSetPersister.NotFoundException | AccessDeniedException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // The method returns a ResponseEntity containing the newly created MenuItem
        // and a 201 Created status code, which tells the client that the request was successful
        // and a new resource was created.
    }

    @DeleteMapping("/delete/{menu_id}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable("menu_id") Long id) throws ChangeSetPersister.NotFoundException, AccessDeniedException {
        menuService.deleteMenuItem(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/update/{menu_id}")
    public ResponseEntity<MenuItem> updateMenuItem(@PathVariable("menu_id") Long menu_id,
                                                   @RequestBody Map<String, Object> updates) {
        try {
            MenuItem updatedMenuItem = menuService.updateMenuItem(menu_id,updates);
            return new ResponseEntity<>(updatedMenuItem, HttpStatus.OK);
        } catch (ChangeSetPersister.NotFoundException | AccessDeniedException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/addMenu")
    public ResponseEntity<List<MenuItem>> addMenu(@RequestBody List<MenuItem> menuItems) {
        try {
            List<MenuItem> newMenuItems = menuService.addMenu(menuItems);
            return new ResponseEntity<>(newMenuItems, HttpStatus.CREATED);
        } catch (ChangeSetPersister.NotFoundException | AccessDeniedException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/dailyMenu")
    public ResponseEntity<List<MenuItem>> showDailyMenu() {
        List<MenuItem> newDailyMenu = menuService.showDailyMenu();
        return new ResponseEntity<List<MenuItem>>(newDailyMenu, HttpStatus.OK);
    }

    @GetMapping("/dailySuggestion")
    public ResponseEntity<MenuItem> getDailySuggestion() {
        MenuItem newDailySuggestion = menuService.getDailySuggestion();
        return new ResponseEntity<MenuItem>(newDailySuggestion, HttpStatus.OK);
    }
}
