package com.example.algamoney.api.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.algamoney.api.event.RecursoCriadoEvent;
import com.example.algamoney.api.model.Pessoa;
import com.example.algamoney.api.service.PessoaService;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private PessoaService service;
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Pessoa> listar(){
		return service.findAll();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void criar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response){
		Pessoa pessoaSalva = service.save(pessoa);
		publisher.publishEvent(new RecursoCriadoEvent(this, response ,pessoaSalva.getId()));
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Pessoa buscarPeloId(@PathVariable Long id) {
		return service.findById(id);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletePessoa(@PathVariable Long id) {
		service.delete(id);
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Pessoa atualizar(@PathVariable Long id, @Valid @RequestBody Pessoa pessoa){
		return service.update(id, pessoa);
	}
	
	@PutMapping("/{id}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarPropriedadeAtivo(@PathVariable Long id, @RequestBody Boolean ativo) {
		service.atualizarPropriedadeAtivo(id, ativo);
	}
	
}
