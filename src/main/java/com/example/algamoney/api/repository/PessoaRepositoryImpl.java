package com.example.algamoney.api.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.example.algamoney.api.model.Pessoa;
import com.example.algamoney.api.model.QPessoa;
import com.example.algamoney.api.repository.filter.PessoaFilter;
import com.querydsl.jpa.impl.JPAQuery;

public class PessoaRepositoryImpl implements PessoaRepositoryQuery{
	
	@PersistenceContext
	private EntityManager manager;

    @Override
    public Page<Pessoa> filtrar(PessoaFilter filter, Pageable pageable) {
        JPAQuery<Pessoa> query = new JPAQuery<Pessoa>(manager);
        QPessoa pessoa = QPessoa.pessoa;
        query.from(pessoa);
        long total = query.fetchCount();
        restricoesPaginacao(query, pageable);
        
        if(StringUtils.isNotBlank(filter.getNome())) {
            query.where(pessoa.nome.like("%" + filter.getNome() + "%"));
        }
        
        return new PageImpl<>(query.fetch(), pageable, total);
    }
    
    private void restricoesPaginacao(JPAQuery<?> query, Pageable pageable) {
        query.offset(pageable.getOffset());
        query.limit(pageable.getPageSize());
    }
	
}
