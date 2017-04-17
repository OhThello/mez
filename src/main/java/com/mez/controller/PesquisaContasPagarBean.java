package com.mez.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mez.dao.ContasPagarDao;
import com.mez.model.ContasPagar;
import com.mez.repository.filter.ContasPagarFilter;

@Named
@RequestScoped
public class PesquisaContasPagarBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	ContasPagarDao contasPagarDao;

	private List<ContasPagar> contasPagas = new ArrayList<>();

	private ContasPagar contasPagarSelecionado;
	private ContasPagarFilter filtro;

	public PesquisaContasPagarBean() {
		filtro = new ContasPagarFilter();
		contasPagas = new ArrayList<>();
	}

	public void pesquisar() {
		contasPagas = contasPagarDao.filtrados(filtro);
	}

	public ContasPagarFilter getFiltro() {
		return filtro;
	}

	public ContasPagarDao getContasPagarDao() {
		return contasPagarDao;
	}

	public List<ContasPagar> getContasPagas() {
		return contasPagas;
	}

	public void setContasPagas(List<ContasPagar> contasPagas) {
		this.contasPagas = contasPagas;
	}

	public ContasPagar getContasPagarSelecionado() {
		return contasPagarSelecionado;
	}

	public void setContasPagarSelecionado(ContasPagar contasPagarSelecionado) {
		this.contasPagarSelecionado = contasPagarSelecionado;
	}

	@PostConstruct
	public void inicializar() {
		contasPagas = contasPagarDao.buscarTodos();
	}

	public List<ContasPagar> getListaContasPagar() {
		contasPagas = contasPagarDao.listarContas("(receber_valor - receber_valorPagamento) > 0");
		return contasPagas;
	}

	public List<ContasPagar> getListaContasQuitadas() {
		contasPagas = contasPagarDao.listarContas("(receber_valor - receber_valorPagamento) <= 0");
		return contasPagas;
	}

}
