package com.example.endterm.controller;

import com.example.endterm.dto.ItemRequest;
import com.example.endterm.dto.ItemResponse;
import com.example.endterm.model.GameItem;
import org.springframework.web.bind.annotation.*;
import com.example.endterm.service.ItemService;
import com.example.endterm.utils.ItemMapper;

import java.util.List;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService service;

    public ItemController(ItemService service) {
        this.service = service;
    }

    @GetMapping
    public List<ItemResponse> getAll() {
        return service.getAll().stream().map(ItemMapper::toResponse).toList();
    }

    @GetMapping("/{id}")
    public ItemResponse getOne(@PathVariable int id) {
        GameItem item = service.get(id);
        return ItemMapper.toResponse(item);
    }

    @PostMapping
    public int create(@RequestBody ItemRequest req) {
        return service.create(req);
    }

    @PutMapping("/{id}")
    public void updateBasic(@PathVariable int id, @RequestBody ItemRequest req) {
        service.updateBasic(id, req);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        service.delete(id);
    }
}
