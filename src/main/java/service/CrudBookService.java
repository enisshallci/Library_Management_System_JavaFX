package service;

import javafx.collections.ObservableList;
import models.BookModel;
import repositories.CrudBookRepository;

public class CrudBookService {

    private final CrudBookRepository crudBookRepository;

    public CrudBookService() {

        this.crudBookRepository = new CrudBookRepository();
    }


    public ObservableList<BookModel> loadTableViewData() {

       return crudBookRepository.loadTableViewData();
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
