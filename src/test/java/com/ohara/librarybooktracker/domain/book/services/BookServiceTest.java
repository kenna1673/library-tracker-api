package com.ohara.librarybooktracker.domain.book.services;

import com.ohara.librarybooktracker.core.excpetions.ResourceNotFoundException;
import com.ohara.librarybooktracker.domain.book.models.Book;
import com.ohara.librarybooktracker.domain.book.repos.BookRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class BookServiceTest {

    @Autowired
    private BookService bookService;

    @MockBean
    private BookRepo bookRepo;

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
    @DisplayName("Create book - success")
    public void createTest01() {
        BDDMockito.doReturn(savedBook01).when(bookRepo).save(mockBook01);
        Book book = bookService.create(mockBook01);
        Assertions.assertEquals(savedBook01, book);
    }

    @Test
    @DisplayName("Get by id - success")
    public void getByIdTest01() {
        BDDMockito.doReturn(Optional.of(savedBook01)).when(bookRepo).findById(1L);
        Book book = bookService.getById(1L);
        Assertions.assertEquals(savedBook01, book);
    }

    @Test
    @DisplayName("Get by id - fail")
    public void getByIdTest02() {
        BDDMockito.doReturn(Optional.empty()).when(bookRepo).findById(1L);
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            bookService.getById(1L);
        });
    }

    @Test
    @DisplayName("Get all - success")
    public void getAllTest01() {
        List<Book> books = new ArrayList<>();
        books.add(savedBook01);
        BDDMockito.doReturn(books).when(bookRepo).findAll();
        List<Book> actualBooks = bookService.getAll();
        int expectedSize = 1;
        int actualSize = actualBooks.size();
        //coment
        Assertions.assertEquals(expectedSize, actualSize);
    }

    @Test
    @DisplayName("Update - success")
    public void updateTest01() {
        Book updated = new Book("Harry Potter and the Goblet of Fire", "J.K. Rowling", "Non-fiction", true);
        updated.setId(1L);
        BDDMockito.doReturn(Optional.of(savedBook01)).when(bookRepo).findById(1L);
        BDDMockito.doReturn(updated).when(bookRepo).save(savedBook01);
        Book actualBook = bookService.update(1L, updated);
        Assertions.assertEquals(updated, actualBook);
    }
}
