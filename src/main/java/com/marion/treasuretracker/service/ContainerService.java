package com.marion.treasuretracker.service;

import com.marion.treasuretracker.model.Container;
import com.marion.treasuretracker.model.Item;
import com.marion.treasuretracker.repository.ContainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContainerService {

    @Autowired
    ContainerRepository containerRepository;

    public void createContainer() {
        Container container = new Container();
        containerRepository.save(container);
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
