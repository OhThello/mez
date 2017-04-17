package com.mez.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.mez.dao.CompraDao;
import com.mez.model.Compra;
import com.mez.model.ItemCompra;
import com.mez.util.jpa.Transactional;

public class EntradaEstoqueService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CompraDao comparaDao;
	
	@Transactional
	public void baixarItensEstoque(Compra compra) {
		compra = this.comparaDao.porId(compra.getId());
		
		for (ItemCompra item : compra.getItens()) {
			item.getProduto().baixarEstoque(item.getQuantidade());
		}
	}

	public void entradaItensEstoque(Compra compra) {
		compra = this.comparaDao.porId(compra.getId());
		
		for (ItemCompra item : compra.getItens()) {
			item.getProduto().adicionarEstoque(item.getQuantidade());
		}
	}
	
}
