package com.mez.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mez.dao.ProdutoDao;
import com.mez.model.AjustaEstoque;
import com.mez.model.Produto;
import com.mez.service.CadastroAjustaEstoqueService;
import com.mez.service.NegocioException;
import com.mez.util.jsf.FacesUtil;

@Named
@RequestScoped
public class CadastroAjustaEstoqueBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private AjustaEstoque ajustaEstoque;

	private List<Produto> produtos;

	@Inject
	private CadastroAjustaEstoqueService cadastroAjustaEstoqueService;

	@Inject
	private ProdutoDao produtoDao;

	public void salvar() throws Exception {
		try {
			this.ajustaEstoque.ajustarEstoque();
			this.cadastroAjustaEstoqueService.salvar(ajustaEstoque);
			FacesUtil.addInfoMessage("Ajuste de estoque salvo com sucesso!");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}

		this.limpar();
	}

	@PostConstruct
	public void inicializar() {
		this.limpar();
		this.produtos = produtoDao.buscarTodos();
	}

	public void limpar() {
		this.ajustaEstoque = new AjustaEstoque();
	}

	public AjustaEstoque getAjustaEstoque() {
		return ajustaEstoque;
	}

	public void setAjustaEstoque(AjustaEstoque ajustaEstoque) {
		this.ajustaEstoque = ajustaEstoque;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

}
