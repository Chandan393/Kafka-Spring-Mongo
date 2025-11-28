package com.example.kafkaspringmongo.repo;

import com.example.kafkaspringmongo.model.OrderEvent;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class OrderRepo {
    private final Map<String, OrderEvent> store = new ConcurrentHashMap<>();

    public Collection<OrderEvent> findAll() { return store.values(); }
    public OrderEvent findById(String id) { return store.get(id); }
    public OrderEvent save(OrderEvent o) { store.put(o.getId(), o); return o; }
    public OrderEvent delete(String id) { return store.remove(id); }
    public boolean exists(String id) { return store.containsKey(id); }
}
