package com.example.todoapp.controller;

import com.example.todoapp.model.Item;
import com.example.todoapp.repository.ItemRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {
    private final ItemRepository itemRepository;

    // random test change

    public ItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    // get all items
    @GetMapping
    public List<Item> getAll() {
        return itemRepository.findAll();
    }

    // add item
    @PostMapping
    public void addItem(@RequestBody String text) {
        itemRepository.save(new Item(text));
    }

    // delete item
    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable String id) {
        itemRepository.deleteById(id);
    }
}
