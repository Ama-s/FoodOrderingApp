package com.ama.FoodOrdering.controller;


import com.ama.FoodOrdering.entities.MenuItem;
import com.ama.FoodOrdering.services.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu")
// this is the root url for menu

public class MenuController {
    @Autowired
    MenuService menuService;

    // this means when you go to the /add url, you add the details of the menuItem
    @PostMapping("/addItem")
    public ResponseEntity<MenuItem> addMenuItem(@RequestBody MenuItem menuItem) {
        // The @RequestBody annotation converts the JSON to a MenuItem object.

        MenuItem newMenuItem = menuService.addMenuItem(menuItem);
        return new ResponseEntity<>(newMenuItem, HttpStatus.CREATED);

        // The method returns a ResponseEntity containing the newly created MenuItem
        // and a 201 Created status code, which tells the client that the request was successful
        // and a new resource was created.
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable("id") String id){
        // had to change the data type of id from UUID to String, I was getting an error and couldn't use UUID because the method findById accepts only Strings
        try {
            menuService.deleteMenuItem(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ChangeSetPersister.NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/addMenu")
    public ResponseEntity<List<MenuItem>> addMenu(@RequestBody List<MenuItem> menuItems) {
        List<MenuItem> newMenuList = menuService.addMenu(menuItems);
        return new ResponseEntity<List<MenuItem>>(newMenuList, HttpStatus.CREATED);
    }

    @GetMapping("/dailyMenu")
    public ResponseEntity<List<MenuItem>> showDailyMenu() {
        List<MenuItem> newDailyMenu = menuService.showDailyMenu();
        return new ResponseEntity<List<MenuItem>>(newDailyMenu, HttpStatus.OK);
    }

    @GetMapping("/getMenuItem")
    public ResponseEntity<MenuItem> getDailySuggestion() {
        MenuItem newDailySuggestion = menuService.getDailySuggestion();
        return new ResponseEntity<MenuItem>(newDailySuggestion, HttpStatus.OK);
    }
}
