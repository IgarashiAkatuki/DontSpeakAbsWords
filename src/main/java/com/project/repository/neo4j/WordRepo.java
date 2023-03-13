package com.project.repository.neo4j;

import com.project.entity.neo4j.WordNode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WordRepo extends CrudRepository<WordNode,Long> {

    List<WordNode> queryAllByVal(String word);
}

