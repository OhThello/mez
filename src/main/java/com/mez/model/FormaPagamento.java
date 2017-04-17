package com.mez.model;

public enum FormaPagamento {

	DINHEIRO("Dinheiro"), 
	CARTAO_CREDITO("Cartão de crédito"), 
	CARTAO_DEBITO("Cartão de débito"), 
	CHEQUE("Cheque");
	
	private String descricao;
	
	FormaPagamento(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
}