package com.marion.treasuretracker.service;

import com.marion.treasuretracker.model.Item;
import com.marion.treasuretracker.model.ItemTag;
import com.marion.treasuretracker.model.Queries;
import com.marion.treasuretracker.model.Tag;
import com.marion.treasuretracker.repository.ItemTagRepository;
import com.marion.treasuretracker.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Service
public class ItemTagService {

    @Autowired
    ItemTagRepository itemTagRepository;

    @Autowired
    EntityManager entityManager;

    ItemTag createItemTag(Item item, Tag tag) {
        ItemTag itemTag = new ItemTag();
        itemTag.setItemId(item);
        itemTag.setTagId(tag);

        return itemTagRepository.save(itemTag);
    }

}
