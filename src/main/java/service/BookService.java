package service;

import javafx.collections.ObservableList;
import models.BookModel;
import repositories.BookRepository;

public class BookService {

    private final BookRepository crudBookRepository;

    public BookService() {

        this.crudBookRepository = new BookRepository();
    }


    public ObservableList<BookModel> getAllBooks() {

       return crudBookRepository.getAllBooks();
    }


    public ObservableList<String> getAllGenres() {

        return crudBookRepository.getAllGenres();
    }

    public boolean addBook(BookModel bookModel, int genreId) {

        return crudBookRepository.insert(bookModel, genreId);
    }

    public boolean deleteBookById(int id) {

        return crudBookRepository.deleteBookById(id);
    }

    public boolean updateBook(BookModel bookModel, int genreId) {

        return crudBookRepository.updateBook(bookModel, genreId);
    }

}
