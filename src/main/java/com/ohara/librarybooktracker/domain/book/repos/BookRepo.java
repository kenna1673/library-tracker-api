package com.ohara.librarybooktracker.domain.book.repos;

import com.ohara.librarybooktracker.domain.book.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepo extends JpaRepository<Book, Long> {
}
