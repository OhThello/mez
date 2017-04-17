package com.mez.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.mez.dao.CompraDao;
import com.mez.model.Compra;
import com.mez.model.StatusPedido;
import com.mez.util.jpa.Transactional;

public class CancelamentoCompraService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CompraDao compraDao;
	
	@Inject
	private EntradaEstoqueService entradaEstoqueService;
	
	@Transactional
	public Compra cancelar(Compra compra) {
		compra = this.compraDao.porId(compra.getId());
		
		if (compra.isNaoCancelavel()) {
			throw new NegocioException("Compra não pode ser cancelado no status "
					+ compra.getStatus().getDescricao() + ".");
		}
		
		if (compra.isEmitido()) {
			this.entradaEstoqueService.baixarItensEstoque(compra);
		}
		
		compra.setStatus(StatusPedido.CANCELADO);
		
		compra = this.compraDao.guardar(compra);
		
		return compra;
	}

}
