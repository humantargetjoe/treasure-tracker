package com.marion.treasuretracker.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marion.treasuretracker.model.Item;
import com.marion.treasuretracker.model.Queries;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QueryService {

    private static Log log = LogFactory.getLog(QueryService.class);
    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    ItemService itemService;

    @Autowired
    ContainerService containerService;

    public List<Item> queryItemsInContainer(Integer containerId) {
        return itemService.queryItems(String.format(Queries.ITEMS_IN_CONTAINER, containerId));
    }
}
