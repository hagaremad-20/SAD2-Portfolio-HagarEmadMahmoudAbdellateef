package com.bookstore.bookstore.observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.bookstore.bookstore.entity.Book;

@Component
public class EmailNotificationObserver implements BookObserver {

    private static final Logger logger = LoggerFactory.getLogger(EmailNotificationObserver.class);

    @Override
    public void update(Book book, String action) {
        // In a real application, this would send an email.
        // For demonstration, we'll log the notification.
        logger.info("EmailNotificationObserver: Sending email notification for book '{}' (ID: {}) action: {}", 
                    book.getName(), book.getId(), action);
    }
}
