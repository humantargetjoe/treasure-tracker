package com.marion.treasuretracker.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marion.treasuretracker.exceptions.InvalidItemException;
import com.marion.treasuretracker.model.Container;
import com.marion.treasuretracker.model.Item;
import com.marion.treasuretracker.model.ItemSubType;
import com.marion.treasuretracker.model.ItemType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ItemServiceTest {
    private static Log log = LogFactory.getLog(ItemServiceTest.class);

    @Autowired
    ItemService itemService;

    @Autowired
    ContainerService containerService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setUp() throws Exception {
    }

    @Rule
    public TestWatcher watcher = new TestWatcher() {
        public void starting(Description description) {
            log.info("Starting test " + description.getMethodName());
        }
    };

    private Container createTestContainer() throws Exception {
        Container container = new Container();
        container.setName("Box");
        container.setDescription("Testing Container.");
        container.setMaximumVolume(1f);
        container.setMaximumWeight(1f);
        container.setIsExtraDimensional(false);
        container.setWeight(1f);
        container.setHeight(1f);
        container.setWidth(1f);
        container.setLength(1f);

        return containerService.createContainer(container);
    }

    @Test
    public void testCreateItem() throws Exception {
        Item item = new Item();
        item.setName("Longsword");
        item.setItemType(ItemType.weapon);
        item.setItemSubType(ItemSubType.none);
        item.setAmount(1);
        Item result = itemService.createItem(item);

        Assert.assertEquals("Names don't match", item.getName(), result.getName());
        Assert.assertEquals("ID hasn't been updated", item.getId(), result.getId());
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
    public void testListItems() throws Exception {
        itemService.listItems();
    }


    @Test
    public void testSellItem() throws Exception {
        Container container = createTestContainer();

        Item item = new Item();
        item.setName("Art");
        item.setDescription("A beautiful painting");
        item.setItemType(ItemType.other);
        item.setItemSubType(ItemSubType.none);
        item.setAmount(1);
        item.setValue(100.0f);
        item.setContainer(container);
        item = itemService.createItem(item);

        itemService.sellItem(item);

        List<Item> items = itemService.listItems();

        Assert.assertEquals("Incorrect number of items", 2, items.size());
        Assert.assertEquals("Incorrect amount of items[0]", (int) 0, (int) items.get(0).getAmount());
        Assert.assertEquals("Incorrect amount of gold from sale", 100, items.get(1).getAmount(), 0.1);
    }

    @Test
    public void testTotalValueInCoins() throws Exception {
        Item silverCoins = new Item();
        silverCoins.setName("Silver Coin");
        silverCoins.setAmount(50);
        silverCoins.setItemType(ItemType.coin);
        silverCoins.setItemSubType(ItemSubType.silver);
        itemService.addCoins(silverCoins);
        float value = itemService.totalCoinValueInGold();

        Assert.assertEquals("Total Value in GP incorrect", 5f, value, 0.001);
    }
}