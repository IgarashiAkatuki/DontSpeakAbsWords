package com.project.repository.neo4j;

import com.project.entity.neo4j.TranslationNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TranslationRepo extends Neo4jRepository<TranslationNode,Long> {

    List<TranslationNode> queryAllByVal(String translation);


    @Query("match (n)-[r*]-(m) WHERE n.translation = $val RETURN collect(n), collect(r), collect(m)")
    List<Map<String,Object>> generateGraph(String val);

}

