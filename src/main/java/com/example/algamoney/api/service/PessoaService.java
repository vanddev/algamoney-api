package com.example.algamoney.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.algamoney.api.exception.service.InactivePersonException;
import com.example.algamoney.api.model.Pessoa;
import com.example.algamoney.api.repository.PessoaRepository;
import com.example.algamoney.api.repository.filter.PessoaFilter;

@Service
public class PessoaService {
	
	@Autowired
	private PessoaRepository repository;
	
	public Page<Pessoa> filtrar(PessoaFilter filter, Pageable pageable){
		return repository.filtrar(filter, pageable);
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
	
	public boolean validarPessoa(Pessoa pessoa) {
	    if(pessoa == null || !findById(pessoa.getId()).getAtivo())
	        throw new InactivePersonException();
	    return true;
	}
}
