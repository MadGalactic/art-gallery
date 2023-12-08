package org.launchcode.artgallery.models;

import java.util.Objects;

public class Artwork {

    private static int nextid = 1;

    private final int id;
    private String title;
    private String artist;

    public Artwork(String title, String artist) {
        this.id = nextid;
        this.title = title;
        this.artist = artist;
        nextid++;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return title + " (" + artist + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Artwork artwork = (Artwork) o;
        return id == artwork.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}