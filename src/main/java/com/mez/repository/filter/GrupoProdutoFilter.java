package com.mez.repository.filter;

import java.io.Serializable;

public class GrupoProdutoFilter implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nomeGrupo;

	public String getNomeGrupo() {
		return nomeGrupo;
	}

	public void setNomeGrupo(String nomeGrupo) {
		this.nomeGrupo = nomeGrupo;
	}

}