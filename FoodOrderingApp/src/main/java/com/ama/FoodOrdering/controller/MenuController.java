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
    @PostMapping("/addItem/{admin_id}")
    public ResponseEntity<MenuItem> addMenuItem(@RequestBody MenuItem menuItem, @PathVariable("admin_id") Long admin_id){
        // The @RequestBody annotation converts the JSON to a MenuItem object.

        try {
            MenuItem newMenuItem = menuService.addMenuItem(menuItem, admin_id);
            return new ResponseEntity<>(newMenuItem, HttpStatus.CREATED);
        } catch (ChangeSetPersister.NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // The method returns a ResponseEntity containing the newly created MenuItem
        // and a 201 Created status code, which tells the client that the request was successful
        // and a new resource was created.
    }

    @DeleteMapping("/delete/{id}/{admin_id}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable("id") Long id, @PathVariable("admin_id") Long admin_id) throws ChangeSetPersister.NotFoundException {
        menuService.deleteMenuItem(id, admin_id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/addMenu/{admin_id}")
    public ResponseEntity<List<MenuItem>> addMenu(@RequestBody List<MenuItem> menuItems, @PathVariable("admin_id") Long admin_id) {
        try {
            List<MenuItem> newMenuItems = menuService.addMenu(menuItems, admin_id);
            return new ResponseEntity<>(newMenuItems, HttpStatus.CREATED);
        } catch (ChangeSetPersister.NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/dailyMenu")
    public ResponseEntity<List<MenuItem>> showDailyMenu() {
        List<MenuItem> newDailyMenu = menuService.showDailyMenu();
        return new ResponseEntity<List<MenuItem>>(newDailyMenu, HttpStatus.OK);
    }

    @GetMapping("/getDailySuggestion")
    public ResponseEntity<MenuItem> getDailySuggestion() {
        MenuItem newDailySuggestion = menuService.getDailySuggestion();
        return new ResponseEntity<MenuItem>(newDailySuggestion, HttpStatus.OK);
    }

    // might have to add @PutMapping to update or make changes to existing MenuItems or Menu, that is where I'll implement modifiedOn and modifiedBy
}
