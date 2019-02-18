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

@Service
public class ContainerService {
    private static Log log = LogFactory.getLog(ContainerService.class);

    @Autowired
    ContainerRepository containerRepository;

    @Autowired
    EntityManager entityManager;

    ObjectMapper objectMapper = new ObjectMapper();

    public Container createContainer(Container container) throws InvalidContainerException {
        if(!DataValidator.validateContainer(container)) {
            throw new InvalidContainerException();        }

        return containerRepository.save(container);
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
