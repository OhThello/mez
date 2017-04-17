package com.mez.model;

public enum PermissaoUsuario {

	ADMINISTRADORES("Adminstrador"),
	VENDEDORES("Vendedor");

    private String descricao;

    private PermissaoUsuario(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
