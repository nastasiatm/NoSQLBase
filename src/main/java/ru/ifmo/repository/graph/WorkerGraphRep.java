package ru.ifmo.repository.graph;

import org.springframework.data.neo4j.repository.GraphRepository;
import ru.ifmo.model.graph.Worker;

/**
 * Created by anastasia on 15.05.17.
 */
public interface WorkerGraphRep extends GraphRepository<Worker> {

}
