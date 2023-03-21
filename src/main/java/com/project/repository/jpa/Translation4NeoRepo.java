package com.project.repository.jpa;

import com.project.entity.jpa.Persistence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Translation4NeoRepo extends CrudRepository<Persistence,Integer>, JpaRepository<Persistence,Integer> {

    /*
    查找id大于给定值的数据
     */
    public List<Persistence> queryAllByIdGreaterThan(int id);
}
