package com.marion.treasuretracker.controller;

import com.marion.treasuretracker.model.Item;
import com.marion.treasuretracker.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller()
public class ItemController {

    @Autowired
    ItemService itemService;

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
