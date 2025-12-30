package com.bookstore.bookstore.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.bookstore.bookstore.entity.Book;

@Service
public class LoggingBookServiceDecorator extends BookServiceDecorator {

    private static final Logger logger = LoggerFactory.getLogger(LoggingBookServiceDecorator.class);

    public LoggingBookServiceDecorator(BookService decoratedService) {
        super(decoratedService);
    }

    @Override
    public void save(Book b) {
        logger.info("LoggingDecorator: Attempting to save book: {}", b.getName());
        super.save(b);
        logger.info("LoggingDecorator: Successfully saved book: {}", b.getName());
    }

    @Override
    public List<Book> getAllBook() {
        logger.info("LoggingDecorator: Fetching all books.");
        List<Book> books = super.getAllBook();
        logger.info("LoggingDecorator: Found {} books.", books.size());
        return books;
    }

    @Override
    public Book getBookById(int id) {
        logger.info("LoggingDecorator: Fetching book with ID: {}", id);
        Book book = super.getBookById(id);
        logger.info("LoggingDecorator: Found book: {}", book.getName());
        return book;
    }

    @Override
    public void deleteBookById(int id) {
        logger.warn("LoggingDecorator: Attempting to delete book with ID: {}", id);
        super.deleteBookById(id);
        logger.warn("LoggingDecorator: Successfully deleted book with ID: {}", id);
    }
}
