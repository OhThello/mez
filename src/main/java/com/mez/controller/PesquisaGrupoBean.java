package com.mez.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mez.dao.GrupoDao;
import com.mez.model.Grupo;
import com.mez.repository.filter.GrupoFilter;
import com.mez.service.NegocioException;
import com.mez.util.jsf.FacesUtil;

@Named
@RequestScoped
public class PesquisaGrupoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	GrupoDao grupoDao;

	private List<Grupo> grupos = new ArrayList<>();

	private Grupo grupoSelecionado;
	private GrupoFilter filtro;

	public void excluir() {
		try {
			grupoDao.remover(grupoSelecionado);
			grupos.remove(grupoSelecionado);
			FacesUtil.addInfoMessage("Grupo " + grupoSelecionado.getNome() + " excluído com sucesso.");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	public PesquisaGrupoBean() {
		filtro = new GrupoFilter();
		grupos = new ArrayList<>();
	}

	public void pesquisar() {
		grupos = grupoDao.filtrados(filtro);
	}

	public GrupoFilter getFiltro() {
		return filtro;
	}

	public List<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}

	public Grupo getGrupoSelecionado() {
		return grupoSelecionado;
	}

	public void setGrupoSelecionado(Grupo grupoSelecionado) {
		this.grupoSelecionado = grupoSelecionado;
	}

	@PostConstruct
	public void inicializar() {
		grupos = grupoDao.buscarTodos();
	}

}
