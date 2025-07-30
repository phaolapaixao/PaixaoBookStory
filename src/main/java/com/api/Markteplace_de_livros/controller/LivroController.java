package com.api.Markteplace_de_livros.controller;

import com.api.Markteplace_de_livros.model.*;
import com.api.Markteplace_de_livros.repository.*;
import com.api.Markteplace_de_livros.service.LivroService;
import com.api.Markteplace_de_livros.security.VendedorDetailsImpl;
import com.api.Markteplace_de_livros.service.VendedorLivroService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/livros")
public class LivroController {

    private final LivroService livroService;
    private final CategoriaRepository categoriasRepository;
    private final EditoraRepository editoraRepository;
    private final AutorRepository autoresRepository;
    private final VendedorLivroRepository vendedoresLivrosRepository;
    private final VendedorRepository vendedorRepository;
    private final VendedorLivroService vendedorLivroService;

    public LivroController(LivroService livroService,
                           CategoriaRepository categoriasRepository,
                           EditoraRepository editoraRepository,
                           AutorRepository autoresRepository,
                           VendedorLivroRepository vendedoresLivrosRepository,
                           VendedorRepository vendedorRepository,
                           VendedorLivroService vendedorLivroService) {
        this.livroService = livroService;
        this.categoriasRepository = categoriasRepository;
        this.editoraRepository = editoraRepository;
        this.autoresRepository = autoresRepository;
        this.vendedoresLivrosRepository = vendedoresLivrosRepository;
        this.vendedorRepository = vendedorRepository;
        this.vendedorLivroService = vendedorLivroService;
    }

    private Vendedor getVendedorLogado() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof VendedorDetailsImpl) {
            return ((VendedorDetailsImpl) principal).getVendedor();
        }

        throw new AccessDeniedException("Acesso negado: página disponível somente para vendedores.");
    }

    @GetMapping("/novo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("livro", new Livro());
        model.addAttribute("vendedorLivro", new VendedorLivro());
        model.addAttribute("todasCategorias", categoriasRepository.findAll());
        model.addAttribute("editoras", editoraRepository.findAll());
        model.addAttribute("autores", autoresRepository.findAll());
        return "livros/formulario";
    }

    @PostMapping("/salvar")
    public String salvarLivro(@ModelAttribute("livro") Livro livro,
                              @ModelAttribute("vendedorLivro") VendedorLivro vendedorLivro,
                              @RequestParam(required = false) List<Integer> categorias,
                              @RequestParam(required = false) List<Integer> autores,
                              @RequestParam(value = "imagem", required = false) MultipartFile imagem,
                              RedirectAttributes redirectAttributes) {

        if (categorias != null) {
            livro.setCategorias(new HashSet<>(categoriasRepository.findAllById(categorias)));
        } else {
            livro.setCategorias(new HashSet<>());
        }

        if (autores != null) {
            livro.setAutores(new HashSet<>(autoresRepository.findAllById(autores)));
        } else {
            livro.setAutores(new HashSet<>());
        }

        if (imagem != null && !imagem.isEmpty()) {
            try {
                String contentType = imagem.getContentType();
                if (contentType == null || !contentType.startsWith("image/")) {
                    redirectAttributes.addFlashAttribute("erro", "Por favor, envie apenas arquivos de imagem.");
                    return "redirect:/livros/novo";
                }

                long maxFileSize = 2 * 1024 * 1024; // 2MB
                if (imagem.getSize() > maxFileSize) {
                    redirectAttributes.addFlashAttribute("erro", "A imagem não pode exceder 2MB.");
                    return "redirect:/livros/novo";
                }

                byte[] bytes = imagem.getBytes();
                String base64Imagem = Base64.getEncoder().encodeToString(bytes);
                String base64ComPrefixo = "data:" + contentType + ";base64," + base64Imagem;

                livro.setImg(base64ComPrefixo);

            } catch (IOException e) {
                redirectAttributes.addFlashAttribute("erro", "Erro ao processar a imagem. Tente novamente.");
                return "redirect:/livros/novo";
            }
        } else if (livro.getId() != null) {
            Livro existente = livroService.findById(livro.getId()).orElse(null);
            if (existente != null) {
                livro.setImg(existente.getImg());
            }
        }

        try {
            Livro livroSalvo = livroService.save(livro);
            Vendedor vendedor = getVendedorLogado();

            vendedorLivroService.salvarVendedorLivro(
                    livroSalvo.getId(),
                    vendedor.getId(),
                    vendedorLivro.getCondicao(),
                    vendedorLivro.getFormato(),
                    vendedorLivro.getPreco(),
                    vendedorLivro.getIdioma()
            );

            redirectAttributes.addFlashAttribute("sucesso", "Livro cadastrado/atualizado com sucesso!");
            return "redirect:/livros";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Ocorreu um erro ao salvar o livro. Por favor, tente novamente.");
            return "redirect:/livros/novo";
        }
    }

    @GetMapping
    public String listarLivros(Model model) {
        Vendedor vendedor = getVendedorLogado();
        List<Livro> livros = livroService.findLivrosByVendedor(vendedor);
        model.addAttribute("livros", livros);
        return "livros/cards";
    }

    @GetMapping("/editar/{id}")
    public String editarLivro(@PathVariable Integer id, Model model) {
        Livro livro = livroService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID do livro inválido: " + id));

        Vendedor vendedor = getVendedorLogado();

        Optional<VendedorLivro> optionalVendedorLivro = vendedoresLivrosRepository.findByLivroAndVendedor(livro, vendedor);
        if (optionalVendedorLivro.isEmpty()) {
            throw new AccessDeniedException("Você não tem permissão para editar este livro.");
        }

        model.addAttribute("livro", livro);
        model.addAttribute("vendedorLivro", optionalVendedorLivro.get());
        model.addAttribute("todasCategorias", categoriasRepository.findAll());
        model.addAttribute("editoras", editoraRepository.findAll());
        model.addAttribute("autores", autoresRepository.findAll());

        return "livros/formulario";
    }

    @PostMapping("/excluir/{id}")
    public String excluirLivro(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        Livro livro = livroService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID do livro inválido: " + id));

        Vendedor vendedor = getVendedorLogado();

        Optional<VendedorLivro> optionalVendedorLivro = vendedoresLivrosRepository.findByLivroAndVendedor(livro, vendedor);
        if (optionalVendedorLivro.isEmpty()) {
            redirectAttributes.addFlashAttribute("erro", "Você não tem permissão para excluir este livro.");
            return "redirect:/livros";
        }

        vendedoresLivrosRepository.delete(optionalVendedorLivro.get());
        livroService.deleteById(id);

        redirectAttributes.addFlashAttribute("sucesso", "Livro excluído com sucesso.");
        return "redirect:/livros";
    }
}