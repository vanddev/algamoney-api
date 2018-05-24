package com.example.algamoney.api.repository;

import java.util.List;

import com.example.algamoney.api.model.Pessoa;
import com.example.algamoney.api.repository.filter.PessoaFilter;

public interface PessoaRepositoryQuery {

    public List<Pessoa> filtrar(PessoaFilter filter);
}
