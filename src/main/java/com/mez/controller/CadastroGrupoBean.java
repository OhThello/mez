package com.mez.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mez.model.Grupo;
import com.mez.service.CadastroGrupoService;
import com.mez.service.NegocioException;
import com.mez.util.jsf.FacesUtil;

@Named
@RequestScoped
public class CadastroGrupoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Grupo grupo;

	@Inject
	private CadastroGrupoService cadastroGrupoService;


	public void salvar() {
		try {
			this.cadastroGrupoService.salvar(grupo);
			FacesUtil.addInfoMessage("Grupo salvo com sucesso!");
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
		this.grupo = new Grupo();
	}


	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public boolean isEditando() {
		return this.grupo.getId() != null;
	}

}
