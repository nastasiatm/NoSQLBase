package ru.ifmo.model.coloumn;

import com.datastax.driver.core.DataType;
import lombok.Data;
import org.springframework.data.cassandra.mapping.CassandraType;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anastasia on 15.05.17.
 */

@Table
@Data
public class Apartment {
    @PrimaryKey
    private String id;
    private Integer number;
    private Integer rooms;
    private Integer square;
    private Integer price;
    private Integer floor;
    private Boolean sold;
    private String id_mongo;
}