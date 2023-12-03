package org.launchcode.artgallery.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@Controller
public class GalleryController {

    @GetMapping("/")
    public String displayHomepage(Model model){
        model.addAttribute("headingText", "Welcome");
        return "index";
    }

}
