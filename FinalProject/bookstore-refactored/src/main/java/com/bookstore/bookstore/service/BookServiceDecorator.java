package com.bookstore.bookstore.service;

import java.util.List;
import com.bookstore.bookstore.entity.Book;
import com.bookstore.bookstore.observer.BookObserver;

public abstract class BookServiceDecorator implements IBookService {
    protected final IBookService decoratedService;

    public BookServiceDecorator(IBookService decoratedService) {
        this.decoratedService = decoratedService;
    }

    @Override
    public void save(Book b) {
        decoratedService.save(b);
    }

    @Override
    public List<Book> getAllBook() {
        return decoratedService.getAllBook();
    }

    @Override
    public Book getBookById(int id) {
        return decoratedService.getBookById(id);
    }

    @Override
    public void deleteBookById(int id) {
        decoratedService.deleteBookById(id);
    }

    @Override
    public void attach(BookObserver observer) {
        decoratedService.attach(observer);
    }

    @Override
    public void detach(BookObserver observer) {
        decoratedService.detach(observer);
    }

    @Override
    public void notifyObservers(Book book, String action) {
        decoratedService.notifyObservers(book, action);
    }
}
