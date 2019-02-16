package com.marion.treasuretracker.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marion.treasuretracker.model.Container;
import com.marion.treasuretracker.model.Item;
import com.marion.treasuretracker.repository.ContainerRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Service
public class ContainerService {
    private static Log log = LogFactory.getLog(ContainerService.class);

    @Autowired
    ContainerRepository containerRepository;

    @Autowired
    EntityManager entityManager;

    ObjectMapper objectMapper = new ObjectMapper();

    public void createContainer() {
        Container container = new Container();
        container.setName("Wooden Chest");
        container.setMaximumVolume(12f);
        container.setMaximumWeight(300f);
        container.setIsExtraDimensional(false);
        container.setWeight(25f);
        container.setHeight(2f);
        container.setWidth(2f);
        container.setLength(3f);
        containerRepository.save(container);
    }

    public List<Container> listContainers() {
        Query nativeQuery = entityManager.createNativeQuery("SELECT * FROM container", Container.class);
        List<Container> containers = nativeQuery.getResultList();

        try {
            for (Container item : containers) {
                log.info(objectMapper.writeValueAsString(item));
            }
        }
        catch (JsonProcessingException jpEx) {
            log.error(jpEx.getMessage(), jpEx);
        }

        return containers;
    }

    public void addItemToContainer(Container container, Item item) {
        container.getItems().add(item);
        containerRepository.save(container);
    }

    public void removeItemFromContainer(Container container, Item item) {
        container.getItems().remove(item);
        containerRepository.save(container);
    }
}
