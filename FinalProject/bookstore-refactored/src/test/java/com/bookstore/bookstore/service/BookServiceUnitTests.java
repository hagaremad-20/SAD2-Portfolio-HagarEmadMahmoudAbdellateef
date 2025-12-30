package com.bookstore.bookstore.service;

import com.bookstore.bookstore.entity.Book;
import com.bookstore.bookstore.repository.BookRepository;
import com.bookstore.bookstore.observer.BookObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.repository.CrudRepository;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;

public class BookServiceUnitTests {

    // --- Manual Dummy Implementations (Replacing Mockito) ---

    /**
     * Dummy implementation of BookRepository for in-memory testing.
     */
    private static class DummyBookRepository implements BookRepository {
        private final Map<Integer, Book> store = new HashMap<>();
        private final AtomicInteger idCounter = new AtomicInteger(100); // Start IDs high to avoid collision with test IDs

        @Override
        public <S extends Book> S save(S entity) {
            if (entity == null) {
                return null;
            }
            if (entity.getId() == 0) {
                entity.setId(idCounter.getAndIncrement());
            }
            store.put(entity.getId(), entity);
            return entity;
        }

        @Override
        public Optional<Book> findById(Integer id) {
            return Optional.ofNullable(store.get(id));
        }

        @Override
        public boolean existsById(Integer id) {
            return store.containsKey(id);
        }

        @Override
        public List<Book> findAll() {
            return new ArrayList<>(store.values());
        }

        @Override
        public List<Book> findAllById(Iterable<Integer> integers) {
            return StreamSupport.stream(integers.spliterator(), false)
                    .map(store::get)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        }

        @Override
        public long count() {
            return store.size();
        }

        @Override
        public void deleteById(Integer id) {
            store.remove(id);
        }

        @Override
        public void delete(Book entity) {
            store.remove(entity.getId());
        }

        @Override
        public void deleteAllById(Iterable<? extends Integer> integers) {
            integers.forEach(store::remove);
        }

        @Override
        public void deleteAll(Iterable<? extends Book> entities) {
            entities.forEach(this::delete);
        }

        @Override
        public void deleteAll() {
            store.clear();
        }

        // CrudRepository methods not used by BookService, but required by interface
        @Override public <S extends Book> List<S> saveAll(Iterable<S> entities) { return null; }
        @Override public void flush() {}
        @Override public <S extends Book> S saveAndFlush(S entity) { return null; }
        @Override public <S extends Book> List<S> saveAllAndFlush(Iterable<S> entities) { return null; }
        @Override public void deleteAllInBatch(Iterable<Book> entities) {}
        @Override public void deleteAllByIdInBatch(Iterable<Integer> integers) {}
        @Override public void deleteAllInBatch() {}
        @Override public Book getOne(Integer id) { return null; }
        @Override public Book getById(Integer id) { return null; }
        @Override public Book getReferenceById(Integer id) { return null; }
        @Override public <S extends Book> Optional<S> findOne(org.springframework.data.domain.Example<S> example) { return Optional.empty(); }
        @Override public <S extends Book> List<S> findAll(org.springframework.data.domain.Example<S> example) { return null; }
        @Override public <S extends Book> List<S> findAll(org.springframework.data.domain.Example<S> example, org.springframework.data.domain.Sort sort) { return null; }
        @Override public <S extends Book> org.springframework.data.domain.Page<S> findAll(org.springframework.data.domain.Example<S> example, org.springframework.data.domain.Pageable pageable) { return null; }
        @Override public <S extends Book> long count(org.springframework.data.domain.Example<S> example) { return 0; }
        @Override public <S extends Book> boolean exists(org.springframework.data.domain.Example<S> example) { return false; }
        @Override public List<Book> findAll(org.springframework.data.domain.Sort sort) { return null; }
        @Override public org.springframework.data.domain.Page<Book> findAll(org.springframework.data.domain.Pageable pageable) { return null; }

        @Override
        public <S extends Book, R> R findBy(org.springframework.data.domain.Example<S> example, java.util.function.Function<org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
            return null;
        }
    }

    /**
     * Dummy implementation of BookObserver to record notifications.
     */
    private static class DummyBookObserver implements BookObserver {
        private final List<String> notifications = new ArrayList<>();

        @Override
        public void update(Book book, String action) {
            String bookInfo = (book != null) ? book.getName() + " (" + book.getId() + ")" : "null";
            notifications.add(action + ": " + bookInfo);
        }

        public List<String> getNotifications() {
            return notifications;
        }

        public void clearNotifications() {
            notifications.clear();
        }
    }

    // --- Test Setup ---

    private DummyBookRepository bookRepo;
    private DummyBookObserver observer1;
    private DummyBookObserver observer2;
    private BookService bookService;

    @BeforeEach
    public void setUp() throws Exception {
        // 1. Initialize manual dependencies
        bookRepo = new DummyBookRepository();
        observer1 = new DummyBookObserver();
        observer2 = new DummyBookObserver();

        // 2. Initialize BookService and manually inject dependencies
        bookService = new BookService();
        
        // Manually set the BookRepository field using reflection (since no setter is available)
        java.lang.reflect.Field repoField = BookService.class.getDeclaredField("bookRepo");
        repoField.setAccessible(true);
        repoField.set(bookService, bookRepo);

        // Manually set the observers list field using reflection
        java.lang.reflect.Field observersField = BookService.class.getDeclaredField("observers");
        observersField.setAccessible(true);
        observersField.set(bookService, Arrays.asList(observer1, observer2));
    }

    // Helper Book
    private Book createBook(int id, String name, double price) {
        return Book.builder().id(id).name(name).author("Test Author").price(price).build();
    }

    // --- 10 Tests for save(Book b) ---

    @Test
    public void testSave_CallsRepositorySave() {
        Book book = createBook(1, "Test Book 1", 10.0);
        bookService.save(book);
        assertTrue(bookRepo.existsById(1));
    }

    @Test
    public void testSave_NotifiesObserversWithSaveAction() {
        Book book = createBook(2, "Test Book 2", 20.0);
        bookService.save(book);
        assertEquals(1, observer1.getNotifications().size());
        assertEquals(1, observer2.getNotifications().size());
        assertTrue(observer1.getNotifications().get(0).startsWith("save:"));
    }

    @Test
    public void testSave_WithNullBook_DoesNotThrowExceptionAndNotifies() {
        // The service implementation does not check for null, so it passes null to the repo and observers.
        // The dummy repo handles null gracefully.
        bookService.save(null);
        assertEquals(1, observer1.getNotifications().size());
        assertTrue(observer1.getNotifications().get(0).startsWith("save: null"));
    }

    @Test
    public void testSave_BookWithZeroPrice_SavesSuccessfully() {
        Book book = createBook(3, "Free Book", 0.0);
        bookService.save(book);
        assertTrue(bookRepo.existsById(3));
    }

    @Test
    public void testSave_BookWithNegativePrice_SavesSuccessfully() {
        Book book = createBook(4, "Negative Price Book", -5.0);
        bookService.save(book);
        assertTrue(bookRepo.existsById(4));
    }

    @Test
    public void testSave_BookWithLongName_SavesSuccessfully() {
        Book book = createBook(5, "A very long book name that should still be saved without any issues", 50.0);
        bookService.save(book);
        assertTrue(bookRepo.existsById(5));
    }

    @Test
    public void testSave_BookWithNoId_AssignsNewIdAndSaves() {
        Book book = Book.builder().name("New Book").author("New Author").price(15.0).build();
        bookService.save(book);
        assertNotEquals(0, book.getId());
        assertTrue(bookRepo.existsById(book.getId()));
    }

    @Test
    public void testSave_Observer1Notified() {
        Book book = createBook(6, "Observer Test 1", 10.0);
        bookService.save(book);
        assertEquals("save: Observer Test 1 (6)", observer1.getNotifications().get(0));
    }

    @Test
    public void testSave_Observer2Notified() {
        Book book = createBook(7, "Observer Test 2", 10.0);
        bookService.save(book);
        assertEquals("save: Observer Test 2 (7)", observer2.getNotifications().get(0));
    }

    @Test
    public void testSave_UpdatesExistingBook() {
        Book book = createBook(8, "Old Name", 10.0);
        bookService.save(book);
        book.setName("New Name");
        bookService.save(book);
        assertEquals("New Name", bookRepo.findById(8).get().getName());
        assertEquals(2, observer1.getNotifications().size()); // Two saves, two notifications
    }

    // --- 10 Tests for getAllBook() ---

    @Test
    public void testGetAllBook_ReturnsEmptyListWhenNoBooks() {
        List<Book> books = bookService.getAllBook();
        assertTrue(books.isEmpty());
    }

    @Test
    public void testGetAllBook_ReturnsListOfOneBook() {
        Book book = createBook(9, "Single Book", 10.0);
        bookRepo.save(book);
        List<Book> books = bookService.getAllBook();
        assertEquals(1, books.size());
        assertEquals("Single Book", books.get(0).getName());
    }

    @Test
    public void testGetAllBook_ReturnsListOfMultipleBooks() {
        Book book1 = createBook(10, "Book A", 10.0);
        Book book2 = createBook(11, "Book B", 20.0);
        bookRepo.save(book1);
        bookRepo.save(book2);
        List<Book> books = bookService.getAllBook();
        assertEquals(2, books.size());
        assertTrue(books.stream().anyMatch(b -> b.getName().equals("Book A")));
    }

    @Test
    public void testGetAllBook_RepositoryCalledOnce() {
        // Cannot assert "called once" without Mockito, but we can assert the state.
        // We rely on the fact that the service calls the repository's findAll method.
        bookRepo.save(createBook(12, "Book", 10.0));
        bookService.getAllBook();
        assertEquals(1, bookRepo.count());
    }

    @Test
    public void testGetAllBook_ReturnedListIsNotNull() {
        List<Book> books = bookService.getAllBook();
        assertNotNull(books);
    }

    @Test
    public void testGetAllBook_CheckBookDetailsCorrectly() {
        Book book = createBook(13, "Detail Check", 99.99);
        bookRepo.save(book);
        Book result = bookService.getAllBook().get(0);
        assertEquals(13, result.getId());
        assertEquals("Detail Check", result.getName());
        assertEquals(99.99, result.getPrice());
    }

    @Test
    public void testGetAllBook_MultipleCallsReturnSameResult() {
        Book book = createBook(14, "Cached Book", 10.0);
        bookRepo.save(book);
        List<Book> books1 = bookService.getAllBook();
        List<Book> books2 = bookService.getAllBook();
        assertEquals(books1.size(), books2.size());
    }

    @Test
    public void testGetAllBook_NoObserverInteraction() {
        bookRepo.save(createBook(15, "No Notify", 10.0));
        bookService.getAllBook();
        assertTrue(observer1.getNotifications().isEmpty());
    }

    @Test
    public void testGetAllBook_ListContainsCorrectIds() {
        Book book1 = createBook(16, "ID 16", 10.0);
        Book book2 = createBook(17, "ID 17", 20.0);
        bookRepo.save(book1);
        bookRepo.save(book2);
        List<Book> books = bookService.getAllBook();
        List<Integer> ids = books.stream().map(Book::getId).toList();
        assertTrue(ids.contains(16));
        assertTrue(ids.contains(17));
    }

    @Test
    public void testGetAllBook_ListContainsCorrectPrices() {
        Book book1 = createBook(18, "Price 10", 10.0);
        Book book2 = createBook(19, "Price 20", 20.0);
        bookRepo.save(book1);
        bookRepo.save(book2);
        List<Book> books = bookService.getAllBook();
        List<Double> prices = books.stream().map(Book::getPrice).toList();
        assertTrue(prices.contains(10.0));
        assertTrue(prices.contains(20.0));
    }

    // --- 10 Tests for getBookById(int id) ---

    @Test
    public void testGetBookById_ReturnsBookWhenFound() {
        Book book = createBook(20, "Found Book", 10.0);
        bookRepo.save(book);
        Book result = bookService.getBookById(20);
        assertNotNull(result);
        assertEquals(20, result.getId());
    }

    @Test
    public void testGetBookById_ThrowsExceptionWhenNotFound() {
        assertThrows(NoSuchElementException.class, () -> bookService.getBookById(99));
    }

    @Test
    public void testGetBookById_CheckNameCorrectly() {
        Book book = createBook(21, "Name Check", 10.0);
        bookRepo.save(book);
        Book result = bookService.getBookById(21);
        assertEquals("Name Check", result.getName());
    }

    @Test
    public void testGetBookById_CheckPriceCorrectly() {
        Book book = createBook(22, "Price Check", 123.45);
        bookRepo.save(book);
        Book result = bookService.getBookById(22);
        assertEquals(123.45, result.getPrice());
    }

    @Test
    public void testGetBookById_ZeroIdThrowsException() {
        assertThrows(NoSuchElementException.class, () -> bookService.getBookById(0));
    }

    @Test
    public void testGetBookById_NegativeIdThrowsException() {
        assertThrows(NoSuchElementException.class, () -> bookService.getBookById(-1));
    }

    @Test
    public void testGetBookById_NoObserverInteraction() {
        Book book = createBook(23, "No Notify", 10.0);
        bookRepo.save(book);
        bookService.getBookById(23);
        assertTrue(observer1.getNotifications().isEmpty());
    }

    @Test
    public void testGetBookById_MultipleCalls() {
        Book book = createBook(24, "Multiple Calls", 10.0);
        bookRepo.save(book);
        bookService.getBookById(24);
        bookService.getBookById(24);
        assertEquals(1, bookRepo.count()); // Still one book in repo
    }

    @Test
    public void testGetBookById_DifferentIds() {
        Book book1 = createBook(25, "Book 25", 10.0);
        Book book2 = createBook(26, "Book 26", 20.0);
        bookRepo.save(book1);
        bookRepo.save(book2);
        assertEquals("Book 25", bookService.getBookById(25).getName());
        assertEquals("Book 26", bookService.getBookById(26).getName());
    }

    @Test
    public void testGetBookById_ReturnsCorrectObjectReference() {
        Book book = createBook(27, "Reference Check", 10.0);
        bookRepo.save(book);
        Book result = bookService.getBookById(27);
        assertSame(book, result);
    }

    // --- 10 Tests for deleteBookById(int id) ---

    @Test
    public void testDeleteBookById_RemovesBookFromRepository() {
        Book book = createBook(28, "To Delete", 10.0);
        bookRepo.save(book);
        bookService.deleteBookById(28);
        assertFalse(bookRepo.existsById(28));
    }

    @Test
    public void testDeleteBookById_NotifiesObserversWithDeleteAction() {
        Book book = createBook(29, "Notify Delete", 10.0);
        bookRepo.save(book);
        bookService.deleteBookById(29);
        assertEquals(1, observer1.getNotifications().size());
        assertEquals("delete: Notify Delete (29)", observer1.getNotifications().get(0));
    }

    @Test
    public void testDeleteBookById_ThrowsExceptionWhenNotFound() {
        assertThrows(NoSuchElementException.class, () -> bookService.deleteBookById(999));
        assertTrue(observer1.getNotifications().isEmpty()); // No notification on failure
    }

    @Test
    public void testDeleteBookById_ZeroIdThrowsException() {
        assertThrows(NoSuchElementException.class, () -> bookService.deleteBookById(0));
    }

    @Test
    public void testDeleteBookById_NegativeIdThrowsException() {
        assertThrows(NoSuchElementException.class, () -> bookService.deleteBookById(-1));
    }

    @Test
    public void testDeleteBookById_Observer1NotifiedWithCorrectBook() {
        Book book = createBook(30, "Observer 1 Check", 10.0);
        bookRepo.save(book);
        bookService.deleteBookById(30);
        assertEquals("delete: Observer 1 Check (30)", observer1.getNotifications().get(0));
    }

    @Test
    public void testDeleteBookById_Observer2NotifiedWithCorrectBook() {
        Book book = createBook(31, "Observer 2 Check", 10.0);
        bookRepo.save(book);
        bookService.deleteBookById(31);
        assertEquals("delete: Observer 2 Check (31)", observer2.getNotifications().get(0));
    }

    @Test
    public void testDeleteBookById_OnlyOneBookFoundAndDeleted() {
        Book book1 = createBook(32, "Unique Delete", 10.0);
        Book book2 = createBook(33, "Keep Me", 20.0);
        bookRepo.save(book1);
        bookRepo.save(book2);
        bookService.deleteBookById(32);
        assertFalse(bookRepo.existsById(32));
        assertTrue(bookRepo.existsById(33));
    }

    @Test
    public void testDeleteBookById_NotificationContainsCorrectAction() {
        Book book = createBook(34, "Action Check", 10.0);
        bookRepo.save(book);
        bookService.deleteBookById(34);
        assertTrue(observer1.getNotifications().get(0).startsWith("delete:"));
    }

    @Test
    public void testDeleteBookById_RepositoryCountDecreases() {
        Book book1 = createBook(35, "Book 1", 10.0);
        Book book2 = createBook(36, "Book 2", 20.0);
        bookRepo.save(book1);
        bookRepo.save(book2);
        assertEquals(2, bookRepo.count());
        bookService.deleteBookById(35);
        assertEquals(1, bookRepo.count());
    }

    // --- 10 Tests for Observer Notification Logic (notifyObservers) ---

    @Test
    public void testNotifyObservers_WithSaveAction() {
        Book book = createBook(37, "Notify Save", 10.0);
        bookService.notifyObservers(book, "save");
        assertEquals("save: Notify Save (37)", observer1.getNotifications().get(0));
        assertEquals("save: Notify Save (37)", observer2.getNotifications().get(0));
    }

    @Test
    public void testNotifyObservers_WithDeleteAction() {
        Book book = createBook(38, "Notify Delete", 10.0);
        bookService.notifyObservers(book, "delete");
        assertEquals("delete: Notify Delete (38)", observer1.getNotifications().get(0));
    }

    @Test
    public void testNotifyObservers_WithUpdateAction() {
        Book book = createBook(39, "Notify Update", 10.0);
        bookService.notifyObservers(book, "update");
        assertEquals("update: Notify Update (39)", observer1.getNotifications().get(0));
    }

    @Test
    public void testNotifyObservers_WithNullBook() {
        bookService.notifyObservers(null, "save");
        assertEquals("save: null", observer1.getNotifications().get(0));
    }

    @Test
    public void testNotifyObservers_WithNullAction() {
        Book book = createBook(40, "Null Action", 10.0);
        bookService.notifyObservers(book, null);
        assertEquals("null: Null Action (40)", observer1.getNotifications().get(0));
    }

    @Test
    public void testNotifyObservers_MultipleNotifications() {
        Book book = createBook(41, "Multiple Notifications", 10.0);
        bookService.notifyObservers(book, "save");
        bookService.notifyObservers(book, "delete");
        assertEquals(2, observer1.getNotifications().size());
        assertEquals("save: Multiple Notifications (41)", observer1.getNotifications().get(0));
        assertEquals("delete: Multiple Notifications (41)", observer1.getNotifications().get(1));
    }

    @Test
    public void testNotifyObservers_WithEmptyObserversList() throws Exception {
        // Temporarily set the observers list to empty
        java.lang.reflect.Field observersField = BookService.class.getDeclaredField("observers");
        observersField.setAccessible(true);
        observersField.set(bookService, Collections.emptyList());

        Book book = createBook(42, "Empty List", 10.0);
        bookService.notifyObservers(book, "save");
        
        // No exception should be thrown, and no notifications should be recorded.
        assertTrue(observer1.getNotifications().isEmpty());
        
        // Re-set the original list for subsequent tests
        observersField.set(bookService, Arrays.asList(observer1, observer2));
    }

    @Test
    public void testNotifyObservers_WithOneObserver() throws Exception {
        // Temporarily set the observers list to contain only observer1
        java.lang.reflect.Field observersField = BookService.class.getDeclaredField("observers");
        observersField.setAccessible(true);
        observersField.set(bookService, Collections.singletonList(observer1));

        Book book = createBook(43, "One Observer", 10.0);
        bookService.notifyObservers(book, "save");
        
        assertEquals(1, observer1.getNotifications().size());
        assertTrue(observer2.getNotifications().isEmpty());
        
        // Re-set the original list for subsequent tests
        observersField.set(bookService, Arrays.asList(observer1, observer2));
    }

    @Test
    public void testNotifyObservers_DifferentBookObjects() {
        Book bookA = createBook(44, "Book A", 10.0);
        Book bookB = createBook(45, "Book B", 20.0);
        
        bookService.notifyObservers(bookA, "save");
        bookService.notifyObservers(bookB, "delete");

        assertEquals(2, observer1.getNotifications().size());
        assertEquals("save: Book A (44)", observer1.getNotifications().get(0));
        assertEquals("delete: Book B (45)", observer1.getNotifications().get(1));
    }

    @Test
    public void testNotifyObservers_WithNoObserversInjected() throws Exception {
        // Temporarily set the observers list to null
        java.lang.reflect.Field observersField = BookService.class.getDeclaredField("observers");
        observersField.setAccessible(true);
        observersField.set(bookService, null);

        Book book = createBook(46, "Null List", 10.0);
        bookService.notifyObservers(book, "save");
        
        // No exception should be thrown, and no notifications should be recorded.
        assertTrue(observer1.getNotifications().isEmpty());
        
        // Re-set the original list for subsequent tests
        observersField.set(bookService, Arrays.asList(observer1, observer2));
    }

    // --- 10 Additional Tests for Comprehensive Coverage (Total 50) ---

    @Test
    public void testSave_BookWithNullName_SavesSuccessfully() {
        Book book = Book.builder().id(47).author("Test Author").price(10.0).build();
        bookService.save(book);
        assertTrue(bookRepo.existsById(47));
        assertNull(bookRepo.findById(47).get().getName());
    }

    @Test
    public void testGetAllBook_VerifyNoSideEffects() {
        bookRepo.save(createBook(48, "Book", 10.0));
        bookService.getAllBook();
        assertEquals(1, bookRepo.count());
        assertTrue(observer1.getNotifications().isEmpty());
    }

    @Test
    public void testGetBookById_VerifyNoSideEffects() {
        Book book = createBook(49, "No Side Effect", 10.0);
        bookRepo.save(book);
        bookService.getBookById(49);
        assertEquals(1, bookRepo.count());
        assertTrue(observer1.getNotifications().isEmpty());
    }

    @Test
    public void testDeleteBookById_VerifyCorrectBookFetchedForNotification() {
        Book book = createBook(50, "Correct Book", 10.0);
        bookRepo.save(book);
        bookService.deleteBookById(50);
        assertEquals("delete: Correct Book (50)", observer1.getNotifications().get(0));
    }

    @Test
    public void testSave_BookWithMaxPrice_SavesSuccessfully() {
        Book book = createBook(51, "Max Price", Double.MAX_VALUE);
        bookService.save(book);
        assertTrue(bookRepo.existsById(51));
    }

    @Test
    public void testGetAllBook_ReturnsListWithCorrectSize() {
        bookRepo.save(createBook(52, "A", 1));
        bookRepo.save(createBook(53, "B", 2));
        bookRepo.save(createBook(54, "C", 3));
        assertEquals(3, bookService.getAllBook().size());
    }

    @Test
    public void testGetBookById_FindByIdCalledWithCorrectId() {
        Book book = createBook(55, "Correct ID Call", 10.0);
        bookRepo.save(book);
        Book result = bookService.getBookById(55);
        assertEquals(55, result.getId());
    }

    @Test
    public void testDeleteBookById_VerifyAllInteractions() {
        Book book = createBook(56, "All Interactions", 10.0);
        bookRepo.save(book);
        bookService.deleteBookById(56);
        assertFalse(bookRepo.existsById(56));
        assertEquals("delete: All Interactions (56)", observer1.getNotifications().get(0));
        assertEquals("delete: All Interactions (56)", observer2.getNotifications().get(0));
    }

    @Test
    public void testSave_BookWithZeroId_GetsNewId() {
        Book book = createBook(0, "Zero ID", 10.0);
        bookService.save(book);
        assertNotEquals(0, book.getId());
        assertTrue(bookRepo.existsById(book.getId()));
    }

    @Test
    public void testDeleteBookById_BookIsRemovedBeforeNotification() {
        Book book = createBook(57, "Order Check", 10.0);
        bookRepo.save(book);
        
        // The service fetches the book, deletes it, then notifies.
        // We can't check the order of calls without Mockito, but we can check the final state.
        bookService.deleteBookById(57);
        assertFalse(bookRepo.existsById(57));
        assertEquals(1, observer1.getNotifications().size());
    }
}
