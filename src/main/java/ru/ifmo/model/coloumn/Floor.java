package ru.ifmo.model.coloumn;

import lombok.Data;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

/**
 * Created by anastasia on 15.05.17.
 */
@Table
@Data
public class Floor {
    @PrimaryKey
    private String id;
    private Integer number;
    private String idBuildFrom4;

}
