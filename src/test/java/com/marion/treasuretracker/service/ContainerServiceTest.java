package com.marion.treasuretracker.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ContainerServiceTest {
    private static Log log = LogFactory.getLog(ContainerServiceTest.class);

    @Autowired
    ContainerService containerService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void createContainer() {
        containerService.createContainer();
    }

    @Test
    public void listContainers() {
        containerService.listContainers();
    }
}