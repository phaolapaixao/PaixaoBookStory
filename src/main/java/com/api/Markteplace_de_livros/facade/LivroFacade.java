package com.api.Markteplace_de_livros.facade;

import com.api.Markteplace_de_livros.dto.LivroClienteDTO;
import com.api.Markteplace_de_livros.model.Categoria;
import com.api.Markteplace_de_livros.model.VendedorLivro;
import com.api.Markteplace_de_livros.repository.CategoriaRepository;
import com.api.Markteplace_de_livros.repository.VendedorLivroRepository;
import com.api.Markteplace_de_livros.service.CategoriaService;
import com.api.Markteplace_de_livros.service.LivroService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class LivroFacade {

    private final LivroService livroService;
    private final CategoriaRepository categoriaRepository;
    private final CategoriaService categoriaService;
    private final VendedorLivroRepository vendedorLivroRepository;

    public LivroFacade(LivroService livroService,
                       CategoriaRepository categoriaRepository,
                       CategoriaService categoriaService,
                       VendedorLivroRepository vendedorLivroRepository) {
        this.livroService = livroService;
        this.categoriaRepository = categoriaRepository;
        this.categoriaService = categoriaService;
        this.vendedorLivroRepository = vendedorLivroRepository;
    }

    public List<LivroClienteDTO> buscarLivrosParaCliente() {
        return livroService.buscarTodosParaCliente();
    }

    public List<LivroClienteDTO> pesquisarLivrosParaCliente(String termo) {
        return livroService.pesquisar(termo).stream()
                .map(livroService::converterParaDTO)
                .toList();
    }

    public List<LivroClienteDTO> buscarPorCategoria(Integer categoriaId) {
        return livroService.buscarPorCategoria(categoriaId);
    }

    public List<Categoria> buscarCategorias() {
        return categoriaRepository.findAll();
    }

    public Categoria buscarCategoriaPorId(Integer id) {
        return categoriaService.buscarCategoriaPorId(id);
    }

    public List<LivroClienteDTO> buscarPorFiltros(String idioma, String formato, String preco) {
        boolean temIdioma = idioma != null && !idioma.isBlank();
        boolean temFormato = formato != null && !formato.isBlank();
        boolean ordenarPreco = preco != null && (preco.equalsIgnoreCase("asc") || preco.equalsIgnoreCase("desc"));

        List<VendedorLivro> livrosFiltrados;

        if (ordenarPreco) {
            if (temIdioma && temFormato) {
                livrosFiltrados = preco.equalsIgnoreCase("asc")
                        ? vendedorLivroRepository.findByIdiomaAndFormatoOrderByPrecoAsc(idioma, formato)
                        : vendedorLivroRepository.findByIdiomaAndFormatoOrderByPrecoDesc(idioma, formato);
            } else if (temIdioma) {
                livrosFiltrados = vendedorLivroRepository.findAll().stream()
                        .filter(vl -> idioma.equalsIgnoreCase(vl.getIdioma()))
                        .toList();
                livrosFiltrados = preco.equalsIgnoreCase("asc")
                        ? livrosFiltrados.stream()
                        .sorted(Comparator.comparing(VendedorLivro::getPreco))
                        .toList()
                        : livrosFiltrados.stream()
                        .sorted(Comparator.comparing(VendedorLivro::getPreco).reversed())
                        .toList();
            } else if (temFormato) {
                livrosFiltrados = vendedorLivroRepository.findAll().stream()
                        .filter(vl -> formato.equalsIgnoreCase(vl.getFormato()))
                        .toList();
                livrosFiltrados = preco.equalsIgnoreCase("asc")
                        ? livrosFiltrados.stream()
                        .sorted(Comparator.comparing(VendedorLivro::getPreco))
                        .toList()
                        : livrosFiltrados.stream()
                        .sorted(Comparator.comparing(VendedorLivro::getPreco).reversed())
                        .toList();
            } else {
                livrosFiltrados = preco.equalsIgnoreCase("asc")
                        ? vendedorLivroRepository.findAllByOrderByPrecoAsc()
                        : vendedorLivroRepository.findAllByOrderByPrecoDesc();
            }
        } else {
            if (temIdioma && temFormato) {
                livrosFiltrados = vendedorLivroRepository.findByIdiomaAndFormato(idioma, formato);
            } else if (temIdioma) {
                livrosFiltrados = vendedorLivroRepository.findAll().stream()
                        .filter(vl -> idioma.equalsIgnoreCase(vl.getIdioma()))
                        .toList();
            } else if (temFormato) {
                livrosFiltrados = vendedorLivroRepository.findAll().stream()
                        .filter(vl -> formato.equalsIgnoreCase(vl.getFormato()))
                        .toList();
            } else {
                livrosFiltrados = vendedorLivroRepository.findAll();
            }
        }

        return livrosFiltrados.stream()
                .map(vl -> livroService.converterParaDTO(vl.getLivro()))
                .toList();
    }
}
