package com.mez.repository.filter;

import java.io.Serializable;

public class EstadoFilter implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nomeEstado;
	private String nomeSigla;

	public String getNomeEstado() {
		return nomeEstado;
	}

	public void setNomeEstado(String nomeEstado) {
		this.nomeEstado = nomeEstado;
	}

	public String getNomeSigla() {
		return nomeSigla;
	}

	public void setNomeSigla(String nomeSigla) {
		this.nomeSigla = nomeSigla;
	}

}