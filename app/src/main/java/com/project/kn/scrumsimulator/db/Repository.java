package com.project.kn.scrumsimulator.db;

import com.project.kn.scrumsimulator.entity.AbstractObject;

import java.util.List;
import java.util.Optional;

public interface Repository<T extends AbstractObject> {
    Optional<T> findById(int id);
    List<T> findAll();
    T save(T t);
}
