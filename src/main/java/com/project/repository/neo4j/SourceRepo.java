package com.project.repository.neo4j;

import com.project.entity.neo4j.SourceNode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SourceRepo extends CrudRepository<SourceNode,Long> {

    List<SourceNode> queryAllByVal(String source);


}
