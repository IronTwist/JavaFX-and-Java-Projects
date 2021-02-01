package Domain;

public class Movie extends Entity {

    private String title;
    private int movieReleaseYear;
    private int price;
    private boolean isInProgram;
    String isRunningValue;

    public Movie(String title, int movieReleaseYear, int price, boolean isInProgram) {
        this(-1, title, movieReleaseYear, price, isInProgram);
        this.isRunningValue = Boolean.toString(isInProgram());
    }

    public Movie(int id, String title, int movieReleaseYear, int price, boolean isInProgram) {
        super(id);
        this.title = title;
        this.movieReleaseYear = movieReleaseYear;
        this.price = price;
        this.isInProgram = isInProgram;
        this.isRunningValue = Boolean.toString(isInProgram());
    }

    public String getIsRunningValue() {
        return isRunningValue;
    }

    public void setIsRunningValue(String isRunningValue) {
        this.isRunningValue = isRunningValue;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getMovieReleaseYear() {
        return movieReleaseYear;
    }

    public void setMovieReleaseYear(int movieReleaseYear) {
        this.movieReleaseYear = movieReleaseYear;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isInProgram() {
        return isInProgram;
    }

    public void setInProgram(boolean inProgram) {
        isInProgram = inProgram;
        this.isRunningValue = Boolean.toString(isInProgram());
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + getId() +
                ", title='" + title + '\'' +
                ", movieReleaseYear=" + movieReleaseYear +
                ", price=" + price +
                ", isInProgram=" + isInProgram +
                '}';
    }
}
