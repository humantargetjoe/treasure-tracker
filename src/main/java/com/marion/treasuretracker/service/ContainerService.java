package com.marion.treasuretracker.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marion.treasuretracker.exceptions.InvalidContainerException;
import com.marion.treasuretracker.exceptions.InvalidItemException;
import com.marion.treasuretracker.model.Container;
import com.marion.treasuretracker.model.DataValidator;
import com.marion.treasuretracker.model.Item;
import com.marion.treasuretracker.repository.ContainerRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.omg.CORBA.DynAnyPackage.Invalid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Service
public class ContainerService {
    private static Log log = LogFactory.getLog(ContainerService.class);

    @Autowired
    ContainerRepository containerRepository;

    @Autowired
    ItemService itemService;

    @Autowired
    ChangeLogService changeLogService;

    @Autowired
    EntityManager entityManager;

    ObjectMapper objectMapper = new ObjectMapper();

    public Container createContainer(Container container) throws InvalidContainerException {
        if (!DataValidator.validateContainer(container)) {
            throw new InvalidContainerException();
        }

        containerRepository.save(container);
        changeLogService.recordAcquiredContainer(container);

        return container;
    }

    public Container updateContainer(Container container) throws InvalidContainerException {
        if (!DataValidator.validateContainer(container)) {
            throw new InvalidContainerException();
        }

        Container previous = findContainerById(container.getId());
        containerRepository.save(container);
        changeLogService.recordUpdateContainer(previous, container);

        return container;
    }

    public Container findContainerById(String id) {
        return findContainerById(Integer.parseInt(id));
    }

    public Container findContainerById(Integer id) {
        Optional<Container> result = containerRepository.findById(id);

        if (result.isPresent()) {
            return result.get();
        }
        return null;
    }

    public List<Container> queryContainers(String query) {
        Query nativeQuery = entityManager.createNativeQuery(query, Container.class);
        List<Container> containers = nativeQuery.getResultList();

        try {
            for (Container container : containers) {
                log.info(objectMapper.writeValueAsString(container));
            }
        } catch (JsonProcessingException jpEx) {
            log.error(jpEx.getMessage(), jpEx);
        }

        return containers;
    }

    public List<Container> listContainers() {
        String query = "SELECT * FROM container";
        return queryContainers(query);
    }

    public void addItemToContainer(Container container, Integer item) throws Exception {
        addItemToContainer(container, itemService.findItemById(item));
    }

    private void addItemToContainer(Container container, Item item) throws Exception {
        container.getItems().add(item);
        containerRepository.save(container);
        changeLogService.recordMoveItemContainer("Added", item);
    }

    public void removeItemFromContainer(Container container, Integer item) throws Exception {
        removeItemFromContainer(container, itemService.findItemById(item));
    }

    private void removeItemFromContainer(Container container, Item item) {
        container.getItems().remove(item);
        containerRepository.save(container);
        changeLogService.recordMoveItemContainer("Removed", item);
    }
}
