package service;

import javafx.collections.ObservableList;
import models.BookModel;
import repositories.CardRepository;

public class CardBookService {

    private final CardRepository cardRepository;

    public CardBookService() {

        this.cardRepository = new CardRepository();
    }

    public ObservableList<BookModel> recentlyAddedBooks() {

        return cardRepository.getRecentlyAddedBooks();
    }

}
