package com.example.todoapp;

import com.example.todoapp.controller.ItemController;
import com.example.todoapp.model.Item;
import com.example.todoapp.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

// web slice test class for the itemController
@WebMvcTest(ItemController.class)
public class ItemControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ItemRepository itemRepository;

    @Test
    public void getAllItems_shouldReturn200AndJsonArray_whenItemsExist() throws Exception {
        List<Item> items = new ArrayList<>();
        Item item1 = new Item("Buy milk");
        item1.setId("1");
        Item item2 = new Item("Walk dog");
        item2.setId("2");
        items.add(item1);
        items.add(item2);

        given(itemRepository.findAll()).willReturn(items);

        mockMvc.perform(get("/items"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].text").value("Buy milk"))
                .andExpect(jsonPath("$[1].id").value("2"))
                .andExpect(jsonPath("$[1].text").value("Walk dog"));

        verify(itemRepository).findAll();
    }

    @Test
    public void getAllItems_shouldReturn200AndEmptyArray_whenNoItemsExist() throws Exception {
        given(itemRepository.findAll()).willReturn(new ArrayList<>());

        mockMvc.perform(get("/items"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.length()").value(0));

        verify(itemRepository).findAll();
    }

    @Test
    public void getAllItems_shouldReturn500_whenRepositoryThrows() throws Exception {
        given(itemRepository.findAll()).willThrow(new RuntimeException("boom"));

        mockMvc.perform(get("/items"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType("application/json"));

        verify(itemRepository).findAll();
    }
}
