package com.marion.treasuretracker.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.marion.treasuretracker.model.Item;
import com.marion.treasuretracker.model.Queries;
import com.marion.treasuretracker.model.Tag;
import com.marion.treasuretracker.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Service
public class TagService {

    @Autowired
    TagRepository tagRepository;

    @Autowired
    EntityManager entityManager;

    Tag createTag(String name) {
        Tag tag = new Tag();
        tag.setName(name);

        return tagRepository.save(tag);
    }

    Tag findTagByName(String name) {
        List<Tag> tags = queryItems(String.format(Queries.TAGS_BY_NAME, name));

        if (tags.size() > 0) {
            return tags.get(0);
        }

        return createTag(name);
    }

    public List<Tag> queryItems(String query) {
        Query nativeQuery = entityManager.createNativeQuery(query, Tag.class);
        List<Tag> tags = nativeQuery.getResultList();

        return tags;
    }
}
