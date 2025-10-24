package com.example.todoapp;

import com.example.todoapp.controller.ItemController;
import com.example.todoapp.model.Item;
import com.example.todoapp.repository.ItemRepository;
import com.example.todoapp.security.CurrentUserService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(ItemController.class)
class ItemControllerTest {

    private static final String USER_ID = "user-123";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ItemRepository itemRepository;

    @MockitoBean
    private CurrentUserService currentUserService;

    // ---------- GET /items ----------
    // Note: Controller uses findAll() and relies on @PostFilter for per-user filtering,
    // which is disabled in this test via addFilters = false. So we verify findAll().

    @Test
    void getAllItems_returnsAllItemsWhenFiltersDisabled() throws Exception {
        Item i1 = Item.of("Buy milk", USER_ID); i1.setId("1");
        Item i2 = Item.of("Walk dog", USER_ID); i2.setId("2");

        given(itemRepository.findAll()).willReturn(List.of(i1, i2));

        mockMvc.perform(get("/items"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].text").value("Buy milk"))
                .andExpect(jsonPath("$[1].id").value("2"))
                .andExpect(jsonPath("$[1].text").value("Walk dog"));

        verify(itemRepository).findAll();
        verifyNoInteractions(currentUserService);
    }

    @Test
    void getAllItems_returnsEmptyArrayWhenNoneExist() throws Exception {
        given(itemRepository.findAll()).willReturn(List.of());

        mockMvc.perform(get("/items"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.length()").value(0));

        verify(itemRepository).findAll();
        verifyNoInteractions(currentUserService);
    }

    @Test
    void getAllItems_returns500WhenRepositoryThrows() throws Exception {
        given(itemRepository.findAll()).willThrow(new RuntimeException("boom"));

        mockMvc.perform(get("/items"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));

        verify(itemRepository).findAll();
        verifyNoInteractions(currentUserService);
    }

    // ---------- POST /items ----------

    @Test
    void addItem_setsOwnerIdFromCurrentUser() throws Exception {
        given(currentUserService.getUserId()).willReturn(USER_ID);
        ArgumentCaptor<Item> captor = ArgumentCaptor.forClass(Item.class);
        given(itemRepository.save(any(Item.class))).willAnswer(inv -> inv.getArgument(0));

        mockMvc.perform(post("/items")
                        .contentType(MediaType.TEXT_PLAIN)
                        .content("Read book"))
                .andExpect(status().isOk());

        verify(itemRepository).save(captor.capture());
        Item saved = captor.getValue();
        assertThat(saved.getText()).isEqualTo("Read book");
        assertThat(saved.getOwnerId()).isEqualTo(USER_ID);
    }

    // ---------- DELETE /items/{id} ----------

    @Test
    void deleteItem_allowsOwner() throws Exception {
        given(currentUserService.getUserId()).willReturn(USER_ID);
        Item item = Item.of("X", USER_ID); item.setId("abc");
        given(itemRepository.findById("abc")).willReturn(Optional.of(item));

        mockMvc.perform(delete("/items/abc"))
                .andExpect(status().isOk());

        verify(itemRepository).deleteById("abc");
    }

    @Test
    void deleteItem_forbiddenForNonOwner() throws Exception {
        given(currentUserService.getUserId()).willReturn(USER_ID);
        Item item = Item.of("X", "someone-else"); item.setId("abc");
        given(itemRepository.findById("abc")).willReturn(Optional.of(item));

        mockMvc.perform(delete("/items/abc"))
                .andExpect(status().isForbidden());

        verify(itemRepository, never()).deleteById(anyString());
    }

    @Test
    void deleteItem_notFoundWhenMissing() throws Exception {
        given(currentUserService.getUserId()).willReturn(USER_ID);
        given(itemRepository.findById("missing")).willReturn(Optional.empty());

        mockMvc.perform(delete("/items/missing"))
                .andExpect(status().isNotFound());

        verify(itemRepository, never()).deleteById(anyString());
    }
}