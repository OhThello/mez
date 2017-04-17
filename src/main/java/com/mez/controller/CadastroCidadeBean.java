package com.mez.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mez.dao.EstadoDao;
import com.mez.model.Cidade;
import com.mez.model.Estado;
import com.mez.service.CadastroCidadeService;
import com.mez.service.NegocioException;
import com.mez.util.jsf.FacesUtil;

@Named
@RequestScoped
public class CadastroCidadeBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Cidade cidade = new Cidade();

	private List<Estado> estados;

	@Inject
	private CadastroCidadeService cadastroCidadeService;

	@Inject
	private EstadoDao estadoDao;

	public void salvar() {
		try {
			this.cadastroCidadeService.salvar(cidade);
			FacesUtil.addInfoMessage("Cidade salvo com sucesso!");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}

		this.limpar();
	}

	@PostConstruct
	public void inicializar() {
		this.limpar();
		this.estados = estadoDao.buscarTodos();
	}

	public void limpar() {
		this.cidade = new Cidade();
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}
	
	public boolean isEditando() {
		return this.cidade.getCodigo() != null;
	}

}
