package com.bookstore.bookstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.bookstore.bookstore.service.BookService;
import com.bookstore.bookstore.service.IBookService;
import com.bookstore.bookstore.service.LoggingBookServiceDecorator;

@Configuration
public class ServiceConfig {

    @Bean
    public BookService bookService() {
        return new BookService();
    }

    @Bean
    @Primary // This bean will be injected when IBookService is requested
    public IBookService decoratedBookService(BookService bookService) {
        // We wrap the core service with the decorator
        return new LoggingBookServiceDecorator(bookService);
    }
}
