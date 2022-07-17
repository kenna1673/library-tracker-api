package com.ohara.librarybooktracker.domain.book.services;

import com.ohara.librarybooktracker.core.excpetions.ResourceNotFoundException;
import com.ohara.librarybooktracker.domain.book.models.Book;
import com.ohara.librarybooktracker.domain.book.repos.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private BookRepo bookRepo;

    @Autowired
    public BookServiceImpl(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    @Override
    public Book create(Book book) {
        return bookRepo.save(book);
    }

    @Override
    public Book getById(Long id) throws ResourceNotFoundException {
        Optional<Book> optional = bookRepo.findById(id);
        if (optional.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Book with %d not found!", id));
        }
        return optional.get();
    }

    @Override
    public List<Book> getAll() {
        return bookRepo.findAll();
    }

    @Override
    public Book update(Long id, Book bookDetails) throws ResourceNotFoundException {
        Book book = getById(id);
        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        book.setGenre(bookDetails.getGenre());
        book.setIsAvailable(bookDetails.getIsAvailable());
        return bookRepo.save(book);
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        Book book = getById(id);
        bookRepo.delete(book);
    }
}
