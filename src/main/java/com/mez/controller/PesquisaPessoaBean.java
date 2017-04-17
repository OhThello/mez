package com.mez.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mez.dao.PessoaDao;
import com.mez.model.Pessoa;
import com.mez.service.NegocioException;
import com.mez.util.jsf.FacesUtil;

@Named
@RequestScoped
public class PesquisaPessoaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	PessoaDao pessoaDao;

	private List<Pessoa> pessoas = new ArrayList<>();

	private Pessoa pessoaSelecionado;

	public void excluir() {
		try {
			pessoaDao.remover(pessoaSelecionado);
			pessoas.remove(pessoaSelecionado);
			FacesUtil.addInfoMessage("Cliente " + pessoaSelecionado.getNome() + " excluído com sucesso.");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

	public Pessoa getPessoaSelecionado() {
		return pessoaSelecionado;
	}

	public void setPessoaSelecionado(Pessoa pessoaSelecionado) {
		this.pessoaSelecionado = pessoaSelecionado;
	}

	@PostConstruct
	public void inicializar() {
		pessoas = pessoaDao.buscarTodos();
	}

}
