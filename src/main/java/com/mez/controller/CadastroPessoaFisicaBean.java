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
import com.mez.model.PessoaFisica;
import com.mez.service.CadastroPessoaFisicaService;
import com.mez.service.NegocioException;
import com.mez.util.jsf.FacesUtil;

@Named
@ManagedBean
@RequestScoped
public class CadastroPessoaFisicaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CadastroPessoaFisicaService cadastroPessoaFisicaService;

	private PessoaFisica pessoaFisica;

	private List<Cidade> cidades;

	@Inject
	private CidadeDao cidadeDao;

	public void salvar() {
		try {
			this.cadastroPessoaFisicaService.salvar(pessoaFisica);
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
		this.pessoaFisica = new PessoaFisica();

	}


	public PessoaFisica getPessoaFisica() {
		return pessoaFisica;
	}

	public void setPessoaFisica(PessoaFisica pessoaFisica) {
		this.pessoaFisica = pessoaFisica;
	}

	public List<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}

	public boolean isEditando() {
		return this.pessoaFisica.getCodigo() != null;
	}

}
