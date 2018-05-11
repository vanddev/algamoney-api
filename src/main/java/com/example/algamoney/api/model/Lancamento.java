package com.example.algamoney.api.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="Lancamento")
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Lancamento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Setter
	@NotNull
	private String descricao;
	
	@NotNull
	@Setter
	@Column(name = "data_vencimento")
	private LocalDate dataVencimento;
	
	@Setter
	@Column(name = "data_pagamento")
	private LocalDate dataPagamento;
	
	@NotNull
	@Setter
	private BigDecimal valor;
	
	@Setter
	private String observacao;
	
	@NotNull
	@Setter
	@Enumerated(EnumType.STRING)
	private TipoLancamento tipo;
	
	@NotNull
	@Setter
	@ManyToOne
	@JoinColumn(name = "id_categoria")
	private Categoria categoria;
	
	@NotNull
	@Setter
	@ManyToOne
	@JoinColumn(name = "id_pessoa")
	private Pessoa pessoa;

}
