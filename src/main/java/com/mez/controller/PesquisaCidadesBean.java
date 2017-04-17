package com.mez.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mez.dao.CidadeDao;
import com.mez.model.Cidade;
import com.mez.repository.filter.CidadeFilter;
import com.mez.service.NegocioException;
import com.mez.util.jsf.FacesUtil;

@Named
@RequestScoped
public class PesquisaCidadesBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	CidadeDao cidadeDao;

	private List<Cidade> cidades = new ArrayList<>();

	private Cidade cidadeSelecionado;
	private CidadeFilter filtro;

	public List<Cidade> getCidades() {
		return cidades;
	}

	public void excluir() {
		try {
			cidadeDao.remover(cidadeSelecionado);
			cidades.remove(cidadeSelecionado);
			FacesUtil.addInfoMessage("Cidade " + cidadeSelecionado.getNome() + " excluído com sucesso.");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	public PesquisaCidadesBean() {
		filtro = new CidadeFilter();
		cidades = new ArrayList<>();
	}

	public void pesquisar() {
		cidades = cidadeDao.filtrados(filtro);
	}

	public CidadeFilter getFiltro() {
		return filtro;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}

	public Cidade getCidadeSelecionado() {
		return cidadeSelecionado;
	}

	public void setCidadeSelecionado(Cidade cidadeSelecionado) {
		this.cidadeSelecionado = cidadeSelecionado;
	}

	@PostConstruct
	public void inicializar() {
		cidades = cidadeDao.buscarTodos();
	}

}
