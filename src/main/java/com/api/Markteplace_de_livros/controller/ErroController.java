package com.api.Markteplace_de_livros.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErroController {

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "error/403"; // nome da sua view HTML
    }
}
