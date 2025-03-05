package models;

public class GenreModel {

    private int id;
    private String genreName;

    public GenreModel(int id, String name) {
        this.id = id;
        this.genreName = name;
    }

    public GenreModel(String genreName) {
        this.genreName = genreName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }
}
