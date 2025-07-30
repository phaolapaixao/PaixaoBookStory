package com.api.Markteplace_de_livros.controller;

import com.api.Markteplace_de_livros.dto.LivroClienteDTO;
import com.api.Markteplace_de_livros.dto.LoginClienteDTO;
import com.api.Markteplace_de_livros.dto.RegistroClienteDTO;
import com.api.Markteplace_de_livros.dto.LivroVendedorDTO;
import com.api.Markteplace_de_livros.facade.LivroFacade;
import com.api.Markteplace_de_livros.model.Categoria;
import com.api.Markteplace_de_livros.model.Cliente;
import com.api.Markteplace_de_livros.service.ClienteAuthService;
import com.api.Markteplace_de_livros.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        this.livroFacade = livroFacade;
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
    public String paginaInicialCliente(
            @RequestParam(required = false) String idioma,
            @RequestParam(required = false) String formato,
            @RequestParam(required = false) String preco,  // "asc", "desc" ou null
            Model model, Principal principal) {

        if (principal != null) {
            String email = principal.getName();
            Cliente cliente = clienteService.findByEmail(email);
            model.addAttribute("cliente", cliente);
        }

        List<LivroClienteDTO> livros;
        if (idioma != null || formato != null || preco != null) {
            livros = livroFacade.buscarPorFiltros(idioma, formato, preco);
        } else {
            livros = livroFacade.buscarLivrosParaCliente();
        }

        List<Categoria> categorias = livroFacade.buscarCategorias();
        model.addAttribute("categorias", categorias);
        model.addAttribute("livros", livros);

        return "clientes/index";
    }
    
    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("registroDTO", new RegistroClienteDTO());
        return "clientes/registro";
    }

    @PostMapping("/registro")
    public String registrarCliente(
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
            return "redirect:/clientes/login?registroSuccess";
        } catch (RuntimeException ex) {
            model.addAttribute("erro", ex.getMessage());
            return "clientes/registro";
        }
    }

    @GetMapping("/pesquisar")
    public String pesquisarLivros(@RequestParam("q") String query, Model model, Principal principal) {

        if (principal != null) {
            String email = principal.getName();
            Cliente cliente = clienteService.findByEmail(email);
            model.addAttribute("cliente", cliente);
        }

        List<LivroClienteDTO> livrosEncontrados = livroFacade.pesquisarLivrosParaCliente(query);
        model.addAttribute("livros", livrosEncontrados);

        return "clientes/index";
    }
    @GetMapping("/perfil")
    public String perfilUsuario(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/clientes/login";
        }

        String email = principal.getName();
        Cliente cliente = clienteService.findByEmail(email);
        model.addAttribute("cliente", cliente);
        return "clientes/perfil";
    }

    @PostMapping("/perfil/editar")
    public String editarPerfil(@RequestParam String nome,
                               @RequestParam String email,
                               Principal principal,
                               Model model) {
        if (principal == null) {
            return "redirect:/clientes/login";
        }

        String emailLogado = principal.getName();
        Cliente cliente = clienteService.findByEmail(emailLogado);

        cliente.setNome(nome);
        cliente.setEmail(email);

        clienteService.salvarCliente(cliente);

        model.addAttribute("cliente", cliente);
        model.addAttribute("msgSucesso", "Perfil atualizado com sucesso!");
        return "clientes/perfil";
    }

    @PostMapping("/perfil/apagar")
    public String apagarConta(Principal principal, RedirectAttributes redirectAttributes) {
        if (principal == null) {
            return "redirect:/clientes/login";
        }

        String email = principal.getName();
        Cliente cliente = clienteService.findByEmail(email);

        if (clienteService.temPedidosPendentes(cliente.getId())) {
            redirectAttributes.addFlashAttribute("erro", "Você não pode apagar sua conta porque possui pedidos pendentes.");
            return "redirect:/clientes/perfil";
        }

        clienteService.delete(cliente.getId());

        redirectAttributes.addFlashAttribute("sucesso", "Conta apagada com sucesso.");
        return "redirect:/clientes/logout?contaApagada";
    }
    @Controller
    @RequestMapping("/clientes")
    public class ClienteController {
        @GetMapping("/livros/categoria/{id}")
        public String listarPorCategoria(@PathVariable Integer id, Model model) {
            List<LivroClienteDTO> livros = livroFacade.buscarPorCategoria(id);
            model.addAttribute("livros", livros);
            return "clientes/index";
        }
    }
    @GetMapping("/livros/filtros")
    public String filtrarLivros(
            @RequestParam(required = false) String idioma,
            @RequestParam(required = false) String formato,
            @RequestParam(required = false) String preco,
            Model model) {

        List<LivroClienteDTO> livrosFiltrados = livroFacade.buscarPorFiltros(idioma, formato, preco);
        model.addAttribute("livros", livrosFiltrados);

        List<Categoria> categorias = livroFacade.buscarCategorias();
        model.addAttribute("categorias", categorias);

        return "clientes/index";
    }

}
