package com.mez.repository.filter;

import java.io.Serializable;

public class GrupoFilter implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nomeGrupo;
	private String nomeDescricao;

	public String getNomeGrupo() {
		return nomeGrupo;
	}

	public void setNomeGrupo(String nomeGrupo) {
		this.nomeGrupo = nomeGrupo;
	}

	public String getNomeDescricao() {
		return nomeDescricao;
	}

	public void setNomeDescricao(String nomeDescricao) {
		this.nomeDescricao = nomeDescricao;
	}

}