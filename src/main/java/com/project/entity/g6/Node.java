package com.project.entity.g6;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Objects;

public class Node {
    private static int currId = 0;

    @JsonSerialize(using = com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class)
    private int id;

    @JsonProperty("class")
    private String nodeClass;

    private String label;

    public Node(String nodeClass, String label) {
        this.nodeClass = nodeClass;
        this.label = label;
    }
    {
        this.id = ++currId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNodeClass() {
        return nodeClass;
    }

    public void setNodeClass(String nodeClass) {
        this.nodeClass = nodeClass;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node nodeSet = (Node) o;
        return id == nodeSet.id && Objects.equals(nodeClass, nodeSet.nodeClass) && Objects.equals(label, nodeSet.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nodeClass, label);
    }

    @Override
    public String toString() {
        String s = null;
        try{
            ObjectMapper mapper = new ObjectMapper();
            s = mapper.writeValueAsString(this);
        }catch (Exception e){
            e.printStackTrace();
        }
        return s;
    }
}
