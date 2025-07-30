package com.api.Markteplace_de_livros.service;

import com.api.Markteplace_de_livros.model.Livro;
import com.api.Markteplace_de_livros.model.Vendedor;
import com.api.Markteplace_de_livros.model.VendedorLivro;
import com.api.Markteplace_de_livros.repository.LivroRepository;
import com.api.Markteplace_de_livros.repository.VendedorLivroRepository;
import com.api.Markteplace_de_livros.repository.VendedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class VendedorLivroService {

    @Autowired
    private VendedorLivroRepository vendedorLivroRepository;

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private VendedorRepository vendedorRepository;

    public VendedorLivro salvarVendedorLivro(Integer livroId, Integer vendedorId,
                                             String condicao, String formato,
                                             BigDecimal preco, String idioma) {

        Livro livro = livroRepository.findById(livroId)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado"));
        Vendedor vendedor = vendedorRepository.findById(vendedorId)
                .orElseThrow(() -> new RuntimeException("Vendedor não encontrado"));

        // Buscar vínculo existente
        Optional<VendedorLivro> optionalVL = vendedorLivroRepository.findByLivroAndVendedor(livro, vendedor);

        VendedorLivro vl;
        if (optionalVL.isPresent()) {
            vl = optionalVL.get();
            vl.setCondicao(condicao);
            vl.setFormato(formato);
            vl.setPreco(preco);
            vl.setIdioma(idioma);
        } else {
            vl = new VendedorLivro();
            vl.setLivro(livro);
            vl.setVendedor(vendedor);
            vl.setCondicao(condicao);
            vl.setFormato(formato);
            vl.setPreco(preco);
            vl.setIdioma(idioma);
        }

        return vendedorLivroRepository.save(vl);
    }
}
