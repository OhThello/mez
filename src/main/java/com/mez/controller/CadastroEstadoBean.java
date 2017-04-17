package com.mez.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mez.model.Estado;
import com.mez.service.CadastroEstadoService;
import com.mez.service.NegocioException;
import com.mez.util.jsf.FacesUtil;

@Named
@RequestScoped
public class CadastroEstadoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CadastroEstadoService cadastroEstadoService;

	private Estado estado;

	public void salvar() {
		try {
			this.cadastroEstadoService.salvar(estado);
			FacesUtil.addInfoMessage("Estado salvo com sucesso!");
			this.limpar();
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	@PostConstruct
	public void inicializar() {
		this.limpar();
	}

	private void limpar() {
		this.estado = new Estado();

	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public boolean isEditando() {
		return this.estado.getCodigo() != null;
	}

}