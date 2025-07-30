package com.api.Markteplace_de_livros.controller;

import com.api.Markteplace_de_livros.dto.ItemCarrinhoDTO;
import com.api.Markteplace_de_livros.dto.PedidoRequestDTO;
import com.api.Markteplace_de_livros.model.*;
import com.api.Markteplace_de_livros.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("clientes")
public class CarrinhoController {

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @Autowired
    private ItemCarrinhoRepository itemCarrinhoRepository;

    @Autowired
    private VendedorLivroRepository vendedorLivroRepository;

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @GetMapping("/carrinho")
    public String carrinho() {
        return "clientes/carrinho";
    }

    @GetMapping
    public String listarLivrosParaClientes(Model model) {
        List<VendedorLivro> ofertas = vendedorLivroRepository.findAll();
        model.addAttribute("ofertas", ofertas);
        return "clientes/index";
    }

    @PostMapping("/carrinho/adicionar")
    @ResponseBody
    public ResponseEntity<?> adicionarItem(
            @RequestParam Integer vendedorLivroId,
            @RequestParam int quantidade,
            Principal principal) {

        try {
            Cliente cliente = clienteRepository.findByEmail(principal.getName())
                    .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

            Carrinho carrinho = carrinhoRepository.findByClienteAndStatus(cliente, "ATIVO")
                    .orElseGet(() -> {
                        Carrinho novo = new Carrinho();
                        novo.setCliente(cliente);
                        return carrinhoRepository.save(novo);
                    });

            VendedorLivro vendedorLivro = vendedorLivroRepository.findById(vendedorLivroId)
                    .orElseThrow(() -> new RuntimeException("Oferta não encontrada"));

            ItemCarrinho item = new ItemCarrinho();
            item.setCarrinho(carrinho);
            item.setLivro(vendedorLivro.getLivro());
            item.setVendedorLivro(vendedorLivro);
            item.setQuantidade(quantidade);
            item.setPrecoUnitario(vendedorLivro.getPreco());

            itemCarrinhoRepository.save(item);

            return ResponseEntity.ok(carrinhoRepository.findById(carrinho.getId()).get());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao adicionar item: " + e.getMessage());
        }
    }

    @GetMapping("/carrinho/buscar")
    @ResponseBody
    public ResponseEntity<?> buscarCarrinho(Principal principal) {
        try {
            Cliente cliente = clienteRepository.findByEmail(principal.getName())
                    .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

            Carrinho carrinho = carrinhoRepository.findByClienteAndStatus(cliente, "ATIVO")
                    .orElseGet(() -> {
                        Carrinho novo = new Carrinho();
                        novo.setCliente(cliente);
                        return carrinhoRepository.save(novo);
                    });

            carrinho.getItens().forEach(item -> item.getLivro().getTitulo());

            return ResponseEntity.ok(carrinho);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao buscar carrinho: " + e.getMessage());
        }
    }

    @PostMapping("/carrinho/finalizar")
    @ResponseBody
    public ResponseEntity<String> finalizarCarrinho(
            @RequestBody PedidoRequestDTO pedidoDTO,
            Principal principal) {

        try {
            Cliente cliente = clienteRepository.findByEmail(principal.getName())
                    .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

            Pedido pedido = new Pedido();
            pedido.setCliente(cliente);
            pedido.setStatus("PENDENTE");
            pedido.setFormaPagamento(pedidoDTO.getFormaPagamento());
            pedido.setValorTotal(BigDecimal.ZERO);
            pedido.setDataPedido(LocalDate.now());
            pedido = pedidoRepository.save(pedido);

            BigDecimal total = BigDecimal.ZERO;

            for (ItemCarrinhoDTO itemDTO : pedidoDTO.getItens()) {
                ItemPedido itemPedido = new ItemPedido();
                itemPedido.setPedido(pedido);

                Livro livro = livroRepository.findById(itemDTO.getLivroId())
                        .orElseThrow(() -> new RuntimeException("Livro não encontrado"));

                itemPedido.setLivro(livro);
                itemPedido.setQuantidade(itemDTO.getQuantidade());
                itemPedido.setPrecoUnitario(itemDTO.getPreco());

                total = total.add(
                        itemDTO.getPreco().multiply(BigDecimal.valueOf(itemDTO.getQuantidade()))
                );

                itemPedidoRepository.save(itemPedido);
            }

            pedido.setValorTotal(total);
            pedidoRepository.save(pedido);

            Carrinho carrinho = carrinhoRepository.findByClienteAndStatus(cliente, "ATIVO").orElse(null);
            if (carrinho != null) {
                carrinho.setStatus("FINALIZADO");
                carrinhoRepository.save(carrinho);
            }

            return ResponseEntity.ok("Pedido criado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao finalizar pedido: " + e.getMessage());
        }
    }
}
