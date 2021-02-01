package ro.ubb.lab6x.model;

import java.util.Objects;

public class Song extends Entity<Long> {
    private String title;
    private int yearOfRelease;
    private double price;


    public Song(String title, int yearOfRelease, double price) {
        this.title = title;
        this.yearOfRelease = yearOfRelease;
        this.price = price;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYearOfRelease() {
        return yearOfRelease;
    }

    public void setYearOfRelease(int yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Song)) return false;
        Song song = (Song) o;
        return getYearOfRelease() == song.getYearOfRelease() &&
                Double.compare(song.getPrice(), getPrice()) == 0 &&
                getTitle().equals(song.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getYearOfRelease(), getPrice());
    }

    @Override
    public String toString() {
        return "Song{" +
                "id= " + getId() +
                " title='" + title + '\'' +
                ", yearOfRelease=" + yearOfRelease +
                ", price=" + price +
                '}' + '\n';
    }
}
