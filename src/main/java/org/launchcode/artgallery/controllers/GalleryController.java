package org.launchcode.artgallery.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class GalleryController {

    private static int nextId = 6;

    private static final Map<Integer, String> artworks = new HashMap<>() {{
          put(1, "Woman in Orange");
          put(2, "Couple dining");
          put(3, "The Red Parka");
          put(4, "In Time");
          put(5, "Greener Pastures");
        }};

    @GetMapping("/")
    @ResponseBody
    public String displayHomepage(){
        return "<h2>ML Gallery</h2>" +
                "<p>Welcome. View the available <a href='/artworks'>collection</a></p>";
    }

    @GetMapping("/artworks")
    @ResponseBody
    public String displayArtworksHomePage(){
        StringBuilder artworksList = new StringBuilder();
        for (int artworkId : artworks.keySet()) {
            String artwork = artworks.get(artworkId);
            artworksList.append("<li><a href='/artworks/details/").append(artworkId).append("'>").append(artwork).append("</a>");
        }

        return "<html>" +
                "<body>" +
                "<h2>Artworks</h2>" +
                "<ul>" +
                artworksList +
                "</ul>" +
                "<p>Click <a href='/artworks/add'>here</a> to add another artwork.</p>" +
                "</body>" +
                "</html>";
    }

    @GetMapping("/artworks/add")
    @ResponseBody
    public String addArtwork(@RequestParam String artwork) {
        artworks.put(nextId, artwork);
        nextId++;
        return "<html>" +
                "<body>" +
                "<p>You have successfully added " + artwork + " to the collection.</p>" +
                "<p><a href='/artworks'> view the updated list</a></p>" +
                "</body>" +
                "</html>";
    }

    @GetMapping("/artworks/details/{artworkId}")
    @ResponseBody
    public String displayArtworkDetails(@PathVariable int artworkId){
        return "<html>" +
                "<body>" +
                "<h3>Artwork</h3>" +
                "<p><b>ID:</b>" + artworkId + "</p>" +
                "<p><b>Name:</b>" + artworks.get(artworkId) + "</p>" +
                "</body>" +
                "</html>";
    }



}
