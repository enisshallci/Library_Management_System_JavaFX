package service;

import models.GenreModel;
import repositories.GenreRepository;

public class GenreService {

    private GenreRepository genreRepository;

    public GenreService() {
        genreRepository = new GenreRepository();
    }

    public GenreModel getGenreByName(String genreName) {

       return genreRepository.getGenreByName(genreName);
    }
}
