package com.example.endterm.controller;

import com.example.endterm.dto.BackpackRequest;
import com.example.endterm.dto.BackpackResponse;
import com.example.endterm.dto.ItemResponse;
import com.example.endterm.service.BackpackService;
import com.example.endterm.service.ItemService;
import com.example.endterm.utils.ItemMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/backpacks")
public class BackpackController {

    private final BackpackService backpackService;
    private final ItemService itemService;

    public BackpackController(BackpackService backpackService, ItemService itemService) {
        this.backpackService = backpackService;
        this.itemService = itemService;
    }

    @PostMapping
    public int create(@RequestBody BackpackRequest req) {
        return backpackService.create(req);
    }

    @GetMapping
    public List<BackpackResponse> getAll() {
        return backpackService.getAll().stream().map(b -> {
            BackpackResponse r = new BackpackResponse();
            r.id = b.getId();
            r.owner = b.getOwner();
            r.maxWeight = b.getMaxWeight();
            return r;
        }).toList();
    }

    @GetMapping("/{id}/items")
    public List<ItemResponse> getItemsInBackpack(@PathVariable int id) {
        // check backpack exists
        backpackService.get(id);

        // filter items by backpackId
        return itemService.getAll().stream()
                .filter(i -> i.getBackpackid() == id)
                .map(ItemMapper::toResponse)
                .toList();
    }
}
