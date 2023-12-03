package org.launchcode.artgallery.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/artworks")
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
    public String displayArtworksHomePage(Model model){
        List<String> artworksList = new ArrayList<>(artworks.values());
        model.addAttribute("artworksList", artworksList);
        return "artworks/index";
    }

    @GetMapping("/add")
    public String displayAddArtworkForm() {
        return "artworks/add";
    }

    @PostMapping("/add")
    public String addArtwork(@RequestParam String artwork) {
        artworks.put(nextId, artwork);
        nextId++;
        return "redirect:/artworks";
    }

    @GetMapping("/details/{artworkId}")
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
