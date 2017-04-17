package com.mez.repository.filter;

import java.io.Serializable;

public class CidadeFilter implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nomeCidade;
	private String nomeEstado;

	public String getNomeCidade() {
		return nomeCidade;
	}

	public void setNomeCidade(String nomeCidade) {
		this.nomeCidade = nomeCidade;
	}

	public String getNomeEstado() {
		return nomeEstado;
	}

	public void setNomeEstado(String nomeEstado) {
		this.nomeEstado = nomeEstado;
	}

}