package com.mez.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.mez.dao.CompraDao;
import com.mez.dao.ContasPagarDao;
import com.mez.model.Compra;
import com.mez.model.ContasPagar;
import com.mez.model.StatusPedido;
import com.mez.util.jpa.Transactional;

public class EmissaoCompraService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CadastroCompraService cadastroCompraService;
	
	@Inject
	private EntradaEstoqueService entradaEstoqueService;
	
	@Inject
	private CompraDao compraDao;
	
	@Inject
	private ContasPagarDao ContasPagarDao;
	
	@Transactional
	public Compra emitir(Compra compra) {
		compra = this.cadastroCompraService.salvar(compra);
		
		if (compra.isNaoEmissivel()) {
			throw new NegocioException("Compra não pode ser emitido com status "
					+ compra.getStatus().getDescricao() + ".");
		}
		
		this.entradaEstoqueService.entradaItensEstoque(compra);
		
		compra.setStatus(StatusPedido.EMITIDO);
		
		compra = this.compraDao.guardar(compra);
		   ContasPagar c = new ContasPagar();
		   c.setPessoaJuridica(compra.getPessoaJuridica());
	        c.setValor(compra.getValorTotal());
	        c.setCompra(compra);
	        c.setDataVencimento(compra.getDataCriacao());
	        ContasPagarDao.guardar(c);
		
		return compra;
	}
	
}
