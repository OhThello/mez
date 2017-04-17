package com.mez.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mez.dao.CompraDao;
import com.mez.dao.ContaCorrenteDao;
import com.mez.dao.PessoaJuridicaDao;
import com.mez.model.Compra;
import com.mez.model.ContaCorrente;
import com.mez.model.ContasPagar;
import com.mez.model.PessoaJuridica;
import com.mez.service.CadastroContasPagarService;
import com.mez.service.NegocioException;
import com.mez.util.jsf.FacesUtil;

@Named
@RequestScoped
public class CadastroContasPagarBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private ContasPagar contasPagar;

	private List<PessoaJuridica> pessoas;

	private List<Compra> compras;

	private List<ContaCorrente> contaCorrentes;

	@Inject
	private CadastroContasPagarService cadastroContasPagarService;

	@Inject
	private PessoaJuridicaDao pessoaJuridicaDao;

	@Inject
	private CompraDao compraDao;

	@Inject
	private ContaCorrenteDao contaCorrenteDao;

	public void salvar() {
		try {
			this.contasPagar.Saldo();
			ContaCorrente conta = new ContaCorrente();
			conta.setData(contasPagar.getDataPagamento());
			contaCorrenteDao.guardar(conta);
			this.cadastroContasPagarService.salvar(contasPagar);
			FacesUtil.addInfoMessage("Conta paga com sucesso!");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}

		this.limpar();
	}
	
	public void salvarConta() {
		try {
			this.cadastroContasPagarService.salvar(contasPagar);
			FacesUtil.addInfoMessage("Conta cadastrada com sucesso!");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}

		this.limpar();
	}

	@PostConstruct
	public void inicializar() {
		this.limpar();
		this.pessoas = pessoaJuridicaDao.buscarTodos();
		this.compras = compraDao.buscarTodos();
		this.contaCorrentes = contaCorrenteDao.buscarTodos();
	}

	public void limpar() {
		this.contasPagar = new ContasPagar();
	}

	public ContasPagar getContasPagar() {
		return contasPagar;
	}

	public void setContasPagar(ContasPagar contasPagar) {
		this.contasPagar = contasPagar;
	}

	public List<PessoaJuridica> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<PessoaJuridica> pessoas) {
		this.pessoas = pessoas;
	}

	public List<Compra> getCompras() {
		return compras;
	}

	public void setCompras(List<Compra> compras) {
		this.compras = compras;
	}

	public List<ContaCorrente> getContaCorrentes() {
		return contaCorrentes;
	}

	public void setContaCorrentes(List<ContaCorrente> contaCorrentes) {
		this.contaCorrentes = contaCorrentes;
	}

}
