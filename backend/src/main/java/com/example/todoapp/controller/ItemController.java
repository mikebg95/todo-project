package com.example.todoapp.controller;

import com.example.todoapp.aop.LogExecutionTime;
import com.example.todoapp.aop.RequireOwner;
import com.example.todoapp.model.Item;
import com.example.todoapp.repository.ItemRepository;
import com.example.todoapp.security.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@LogExecutionTime
@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {
    private final CurrentUserService currentUserService;
    private final ItemRepository itemRepository;

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
    @RequireOwner
    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable String id) {
        itemRepository.deleteById(id);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN_ROLE')")
    public List<Item> getAllItemsForAdmin() {
        return itemRepository.findAll();
    }
}
