package models;

public class BookModel {

    private int id;
    private String bookTitle;
    private String bookAuthor;
    private String bookGenre;
    private int publishedYear;
    private String imageSrc;
    private int numberOfCopies;
    private int available;

    public BookModel(int id, String bookTitle, String bookAuthor, String bookGenre, int publishedYear, String imgSrc, int numberOfCopies) {
        this(bookTitle, bookAuthor, bookGenre, publishedYear, imgSrc, numberOfCopies);
        this.id = id;
    }

    public BookModel(String bookTitle, String bookAuthor, String bookGenre, int publishedYear, String imgSrc, int numberOfCopies) {
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.bookGenre = bookGenre;
        this.publishedYear = publishedYear;
        this.numberOfCopies = numberOfCopies;
        this.imageSrc = imgSrc;
        this.available = numberOfCopies;
    }

    public BookModel() {

    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getBookGenre() {
        return bookGenre;
    }

    public void setBookGenre(String bookGenre) {
        this.bookGenre = bookGenre;
    }

    public int getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(int publishedYear) {
        this.publishedYear = publishedYear;
    }

    public int getNumberOfCopies() {
        return numberOfCopies;
    }

    public void setNumberOfCopies(int numberOfCopies) {
        this.numberOfCopies = numberOfCopies;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }
}
