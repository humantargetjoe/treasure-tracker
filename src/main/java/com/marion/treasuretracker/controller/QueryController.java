package com.marion.treasuretracker.controller;

import com.marion.treasuretracker.model.ItemSubType;
import com.marion.treasuretracker.model.Totals;
import com.marion.treasuretracker.model.TreasureQuery;
import com.marion.treasuretracker.service.ContainerService;
import com.marion.treasuretracker.service.ItemService;
import com.marion.treasuretracker.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class QueryController {
    @Autowired
    ItemService itemService;

    @Autowired
    ContainerService containerService;

    @Autowired
    QueryService queryService;

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
        model.addAttribute("caption", treasureQuery.getQuery());
        return "list-items";
    }

    @RequestMapping(value = "/query/container/{id}/items", method = RequestMethod.GET)
    public String itemsInContainer(@PathVariable Integer id, ModelMap model) {
        model.addAttribute("caption", containerService.findContainerById(id).getName());
        model.addAttribute("items", queryService.queryItemsInContainer(id));
        model.addAttribute("containers", containerService.listContainers());
        return "list-items";
    }

    @RequestMapping(value = "/query/totals", method = RequestMethod.GET)
    public String itemsInContainer(ModelMap model) {
        model.addAttribute("totals", itemService.collectTotals());
        return "totals";
    }
}
