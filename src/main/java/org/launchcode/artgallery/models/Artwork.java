package org.launchcode.artgallery.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public class Artwork {

    private static int nextid = 1;

    private int id;
    @NotBlank(message = "Title is required.")
    private String title;
   @Size(min = 2, max = 30, message ="Artist name must be 2-10 characters wrong")
    private String artist;


   // No-arg constructor that lets the form know about the existence of the fields when the page loads, before the user submits anything
   public Artwork() {
       this.id = nextid;
       nextid++;
   }

   @NotNull(message="Style is required")
   private Style style;

    public Artwork(String title, String artist, Style style) {
        this.title = title;
        this.artist = artist;
        this.style = style;
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

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
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
