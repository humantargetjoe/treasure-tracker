package com.marion.treasuretracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marion.treasuretracker.model.Container;
import com.marion.treasuretracker.model.Container;
import com.marion.treasuretracker.service.ContainerService;
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

@Controller
public class ContainerController {
    private static Log log = LogFactory.getLog(ContainerController.class);
    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    ContainerService containerService;

    @RequestMapping(value = "/list-containers", method = RequestMethod.GET)
    public String listContainers(ModelMap model) {
        model.addAttribute("containers", containerService.listContainers());
        return "list-containers";
    }

    @RequestMapping(value = "/add-container", method = RequestMethod.GET)
    public String addContainer(ModelMap model) {
        model.addAttribute("container", new Container());
        return "add-container";
    }

    @RequestMapping(value = "/edit-container/{id}", method = RequestMethod.GET)
    public String editContainer(@PathVariable Integer id, ModelMap model) {
        model.addAttribute("container", containerService.findContainerById(id));
        return "add-container";
    }

    @RequestMapping(value = "container/create", method = RequestMethod.POST)
    public String saveProduct(Container container) throws Exception {
        log.info(objectMapper.writeValueAsString(container));
        containerService.createContainer(container);
        return "redirect:/list-containers";
    }

    @RequestMapping(value = "container/add/{id}", method = RequestMethod.POST)
    public String addItemToContainer(@PathVariable Integer id, Container container, ModelMap model) throws Exception {

        containerService.addItemToContainer(container, id);
        return "redirect:/list-containers";
    }

    @RequestMapping(value = "/api/container", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> createContainer(@RequestBody Container container) {
        Container result;
        try {
            result = containerService.createContainer(container);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/container/{id}", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<?> updateContainer(@PathVariable(name = "id") String id, @RequestBody Container container) {
        Container result;
        try {
            result = containerService.updateContainer(container);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/container", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> queryContainers(@RequestParam("query") String query) {
        List<Container> result;
        try {
            if (StringUtils.isEmpty(query)) {
                result = containerService.listContainers();
            } else {
                result = containerService.queryContainers(query);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/container/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> findContainerById(@PathVariable(name = "id") String id) {
        Container result;
        try {
            result = containerService.findContainerById(id);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
