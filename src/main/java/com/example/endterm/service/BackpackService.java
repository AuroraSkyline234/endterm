package com.example.endterm.service;

import com.example.endterm.dto.BackpackRequest;
import com.example.endterm.exception.InvalidInputException;
import com.example.endterm.exception.ResourceNotFoundException;
import com.example.endterm.model.Backpack;
import com.example.endterm.repository.BackpackRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BackpackService {

    private final BackpackRepository repo;

    public BackpackService(BackpackRepository repo) {
        this.repo = repo;
    }

    public int create(BackpackRequest req) {
        if (req == null) throw new InvalidInputException("Request body is required");
        if (req.owner == null || req.owner.isBlank()) throw new InvalidInputException("owner is required");
        if (req.maxWeight <= 0) throw new InvalidInputException("maxWeight must be > 0");

        Backpack b = new Backpack(0, req.owner, req.maxWeight);
        return repo.create(b);
    }

    public List<Backpack> getAll() {
        return repo.findAll();
    }

    public Backpack get(int id) {
        Backpack b = repo.findById(id);
        if (b == null) throw new ResourceNotFoundException("Backpack with id " + id + " not found");
        return b;
    }
}
