package org.example;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class BookServiceTest {
    public BookService bookService;
    public Book existingBook;
    public User user;

    @Before
    public void setUp() {
        bookService = new BookService();
        existingBook = Mockito.mock(Book.class);
        user = Mockito.mock(User.class);

        when(existingBook.getTitle()).thenReturn("Existing Book");
        when(existingBook.getAuthor()).thenReturn("John Doe");
        when(existingBook.getGenre()).thenReturn("Fiction");
        when(existingBook.getReviews()).thenReturn(new ArrayList<>());

        when(user.getPurchasedBooks()).thenReturn(new ArrayList<>());
    }

    @Test
    public void testSearchBook_ExistingKeyword() {
        bookService.addBook(existingBook);

        List<Book> searchResult = bookService.searchBook("Fiction");
        assertEquals(1, searchResult.size());
        assertEquals(existingBook, searchResult.get(0));
    }

    @Test
    public void testSearchBook_NonExistingKeyword() {
        List<Book> searchResult = bookService.searchBook("Non-existing");
        assertTrue(searchResult.isEmpty());
    }

    @Test
    public void testSearchBook_EmptyKeyword() {
        List<Book> searchResult = bookService.searchBook("");
        assertTrue(searchResult.isEmpty());
    }

    @Test
    public void testPurchaseBook_ExistingBook() {
        bookService.addBook(existingBook);

        boolean purchaseSuccessful = bookService.purchaseBook(user, existingBook);
        assertTrue(purchaseSuccessful);
    }

    @Test
    public void testPurchaseBook_NonExistingBook() {
        Book nonExistingBook = Mockito.mock(Book.class);

        boolean purchaseSuccessful = bookService.purchaseBook(user, nonExistingBook);
        assertFalse(purchaseSuccessful);
    }

    @Test
    public void testAddBookReview_UserHasPurchasedBook() {
        bookService.addBook(existingBook);
        when(user.getPurchasedBooks()).thenReturn(List.of(existingBook));

        boolean reviewAdded = bookService.addBookReview(user, existingBook, "Great book!");
        assertTrue(reviewAdded);
        assertEquals(1, existingBook.getReviews().size());
        assertEquals("Great book!", existingBook.getReviews().get(0));
    }

    @Test
    public void testAddBookReview_UserHasNotPurchasedBook() {
        boolean reviewAdded = bookService.addBookReview(user, existingBook, "Great book!");
        assertFalse(reviewAdded);
        assertEquals(0, existingBook.getReviews().size());
    }

    @Test
    public void testAddBook_DuplicateBook() {
        bookService.addBook(existingBook);

        Book duplicateBook = Mockito.mock(Book.class);
        when(duplicateBook.getTitle()).thenReturn("Existing Book");
        when(duplicateBook.getAuthor()).thenReturn("John Doe");
        when(duplicateBook.getGenre()).thenReturn("Fiction");

        boolean bookAdded = bookService.addBook(duplicateBook);
        assertTrue(bookAdded);
    }

    @Test
    public void testRemoveBook_ExistingBook() {
        bookService.addBook(existingBook);

        boolean bookRemoved = bookService.removeBook(existingBook);
        assertTrue(bookRemoved);
    }

    @Test
    public void testRemoveBook_NonExistingBook() {
        Book nonExistingBook = Mockito.mock(Book.class);

        boolean bookRemoved = bookService.removeBook(nonExistingBook);
        assertFalse(bookRemoved);
    }

    @Test
    public void testGetBooks() {
        bookService.addBook(existingBook);

        List<Book> books = bookService.getBooks();
        assertEquals(1, books.size());
        assertEquals(existingBook, books.get(0));
    }
}

