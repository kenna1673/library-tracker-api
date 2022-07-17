package com.ohara.librarybooktracker.domain.book.services;

import com.fasterxml.jackson.core.json.WriterBasedJsonGenerator;
import com.ohara.librarybooktracker.core.excpetions.ResourceNotFoundException;
import com.ohara.librarybooktracker.domain.book.models.Book;

import java.util.List;

public interface BookService {
    Book create(Book book);
    Book getById(Long id) throws ResourceNotFoundException;
    List<Book> getAll();
    Book update(Long id, Book bookDetails) throws ResourceNotFoundException;
    void delete(Long id) throws ResourceNotFoundException;
}
