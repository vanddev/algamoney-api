package com.example.algamoney.api.service;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.algamoney.api.model.Lancamento;
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
		if(pessoaService.validarPessoa(lancamento.getPessoa()))
		   return repository.save(lancamento);
		return null;
	}

	public void delete(Long id) {
		Lancamento lancamento = findById(id);
		repository.delete(lancamento);
	}

    public Lancamento atualizar(Long id, @Valid Lancamento lancamento) {
        Lancamento oldLancamento = findById(id);
        if(!oldLancamento.getPessoa().equals(lancamento.getPessoa())) {
            pessoaService.validarPessoa(lancamento.getPessoa());
        }
        
        BeanUtils.copyProperties(lancamento, oldLancamento, "id");
        
        return repository.save(oldLancamento);
    }
}
