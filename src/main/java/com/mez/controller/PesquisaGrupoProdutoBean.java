package com.mez.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mez.dao.GrupoProdutoDao;
import com.mez.model.GrupoProduto;
import com.mez.repository.filter.GrupoProdutoFilter;
import com.mez.service.NegocioException;
import com.mez.util.jsf.FacesUtil;

@Named
@RequestScoped
public class PesquisaGrupoProdutoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	GrupoProdutoDao grupoProdutoDao;

	private List<GrupoProduto> grupoProdutos = new ArrayList<>();

	private GrupoProduto grupoProdutoSelecionado;
	private GrupoProdutoFilter filtro;

	public void excluir() {
		try {
			grupoProdutoDao.remover(grupoProdutoSelecionado);
			grupoProdutos.remove(grupoProdutoSelecionado);
			FacesUtil.addInfoMessage("Grupo Produto " + grupoProdutoSelecionado.getNome() + " excluído com sucesso.");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	public PesquisaGrupoProdutoBean() {
		filtro = new GrupoProdutoFilter();
		grupoProdutos = new ArrayList<>();
	}

	public void pesquisar() {
		grupoProdutos = grupoProdutoDao.filtrados(filtro);
	}

	public GrupoProdutoFilter getFiltro() {
		return filtro;
	}

	public List<GrupoProduto> getGrupoProdutos() {
		return grupoProdutos;
	}

	public void setGrupoProdutos(List<GrupoProduto> grupoProdutos) {
		this.grupoProdutos = grupoProdutos;
	}

	public GrupoProduto getGrupoProdutoSelecionado() {
		return grupoProdutoSelecionado;
	}

	public void setGrupoProdutoSelecionado(GrupoProduto grupoProdutoSelecionado) {
		this.grupoProdutoSelecionado = grupoProdutoSelecionado;
	}

	@PostConstruct
	public void inicializar() {
		grupoProdutos = grupoProdutoDao.buscarTodos();
	}

}
