package com.mez.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mez.dao.EstadoDao;
import com.mez.model.Estado;
import com.mez.repository.filter.EstadoFilter;
import com.mez.service.NegocioException;
import com.mez.util.jsf.FacesUtil;

@Named
@RequestScoped
public class PesquisaEstadosBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	EstadoDao estadoDao;

	private List<Estado> estados;

	private Estado estadoSelecionado;
	private EstadoFilter filtro;

	public List<Estado> getEstados() {
		return estados;
	}

	public void excluir() {
		try {
			estadoDao.remover(estadoSelecionado);
			estados.remove(estadoSelecionado);
			FacesUtil.addInfoMessage("Estado " + estadoSelecionado.getNome() + " excluído com sucesso.");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	public PesquisaEstadosBean() {
		filtro = new EstadoFilter();
		estados = new ArrayList<>();
	}

	public void pesquisar() {
		estados = estadoDao.filtrados(filtro);
	}

	public EstadoFilter getFiltro() {
		return filtro;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}

	public Estado getEstadoSelecionado() {
		return estadoSelecionado;
	}

	public void setEstadoSelecionado(Estado estadoSelecionado) {
		this.estadoSelecionado = estadoSelecionado;
	}

	@PostConstruct
	public void inicializar() {
		estados = estadoDao.buscarTodos();
	}

}
