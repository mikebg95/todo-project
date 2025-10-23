package com.example.todoapp.controller;

import com.example.todoapp.model.Item;
import com.example.todoapp.repository.ItemRepository;
import com.example.todoapp.security.CurrentUserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {
    private final CurrentUserService currentUserService;
    private final ItemRepository itemRepository;

    public ItemController(CurrentUserService currentUserService, ItemRepository itemRepository) {
        this.currentUserService = currentUserService;
        this.itemRepository = itemRepository;
    }

    // get all items
    @GetMapping
    public List<Item> getAll() {
        return itemRepository.findByOwnerId(currentUserService.getUserId());
    }

    // add item
    @PostMapping
    public void addItem(@RequestBody String text) {
        String userId = currentUserService.getUserId();
        itemRepository.save(Item.of(text, userId));
    }

    // delete item
    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable String id) {
        Item item = itemRepository.findById(id).orElseThrow();
        String userId = currentUserService.getUserId();

        if (!item.getOwnerId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not your item");
        }

        itemRepository.deleteById(id);
    }
}
