package com.project.entity.neo4j;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;

@Node
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TranslationNode{
    @Id
    @GeneratedValue()
    private long id;

    private String val;

    private String word;

    private String source;

    private int likes;

    @Relationship(value = "出自", direction = Relationship.Direction.OUTGOING, type = "出自")
    public Set<SourceNode> sourceNodes;

    public void addSourceNode(SourceNode sourceNode){
        if (sourceNodes == null){
            sourceNodes = new HashSet<>();
        }
        sourceNodes.add(sourceNode);
    }

}
