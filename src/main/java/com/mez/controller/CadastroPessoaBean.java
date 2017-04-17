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
import com.mez.model.Pessoa;
import com.mez.model.PessoaFisica;
import com.mez.model.PessoaJuridica;
import com.mez.service.CadastroPessoaService;
import com.mez.service.NegocioException;
import com.mez.util.jsf.FacesUtil;

@Named
@ManagedBean
@RequestScoped
public class CadastroPessoaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CadastroPessoaService cadastroPessoaService;

	private Pessoa pessoa;
	private String tipoPessoa;

	private List<Cidade> cidades;

	@Inject
	private CidadeDao cidadeDao;

	public void salvar() {
		try {
			this.cadastroPessoaService.salvar(pessoa);
			FacesUtil.addInfoMessage("Cliente salvo com sucesso!");
			this.limpar();
			criaPessoa();
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	@PostConstruct
	public void init() {
		this.limpar();
	}

	private void limpar() {
		this.pessoa = new Pessoa();
		this.cidades = cidadeDao.buscarTodos();

	}

	public String novo() {
		criaPessoa();
		return "CadastroPessoa?faces-redirect=true";
	}

	public void criaPessoa() {
		if (getTipoPessoa() != null && getTipoPessoa().equals("PF")) {
			pessoa = new PessoaFisica();
		} if (getTipoPessoa() != null && getTipoPessoa().equals("PJ")){
			pessoa = new PessoaJuridica();
		}
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		if (pessoa instanceof PessoaFisica) {
			tipoPessoa = "PF";
		} if (pessoa instanceof PessoaJuridica) {
			tipoPessoa = "PJ";
		}
		this.pessoa = pessoa;
	}

	public String getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(String tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public List<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}

	public boolean isEditando() {
		return this.pessoa.getCodigo() != null;
	}

}
