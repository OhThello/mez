package com.mez.service;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import com.mez.dao.CompraDao;
import com.mez.model.Compra;
import com.mez.model.StatusPedido;
import com.mez.util.jpa.Transactional;

public class CadastroCompraService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CompraDao compraDao;
	
	@Transactional
	public Compra salvar(Compra compra) {
		if (compra.isNovo()) {
			compra.setDataCriacao(new Date());
			compra.setStatus(StatusPedido.ORCAMENTO);
		}
		
		compra.recalcularValorTotal();
		
		if (compra.isNaoAlteravel()) {
			throw new NegocioException("Compra não pode ser alterado no status "
					+ compra.getStatus().getDescricao() + ".");
		}
		
		if (compra.getItens().isEmpty()) {
			throw new NegocioException("O compra deve possuir pelo menos um item.");
		}
		
		if (compra.isValorTotalNegativo()) {
			throw new NegocioException("Valor total da compra não pode ser negativo.");
		}
		
		compra = this.compraDao.guardar(compra);
		return compra;
	}
	
}
