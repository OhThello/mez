package com.mez.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mez.dao.CompraDao;
import com.mez.model.Compra;
import com.mez.model.StatusPedido;
import com.mez.repository.filter.CompraFilter;


@Named
@ViewScoped
public class PesquisaComprasBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private CompraDao compraDao;
	
	private Compra compraSelecionado;
	private CompraFilter filtro;
	private List<Compra> comprasFiltrados;
	
	public PesquisaComprasBean() {
		filtro = new CompraFilter();
		comprasFiltrados = new ArrayList<>();
	}

	public void pesquisar() {
		comprasFiltrados = compraDao.filtrados(filtro);
	}
	
	public StatusPedido[] getStatuses() {
		return StatusPedido.values();
	}
	
	public List<Compra> getComprasFiltrados() {
		return comprasFiltrados;
	}

	public CompraFilter getFiltro() {
		return filtro;
	}
	
	
	public Compra getCompraSelecionado() {
		return compraSelecionado;
	}

	public void setCompraSelecionado(Compra compraSelecionado) {
		this.compraSelecionado = compraSelecionado;
	}

	@PostConstruct
	public void inicializar(){
		comprasFiltrados = compraDao.buscarTodos();
	}
	
}