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
@AllArgsConstructor
@NoArgsConstructor
public class WordNode{
    @Id
    @GeneratedValue()
    private long id;

    private String val;

    @Relationship(type = "释义", direction = Relationship.Direction.OUTGOING, value = "释义")
    public Set<TranslationNode> translationNodes;

    public void addTranslationNodes(TranslationNode translationNode){
        if (this.translationNodes == null){
            this.translationNodes = new HashSet<>();
        }
        translationNodes.add(translationNode);
    }

}
