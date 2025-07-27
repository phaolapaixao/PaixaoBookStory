package com.api.Markteplace_de_livros.service;

import com.api.Markteplace_de_livros.dto.LivroClienteDTO;
import com.api.Markteplace_de_livros.model.*;
import com.api.Markteplace_de_livros.repository.AutorRepository;
import com.api.Markteplace_de_livros.repository.LivroRepository;
import com.api.Markteplace_de_livros.repository.CategoriaRepository;
import com.api.Markteplace_de_livros.repository.VendedorLivroRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private CategoriaRepository categoriasRepository;

    @Autowired
    private VendedorLivroRepository vendedoresLivrosRepository;

    @Autowired
    private AutorRepository autoresRepository;

    public Livro save(Livro livro) {
        return livroRepository.save(livro);
    }

    public VendedorLivro save(VendedorLivro vendedorLivro) {
        return vendedoresLivrosRepository.save(vendedorLivro);
    }

    public List<Livro> findAll() {
        return livroRepository.findAll();
    }

    public Optional<Livro> findById(Integer id) {
        return livroRepository.findById(id);
    }

    public void deleteById(Integer id) {
        livroRepository.deleteById(id);
    }

    public List<Livro> findLivrosByVendedor(Vendedor vendedor) {
        return livroRepository.findByVendedor(vendedor);
    }

    @Transactional
    public void salvarLivros(Livro livro) {
        if (livro.getId() != null) {
            Livro livroExistente = livroRepository.findById(livro.getId())
                    .orElseThrow(() -> new RuntimeException("Livro não encontrado"));

            livroExistente.setTitulo(livro.getTitulo());
            livroExistente.setIsbn(livro.getIsbn());
            livroExistente.setAno_publicacao(livro.getAno_publicacao());
            livroExistente.setEditora(livro.getEditora());

            livroExistente.getCategorias().clear();
            livroExistente.getAutores().clear();

            for (Categoria categoria : livro.getCategorias()) {
                Categoria categoriaExistente = categoriasRepository.findById(categoria.getId())
                        .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
                livroExistente.getCategorias().add(categoriaExistente);
            }

            for (Autor autor : livro.getAutores()) {
                Autor autorExistente = autoresRepository.findById(autor.getId())
                        .orElseThrow(() -> new RuntimeException("Autor não encontrado"));
                livroExistente.getAutores().add(autorExistente);
            }

            livroRepository.save(livroExistente);
        } else {
            Set<Categoria> categoriasPersistidas = new HashSet<>();
            for (Categoria categoria : livro.getCategorias()) {
                categoriasPersistidas.add(categoriasRepository.findById(categoria.getId())
                        .orElseThrow(() -> new RuntimeException("Categoria não encontrada")));
            }
            Set<Autor> autoresPersistidos = new HashSet<>();
            for (Autor autor : livro.getAutores()) {
                autoresPersistidos.add(autoresRepository.findById(autor.getId())
                        .orElseThrow(() -> new RuntimeException("Autor não encontrado")));
            }
            for (Categoria categoria : categoriasPersistidas) {
                categoria.getLivros().add(livro);
            }

            livro.setCategorias(categoriasPersistidas);
            livro.setAutores(autoresPersistidos);
            livroRepository.save(livro);
        }
    }

    public LivroClienteDTO converterParaDTO(Livro livro) {
        LivroClienteDTO dto = new LivroClienteDTO();
        dto.setId(livro.getId());
        dto.setTitulo(livro.getTitulo());
        dto.setIsbn(livro.getIsbn());
        dto.setAnoPublicacao(livro.getAno_publicacao() != null ? livro.getAno_publicacao().getValue() : null);
        dto.setImg(livro.getImg());

        if (livro.getEditora() != null) {
            dto.setNomeEditora(livro.getEditora().getNome());
        }

        dto.setCategorias(livro.getCategorias().stream()
                .map(c -> c.getNome())
                .collect(Collectors.toList()));

        dto.setAutores(livro.getAutores().stream()
                .map(a -> a.getNome())
                .collect(Collectors.toList()));

        List<LivroClienteDTO.VendedorLivroDTO> vendedores = livro.getVendedoresLivros().stream().map(v -> {
            LivroClienteDTO.VendedorLivroDTO vDto = new LivroClienteDTO.VendedorLivroDTO();
            vDto.setPreco(v.getPreco() != null ? v.getPreco().doubleValue() : null);
            vDto.setIdioma(v.getIdioma());
            vDto.setFormato(v.getFormato());
            vDto.setCondicao(v.getCondicao());
            vDto.setNomeFantasia(v.getVendedor().getNome_fantasia());
            return vDto;
        }).collect(Collectors.toList());

        dto.setVendedores(vendedores);

        return dto;
    }

    public List<LivroClienteDTO> buscarTodosParaCliente() {
        List<Livro> livros = livroRepository.findAll();
        return livros.stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }
}
