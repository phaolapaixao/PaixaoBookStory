package com.api.Markteplace_de_livros.service;

import com.api.Markteplace_de_livros.model.Autor;
import com.api.Markteplace_de_livros.model.Vendedor;
import com.api.Markteplace_de_livros.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepository;

    public Autor save(Autor autor) {
        return autorRepository.save(autor);
    }

    public List<Autor> findAll() {
        return autorRepository.findAll();
    }

    public Optional<Autor> findById(Integer id) {
        return autorRepository.findById(id);
    }

    public void deleteById(Integer id) {
        autorRepository.deleteById(id);
    }

    public Optional<Autor> findByNome(String nome) {
        return autorRepository.findByNome(nome);
    }
}
