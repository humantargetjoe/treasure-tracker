package com.marion.treasuretracker.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marion.treasuretracker.InvalidItemException;
import com.marion.treasuretracker.model.Item;
import com.marion.treasuretracker.model.ItemType;
import com.marion.treasuretracker.model.ItemValidator;
import com.marion.treasuretracker.repository.ItemRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import java.util.List;

import static com.marion.treasuretracker.model.Constants.coinsPerPound;

@Service
public class ItemService {
    private static Log log = LogFactory.getLog(ItemService.class);

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    EntityManager entityManager;

    ObjectMapper objectMapper = new ObjectMapper();

    public void createItem() {
        Item item = new Item();
        item.setItemType(ItemType.weapon);
        item.setName("Longsword");
        item.setAmount(1);

        itemRepository.save(item);
    }

    public void editItem(Item item) {

    }

    public void addCoins(Item item) throws InvalidItemException {
        if (!ItemValidator.validateCoins(item)) {
            throw new InvalidItemException();
        }
        item.setWeight(coinsPerPound * item.getAmount());

        itemRepository.save(item);
    }

    public void addGems(Item item) throws InvalidItemException {
        if (!ItemValidator.validateGems(item)) {
            throw new InvalidItemException();
        }

        itemRepository.save(item);
    }

    public void addJewelry(Item item) throws InvalidItemException {
        if (!ItemValidator.validateJewelry(item)) {
            throw new InvalidItemException();
        }

        itemRepository.save(item);
    }

    public int totalValueInCoins() throws JsonProcessingException {
        Query nativeQuery = entityManager.createNativeQuery("SELECT * FROM item;", Item.class);
        List<Item> items = nativeQuery.getResultList();

        for (Item item : items) {
            log.info(objectMapper.writeValueAsString(item));

        }
        return 0;
    }
}
