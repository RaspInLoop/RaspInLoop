package org.raspinloop.web.pages.dao;

import java.util.List;

import org.raspinloop.web.pages.model.db.Model;
import org.springframework.data.repository.CrudRepository;

public interface ModelRepository extends CrudRepository<Model, Long> {

    List<Model> findByReference(String reference);
}