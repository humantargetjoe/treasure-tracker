package com.marion.treasuretracker.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marion.treasuretracker.exceptions.InvalidContainerException;
import com.marion.treasuretracker.exceptions.InvalidItemException;
import com.marion.treasuretracker.exceptions.InvalidSaleException;
import com.marion.treasuretracker.model.*;
import com.marion.treasuretracker.repository.ItemRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.*;

import static com.marion.treasuretracker.model.Constants.poundsPerCoin;

@Service
public class ItemService {
    private static Log log = LogFactory.getLog(ItemService.class);

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ChangeLogService changeLogService;

    @Autowired
    ContainerService containerService;

    @Autowired
    EntityManager entityManager;

    private ObjectMapper objectMapper = new ObjectMapper();

    private void validateAndSave(Item item) throws InvalidItemException {
        switch (item.getItemType()) {
            case coin:
                addCoins(item);
                mergeCoinsInContainer(item.getItemSubType(), item.getContainer());
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

    public Item createOrUpdateItem(Item item) throws InvalidItemException {
        if (item.getId() != null) {
            return updateItem(item);
        }
        return lootItem(item);
    }

    @Transactional
    public Item createItem(Item item) throws InvalidItemException {
        validateAndSave(item);
        adjustContainerWeight(item.getContainer());

        return item;
    }

    @Transactional
    public Item updateItem(Item item) throws InvalidItemException {
        if (item.getId() == null) {
            throw new InvalidItemException("Item does not exist.");
        }

        Item previous = findItemById(item.getId()).copy();

        validateAndSave(item);
        adjustContainerWeight(previous.getContainer());
        adjustContainerWeight(item.getContainer());
        changeLogService.recordUpdateItem(previous, item);

        return item;
    }

    @Transactional
    public Item moveItem(Item item, int amount) throws InvalidItemException {
        if (item.getId() == null) {
            throw new InvalidItemException("Item does not exist.");
        }
        Item previous = findItemById(item.getId()).copy();
        previous.setId(item.getId());

        Container destination = item.getContainer();
        item = previous.copy();
        item.setAmount(amount);
        item.setContainer(destination);

        if (previous.getAmount() < amount) {
            throw new InvalidItemException("Can't move more than the amount in the item.");
        }

        if (previous.getContainer().getId().equals(item.getContainer().getId())) {
            log.error("SAME CONTAINER");
            return previous;
        }

        previous.setAmount(previous.getAmount() - amount);
        validateAndSave(previous);
        validateAndSave(item);
        adjustContainerWeight(previous.getContainer());
        adjustContainerWeight(item.getContainer());

        changeLogService.recordMoveItemContainer(previous);
        changeLogService.recordMoveItemContainer(item);

        return item;
    }

    @Transactional
    public Item purchaseItem(Item item, int amount, ItemSubType itemSubType) throws InvalidItemException, InvalidSaleException {
        List<Item> coins = queryItems(
                String.format(
                        Queries.ITEMS_BY_TYPE_AND_SUBTYPE,
                        ItemType.coin, itemSubType));

        for (Item coin : coins) {
            if (coin.getAmount() >= amount) {
                spendCoins(coin, amount, String.format("purchase of %d '%s'", item.getAmount(), item.getName()));
                item = createItem(item);
                changeLogService.recordPurchasedItem(item, amount, ItemType.coin, itemSubType);

                return item;
            }
        }

        return null;
    }

    public Item lootItem(Item item) throws InvalidItemException {
        item = createItem(item);
        changeLogService.recordLootedItem(item);

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

    @Transactional
    public void sellItem(Item item) throws InvalidSaleException, InvalidItemException {
        Integer amount = item.getAmount();
        item = findItemById(item.getId());

        if (item.getItemType() == ItemType.coin) {
            throw new InvalidSaleException("Cannot sell coins");
        }

        if (item.getValue() == null) {
            throw new InvalidSaleException("Cannot sell item without value.");
        }

        // Can't sell more than you have
        if (item.getAmount() < amount) {
            amount = item.getAmount();
        }

        Float value = item.getValue() * amount;

        log.debug(String.format("Selling %s for %s gp", item.getName(), value));

        if (value % 10 > 0.5) {
            log.debug("Need to convert item to silver/copper as well as gold");
        }

        // Preferentially increment the first gold coins item found in the same container
        String query = String.format(Queries.GOLD_COINS_IN_CONTAINER, item.getContainer().getId());
        List<Item> goldCoins = queryItems(query);
        Item coins = null;
        if (goldCoins.size() > 0) {
            coins = goldCoins.get(0);
            coins.setAmount(coins.getAmount() + value.intValue());
            addCoins(coins);
        } else {
            coins = new Item();
            coins.setName("Gold Coins");
            coins.setItemType(ItemType.coin);
            coins.setItemSubType(ItemSubType.gold);
            coins.setAmount(value.intValue());
            coins.setContainer(item.getContainer());
        }
        addCoins(coins);

        item.setAmount(item.getAmount() - amount);
        item.setContainer(null);
        item.getTags().add("SOLD");
        itemRepository.save(item);

        changeLogService.recordSoldItem(item, amount, value);
    }

    public void spendCoins(Item item) throws InvalidSaleException, InvalidItemException {
        int amount = item.getAmount();
        String description = item.getDescription();

        item = findItemById(item.getId());

        spendCoins(item, amount, description);
    }

    public void spendCoins(Item item, int amount, String description) throws InvalidSaleException, InvalidItemException {
        if (item.getItemType() != ItemType.coin) {
            throw new InvalidSaleException("Can only spend coins.");
        }
        if (amount < 0) {
            throw new InvalidSaleException("Can't spend negative coins");
        }

        if (amount > item.getAmount()) {
            amount = item.getAmount();
        }

        item.setAmount(item.getAmount() - amount);
        addCoins(item);

        changeLogService.recordSpentItem(item, amount, description);
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
        return items;
    }

    public List<Item> listItems() {
        String query = "SELECT * FROM item WHERE AMOUNT > 0";
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

        Totals.Valuable gemTotal = totals.addTotal(ItemType.gem);
        Totals.Valuable jewelryTotal = totals.addTotal(ItemType.jewelry);
        Totals.Valuable otherTotal = totals.addTotal(ItemType.other);
        for (ItemSubType subType : Constants.GEMS) {
            Totals.Valuable valuable = totals.addGem(subType);
            totalAmountAndValue(valuable, ItemType.gem, subType);
            gemTotal.setCount(gemTotal.getCount() + valuable.getCount());
            gemTotal.setValue(gemTotal.getValue() + valuable.getValue());
        }

        for (ItemSubType subType : Constants.JEWELRY) {
            Totals.Valuable valuable = totals.addJewelry(subType);
            totalAmountAndValue(valuable, ItemType.jewelry, subType);
            jewelryTotal.setCount(jewelryTotal.getCount() + valuable.getCount());
            jewelryTotal.setValue(jewelryTotal.getValue() + valuable.getValue());
        }

        for (ItemSubType subType : Constants.OTHER) {
            Totals.Valuable valuable = totals.addOther(subType);
            totalAmountAndValue(valuable, ItemType.other, subType);
            otherTotal.setCount(otherTotal.getCount() + valuable.getCount());
            otherTotal.setValue(otherTotal.getValue() + valuable.getValue());
        }

        // Add miscellaneous things to totals, like weapons that are more decorative than functional
        Totals.Valuable valuable = totals.addOther(ItemSubType.none);
        valuable.setName("Weapons");
        totalAmountAndValue(valuable, ItemType.weapon, ItemSubType.none);
        otherTotal.setCount(otherTotal.getCount() + valuable.getCount());
        otherTotal.setValue(otherTotal.getValue() + valuable.getValue());

        totals.setGrandTotal(
                totals.getCoins().getTotal() +
                        gemTotal.getValue() +
                        jewelryTotal.getValue() +
                        otherTotal.getValue());
        return totals;
    }

    public Map<ItemSubType, Item> queryCoinsInContainer(Integer containerId) throws InvalidItemException {
        List<Item> coins = queryItems(String.format(Queries.ITEMS_BY_TYPE_IN_CONTAINER, ItemType.coin, containerId));

        Map<ItemSubType, Item> map = new HashMap<>();
        for (Item coin : coins) {
            map.put(coin.getItemSubType(), coin);
        }

        return map;
    }

    public Item convertCoinDenomination(Item item, ItemSubType itemSubType, int amount) throws InvalidItemException {
        item = findItemById(item.getId());

        if (item.getItemType() != ItemType.coin || item.getItemSubType() == itemSubType) {
            return item;
        }

        float adjustedAmount = convertToGold(item.getItemSubType(), amount);

        // Trying to convert more than is there
        if (adjustedAmount > item.getValue()) {
            throw new InvalidItemException("Cannot exchange, inadequate value.");
        }

        adjustedAmount = convertFromGold(itemSubType, adjustedAmount);

        // Not enough value to exchange to
        if (adjustedAmount < 1.0f) {
            throw new InvalidItemException("Cannot exchange, fractional value.");
        }

        item.setAmount(item.getAmount() - amount);

        Item coin = new Item();
        coin.setName(String.format("%s Coins", itemSubType.name()));
        coin.setItemType(ItemType.coin);
        coin.setItemSubType(itemSubType);
        coin.setSource(String.format("Changed from %d %s coins", amount, item.getItemSubType()));
        coin.setContainer(item.getContainer());
        coin.setAmount((int) adjustedAmount);

        updateItem(item);
        coin = createItem(coin);

        changeLogService.recordExchangeCoins(coin, item.getItemSubType(), amount);

        return coin;
    }

    private void mergeCoinsInContainer(ItemSubType itemSubType, Container container) throws InvalidItemException {
        List<Item> results = queryItems(String.format(Queries.ITEMS_BY_TYPE_AND_SUBTYPE_IN_CONTAINER, ItemType.coin, itemSubType, container.getId()));

        if (results.size() <= 1) {
            return;
        }

        int amount = 0;
        for (int i = 1; i < results.size(); ++i) {
            Item item = results.get(i);
            amount += item.getAmount();

            item.setAmount(0);
            item.setWeight(0f);
            item.setValue(0f);
            itemRepository.save(item);
        }

        Item baseCoin = results.get(0);
        baseCoin.setAmount(baseCoin.getAmount() + amount);
        addCoins(baseCoin);
    }

    private void totalAmountAndValue(Totals.Valuable valuable, ItemType itemType, ItemSubType itemSubType) {
        int total = 0;
        Float value = 0f;
        List<Item> items = queryItems(String.format(Queries.ITEMS_BY_TYPE_AND_SUBTYPE, itemType, itemSubType));
        for (Item item : items) {
            // Magic rings, etc., shouldn't show up here despite being jewelry
            if (item.getValue() != null) {
                total += item.getAmount();
                value += item.getAmount() * item.getValue();
                for (int i = 0; i < item.getAmount(); ++i) {
                    valuable.getDenominations().add(item.getValue());
                }
            }
        }

        valuable.setCount(total);
        valuable.setValue(value);
    }

    private int totalCoinAmount(ItemSubType itemSubType) {
        int total = 0;
        List<Item> items = queryItems(String.format(Queries.ITEMS_BY_TYPE_AND_SUBTYPE, ItemType.coin, itemSubType));
        for (Item item : items) {
            total += item.getAmount();
        }
        return total;
    }

    float totalCoinValueInGold() {
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

    private static float convertToGold(ItemSubType subType, float amount) {
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

    private static float convertFromGold(ItemSubType subType, float amount) {
        switch (subType) {
            case platinum:
                return amount / Constants.goldPerPlatinum;
            case electrum:
                return amount / Constants.goldPerElectrum;
            case silver:
                return amount / Constants.goldPerSilver;
            case copper:
                return amount / Constants.goldPerCopper;
        }
        return amount;
    }

    private void adjustContainerWeight(Container container) {
        List<Item> items = queryItems(String.format(Queries.ITEMS_IN_CONTAINER, container.getId()));
        Float weight = 0f;
        for (Item itemInList : items) {
            if (itemInList.getWeight() != null) {
                weight += itemInList.getWeight();
            }
            log.debug("Weight: " + weight);
        }

        if (container.getMaximumWeight() < weight) {
            log.error(
                    String.format("Cannot move item to %s, exceeds maximum weight (%1.01f/%1.01f).",
                            container.getName(),
                            weight,
                            container.getMaximumWeight()));
        }

        container.setCurrentWeight(weight);
        log.debug("Updating " + container.getName() + " to weight " + weight);
        try {
            containerService.updateContainer(container);
        } catch (InvalidContainerException icEx) {
            log.error(icEx.getMessage(), icEx);
        }
    }

}
