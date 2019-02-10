package com.marion.treasuretracker.repository;

import com.marion.treasuretracker.model.Container;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContainerRepository extends CrudRepository<Container, Integer> {
}
