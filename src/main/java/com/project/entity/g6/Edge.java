package com.project.entity.g6;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Objects;

public class Edge {
    private static int currId = 100000;

    @JsonSerialize(using = com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class)
    private int id;

    @JsonSerialize(using = com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class)
    private int source;

    @JsonSerialize(using = com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class)
    private int target;

    private String label;

    {
        this.id = ++currId;
    }

    public Edge(int source, int target, String label) {
        this.source = source;
        this.target = target;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
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
        Edge edgeSet = (Edge) o;
        return id == edgeSet.id && source == edgeSet.source && target == edgeSet.target && Objects.equals(label, edgeSet.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, source, target, label);
    }

    @Override
    public String toString() {
        String s = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            s = mapper.writeValueAsString(this);
        }catch (Exception e){
            e.printStackTrace();
        }
        return s;
    }
}
