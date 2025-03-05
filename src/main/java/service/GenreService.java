package service;

import models.GenreModel;
import models.MemberModel;
import repositories.GenreRepository;

public class GenreService {

    private GenreRepository genreRepository;

    public GenreService() {
        genreRepository = new GenreRepository();
    }

    public GenreModel getGenreByName(String genreName) {

       return genreRepository.getGenreByName(genreName);
    }

    public boolean addGenre(GenreModel genreModel) {

        return genreRepository.insert(genreModel);
    }

    public boolean deleteGenre(String genreName) {

        return genreRepository.deleteGenre(genreName);
    }

    public boolean genreExists(String genreName) {

        return genreRepository.genreExists(genreName);
    }
}
