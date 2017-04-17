package com.mez.repository.filter;

import java.io.Serializable;

public class PessoaFisicaFilter implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nomeCliente;
	private String nomeCidade;

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public String getNomeCidade() {
		return nomeCidade;
	}

	public void setNomeCidade(String nomeCidade) {
		this.nomeCidade = nomeCidade;
	}

}