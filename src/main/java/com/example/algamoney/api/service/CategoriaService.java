package com.example.algamoney.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.algamoney.api.model.Categoria;
import com.example.algamoney.api.repository.CategoriaRepository;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repository;

	public List<Categoria> findAll() {
		return repository.findAll();
	}

	public Categoria save(Categoria categoria) {
		return repository.save(categoria);
	}

	public Categoria findById(Long id) {
		return repository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException(1));
	}

}
