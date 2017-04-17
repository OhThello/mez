package com.mez.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mez.dao.AjustaEstoqueDao;
import com.mez.model.AjustaEstoque;
import com.mez.repository.filter.AjusteEstoqueFilter;
import com.mez.service.NegocioException;
import com.mez.util.jsf.FacesUtil;

@Named
@RequestScoped
public class PesquisaAjustaEstoqueBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	AjustaEstoqueDao ajustaEstoqueDao;

	private List<AjustaEstoque> ajustaEstoques = new ArrayList<>();

	private AjustaEstoque ajustaEstoqueSelecionado;

	private AjusteEstoqueFilter filtro;

	public void excluir() {
		try {
			ajustaEstoqueDao.remover(ajustaEstoqueSelecionado);
			ajustaEstoques.remove(ajustaEstoqueSelecionado);
			FacesUtil.addInfoMessage(
					"Ajusta Estoque " + ajustaEstoqueSelecionado.getCodigo() + " excluído com sucesso.");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	public PesquisaAjustaEstoqueBean() {
		filtro = new AjusteEstoqueFilter();
		ajustaEstoques = new ArrayList<>();
	}

	public void pesquisar() {
		ajustaEstoques = ajustaEstoqueDao.filtrados(filtro);
	}

	public AjusteEstoqueFilter getFiltro() {
		return filtro;
	}

	public List<AjustaEstoque> getAjustaEstoques() {
		return ajustaEstoques;
	}

	public void setAjustaEstoques(List<AjustaEstoque> ajustaEstoques) {
		this.ajustaEstoques = ajustaEstoques;
	}

	public AjustaEstoque getAjustaEstoqueSelecionado() {
		return ajustaEstoqueSelecionado;
	}

	public void setAjustaEstoqueSelecionado(AjustaEstoque ajustaEstoqueSelecionado) {
		this.ajustaEstoqueSelecionado = ajustaEstoqueSelecionado;
	}

	@PostConstruct
	public void inicializar() {
		ajustaEstoques = ajustaEstoqueDao.buscarTodos();
	}

}
