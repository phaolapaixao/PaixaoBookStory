package com.api.Markteplace_de_livros.controller;

import com.api.Markteplace_de_livros.dto.LoginDTO;
import com.api.Markteplace_de_livros.dto.RegistroDTO;
import com.api.Markteplace_de_livros.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/vendedores")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public String showLoginForm(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            Model model) {

        if (error != null) {
            model.addAttribute("error", "Email ou senha inválidos");
        }

        if (logout != null) {
            model.addAttribute("message", "Você foi desconectado com sucesso");
        }

        model.addAttribute("loginDTO", new LoginDTO());
        return "vendedores/login";
    }

    /*@PostMapping("/login")
    public String login(@Valid @ModelAttribute("loginDTO") LoginDTO loginDTO,
                        BindingResult bindingResult,
                        Model model,
                        RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "vendedores/login";
        }

        try {
            authService.autenticar(loginDTO.getEmail(), loginDTO.getSenha());
            return "redirect:/vendedores/index";
        } catch (RuntimeException ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("loginDTO", loginDTO);
            return "vendedores/login";
        }
    }*/

    @GetMapping("/registro")
    public String showRegistrationForm(Model model) {
        model.addAttribute("registroDTO", new RegistroDTO());
        return "vendedores/registro";
    }

    @PostMapping("/registro")
    public String registerVendedor(@Valid @ModelAttribute("registroDTO") RegistroDTO registroDTO,
                                   BindingResult bindingResult,
                                   Model model) {

        if (bindingResult.hasErrors()) {
            return "vendedores/registro";
        }

        if (!registroDTO.getSenha().equals(registroDTO.getConfirmarSenha())) {
            bindingResult.rejectValue("confirmarSenha", "error.registroDTO", "As senhas não coincidem");
            return "vendedores/registro";
        }

        try {
            authService.registerVendedor(registroDTO);
            return "redirect:/vendedores/login?registroSuccess";
        } catch (RuntimeException ex) {
            model.addAttribute("erro", ex.getMessage());
            model.addAttribute("registroDTO", registroDTO);
            return "vendedores/registro";
        }
    }
}
