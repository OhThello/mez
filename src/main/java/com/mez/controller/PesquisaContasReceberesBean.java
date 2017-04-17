package com.mez.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mez.dao.ContasReceberDao;
import com.mez.model.ContasReceber;

@Named
@RequestScoped
public class PesquisaContasReceberesBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	ContasReceberDao contasReceberDao;

	private List<ContasReceber> contasReceberes = new ArrayList<>();

	private ContasReceber contasReceberSelecionado;

	public List<ContasReceber> getContasReceberes() {
		return contasReceberes;
	}

	public void setContasReceberes(List<ContasReceber> contasReceberes) {
		this.contasReceberes = contasReceberes;
	}

	public ContasReceber getContasReceberSelecionado() {
		return contasReceberSelecionado;
	}

	public void setContasReceberSelecionado(ContasReceber contasReceberSelecionado) {
		this.contasReceberSelecionado = contasReceberSelecionado;
	}

	@PostConstruct
	public void inicializar() {
		contasReceberes = contasReceberDao.buscarTodos();
	}

	public List<ContasReceber> getListaContasReceber() {
		contasReceberes = contasReceberDao.listarContas("(receber_valor - receber_valorPagamento) > 0");
		return contasReceberes;
	}

	public List<ContasReceber> getListaContasRecebidas() {
		contasReceberes = contasReceberDao.listarContas("(receber_valor - receber_valorPagamento) <= 0");
		return contasReceberes;
	}

}
