package com.ohara.librarybooktracker.domain.book.controllers;

import com.ohara.librarybooktracker.core.excpetions.ResourceNotFoundException;
import com.ohara.librarybooktracker.domain.book.models.Book;
import com.ohara.librarybooktracker.domain.book.services.BookService;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.ohara.librarybooktracker.domain.book.BaseControllerTest.asJsonString;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class BookControllerTest {

    @MockBean
    private BookService mockBookService;

    @Autowired
    private MockMvc mockMvc;

    private Book mockBook01;
    private Book savedBook01;
    private Book savedBook02;

    @BeforeEach
    public void setUp() {
        mockBook01 = new Book("Harry Potter and the Goblet of Fire", "J.K. Rowling", "Fiction", true);

        savedBook01 = new Book("Harry Potter and the Goblet of Fire", "J.K. Rowling", "Fiction", true);
        savedBook01.setId(1L);

        savedBook02 = new Book("Harry Potter and the Chamber of secrets", "J.K. Rowling", "Fiction", false);
        savedBook02.setId(2L);
    }

    @Test
    @DisplayName("Book POST - success")
    public void createTest01() throws Exception {
        BDDMockito.doReturn(savedBook01).when(mockBookService).create(any());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(mockBook01)))

                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Is.is("Harry Potter and the Goblet of Fire")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author", Is.is("J.K. Rowling")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genre", Is.is("Fiction")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isAvailable", Is.is(true)));
    }

    @Test
    @DisplayName("Book GET by id - success")
    public void getByIdTest01() throws Exception {
        BDDMockito.doReturn(savedBook01).when(mockBookService).getById(1L);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/books/1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Is.is("Harry Potter and the Goblet of Fire")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author", Is.is("J.K. Rowling")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genre", Is.is("Fiction")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isAvailable", Is.is(true)));
    }

    @Test
    @DisplayName("Book GET by id - failed")
    public void getByIdTest02() throws Exception {
        BDDMockito.doThrow(new ResourceNotFoundException("Not found")).when(mockBookService).getById(1L);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/books/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("PUT book - success")
    public void updateTest01() throws Exception {
        Book updated = new Book("Harry Potter and the Goblet of Fire", "J.K. Rowling", "Fiction", false);
        updated.setId(1L);
        BDDMockito.doReturn(updated).when(mockBookService).update(any(), any());

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/books/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(updated)))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Is.is("Harry Potter and the Goblet of Fire")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author", Is.is("J.K. Rowling")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genre", Is.is("Fiction")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isAvailable", Is.is(false)));
    }

    @Test
    @DisplayName("PUT book - failed")
    public void updateTest02() throws Exception {
        BDDMockito.doThrow(new ResourceNotFoundException("Not found")).when(mockBookService).update(any(), any());

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/books/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(mockBook01)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("DELETE book - success")
    public void deleteTest01() throws Exception {
        BDDMockito.doNothing().when(mockBookService).delete(any());
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/books/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("DELETE book - failed")
    public void deleteTest02() throws Exception {
        BDDMockito.doThrow(new ResourceNotFoundException("Not found")).when(mockBookService).delete(any());
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
