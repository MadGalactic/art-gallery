package org.launchcode.artgallery.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/artworks")
@ResponseBody
public class ArtworkController {

    private static int nextId = 6;

    private static final Map<Integer, String> artworks = new HashMap<>() {{
        put(1, "Woman in Orange");
        put(2, "Couple dining");
        put(3, "The Red Parka");
        put(4, "In Time");
        put(5, "Greener Pastures");
    }};

    @GetMapping("")
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

    @GetMapping("/add")
    public String displayAddArtworkForm() {
        return "<html>" +
                "<body>" +
                "<form action='/artworks/add' method='POST'>" +
                "<p>Enter the name of a new work of art:</p>" +
                "<input type='text' name='artwork' />" +
                "<button type='submit'>Submit</button>" +
                "</form>" +
                "</body>" +
                "</html>";
    }

    @PostMapping("/add")
    public String addArtwork(@RequestParam String artwork) {
        artworks.put(nextId, artwork);
        nextId++;
        return "<html>" +
                "<body>" +
                "<h3>ARTWORK ADDED</h3>" +
                "<p>You have successfully added " + artwork + " to the collection.</p>" +
                "<p><a href='/artworks/add'>Add another artwork</a> or<a href='/artworks'> view the updated list</a> of artwork</p>" +
                "</body>" +
                "</html>";
    }

    @GetMapping("/details/{artworkId}")
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
