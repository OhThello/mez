package com.mez.controller;

import com.mez.model.Pedido;

public class PedidoAlteradoEvent {

	private Pedido pedido;
	
	public PedidoAlteradoEvent(Pedido pedido) {
		this.pedido = pedido;
	}

	public Pedido getPedido() {
		return pedido;
	}
	
}
