package com.marion.treasuretracker.controller;

import com.marion.treasuretracker.model.TreasureQuery;
import com.marion.treasuretracker.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class QueryController {
    @Autowired
    ItemService itemService;

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public String showQueryPage(ModelMap model) {
        model.addAttribute("query", new TreasureQuery());
        return "query-db";
    }

    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public String submitQuery(ModelMap model, TreasureQuery treasureQuery) {
        // Process query & return container or items depending on query contents
        // need a service implementation to delegate

        model.addAttribute("items", itemService.queryItems(treasureQuery.getQuery()));
        return "list-items";
    }
}
