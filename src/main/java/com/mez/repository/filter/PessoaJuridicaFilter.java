package com.mez.repository.filter;

import java.io.Serializable;

public class PessoaJuridicaFilter implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nomeFornecedor;
	private String nomeCidade;

	public String getNomeFornecedor() {
		return nomeFornecedor;
	}

	public void setNomeFornecedor(String nomeFornecedor) {
		this.nomeFornecedor = nomeFornecedor;
	}

	public String getNomeCidade() {
		return nomeCidade;
	}

	public void setNomeCidade(String nomeCidade) {
		this.nomeCidade = nomeCidade;
	}

}