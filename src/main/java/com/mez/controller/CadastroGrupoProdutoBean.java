package com.mez.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mez.model.GrupoProduto;
import com.mez.service.CadastroGrupoProdutoService;
import com.mez.service.NegocioException;
import com.mez.util.jsf.FacesUtil;

@Named
@RequestScoped
public class CadastroGrupoProdutoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private GrupoProduto grupoProduto;

	@Inject
	private CadastroGrupoProdutoService cadastroGrupoProdutoService;

	public void salvar() {
		try {
			this.cadastroGrupoProdutoService.salvar(grupoProduto);
			FacesUtil.addInfoMessage("Grupo Produto salvo com sucesso!");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}

		this.limpar();
	}

	@PostConstruct
	public void inicializar() {
		this.limpar();
	}

	public void limpar() {
		this.grupoProduto = new GrupoProduto();
	}

	public GrupoProduto getGrupoProduto() {
		return grupoProduto;
	}

	public void setGrupoProduto(GrupoProduto grupoProduto) {
		this.grupoProduto = grupoProduto;
	}

	public boolean isEditando() {
		return this.grupoProduto.getCodigo() != null;
	}

}
