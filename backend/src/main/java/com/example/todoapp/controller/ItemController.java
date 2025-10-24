package com.example.todoapp.controller;

import com.example.todoapp.aop.LogExecutionTime;
import com.example.todoapp.model.Item;
import com.example.todoapp.repository.ItemRepository;
import com.example.todoapp.security.CurrentUserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@LogExecutionTime
@RestController
@RequestMapping("/items")
public class ItemController {
    private final CurrentUserService currentUserService;
    private final ItemRepository itemRepository;

    public ItemController(CurrentUserService currentUserService, ItemRepository itemRepository) {
        this.currentUserService = currentUserService;
        this.itemRepository = itemRepository;
    }

    // get all items for user
    @GetMapping
    @PostFilter("filterObject.ownerId == principal.claims['sub']")
    public List<Item> getAllItemsForUser() {
        return itemRepository.findAll();
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
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found"));
        String userId = currentUserService.getUserId();

        if (!item.getOwnerId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not your item");
        }

        itemRepository.deleteById(id);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN_ROLE')")
    public List<Item> getAllItemsForAdmin() {
        return itemRepository.findAll();
    }
}
