package com.bookstore.bookstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.bookstore.entity.Book;
import com.bookstore.bookstore.repository.BookRepository;
import com.bookstore.bookstore.observer.BookObserver;

@Service
public class BookService implements IBookService {

	@Autowired
	BookRepository bookRepo;
	
	// Spring will inject all beans implementing BookObserver
	@Autowired(required = false)
	private List<BookObserver> observers; 

	// Observer pattern methods (Subject implementation)
	// Attach and Detach are not strictly needed when using Spring's autowired List, 
	// but are kept in the interface for pattern completeness.
	@Override
	public void attach(BookObserver observer) {
		// Not implemented as observers are autowired by Spring
	}

	@Override
	public void detach(BookObserver observer) {
		// Not implemented as observers are autowired by Spring
	}

	@Override
	public void notifyObservers(Book book, String action) {
		if (observers != null) {
			for (BookObserver observer : observers) {
				observer.update(book, action);
			}
		}
	}

	// Service methods
	@Override
	public void save(Book b) {
		bookRepo.save(b);
		notifyObservers(b, "save");
	}

	@Override
	public List<Book> getAllBook() {
		return bookRepo.findAll();
	}

	@Override
	public Book getBookById(int id) {
		return bookRepo.findById(id).get();
	}

	@Override
	public void deleteBookById(int id) {
		// To notify observers, we need the book object before deletion.
		Book bookToDelete = getBookById(id);
		bookRepo.deleteById(id);
		notifyObservers(bookToDelete, "delete");
	}
}
