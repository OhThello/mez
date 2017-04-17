package com.mez.controller;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

import com.mez.model.Compra;
import com.mez.service.CancelamentoCompraService;
import com.mez.util.jsf.FacesUtil;

@Named
@RequestScoped
public class CancelamentoCompraBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CancelamentoCompraService cancelamentoCompraService;
	
	@Inject
	private Event<CompraAlteradoEvent> compraAlteradoEvent;
	
	@Inject
	@PedidoEdicao
	private Compra compra;
	
	public void cancelarCompra() {
		this.compra = this.cancelamentoCompraService.cancelar(this.compra);
		this.compraAlteradoEvent.fire(new CompraAlteradoEvent(this.compra));
		
		FacesUtil.addInfoMessage("Compra cancelada com sucesso!");
	}
	
}
