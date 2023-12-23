package org.launchcode.artgallery.controllers;

import jakarta.validation.Valid;
import org.launchcode.artgallery.data.ArtistRepository;
import org.launchcode.artgallery.data.ArtworkRepository;
import org.launchcode.artgallery.data.ArtworksData;
import org.launchcode.artgallery.models.Artist;
import org.launchcode.artgallery.models.Artwork;
import org.launchcode.artgallery.models.Style;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/artworks")
public class ArtworkController {

    @Autowired
    private ArtworkRepository artworkRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @GetMapping("")
    public String displayArtworksHomePage(@RequestParam(required = false) Integer artistId, Model model){
        if (artistId != null) {
            Optional<Artist> result = artistRepository.findById(artistId);
            if (result.isPresent()) {
                Artist artist = result.get();
                model.addAttribute("artworks", artist.getArtworks());
            }
        } else{
            model.addAttribute("artworksList", artworkRepository.findAll());
        }
        return "artworks/index";
    }

    @GetMapping("/add")
    public String displayAddArtworkForm(Model model) {
        model.addAttribute(new Artwork());
        model.addAttribute("artists", artistRepository.findAll());// lets the controller know what the model has and what its fields are
        model.addAttribute("styles", Style.values());
        return "artworks/add";
    }

    @PostMapping("/add")
    public String addArtwork(@ModelAttribute @Valid Artwork artwork, Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("styles", Style.values());
            return "artworks/add";
        } else {
            artworkRepository.save(artwork);
            return "redirect:/artworks";
        }

    }

    @GetMapping("/delete")
    public String renderDeleteArtForm(Model model) {
        model.addAttribute("artworkList", artworkRepository.findAll());
        return "artworks/delete";
    }

    @PostMapping("/delete")
    public String processDeleteArtForm(@RequestParam(required = false) int[] artworkIds) {
        for (int id : artworkIds) {
            artworkRepository.deleteById(id);
        }
        return "redirect:/artworks";
    }

}
