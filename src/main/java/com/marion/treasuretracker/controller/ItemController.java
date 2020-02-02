package com.marion.treasuretracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marion.treasuretracker.model.*;
import com.marion.treasuretracker.service.ContainerService;
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
import java.util.Map;

@Controller
public class ItemController {
    private static Log log = LogFactory.getLog(ItemController.class);
    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    ItemService itemService;

    @Autowired
    ContainerService containerService;

    @RequestMapping(value = "/list-items", method = RequestMethod.GET)
    public String listItems(ModelMap model) {
        model.addAttribute("items", itemService.listItems());
        model.addAttribute("containers", containerService.listContainers());
        return "list-items";
    }

    @RequestMapping(value = "/add-item", method = RequestMethod.GET)
    public String addItem(ModelMap model) {
        model.addAttribute("caption", "New Item");
        model.addAttribute("item", new Item());
        model.addAttribute("itemTypes", ItemType.values());
        model.addAttribute("itemSubTypes", ItemSubType.values());
        model.addAttribute("containers", containerService.listContainers());
        return "create-item";
    }

    @RequestMapping(value = "/buy-item", method = RequestMethod.GET)
    public String buyItem(ModelMap model) {
        model.addAttribute("caption", "New Item");
        model.addAttribute("wrapper", new ItemBuyWrapper(new Item()));
        model.addAttribute("itemTypes", ItemType.values());
        model.addAttribute("itemSubTypes", ItemSubType.values());
        model.addAttribute("containers", containerService.listContainers());
        return "buy-item";
    }

    @RequestMapping(value = "/move-item/{id}", method = RequestMethod.GET)
    public String moveItem(@PathVariable(name = "id") String id, ModelMap model) {
        model.addAttribute("caption", "Move Item");
        model.addAttribute("wrapper", new ItemMoveWrapper(itemService.findItemById(id)));
        model.addAttribute("containers", containerService.listContainers());
        return "move-item";
    }

    @RequestMapping(value = "/exchange-item/{id}", method = RequestMethod.GET)
    public String exchangeItem(@PathVariable(name = "id") String id, ModelMap model) {
        model.addAttribute("caption", "Exchange Item");
        model.addAttribute("wrapper", new ItemBuyWrapper(itemService.findItemById(id)));
        model.addAttribute("itemTypes", ItemType.values());
        model.addAttribute("itemSubTypes", Constants.Coins);
        model.addAttribute("containers", containerService.listContainers());
        return "exchange-item";
    }

    @RequestMapping(value = "/view-item/{id}", method = RequestMethod.GET)
    public String viewItem(@PathVariable(name = "id") String id, ModelMap model) {
        model.addAttribute("caption", "View Item");
        model.addAttribute("item", itemService.findItemById(id));
        return "view-item";
    }

    @RequestMapping(value = "/edit-item/{id}", method = RequestMethod.GET)
    public String editItem(@PathVariable Integer id, ModelMap model) {
        model.addAttribute("caption", "Update Item");
        model.addAttribute("item", itemService.findItemById(id));
        model.addAttribute("itemTypes", ItemType.values());
        model.addAttribute("itemSubTypes", ItemSubType.values());
        model.addAttribute("containers", containerService.listContainers());

        return "create-item";
    }

    @RequestMapping(value = "item/create", method = RequestMethod.POST)
    public String createItem(Item item) throws Exception {
        log.info(objectMapper.writeValueAsString(item));
        itemService.createOrUpdateItem(item);
        return "redirect:/list-items";
    }

    @RequestMapping(value = "item/purchase", method = RequestMethod.POST)
    public String purchaseItem(ItemBuyWrapper itemBuyWrapper) throws Exception {
        log.info(objectMapper.writeValueAsString(itemBuyWrapper));
        itemService.purchaseItem(itemBuyWrapper.getItem(), itemBuyWrapper.getAmount(), itemBuyWrapper.getItemSubType());
        return "redirect:/list-items";
    }

    @RequestMapping(value = "item/exchange", method = RequestMethod.POST)
    public String exchangeItem(ItemBuyWrapper itemBuyWrapper) throws Exception {
        log.info(objectMapper.writeValueAsString(itemBuyWrapper));
        itemService.convertCoinDenomination(itemBuyWrapper.getItem(), itemBuyWrapper.getItemSubType(), itemBuyWrapper.getAmount());
        return "redirect:/list-items";
    }

    @RequestMapping(value = "item/sell", method = RequestMethod.POST)
    public String sellItem(Item item) throws Exception {
        log.info(objectMapper.writeValueAsString(item));
        itemService.sellItem(item);
        return "redirect:/list-items";
    }

    @RequestMapping(value = "/item/move", method = RequestMethod.POST)
    public String exchangeItem(ItemMoveWrapper wrapper) throws Exception {
        log.info(objectMapper.writeValueAsString(wrapper));
        itemService.moveItem(wrapper.getItem(), wrapper.getAmount());
        return "redirect:/list-items";
    }

    @RequestMapping(value = "spend-item/{id}", method = RequestMethod.GET)
    public String displaySpend(@PathVariable Integer id, ModelMap model) throws Exception {
        String caption = "Spend Coins from " + containerService.findContainerById(id).getName();

        Map<ItemSubType, Item> coins = itemService.queryCoinsInContainer(id);
        for (Map.Entry<ItemSubType,Item> entry: coins.entrySet()) {
            entry.getValue().setDescription("");
        }
        model.addAttribute("caption", caption);
        model.addAttribute("platinum", coins.get(ItemSubType.platinum));
        model.addAttribute("gold", coins.get(ItemSubType.gold));
        model.addAttribute("electrum", coins.get(ItemSubType.electrum));
        model.addAttribute("silver", coins.get(ItemSubType.silver));
        model.addAttribute("copper", coins.get(ItemSubType.copper));
        return "spend-item";
    }

    @RequestMapping(value = "item/spend", method = RequestMethod.POST)
    public String spendItems(Item item) throws Exception {
        log.info("Received update for coins for spending");
        log.info(objectMapper.writeValueAsString(item));
        itemService.spendCoins(item);
        return "redirect:/list-items";
    }

    @RequestMapping(value = "/api/item", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> createItemApi(@RequestBody Item item) {
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
