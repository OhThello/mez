package com.mez.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mez.dao.GrupoProdutoDao;
import com.mez.model.GrupoProduto;
import com.mez.model.Produto;
import com.mez.service.CadastroProdutoService;
import com.mez.service.NegocioException;
import com.mez.util.jsf.FacesUtil;

@Named
@RequestScoped
public class CadastroProdutoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Produto produto;

	private List<GrupoProduto> grupoProdutos;

	@Inject
	private CadastroProdutoService cadastroProdutoService;

	@Inject
	private GrupoProdutoDao grupoProdutoDao;

	public void salvar() {
		try {
			this.cadastroProdutoService.salvar(produto);
			FacesUtil.addInfoMessage("Produto salvo com sucesso!");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}

		this.limpar();
	}

	@PostConstruct
	public void inicializar() {
		this.limpar();
		this.grupoProdutos = grupoProdutoDao.buscarTodos();
	}

	public void limpar() {
		this.produto = new Produto();
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public List<GrupoProduto> getGrupoProdutos() {
		return grupoProdutos;
	}

	public void setGrupoProdutos(List<GrupoProduto> grupoProdutos) {
		this.grupoProdutos = grupoProdutos;
	}

	public boolean isEditando() {
		return this.produto.getId() != null;
	}

}
