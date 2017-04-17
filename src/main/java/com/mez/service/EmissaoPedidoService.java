package com.mez.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.mez.dao.ContasReceberDao;
import com.mez.dao.Pedidos;
import com.mez.model.ContasReceber;
import com.mez.model.Pedido;
import com.mez.model.StatusPedido;
import com.mez.util.jpa.Transactional;

public class EmissaoPedidoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CadastroPedidoService cadastroPedidoService;
	
	@Inject
	private EstoqueService estoqueService;
	
	@Inject
	private Pedidos pedidos;
	
	@Inject
	private ContasReceberDao contasReceberDao;
	
	@Transactional
	public Pedido emitir(Pedido pedido) {
		pedido = this.cadastroPedidoService.salvar(pedido);
		
		if (pedido.isNaoEmissivel()) {
			throw new NegocioException("Pedido não pode ser emitido com status "
					+ pedido.getStatus().getDescricao() + ".");
		}
		
		this.estoqueService.baixarItensEstoque(pedido);
		
		pedido.setStatus(StatusPedido.EMITIDO);
		
		pedido = this.pedidos.guardar(pedido);
		   ContasReceber c = new ContasReceber();
		   c.setPessoa(pedido.getCliente());
	        c.setValor(pedido.getValorTotal());
	        c.setPedido(pedido);
	        c.setDataVencimento(pedido.getDataEntrega());
	        c.setDataLancamento(pedido.getDataCriacao());
	        contasReceberDao.guardar(c);
		
		return pedido;
	}
	
}
