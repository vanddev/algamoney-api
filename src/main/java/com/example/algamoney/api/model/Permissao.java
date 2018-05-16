package com.example.algamoney.api.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "permissao")
@Getter
@EqualsAndHashCode(of = {"id"})
@ToString
public class Permissao {
	
	@Id
	private Long id;
	 
	@Setter
	private String descricao;

}
