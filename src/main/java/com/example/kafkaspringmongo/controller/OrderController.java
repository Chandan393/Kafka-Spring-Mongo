package com.example.kafkaspringmongo.controller;

import com.example.kafkaspringmongo.kafka.EventPublisher;
import com.example.kafkaspringmongo.model.OrderEvent;
import com.example.kafkaspringmongo.repo.OrderRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderRepo repo;
    private final EventPublisher publisher;
    private final ObjectMapper mapper = new ObjectMapper();

    public OrderController(OrderRepo repo, EventPublisher publisher) {
        this.repo = repo;
        this.publisher = publisher;
    }

    @GetMapping
    public Collection<OrderEvent> all() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderEvent> get(@PathVariable String id) {
        OrderEvent o = repo.findById(id);
        if (o == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(o);
    }

    @PostMapping
    public ResponseEntity<OrderEvent> create(@RequestBody OrderEvent input) throws JsonProcessingException {
        String id = UUID.randomUUID().toString();
        input.setId(id);
        input.setAction("CREATE");
        repo.save(input);
        String json = mapper.writeValueAsString(input);
        publisher.publish(id, json);
        return ResponseEntity.ok(input);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderEvent> update(@PathVariable String id, @RequestBody OrderEvent input) throws JsonProcessingException {
        if (!repo.exists(id)) return ResponseEntity.notFound().build();
        input.setId(id);
        input.setAction("UPDATE");
        repo.save(input);
        String json = mapper.writeValueAsString(input);
        publisher.publish(id, json);
        return ResponseEntity.ok(input);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) throws JsonProcessingException {
        OrderEvent removed = repo.delete(id);
        if (removed == null) return ResponseEntity.notFound().build();
        removed.setAction("DELETE");
        String json = mapper.writeValueAsString(removed);
        publisher.publish(id, json);
        return ResponseEntity.noContent().build();
    }
}
