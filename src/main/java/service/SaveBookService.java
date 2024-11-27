package service;

import javafx.collections.ObservableList;
import models.BookModel;
import repositories.SaveBookRepository;

public class SaveBookService {

    private final SaveBookRepository saveBookRepository;

    public SaveBookService() {

        saveBookRepository = new SaveBookRepository();
    }


    public ObservableList<BookModel> loadTableViewData() {

       return saveBookRepository.loadTableViewData();
    }


    public ObservableList<String> getAllGenres() {

        return saveBookRepository.getAllGenres();
    }


}
