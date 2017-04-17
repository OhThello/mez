package com.mez.controller;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

import com.mez.model.Compra;
import com.mez.service.EmissaoCompraService;
import com.mez.util.jsf.FacesUtil;

@Named
@RequestScoped
public class EmissaoCompraBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EmissaoCompraService EmissaoCompraService;

	@Inject
	@PedidoEdicao
	private Compra compra;

	@Inject
	private Event<CompraAlteradoEvent> compraAlteradoEvent;

	public void emitirCompra() {
		this.compra.removerItemVazio();

		try {
			this.compra = this.EmissaoCompraService.emitir(this.compra);
			this.compraAlteradoEvent.fire(new CompraAlteradoEvent(this.compra));

			FacesUtil.addInfoMessage("Compra emitida com sucesso!");
		} finally {
			this.compra.adicionarItemVazio();
		}
	}

}
