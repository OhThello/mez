package com.mez.repository.filter;

import java.io.Serializable;

public class AjusteEstoqueFilter implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nomeProduto;

	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

}