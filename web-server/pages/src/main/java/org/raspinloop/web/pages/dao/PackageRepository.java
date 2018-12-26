package org.raspinloop.web.pages.dao;

import java.util.List;

import org.raspinloop.web.pages.model.db.Package;
import org.springframework.data.repository.CrudRepository;

public interface PackageRepository extends CrudRepository<Package, Long> {

    List<Package> findByName(String name);
}