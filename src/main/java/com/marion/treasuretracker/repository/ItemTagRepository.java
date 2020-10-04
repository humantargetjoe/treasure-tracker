package com.marion.treasuretracker.repository;

import com.marion.treasuretracker.model.ItemTag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemTagRepository extends CrudRepository<ItemTag, Integer> {
}
