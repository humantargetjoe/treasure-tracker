package com.marion.treasuretracker.controller;

import com.marion.treasuretracker.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ItemController {
    @Autowired
    ItemService itemService;

    @RequestMapping(value = "/list-items", method = RequestMethod.GET)
    public String listItems(ModelMap model) {
        model.put("items", itemService.listItems());
        return "list-items";
    }

    @RequestMapping(value = "/add-item", method = RequestMethod.GET)
    public String addItem(ModelMap model) {
        model.put("items", itemService.listItems());
        return "list-items";
    }

    @RequestMapping(value = "/update-item", method = RequestMethod.GET)
    public String updateItem(@RequestParam Long id, ModelMap model) {
        model.put("items", itemService.listItems());
        return "list-items";
    }

    @RequestMapping(value = "/delete-item", method = RequestMethod.GET)
    public String deleteItem(@RequestParam Long id, ModelMap model) {
        model.put("items", itemService.listItems());
        return "list-items";
    }
}
