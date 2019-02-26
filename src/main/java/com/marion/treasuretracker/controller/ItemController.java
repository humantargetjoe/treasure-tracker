package com.marion.treasuretracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marion.treasuretracker.model.Item;
import com.marion.treasuretracker.model.ItemSubType;
import com.marion.treasuretracker.model.ItemType;
import com.marion.treasuretracker.service.ItemService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller()
public class ItemController {
    private static Log log = LogFactory.getLog(ItemController.class);
    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    ItemService itemService;

    @RequestMapping(value = "/list-items", method = RequestMethod.GET)
    public String listItems(ModelMap model) {
        model.addAttribute("items", itemService.listItems());
        return "list-items";
    }

    @RequestMapping(value = "/add-item", method = RequestMethod.GET)
    public String addItem(ModelMap model) {
        model.addAttribute("item", new Item());
        model.addAttribute("itemTypes", ItemType.values());
        model.addAttribute("itemSubTypes", ItemSubType.values());
        return "add-item";
    }

    @RequestMapping(value = "/edit-item/{id}", method = RequestMethod.GET)
    public String editItem(@PathVariable Integer id, ModelMap model) {
        model.addAttribute("item", itemService.findItemById(id));
        model.addAttribute("itemTypes", ItemType.values());
        model.addAttribute("itemSubTypes", ItemSubType.values());
        return "add-item";
    }

    @RequestMapping(value = "item/create", method = RequestMethod.POST)
    public String saveProduct(Item item) throws Exception {
        log.info(objectMapper.writeValueAsString(item));
        itemService.createItem(item);
        return "redirect:/list-items";
    }

    @RequestMapping(value = "/api/item", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> createItem(@RequestBody Item item) {
        Item result;
        try {
            result = itemService.createItem(item);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/item/{id}", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<?> updateItem(@PathVariable(name = "id") String id, @RequestBody Item item) {
        Item result;
        try {
            result = itemService.updateItem(item);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/item", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> queryItems(@RequestParam("query") String query) {
        List<Item> result;
        try {
            if (StringUtils.isEmpty(query)) {
                result = itemService.listItems();
            } else {
                result = itemService.queryItems(query);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/item/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> findItemById(@PathVariable(name = "id") String id) {
        Item result;
        try {
            result = itemService.findItemById(id);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
