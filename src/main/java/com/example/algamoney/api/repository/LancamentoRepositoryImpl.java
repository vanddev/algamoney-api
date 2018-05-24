package com.example.algamoney.api.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.example.algamoney.api.model.Lancamento;
import com.example.algamoney.api.model.QLancamento;
import com.example.algamoney.api.repository.filter.LancamentoFilter;
import com.example.algamoney.api.repository.projection.ResumoLancamento;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;

public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery{
	
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable) {
		JPAQuery<Lancamento> query = new JPAQuery<Lancamento>(manager);
		QLancamento lancamento = QLancamento.lancamento;
		query.from(lancamento);
		Predicate[] predicates = criarRestricoes(lancamentoFilter, lancamento);
		query.where(predicates);
		long total = query.fetchCount();
		restricoesPaginacao(query, pageable);
		return new PageImpl<>(query.fetch(), pageable, total);
	}

	@Override
	public Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter, Pageable pageable) {
		JPAQuery<Lancamento> query = new JPAQuery<Lancamento>(manager);
		QLancamento lancamento = QLancamento.lancamento;
		Predicate[] predicates = criarRestricoes(lancamentoFilter, lancamento);
		query.from(lancamento).where(predicates);
		long total = query.fetchCount();
		restricoesPaginacao(query, pageable);
		List<ResumoLancamento> dtos = query.select(
			    Projections.constructor(ResumoLancamento.class, 
			    						lancamento.id, lancamento.descricao,
			    						lancamento.dataVencimento, lancamento.dataPagamento,
			    						lancamento.valor, lancamento.tipo,
			    						lancamento.categoria.nome, lancamento.pessoa.nome)).fetch();
	
	    return new PageImpl<>(dtos, pageable, total);
	}
	
	private void restricoesPaginacao(JPAQuery<?> query, Pageable pageable) {
		query.offset(pageable.getOffset());
		query.limit(pageable.getPageSize());
	}

	private Predicate[] criarRestricoes(LancamentoFilter lancamentoFilter, QLancamento lancamento) {
		List<Predicate> predicates = new ArrayList<>();
		
		if(StringUtils.isNotBlank(lancamentoFilter.getDescricao())){
			predicates.add(lancamento.descricao.like("%"+ lancamentoFilter.getDescricao() + "%"));
		}
		
		if(lancamentoFilter.getDataVencimentoDe() != null){
			predicates.add(lancamento.dataVencimento.goe(lancamentoFilter.getDataVencimentoDe()));
		}
		
		if(lancamentoFilter.getDataVencimentoAte() != null){
			predicates.add(lancamento.dataVencimento.loe(lancamentoFilter.getDataVencimentoAte()));
		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}


	
}
