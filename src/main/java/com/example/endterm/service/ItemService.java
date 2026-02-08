package com.example.endterm.service;

import com.example.endterm.dto.ItemRequest;
import com.example.endterm.exception.InvalidInputException;
import com.example.endterm.exception.ResourceNotFoundException;
import com.example.endterm.model.GameItem;
import org.springframework.stereotype.Service;
import com.example.endterm.patterns.GameItemFactory;
import com.example.endterm.repository.ItemRepository;

import java.util.List;

@Service
public class ItemService {

    private final ItemRepository repo;

    public ItemService(ItemRepository repo) {
        this.repo = repo;
    }

    public int create(ItemRequest req) {
        validateRequest(req);
        GameItem item = GameItemFactory.create(req);
        return repo.create(item);
    }

    public GameItem get(int id) {
        GameItem item = repo.findById(id);
        if (item == null) throw new ResourceNotFoundException("Item with id " + id + " not found");
        return item;
    }

    public List<GameItem> getAll() {
        return repo.findAll();
    }

    public void updateBasic(int id, ItemRequest req) {
        validateRequest(req);
        if (repo.findById(id) == null) throw new ResourceNotFoundException("Item with id " + id + " not found");
        repo.updateBasic(id, req.name, req.weight, req.goldValue);
    }

    public void delete(int id) {
        if (repo.findById(id) == null) throw new ResourceNotFoundException("Item with id " + id + " not found");
        repo.delete(id);
    }

    private void validateRequest(ItemRequest req) {
        if (req == null) throw new InvalidInputException("Request body is required");
        if (req.name == null || req.name.isBlank()) throw new InvalidInputException("name is required");
        if (req.weight <= 0) throw new InvalidInputException("weight must be > 0");
        if (req.goldValue < 0) throw new InvalidInputException("goldValue must be >= 0");
        if (req.type == null || req.type.isBlank()) throw new InvalidInputException("type is required");
        if (req.backpackId <= 0) throw new InvalidInputException("backpackId must be > 0");
    }
}
