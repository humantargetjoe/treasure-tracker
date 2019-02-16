package com.marion.treasuretracker.controller;

import com.marion.treasuretracker.service.ContainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ContainerController {

    @Autowired
    ContainerService containerService;

    @RequestMapping(value = "/list-containers", method = RequestMethod.GET)
    public String listItems(ModelMap model) {
        model.put("containers", containerService.listContainers());
        return "list-containers";
    }

    @RequestMapping(value = "/add-container", method = RequestMethod.GET)
    public String addItem(ModelMap model) {
        containerService.createContainer();
        model.put("containers", containerService.listContainers());
        return "list-containers";
    }
}
