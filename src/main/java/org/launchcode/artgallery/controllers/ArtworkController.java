package org.launchcode.artgallery.controllers;

import org.launchcode.artgallery.data.ArtworksData;
import org.launchcode.artgallery.models.Artwork;
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


    @GetMapping("")
    public String displayArtworksHomePage(Model model){
        model.addAttribute("artworksList", ArtworksData.getAll());
        return "artworks/index";
    }

    @GetMapping("/add")
    public String displayAddArtworkForm() {
        return "artworks/add";
    }

    @PostMapping("/add")
    public String addArtwork(@ModelAttribute Artwork artwork) {
        ArtworksData.add(artwork);
        return "redirect:/artworks";
    }

    @GetMapping("/delete")
    public String renderDeleteArtForm(Model model) {
        model.addAttribute("artworkList", ArtworksData.getAll());
        return "artworks/delete";
    }

    @PostMapping("/delete")
    public String processDeleteArtForm(@RequestParam(required = false) int[] artworkIds) {
        for (int id : artworkIds) {
            ArtworksData.remove(id);
        }
        return "redirect:/artworks";
    }

}
