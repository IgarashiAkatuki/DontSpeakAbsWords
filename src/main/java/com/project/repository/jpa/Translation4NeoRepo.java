package com.project.repository.jpa;

import com.project.entity.jpa.Translation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Translation4NeoRepo extends CrudRepository<Translation,Integer>, JpaRepository<Translation,Integer> {
}
