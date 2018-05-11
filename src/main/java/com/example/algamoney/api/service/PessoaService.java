package com.example.algamoney.api.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.algamoney.api.model.Pessoa;
import com.example.algamoney.api.repository.PessoaRepository;

@Service
public class PessoaService {
	
	@Autowired
	private PessoaRepository repository;
	
	public List<Pessoa> findAll(){
		return repository.findAll();
	}
	
	public Pessoa findById(Long id) {
		return repository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException(1));
	}
	
	public Pessoa save(Pessoa pessoa) {
		return repository.save(pessoa);
	}
	
	public Pessoa update(Long id, Pessoa pessoa) {
		Pessoa pessoaSalva = findById(id);
		BeanUtils.copyProperties(pessoa, pessoaSalva, "id");
		return save(pessoaSalva);
	}
	
	public void delete(Long id) {
		Pessoa pessoa = findById(id);
		repository.delete(pessoa);
	}

	public void atualizarPropriedadeAtivo(Long id, Boolean ativo) {
		Pessoa pessoa = findById(id);
		pessoa.setAtivo(ativo);
		repository.save(pessoa);
	}
}
