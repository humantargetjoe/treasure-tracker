package com.marion.treasuretracker.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marion.treasuretracker.exceptions.InvalidItemException;
import com.marion.treasuretracker.model.Item;
import com.marion.treasuretracker.model.DataValidator;
import com.marion.treasuretracker.repository.ItemRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

import static com.marion.treasuretracker.model.Constants.poundsPerCoin;

@Service
public class ItemService {
    private static Log log = LogFactory.getLog(ItemService.class);

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    EntityManager entityManager;

    ObjectMapper objectMapper = new ObjectMapper();

    public Item createItem(Item item) throws InvalidItemException {
        switch (item.getItemType()) {
            case coin:
                return addCoins(item);
            case gem:
                return addGems(item);
            case jewelry:
                return addJewelry(item);
            default:
                return addItem(item);
        }
    }

    public Item updateItem(Item item) throws InvalidItemException {
        createItem(item);

        return item;
    }

    private Item addItem(Item item) throws InvalidItemException {
        if (!DataValidator.validateItem(item)) {
            throw new InvalidItemException();
        }
        return itemRepository.save(item);
    }

    Item addCoins(Item item) throws InvalidItemException {
        if (!DataValidator.validateCoins(item)) {
            throw new InvalidItemException();
        }
        item.setWeight(poundsPerCoin * item.getAmount());

        return itemRepository.save(item);
    }

    Item addGems(Item item) throws InvalidItemException {
        if (!DataValidator.validateGems(item)) {
            throw new InvalidItemException();
        }

        return itemRepository.save(item);
    }

    Item addJewelry(Item item) throws InvalidItemException {
        if (!DataValidator.validateJewelry(item)) {
            throw new InvalidItemException();
        }

        return itemRepository.save(item);
    }

    public List<Item> listItems() {
        Query nativeQuery = entityManager.createNativeQuery("SELECT * FROM item", Item.class);
        List<Item> items = nativeQuery.getResultList();

        try {
            for (Item item : items) {
                log.info(objectMapper.writeValueAsString(item));
            }
        } catch (JsonProcessingException jpEx) {
            log.error(jpEx.getMessage(), jpEx);
        }

        return items;
    }

    public int totalCoinValue() {
        Query nativeQuery = entityManager.createNativeQuery("SELECT * FROM item WHERE ITEMTYPE = 'coin';", Item.class);
        List<Item> items = nativeQuery.getResultList();

        float value = 0.0f;
        try {
            for (Item item : items) {
                log.info(objectMapper.writeValueAsString(item));
                switch (item.getItemSubType()) {
                    case platinum:
                        value += item.getAmount() * 10;
                        break;
                    case gold:
                        value += item.getAmount();
                        break;
                    case electrum:
                        value += item.getAmount() * 0.5;
                        break;
                    case silver:
                        value += item.getAmount() * 0.1;
                        break;
                    case copper:
                        value += item.getAmount() * 0.01;
                        break;
                    default:
                        break;
                }
            }
        } catch (JsonProcessingException jpEx) {
            log.error(jpEx.getMessage(), jpEx);
        }

        return (int) value;
    }
}
