package com.marion.treasuretracker.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marion.treasuretracker.InvalidItemException;
import com.marion.treasuretracker.model.Item;
import com.marion.treasuretracker.model.ItemSubType;
import com.marion.treasuretracker.model.ItemType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemServiceTest {
    private static Log log = LogFactory.getLog(ItemServiceTest.class);

    @Autowired
    ItemService itemService;

    ObjectMapper objectMapper =new ObjectMapper();

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testCreateItem() {
        itemService.createItem();
    }

    @Test
    public void testAddCoins() throws Exception {
        Item item = new Item();
        item.setItemType(ItemType.coin);
        item.setItemSubType(ItemSubType.gold);
        item.setAmount(11);
        item.setName("Gold Coins");
        item.setDescription("A suspicious mint, reminiscent of Iuz");
        item.setSource("M29");
        item.getTags().add("Moathouse");
        item.getTags().add("Room 29");
        item.getTags().add("Gnolls");

        log.info(objectMapper.writeValueAsString(item));

        itemService.addCoins(item);
    }

    @Test(expected = InvalidItemException.class)
    public void testAddCoins_InvalidSubtype() throws Exception {
        Item item = new Item();
        item.setItemType(ItemType.coin);
        item.setItemSubType(ItemSubType.coral);
        item.setAmount(11);
        item.setName("Coral Coins?");
        item.setDescription("A suspicious item that shouldn't be in the db");
        item.setSource("M29");
        item.getTags().add("Moathouse");
        item.getTags().add("Room 29");
        item.getTags().add("Gnolls");

        log.info(objectMapper.writeValueAsString(item));
        itemService.addCoins(item);
    }

    @Test
    public void testAddGems() throws Exception {
        Item item = new Item();
        item.setItemType(ItemType.gem);
        item.setItemSubType(ItemSubType.quartz);
        item.setAmount(1);
        item.setName("Blue Quartz");
        item.setDescription("Uncut lump of blue quartz");
        item.setSource("M29");
        item.getTags().add("Moathouse");
        item.getTags().add("Room 29");
        item.getTags().add("Gnolls");

        log.info(objectMapper.writeValueAsString(item));
        itemService.addGems(item);
    }

    @Test(expected = InvalidItemException.class)
    public void testAddGems_InvalidSubtype() throws Exception {
        Item item = new Item();
        item.setItemType(ItemType.gem);
        item.setItemSubType(ItemSubType.copper);
        item.setAmount(1);
        item.setName("Copper Gems?");
        item.setDescription("A suspicious item that shouldn't be in the db");
        item.setSource("M29");
        item.getTags().add("Moathouse");
        item.getTags().add("Room 29");
        item.getTags().add("Gnolls");

        log.info(objectMapper.writeValueAsString(item));
        itemService.addGems(item);
    }

    @Test
    public void testAddJewelry() throws Exception {
        Item item = new Item();
        item.setItemType(ItemType.jewelry);
        item.setItemSubType(ItemSubType.necklace);
        item.setAmount(1);
        item.setName("Silver Necklace with Chrysoberyl");
        item.setDescription("A Silver Necklace with 5 inset Crysoberyl Gems");
        item.setSource("M27");
        item.getTags().add("Moathouse");
        item.getTags().add("Room 27");
        item.getTags().add("Bugbears");

        log.info(objectMapper.writeValueAsString(item));
        itemService.addJewelry(item);
    }

    @Test(expected = InvalidItemException.class)
    public void testAddJewelry_InvalidSubtype() throws Exception {
        Item item = new Item();
        item.setItemType(ItemType.jewelry);
        item.setItemSubType(ItemSubType.zircon);
        item.setAmount(1);
        item.setName("Zircon Jewelry?");
        item.setDescription("A suspicious item that shouldn't be in the db");
        item.setSource("M27");
        item.getTags().add("Moathouse");
        item.getTags().add("Room 27");
        item.getTags().add("Bugbears");

        log.info(objectMapper.writeValueAsString(item));
        itemService.addJewelry(item);
    }

    @Test
    public void testTotalValueInCoins() throws Exception{
        testAddCoins();
        itemService.totalValueInCoins();
    }
}