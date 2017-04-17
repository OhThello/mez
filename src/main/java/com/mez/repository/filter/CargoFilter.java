package com.mez.repository.filter;

import java.io.Serializable;

public class CargoFilter implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nomeCargo;
	private String nomeDescricao;

	public String getNomeCargo() {
		return nomeCargo;
	}

	public void setNomeCargo(String nomeCargo) {
		this.nomeCargo = nomeCargo;
	}

	public String getNomeDescricao() {
		return nomeDescricao;
	}

	public void setNomeDescricao(String nomeDescricao) {
		this.nomeDescricao = nomeDescricao;
	}

}