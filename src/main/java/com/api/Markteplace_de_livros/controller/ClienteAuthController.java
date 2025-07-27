package com.api.Markteplace_de_livros.controller;

import com.api.Markteplace_de_livros.dto.LivroClienteDTO;
import com.api.Markteplace_de_livros.dto.LoginClienteDTO;
import com.api.Markteplace_de_livros.dto.RegistroClienteDTO;
import com.api.Markteplace_de_livros.facade.LivroFacade;
import com.api.Markteplace_de_livros.model.Cliente;
import com.api.Markteplace_de_livros.service.ClienteAuthService;
import com.api.Markteplace_de_livros.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/clientes")
public class ClienteAuthController {

    private final ClienteAuthService clienteAuthService;
    private final ClienteService clienteService;
    private final LivroFacade livroFacade;

    public ClienteAuthController(ClienteAuthService clienteAuthService,
                                 ClienteService clienteService,
                                 LivroFacade livroFacade) {
        this.clienteAuthService = clienteAuthService;
        this.clienteService = clienteService;
        this.livroFacade = livroFacade; ;
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

        model.addAttribute("loginClienteDTO", new LoginClienteDTO());
        return "clientes/login";
    }

    @GetMapping("/index")
    public String paginaInicialCliente(Model model, Principal principal) {
        if (principal != null) {
            String email = principal.getName();
            Cliente cliente = clienteService.findByEmail(email);
            model.addAttribute("cliente", cliente);
        }

        List<LivroClienteDTO> livros = livroFacade.buscarLivrosParaCliente();
        model.addAttribute("livros", livros);

        return "clientes/index";
    }


    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("loginClienteDTO") LoginClienteDTO loginClienteDTO,
                        BindingResult bindingResult,
                        Model model) {

        if (bindingResult.hasErrors()) {
            return "clientes/login";
        }

        try {
            clienteAuthService.autenticar(loginClienteDTO.getEmail(), loginClienteDTO.getSenha());
            return "redirect:/clientes/index";
        } catch (RuntimeException ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("loginClienteDTO", loginClienteDTO);
            return "clientes/login";
        }
    }

    @GetMapping("/registro")
    public String showRegistrationForm(Model model) {
        model.addAttribute("registroDTO", new RegistroClienteDTO());
        return "clientes/registro";
    }

    @PostMapping("/registro")
    public String registerCliente(
            @Valid @ModelAttribute("registroDTO") RegistroClienteDTO registroDTO,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            return "clientes/registro";
        }

        if (!registroDTO.getSenha().equals(registroDTO.getConfirmarSenha())) {
            bindingResult.rejectValue("confirmarSenha", "error.registroDTO", "As senhas não coincidem");
            return "clientes/registro";
        }

        try {
            clienteAuthService.registerCliente(registroDTO);
            return "redirect:/clientes/auth/login?registroSuccess";
        } catch (RuntimeException ex) {
            model.addAttribute("erro", ex.getMessage());
            model.addAttribute("registroDTO", registroDTO);
            return "clientes/registro";
        }
    }
}
