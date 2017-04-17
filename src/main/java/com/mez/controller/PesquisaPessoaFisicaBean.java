package com.mez.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mez.dao.PessoaFisicaDao;
import com.mez.model.PessoaFisica;
import com.mez.repository.filter.PessoaFisicaFilter;
import com.mez.service.NegocioException;
import com.mez.util.jsf.FacesUtil;

@Named
@RequestScoped
public class PesquisaPessoaFisicaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	PessoaFisicaDao pessoaFisicaDao;

	private List<PessoaFisica> pessoaFisicas = new ArrayList<>();

	private PessoaFisica pessoaFisicaSelecionado;
	private PessoaFisicaFilter filtro;

	public void excluir() {
		try {
			pessoaFisicaDao.remover(pessoaFisicaSelecionado);
			pessoaFisicas.remove(pessoaFisicaSelecionado);
			FacesUtil.addInfoMessage("Pessoa Física " + pessoaFisicaSelecionado.getNome() + " excluído com sucesso.");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	public PesquisaPessoaFisicaBean() {
		filtro = new PessoaFisicaFilter();
		pessoaFisicas= new ArrayList<>();
	}

	public void pesquisar() {
		pessoaFisicas = pessoaFisicaDao.filtrados(filtro);
	}

	public List<PessoaFisica> getPessoaFisicas() {
		return pessoaFisicas;
	}

	public void setPessoaFisicas(List<PessoaFisica> pessoaFisicas) {
		this.pessoaFisicas = pessoaFisicas;
	}

	public PessoaFisicaFilter getFiltro() {
		return filtro;
	}

	public PessoaFisica getPessoaFisicaSelecionado() {
		return pessoaFisicaSelecionado;
	}

	public void setPessoaFisicaSelecionado(PessoaFisica pessoaFisicaSelecionado) {
		this.pessoaFisicaSelecionado = pessoaFisicaSelecionado;
	}

	@PostConstruct
	public void inicializar() {
		pessoaFisicas = pessoaFisicaDao.buscarTodos();
	}

}
