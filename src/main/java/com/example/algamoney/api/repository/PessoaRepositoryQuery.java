package com.example.algamoney.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.algamoney.api.model.Pessoa;
import com.example.algamoney.api.repository.filter.PessoaFilter;

public interface PessoaRepositoryQuery {

    public Page<Pessoa> filtrar(PessoaFilter filter, Pageable pageable);
}
