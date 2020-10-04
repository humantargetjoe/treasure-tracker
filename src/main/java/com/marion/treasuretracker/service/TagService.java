package com.marion.treasuretracker.service;

import com.marion.treasuretracker.model.Item;
import com.marion.treasuretracker.model.ItemTag;
import com.marion.treasuretracker.model.Queries;
import com.marion.treasuretracker.model.Tag;
import com.marion.treasuretracker.repository.ItemTagRepository;
import com.marion.treasuretracker.repository.TagRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TagService {
    private static Log log = LogFactory.getLog(TagService.class);

    @Autowired
    TagRepository tagRepository;

    @Autowired
    EntityManager entityManager;

    @Autowired
    ItemTagRepository itemTagRepository;

    public Tag createTag(String tagName) {
        Tag tag = findTagByName(tagName);
        if (tag == null) {
            tag = new Tag();
            tag.setName(tagName);
            tag = createTag(tag);
        }
        return tag;
    }

    public Tag tagItem(String tagName, Item item) {
        Tag tag = createTag(tagName);
        tag = tagItem(tag, item);
        log.info(String.format("Tagging Item %s with %s (%d)", item.getName(), tag.getName(), tag.getId()));
        return tag;
    }

    public Tag tagItem(Tag tag, Item item) {
        ItemTag itemTag = new ItemTag();
        itemTag.setTag(tag);
        itemTag.setItem(item);
        itemTagRepository.save(itemTag);

        return tag;
    }

    public Tag findTagByName(String tagName) {
        List<Tag> tags =  queryTag(String.format(Queries.TAG_BY_NAME, tagName));

        if (tags.size() > 0) {
            return tags.get(0);
        }
        return null;
    }

    public List<Tag> findTagsByItem(Item item) {
        String query = String.format(Queries.ITEMTAGS_BY_ITEM, item.getId());
        List<ItemTag> itemTags = queryItemTag(query);

        Set<Integer> idSet = new HashSet<>();
        for (ItemTag itemTag: itemTags) {
            idSet.add(itemTag.getTag().getId());
        }

        query = String.format(Queries.TAGS_WHERE_ID_IN, StringUtils.join(idSet, ","));

        List<Tag> tags = queryTag(query);

        return tags;
    }

    protected List<ItemTag> queryItemTag(String query) {
        Query nativeQuery = entityManager.createNativeQuery(query, ItemTag.class);
        List<ItemTag> itemTags = nativeQuery.getResultList();

        return itemTags;
    }

    private List<Tag> queryTag(String query) {
        Query nativeQuery = entityManager.createNativeQuery(query, Tag.class);
        List<Tag> tags = nativeQuery.getResultList();

        return tags;
    }

    private Tag createTag(Tag tag) {
        return tagRepository.save(tag);
    }
}
