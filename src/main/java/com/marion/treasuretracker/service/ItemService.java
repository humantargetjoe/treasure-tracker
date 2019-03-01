package com.marion.treasuretracker.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marion.treasuretracker.exceptions.InvalidItemException;
import com.marion.treasuretracker.model.*;
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

    private ObjectMapper objectMapper = new ObjectMapper();

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
        changeLogService.recordAcquiredItem(item);

        return item;
    }

    public Item updateItem(Item item) throws InvalidItemException {
        if (item.getId() == null) {
            throw new InvalidItemException("Item does not exist.");
        }

        Item previous = findItemById(item.getId()).copy();

        validateAndSave(item);
        changeLogService.recordUpdateItem(previous, item);

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
        item.setValue(convertToGold(item.getItemSubType(), item.getAmount()));
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

    public Totals collectTotals() {
        Totals totals = new Totals();
        totals.getCoins().setTotal(totalCoinValueInGold());
        totals.getCoins().setCopper(totalCoinAmount(ItemSubType.copper));
        totals.getCoins().setSilver(totalCoinAmount(ItemSubType.silver));
        totals.getCoins().setElectrum(totalCoinAmount(ItemSubType.electrum));
        totals.getCoins().setGold(totalCoinAmount(ItemSubType.gold));
        totals.getCoins().setPlatinum(totalCoinAmount(ItemSubType.platinum));

        for(ItemSubType subType: Constants.GEMS) {
            totalAmountAndValue(totals.addGem(subType), ItemType.gem, subType);
        }

        for(ItemSubType subType: Constants.JEWELRY) {
            totalAmountAndValue(totals.addJewelry(subType), ItemType.jewelry, subType);
        }

        return totals;
    }

    private void totalAmountAndValue(Totals.Valuable valuable, ItemType itemType, ItemSubType itemSubType) {
        int total = 0;
        Float value = 0f;
        List<Item> items = queryItems(String.format(Queries.ITEMS_BY_TYPE_AND_SUBTYPE, itemType, itemSubType));
        for (Item item : items) {
            total += item.getAmount();
            value += item.getValue();
            for (int i = 0; i < item.getAmount(); ++i) {
                valuable.getDenominations().add(item.getValue());
            }
        }

        valuable.setCount(total);
        valuable.setValue(value);
    }

    public int totalCoinAmount(ItemSubType itemSubType) {
        int total = 0;
        List<Item> items = queryItems(String.format(Queries.ITEMS_BY_TYPE_AND_SUBTYPE, ItemType.coin, itemSubType));
        for (Item item : items) {
            total += item.getAmount();
        }
        return total;
    }

    public float totalCoinValueInGold() {
        List<Item> items = queryItems(String.format(Queries.ITEMS_BY_TYPE, ItemType.coin));

        float value = 0.0f;
        try {
            for (Item item : items) {
                log.info(objectMapper.writeValueAsString(item));
                value += convertToGold(item.getItemSubType(), item.getAmount());
            }
        } catch (JsonProcessingException jpEx) {
            log.error(jpEx.getMessage(), jpEx);
        }

        return value;
    }

    private static float convertToGold(ItemSubType subType, int amount) {
        switch (subType) {
            case platinum:
                return amount * Constants.goldPerPlatinum;
            case electrum:
                return amount * Constants.goldPerElectrum;
            case silver:
                return amount * Constants.goldPerSilver;
            case copper:
                return amount * Constants.goldPerCopper;
        }
        return amount;
    }
}
