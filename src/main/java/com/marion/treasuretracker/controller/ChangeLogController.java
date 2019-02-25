package com.marion.treasuretracker.controller;

import com.marion.treasuretracker.model.ChangeLog;
import com.marion.treasuretracker.model.Item;
import com.marion.treasuretracker.service.ChangeLogService;
import com.marion.treasuretracker.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller()
public class ChangeLogController {

    @Autowired
    ChangeLogService changeLogService;

    @RequestMapping(value = "/list-logs", method = RequestMethod.GET)
    public String listItems(ModelMap model) {
        model.addAttribute("logs", changeLogService.listChangeLog());
        return "list-logs";
    }

    @RequestMapping(value = "/api/log", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> queryItems(@RequestParam("query") String query) {
        List<ChangeLog> result;
        try {
            if (StringUtils.isEmpty(query)) {
                result = changeLogService.listChangeLog();
            } else {
                result = changeLogService.queryChangeLog(query);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
