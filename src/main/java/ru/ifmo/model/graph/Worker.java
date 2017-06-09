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
public class Worker implements Serializable{

        @GraphId
        private Long graphId;
        private String name;
        private String surname;
        private Integer old;
        private String passportData;
        private Boolean teamLeader;
        private Boolean registration;
        private Integer salary;


        @Relationship(type = "WORK_AT")
        private Group group;

        @Override
        public int hashCode() {
            return name.hashCode();
        }

    }
