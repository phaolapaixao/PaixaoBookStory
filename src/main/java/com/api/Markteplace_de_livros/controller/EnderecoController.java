package com.api.Markteplace_de_livros.controller;

import com.api.Markteplace_de_livros.model.Cliente;
import com.api.Markteplace_de_livros.model.Endereco;
import com.api.Markteplace_de_livros.repository.ClienteRepository;
import com.api.Markteplace_de_livros.repository.EnderecoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/clientes")
public class EnderecoController {

    private final EnderecoRepository enderecoRepository;
    private final ClienteRepository clienteRepository;

    public EnderecoController(EnderecoRepository enderecoRepository, ClienteRepository clienteRepository) {
        this.enderecoRepository = enderecoRepository;
        this.clienteRepository = clienteRepository;
    }

    @GetMapping("/enderecos/{clienteId}")
    public String mostrarFormularioEndereco(@PathVariable Integer clienteId, Model model) {
        Optional<Cliente> clienteOpt = clienteRepository.findById(clienteId);
        if (clienteOpt.isEmpty()) {
            return "redirect:/erro";
        }

        Cliente cliente = clienteOpt.get();
        model.addAttribute("cliente", cliente);

        Endereco endereco = cliente.getEndereco() != null ? cliente.getEndereco() : new Endereco();
        model.addAttribute("endereco", endereco);

        return "clientes/enderecos";
    }

    @PostMapping("/enderecos/{clienteId}")
    public String cadastrarOuEditarEndereco(
            @PathVariable Integer clienteId,
            @ModelAttribute Endereco endereco,
            RedirectAttributes redirectAttributes) {

        Optional<Cliente> clienteOpt = clienteRepository.findById(clienteId);
        if (clienteOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("erro", "Cliente não encontrado.");
            return "redirect:/clientes/index";
        }

        try {
            Cliente cliente = clienteOpt.get();

            boolean edicao = endereco.getId() != null;

            Endereco enderecoSalvo = enderecoRepository.save(endereco);
            cliente.setEndereco(enderecoSalvo);
            clienteRepository.save(cliente);

            if (edicao) {
                redirectAttributes.addFlashAttribute("sucesso", "Endereço atualizado com sucesso!");
            } else {
                redirectAttributes.addFlashAttribute("sucesso", "Endereço cadastrado com sucesso!");
            }

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Ocorreu um erro ao salvar o endereço.");
        }

        return "redirect:/clientes/enderecos/" + clienteId;
    }

    @GetMapping("/{clienteId}/visualizar")
    public String visualizarEndereco(@PathVariable Integer clienteId, Model model) {
        Optional<Cliente> clienteOpt = clienteRepository.findById(clienteId);
        if (clienteOpt.isEmpty()) {
            return "redirect:/erro";
        }

        Cliente cliente = clienteOpt.get();
        Endereco endereco = cliente.getEndereco();

        if (endereco == null) {
            return "redirect:/clientes/enderecos/" + clienteId + "?semEndereco";
        }

        model.addAttribute("cliente", cliente);
        model.addAttribute("endereco", endereco);

        return "clientes/visualizarEndereco";
    }
}

