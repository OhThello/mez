package com.mez.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mez.dao.PessoaJuridicaDao;
import com.mez.model.PessoaJuridica;
import com.mez.repository.filter.PessoaJuridicaFilter;
import com.mez.service.NegocioException;
import com.mez.util.jsf.FacesUtil;

@Named
@RequestScoped
public class PesquisaPessoaJuridicaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	PessoaJuridicaDao pessoaJuridicaDao;

	private List<PessoaJuridica> pessoaJuridicas = new ArrayList<>();

	private PessoaJuridica pessoaJuridicaSelecionado;
	private PessoaJuridicaFilter filtro;

	public void excluir() {
		try {
			pessoaJuridicaDao.remover(pessoaJuridicaSelecionado);
			pessoaJuridicas.remove(pessoaJuridicaSelecionado);
			FacesUtil.addInfoMessage(
					"Pessoa Jurídica " + pessoaJuridicaSelecionado.getNome() + " excluído com sucesso.");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	public PesquisaPessoaJuridicaBean() {
		filtro = new PessoaJuridicaFilter();
		pessoaJuridicas = new ArrayList<>();
	}

	public PessoaJuridicaFilter getFiltro() {
		return filtro;
	}

	public void pesquisar() {
		pessoaJuridicas = pessoaJuridicaDao.filtrados(filtro);
	}

	public List<PessoaJuridica> getPessoaJuridicas() {
		return pessoaJuridicas;
	}

	public void setPessoaJuridicas(List<PessoaJuridica> pessoaJuridicas) {
		this.pessoaJuridicas = pessoaJuridicas;
	}

	public PessoaJuridica getPessoaJuridicaSelecionado() {
		return pessoaJuridicaSelecionado;
	}

	public void setPessoaJuridicaSelecionado(PessoaJuridica pessoaJuridicaSelecionado) {
		this.pessoaJuridicaSelecionado = pessoaJuridicaSelecionado;
	}

	@PostConstruct
	public void inicializar() {
		pessoaJuridicas = pessoaJuridicaDao.buscarTodos();
	}

}
