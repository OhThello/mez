package com.mez.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mez.model.Cargo;
import com.mez.service.CadastroCargoService;
import com.mez.service.NegocioException;
import com.mez.util.jsf.FacesUtil;

@Named
@RequestScoped
public class CadastroCargoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Cargo cargo;

	@Inject
	private CadastroCargoService cadastroCargoService;

	public void salvar() {
		try {
			this.cadastroCargoService.salvar(cargo);
			FacesUtil.addInfoMessage("Cargo salvo com sucesso!");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}

		this.limpar();
	}

	@PostConstruct
	public void inicializar() {
		this.limpar();
	}

	public void limpar() {
		this.cargo = new Cargo();
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public boolean isEditando() {
		return this.cargo.getCodigo() != null;
	}

}
