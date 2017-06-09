package ru.ifmo.model.graph;

import lombok.Data;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.io.Serializable;

/**
 * Created by anastasia on 15.05.17.
 */

@Data
@NodeEntity
public class Group implements Serializable{
    @GraphId
    private Long graphId;
    private String typeOfWork;
    private Integer complete;

    @Relationship(type = "BUILDING_AT")
    private Building building;
}
