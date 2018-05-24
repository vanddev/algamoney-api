package com.example.algamoney.api.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;

import com.example.algamoney.api.model.Pessoa;
import com.example.algamoney.api.model.QPessoa;
import com.example.algamoney.api.repository.filter.PessoaFilter;
import com.querydsl.jpa.impl.JPAQuery;

public class PessoaRepositoryImpl implements PessoaRepositoryQuery{
	
	@PersistenceContext
	private EntityManager manager;

    @Override
    public List<Pessoa> filtrar(PessoaFilter filter) {
        JPAQuery<Pessoa> query = new JPAQuery<Pessoa>(manager);
        QPessoa pessoa = QPessoa.pessoa;
        query.from(pessoa);
        
        if(StringUtils.isNotBlank(filter.getNome())) {
            query.where(pessoa.nome.like("%" + filter.getNome() + "%"));
        }
        
        return query.fetch();
    }
	
}
