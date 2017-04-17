package com.mez.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mez.dao.Pedidos;
import com.mez.model.Pedido;
import com.mez.model.StatusPedido;
import com.mez.repository.filter.PedidoFilter;

@Named
@ViewScoped
public class PesquisaPedidosBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Pedidos pedidos;
	
	private Pedido pedidoSelecionado;
	private PedidoFilter filtro;
	private List<Pedido> pedidosFiltrados;
	
	public PesquisaPedidosBean() {
		filtro = new PedidoFilter();
		pedidosFiltrados = new ArrayList<>();
	}

	public void pesquisar() {
		pedidosFiltrados = pedidos.filtrados(filtro);
	}
	
	public StatusPedido[] getStatuses() {
		return StatusPedido.values();
	}
	
	public List<Pedido> getPedidosFiltrados() {
		return pedidosFiltrados;
	}

	public PedidoFilter getFiltro() {
		return filtro;
	}
	
	
	public Pedido getPedidoSelecionado() {
		return pedidoSelecionado;
	}

	public void setPedidoSelecionado(Pedido pedidoSelecionado) {
		this.pedidoSelecionado = pedidoSelecionado;
	}

	@PostConstruct
	public void inicializar(){
		pedidosFiltrados = pedidos.buscarTodos();
	}
	
}