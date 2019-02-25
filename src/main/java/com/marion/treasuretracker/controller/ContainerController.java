package com.marion.treasuretracker.controller;

import com.marion.treasuretracker.model.Container;
import com.marion.treasuretracker.model.Container;
import com.marion.treasuretracker.service.ContainerService;
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

    @Autowired
    ContainerService containerService;

    @RequestMapping(value = "/list-containers", method = RequestMethod.GET)
    public String listItems(ModelMap model) {
        model.addAttribute("containers", containerService.listContainers());
        return "list-containers";
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
