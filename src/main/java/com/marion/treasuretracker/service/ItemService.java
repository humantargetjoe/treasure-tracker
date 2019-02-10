package com.marion.treasuretracker.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marion.treasuretracker.InvalidItemException;
import com.marion.treasuretracker.model.Item;
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

    public void createItem() {
        Item item = new Item();

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

    public int totalValueInCoins() {
        Query nativeQuery = entityManager.createNativeQuery("SELECT * FROM item;");
        List<Object[]> coins = nativeQuery.getResultList();

        for (Object[] a : coins) {
            for(Object o: a){
                if (o != null){
                    log.info(o.toString());
                }
            }
        }
        return 0;
    }
}
