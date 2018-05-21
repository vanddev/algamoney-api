package com.example.algamoney.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.algamoney.api.exception.service.InactivePersonException;
import com.example.algamoney.api.model.Lancamento;
import com.example.algamoney.api.model.Pessoa;
import com.example.algamoney.api.repository.LancamentoRepository;
import com.example.algamoney.api.repository.filter.LancamentoFilter;
import com.example.algamoney.api.repository.projection.ResumoLancamento;

@Service
public class LancamentoService {

	@Autowired
	private LancamentoRepository repository;
	
	@Autowired
	private PessoaService pessoaService;
	
	public Page<Lancamento> findAll(LancamentoFilter lancamentoFilter, Pageable pageable){
		return repository.filtrar(lancamentoFilter, pageable);
	}
	
	public Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter, Pageable pageable){
		return repository.resumir(lancamentoFilter, pageable);
	}

	public Lancamento findById(Long id) {
		return repository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException(1));
	}
	
	public Lancamento save(Lancamento lancamento) {
		Pessoa pessoa = pessoaService.findById(lancamento.getPessoa().getId());
		if(!pessoa.getAtivo())
			throw new InactivePersonException();
		return repository.save(lancamento);
	}

	public void delete(Long id) {
		Lancamento lancamento = findById(id);
		repository.delete(lancamento);
	}
}
