package com.marion.treasuretracker.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marion.treasuretracker.model.*;
import org.apache.commons.lang3.StringUtils;
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

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TagServiceTest {

    private static Log log = LogFactory.getLog(TagServiceTest.class);

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    ItemService itemService;

    @Autowired
    ContainerService containerService;

    @Autowired
    TagService tagService;

    @Rule
    public TestWatcher watcher = new TestWatcher() {
        public void starting(Description description) {
            log.info("Starting test " + description.getMethodName());
        }
    };

    @Before
    public void setUp() {
    }


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

    public Item createTestItem() throws Exception {
        Item item = new Item();
        item.setName("Longsword");
        item.setItemType(ItemType.weapon);
        item.setItemSubType(ItemSubType.none);
        item.setAmount(1);
        item.getTags().add("weapon");
        item.setContainer(containerService.createContainer(createTestContainer()));
        return itemService.createItem(item);
    }

    @Test
    public void testTagItem() throws Exception {
        Item item = createTestItem();

        tagService.tagItem("weapon", item);
        tagService.tagItem("nonmagical", item);

        Tag tag = tagService.findTagByName("weapon");
        log.info(objectMapper.writeValueAsString(tag));

        List<ItemTag> itemTags = tagService.queryItemTag("SELECT * FROM itemtag");
        for (ItemTag itemTag: itemTags) {
            Integer id = itemTag.getItem().getId();
            Item item1 = itemService.findItemById(id);
            Optional<Tag> tag1 = tagService.tagRepository.findById(itemTag.getTag().getId());
        }
    }

    @Test
    public void testFindTagsByItem() throws Exception {
        Item item = createTestItem();
        tagService.tagItem("nonmagical", item);

        List<Tag> tags = tagService.findTagsByItem(item);

        Assert.assertEquals("Incorrect number of Item", 2, tags.size());
    }

    @Test
    public void testItemTagMapping() throws Exception {
        Item item = createTestItem();
        Tag tag = tagService.findTagByName("weapon");

        String query = String.format(Queries.ITEMTAGS_BY_TAG, tag.getId());
        List<ItemTag> itemTags = tagService.queryItemTag(query);
        Assert.assertEquals("Incorrect number of ItemTag", 1, itemTags.size());

        Set<Integer> idSet = new HashSet<>();
        for (ItemTag itemTag: itemTags) {
            idSet.add(itemTag.getItem().getId());
        }

        query = String.format(Queries.ITEMS_WHERE_ID_IN, StringUtils.join(idSet, ","));
        List<Item> items = itemService.queryItems(query);
        Assert.assertEquals("Incorrect number of Item", 1, items.size());
    }
}