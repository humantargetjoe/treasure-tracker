package com.marion.treasuretracker.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marion.treasuretracker.exceptions.InvalidItemException;
import com.marion.treasuretracker.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
        return createTestContainer(1);
    }

    private Container createTestContainer(int i) throws Exception {
        Container container = new Container();
        container.setName("Container " + i);
        container.setDescription("Testing Container #" + i);
        container.setMaximumVolume(1f);
        container.setMaximumWeight(10f);
        container.setIsExtraDimensional(false);
        container.setCurrentWeight(0f);
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
        item.setContainer(containerService.createContainer(createTestContainer()));
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

        Assert.assertEquals("Incorrect number of items", 1, items.size());
//        Assert.assertEquals("Incorrect amount of items[0]", (int) 0, (int) items.get(0).getAmount());
        Assert.assertEquals("Incorrect amount of gold from sale", 100, items.get(0).getAmount(), 0.1);
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

    @Test
    public void testSpendCoins() throws Exception {
        Item goldCoins = new Item();
        goldCoins.setName("Gold Coins");
        goldCoins.setAmount(100);
        goldCoins.setItemType(ItemType.coin);
        goldCoins.setItemSubType(ItemSubType.gold);
        itemService.addCoins(goldCoins);

        float initialValue = itemService.totalCoinValueInGold();

        Item updatedItem = new Item();
        updatedItem.setId(goldCoins.getId());
        updatedItem.setDescription("Living expenses, one week, wealthy");
        updatedItem.setAmount(10);
        itemService.spendCoins(updatedItem);

        float updatedValue = itemService.totalCoinValueInGold();

        Assert.assertEquals("Total Value in GP incorrect", 90f, updatedValue, 0.001f);
    }

    @Test
    public void testMergeCoins() throws Exception {
        Container container = createTestContainer();

        Item goldCoins = new Item();
        goldCoins.setName("Gold Coins 1");
        goldCoins.setAmount(100);
        goldCoins.setItemType(ItemType.coin);
        goldCoins.setItemSubType(ItemSubType.gold);
        goldCoins.setContainer(container);
        itemService.createItem(goldCoins);

        goldCoins = itemService.findItemById(goldCoins.getId());

        Item moreGoldCoins = new Item();
        moreGoldCoins.setName("Gold Coins 2");
        moreGoldCoins.setAmount(15);
        moreGoldCoins.setItemType(ItemType.coin);
        moreGoldCoins.setItemSubType(ItemSubType.gold);
        moreGoldCoins.setContainer(container);
        itemService.createItem(moreGoldCoins);

        goldCoins = itemService.findItemById(goldCoins.getId());
        moreGoldCoins = itemService.findItemById(moreGoldCoins.getId());

        Assert.assertEquals(115, (long) goldCoins.getAmount());
        Assert.assertEquals(0, (long) moreGoldCoins.getAmount());
    }

    @Test
    public void testUpdateContainerWeight_CreateItem() throws Exception {
        Container container = createTestContainer();

        Item item = new Item();
        item.setName("Longsword");
        item.setItemType(ItemType.weapon);
        item.setItemSubType(ItemSubType.none);
        item.setAmount(1);
        item.setWeight(2.0f);
        item.setContainer(container);
        Item result = itemService.createItem(item);

        Assert.assertEquals("Names don't match", item.getName(), result.getName());
        Assert.assertEquals("ID hasn't been updated", item.getId(), result.getId());
        container = containerService.findContainerById(container.getId());
        Assert.assertEquals("", 2.0f, container.getCurrentWeight(), 0.001f);
    }

    @Test
    public void testUpdateContainerWeight_MoveItem() throws Exception {
        Container container1 = createTestContainer(1);
        Container container2 = createTestContainer(2);

        Item item = new Item();
        item.setName("Longsword");
        item.setItemType(ItemType.weapon);
        item.setItemSubType(ItemSubType.none);
        item.setAmount(1);
        item.setWeight(2.0f);
        item.setContainer(container1);
        Item result = itemService.createItem(item);

        Assert.assertEquals("Names don't match", item.getName(), result.getName());
        Assert.assertEquals("ID hasn't been updated", item.getId(), result.getId());
        container1 = containerService.findContainerById(container1.getId());
        container2 = containerService.findContainerById(container2.getId());
        Assert.assertEquals("Incorrect weight for container 1", 2.0f, container1.getCurrentWeight(), 0.001f);
        Assert.assertEquals("Incorrect weight for container 2", 0f, container2.getCurrentWeight(), 0.001f);

        result.setContainer(container2);
        itemService.updateItem(result);
        container1 = containerService.findContainerById(container1.getId());
        container2 = containerService.findContainerById(container2.getId());
        Assert.assertEquals("Incorrect weight for container 1", 0f, container1.getCurrentWeight(), 0.001f);
        Assert.assertEquals("Incorrect weight for container 2", 2.0f, container2.getCurrentWeight(), 0.001f);
    }

    @Test
    public void convertCoinDenomination_SilverToPlatinum() throws Exception {
        Container container = createTestContainer();
        Item item = new Item();
        item.setName("Silver Coins");
        item.setItemType(ItemType.coin);
        item.setItemSubType(ItemSubType.silver);
        item.setContainer(container);
        item.setAmount(1000);

        Item result = itemService.createItem(item);

        Item platinumCoins = itemService.convertCoinDenomination(result, ItemSubType.platinum, 500);
        item = itemService.findItemById(item.getId());

        Assert.assertEquals("Value incorrect for Silver", 500, (int) item.getAmount());
        Assert.assertEquals("Value incorrect for Platinum", 5, (int) platinumCoins.getAmount());
    }

    @Test
    public void convertCoinDenomination_CopperToGold() throws Exception {
        Container container = createTestContainer();
        Item item = new Item();
        item.setName("Copper Coins");
        item.setItemType(ItemType.coin);
        item.setItemSubType(ItemSubType.copper);
        item.setContainer(container);
        item.setAmount(1000);

        Item result = itemService.createItem(item);

        Item goldCoins = itemService.convertCoinDenomination(result, ItemSubType.gold, 1000);
        item = itemService.findItemById(item.getId());

        Assert.assertEquals("Value incorrect for Copper", 0, (int) item.getAmount());
        Assert.assertEquals("Value incorrect for Gold", 10, (int) goldCoins.getAmount());
    }

    @Test
    public void convertCoinDenomination_PlatinumToElectrum() throws Exception {
        Container container = createTestContainer();
        Item item = new Item();
        item.setName("Platinum Coins");
        item.setItemType(ItemType.coin);
        item.setItemSubType(ItemSubType.platinum);
        item.setContainer(container);
        item.setAmount(100);

        Item result = itemService.createItem(item);

        Item electrumCoins = itemService.convertCoinDenomination(result, ItemSubType.electrum, 50);
        item = itemService.findItemById(item.getId());

        Assert.assertEquals("Value incorrect for Platinum", 50, (int) item.getAmount());
        Assert.assertEquals("Value incorrect for Electrum", 2500, (int) electrumCoins.getAmount());
    }

    @Test(expected = InvalidItemException.class)
    public void convertCoinDenomination_NotEnoughToConvert() throws Exception {
        Container container = createTestContainer();
        Item item = new Item();
        item.setName("Copper Coins");
        item.setItemType(ItemType.coin);
        item.setItemSubType(ItemSubType.copper);
        item.setContainer(container);
        item.setAmount(100);

        Item result = itemService.createItem(item);

        Item platinumCoins = itemService.convertCoinDenomination(result, ItemSubType.platinum, 100);
    }

    @Test(expected = InvalidItemException.class)
    public void convertCoinDenomination_SpendMoreThanHave() throws Exception {
        Container container = createTestContainer();
        Item item = new Item();
        item.setName("Copper Coins");
        item.setItemType(ItemType.coin);
        item.setItemSubType(ItemSubType.copper);
        item.setContainer(container);
        item.setAmount(100);

        Item result = itemService.createItem(item);

        Item platinumCoins = itemService.convertCoinDenomination(result, ItemSubType.platinum, 10000);
    }

    @Test
    public void testPurchaseItem() throws Exception {
        Container container = createTestContainer();
        Item item = new Item();
        item.setName("Gold Coins");
        item.setItemType(ItemType.coin);
        item.setItemSubType(ItemSubType.gold);
        item.setContainer(container);
        item.setAmount(100);

        item = itemService.lootItem(item);

        Item purchase = new Item();
        purchase.setName("Gem");
        purchase.setDescription("A gem!");
        purchase.setItemType(ItemType.gem);
        purchase.setItemSubType(ItemSubType.garnet);
        purchase.setAmount(1);
        purchase.setContainer(container);
        purchase.setValue(50f);
        purchase = itemService.purchaseItem(purchase, 50, ItemSubType.gold);

        item = itemService.findItemById(item.getId());

        Assert.assertEquals("Value incorrect for Gold", 50, (int) item.getAmount());
        Assert.assertNotNull("Item not created", purchase);
        Assert.assertNotNull("Item not created", purchase.getId());
    }

    @Test
    public void testMoveItem() throws Exception {
        Container container1 = createTestContainer(1);
        Container container2 = createTestContainer(1);
        Item item = new Item();
        item.setName("Agate");
        item.setItemType(ItemType.gem);
        item.setItemSubType(ItemSubType.agate);
        item.setContainer(container1);
        item.setValue(10f);
        item.setAmount(100);

        item = itemService.createOrUpdateItem(item);

        Item modified = item.copy();
        modified.setId(item.getId());
        modified.setContainer(container2);

        Item result = itemService.moveItem(modified, 30);

        item = itemService.findItemById(item.getId());
        result = itemService.findItemById(result.getId());

        Assert.assertEquals("incorrect amount", 70, (int) item.getAmount());
        Assert.assertEquals("incorrect amount", 30, (int) result.getAmount());
        Assert.assertNotEquals("incorrect container", item.getContainer().getId(), result.getContainer().getId());
    }

    @Test
    public void testCoinTotalsByContainer() throws Exception {
        Container container1 = createTestContainer(1);
        Container container2 = createTestContainer(2);

        Item item1 = new Item();
        item1.setItemType(ItemType.coin);
        item1.setItemSubType(ItemSubType.gold);
        item1.setAmount(11);
        item1.setName("Gold Coins");
        item1.setContainer(container1);

        Item item2 = new Item();
        item2.setItemType(ItemType.coin);
        item2.setItemSubType(ItemSubType.gold);
        item2.setAmount(11);
        item2.setName("Gold Coins");
        item2.setContainer(container2);

        itemService.createItem(item1);
        itemService.createItem(item2);

        List<Total> totals = itemService.collectSortedTotals();

        Assert.assertEquals("incorrect amount", 11, (int) totals.get(0).getCoins().getGold());
        Assert.assertEquals("incorrect amount", 11, (int) totals.get(1).getCoins().getGold());

        log.info(objectMapper.writeValueAsString(totals.get(0)));
        log.info(objectMapper.writeValueAsString(totals.get(1)));

        Assert.assertEquals("incorrect amount", 11.0f, totals.get(0).getCoins().getTotal(), 0.01f);
        Assert.assertEquals("incorrect amount", 11.0f, totals.get(1).getCoins().getTotal(), 0.01f);
    }
}