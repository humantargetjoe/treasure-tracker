package com.marion.treasuretracker.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marion.treasuretracker.model.Container;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ContainerServiceTest {
    private static Log log = LogFactory.getLog(ContainerServiceTest.class);

    @Autowired
    ContainerService containerService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testCreateContainer() throws Exception {
        Container container = new Container();
        container.setName("Wooden Chest");
        container.setDescription("Purchased in Hommlet.  Secured with a DC 20 Pad Lock.");
        container.setMaximumVolume(12f);
        container.setMaximumWeight(300f);
        container.setIsExtraDimensional(false);
        container.setWeight(25f);
        container.setHeight(2f);
        container.setWidth(2f);
        container.setLength(3f);

        containerService.createContainer(container);

        Assert.assertNotNull("Container did not get saved successfully", container.getId());
    }

    @Test
    public void testListContainers() throws Exception {
        Container container = new Container();
        container.setName("Bag of Holding");
        container.setDescription("Would you believe we destroyed it, then wished for it back from an Efreeti?");
        container.setMaximumWeight(500f);
        container.setMaximumVolume(64f);
        container.setIsExtraDimensional(true);
        container.setWeight(15f);
        container.setWidth(2f);
        container.setLength(2f);
        container.setHeight(2f);
        containerService.createContainer(container);

        List<Container> containers = containerService.listContainers();
        Assert.assertEquals("Number of containers incorrect", 1, containers.size());
        Assert.assertEquals("Container[0].name is incorrect", "Bag of Holding", containers.get(0).getName());
    }
}