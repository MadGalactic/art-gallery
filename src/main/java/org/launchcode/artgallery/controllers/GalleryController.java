package org.launchcode.artgallery.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;



@Controller
public class GalleryController {

    @GetMapping("/")
    @ResponseBody
    public String displayHomepage(){
        return "<h2>ML Gallery</h2>" +
                "<p>Welcome. View the available <a href='/artworks'>collection</a></p>";
    }

}
