package com.mez.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import javax.inject.Named;

import com.mez.dao.CidadeDao;
import com.mez.model.Cidade;
import com.mez.model.PessoaJuridica;
import com.mez.service.CadastroPessoaJuridicaService;
import com.mez.service.NegocioException;
import com.mez.util.jsf.FacesUtil;

@Named
@ManagedBean
@RequestScoped
public class CadastroPessoaJuridicaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CadastroPessoaJuridicaService cadastroPessoaJuridicaService;

	private PessoaJuridica pessoaJuridica;

	private List<Cidade> cidades;

	@Inject
	private CidadeDao cidadeDao;

	public void salvar() {
		try {
			this.cadastroPessoaJuridicaService.salvar(pessoaJuridica);
			FacesUtil.addInfoMessage("Cliente salvo com sucesso!");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
		this.limpar();
	}

	@PostConstruct
	public void inicializar() {
		this.limpar();
		this.cidades = cidadeDao.buscarTodos();
	}

	private void limpar() {
		this.pessoaJuridica = new PessoaJuridica();

	}

	public PessoaJuridica getPessoaJuridica() {
		return pessoaJuridica;
	}

	public void setPessoaJuridica(PessoaJuridica pessoaJuridica) {
		this.pessoaJuridica = pessoaJuridica;
	}

	public List<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}

	public boolean isEditando() {
		return this.pessoaJuridica.getCodigo() != null;
	}

}
