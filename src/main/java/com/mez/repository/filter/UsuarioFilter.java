package com.mez.repository.filter;

import java.io.Serializable;

public class UsuarioFilter implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nomeUsuario;

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

}