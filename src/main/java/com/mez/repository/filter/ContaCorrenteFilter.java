package com.mez.repository.filter;

import java.io.Serializable;

public class ContaCorrenteFilter implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nomeConta;

	public String getNomeConta() {
		return nomeConta;
	}

	public void setNomeConta(String nomeConta) {
		this.nomeConta = nomeConta;
	}

}