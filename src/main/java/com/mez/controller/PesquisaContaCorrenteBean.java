package com.mez.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mez.dao.ContaCorrenteDao;
import com.mez.model.ContaCorrente;
import com.mez.repository.filter.ContaCorrenteFilter;
import com.mez.service.NegocioException;
import com.mez.util.jsf.FacesUtil;

@Named
@RequestScoped
public class PesquisaContaCorrenteBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	ContaCorrenteDao contaCorrenteDao;

	private List<ContaCorrente> contaCorrentes = new ArrayList<>();

	private ContaCorrente contaCorrenteSelecionado;
	private ContaCorrenteFilter filtro;

	public void excluir() {
		try {
			contaCorrenteDao.remover(contaCorrenteSelecionado);
			contaCorrentes.remove(contaCorrenteSelecionado);
			FacesUtil.addInfoMessage("Conta corrente " + contaCorrenteSelecionado.getNome() + " excluído com sucesso.");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	public PesquisaContaCorrenteBean() {
		filtro = new ContaCorrenteFilter();
		contaCorrentes = new ArrayList<>();
	}

	public void pesquisar() {
		contaCorrentes = contaCorrenteDao.filtrados(filtro);
	}

	public ContaCorrenteFilter getFiltro() {
		return filtro;
	}

	public List<ContaCorrente> getContaCorrentes() {
		return contaCorrentes;
	}

	public void setContaCorrentes(List<ContaCorrente> contaCorrentes) {
		this.contaCorrentes = contaCorrentes;
	}

	public ContaCorrente getContaCorrenteSelecionado() {
		return contaCorrenteSelecionado;
	}

	public void setContaCorrenteSelecionado(ContaCorrente contaCorrenteSelecionado) {
		this.contaCorrenteSelecionado = contaCorrenteSelecionado;
	}

	@PostConstruct
	public void inicializar() {
		contaCorrentes = contaCorrenteDao.buscarTodos();
	}

}
