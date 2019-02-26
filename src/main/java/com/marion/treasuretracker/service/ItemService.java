package com.marion.treasuretracker.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marion.treasuretracker.exceptions.InvalidItemException;
import com.marion.treasuretracker.model.DataValidator;
import com.marion.treasuretracker.model.Item;
import com.marion.treasuretracker.repository.ItemRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

import static com.marion.treasuretracker.model.Constants.poundsPerCoin;

@Service
public class ItemService {
    private static Log log = LogFactory.getLog(ItemService.class);

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ChangeLogService changeLogService;

    @Autowired
    EntityManager entityManager;

    ObjectMapper objectMapper = new ObjectMapper();

    private void validateAndSave(Item item) throws InvalidItemException {
        switch (item.getItemType()) {
            case coin:
                addCoins(item);
                break;

            case gem:
                addGems(item);
                break;

            case jewelry:
                addJewelry(item);
                break;

            default:
                addItem(item);
        }
    }

    public Item createItem(Item item) throws InvalidItemException {
        if (item.getId() != null) {
            return updateItem(item);
        }

        validateAndSave(item);
        changeLogService.recordAcquiredItem("Some description", item);

        return item;
    }

    public Item updateItem(Item item) throws InvalidItemException {
        if (item.getId() == null) {
            throw new InvalidItemException("Item does not exist.");
        }

        validateAndSave(item);
        changeLogService.recordUpdateItem("Some description", item);

        return item;
    }

    private void addItem(Item item) throws InvalidItemException {
        if (!DataValidator.validateItem(item)) {
            throw new InvalidItemException();
        }
        itemRepository.save(item);
    }

    void addCoins(Item item) throws InvalidItemException {
        if (!DataValidator.validateCoins(item)) {
            throw new InvalidItemException();
        }
        item.setWeight(poundsPerCoin * item.getAmount());

        itemRepository.save(item);
    }

    void addGems(Item item) throws InvalidItemException {
        if (!DataValidator.validateGems(item)) {
            throw new InvalidItemException();
        }

        itemRepository.save(item);
    }

    void addJewelry(Item item) throws InvalidItemException {
        if (!DataValidator.validateJewelry(item)) {
            throw new InvalidItemException();
        }

        itemRepository.save(item);
    }

    public Item findItemById(String id) {
        return findItemById(Integer.parseInt(id));
    }

    public Item findItemById(Integer id) {
        Optional<Item> result = itemRepository.findById(id);

        if (result.isPresent()) {
            return result.get();
        }
        return null;
    }

    public List<Item> queryItems(String query) {
        Query nativeQuery = entityManager.createNativeQuery(query, Item.class);
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

    public List<Item> listItems() {
        String query = "SELECT * FROM item";
        return queryItems(query);
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
