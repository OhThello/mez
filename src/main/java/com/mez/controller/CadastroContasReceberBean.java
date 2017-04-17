package com.mez.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mez.dao.ContaCorrenteDao;
import com.mez.dao.Pedidos;
import com.mez.dao.PessoaDao;
import com.mez.model.ContaCorrente;
import com.mez.model.ContasReceber;
import com.mez.model.Pedido;
import com.mez.model.Pessoa;
import com.mez.service.CadastroContasReceberService;
import com.mez.service.NegocioException;
import com.mez.util.jsf.FacesUtil;

@Named
@RequestScoped
public class CadastroContasReceberBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private ContasReceber contasReceber;

	private List<Pessoa> pessoas;

	private List<Pedido> pedidos;
	
	private List<ContaCorrente> contaCorrentes;

	@Inject
	private CadastroContasReceberService cadastroContasReceberService;

	@Inject
	private PessoaDao pessoaDao;

	@Inject
	private Pedidos pedidosDao;
	
	@Inject
	private ContaCorrenteDao contaCorrenteDao;

	public void salvar() {
		try {
			this.contasReceber.Saldo();
			this.cadastroContasReceberService.salvar(contasReceber);
			FacesUtil.addInfoMessage("Conta a recida com sucesso!");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}

		this.limpar();
	}

	@PostConstruct
	public void inicializar() {
		this.limpar();
		this.pessoas = pessoaDao.buscarTodos();
		this.pedidos = pedidosDao.buscarTodos();
		this.contaCorrentes = contaCorrenteDao.buscarTodos();
	}

	public void limpar() {
		this.contasReceber = new ContasReceber();
	}

	public ContasReceber getContasReceber() {
		return contasReceber;
	}

	public void setContasReceber(ContasReceber contasReceber) {
		this.contasReceber = contasReceber;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	public List<ContaCorrente> getContaCorrentes() {
		return contaCorrentes;
	}

	public void setContaCorrentes(List<ContaCorrente> contaCorrentes) {
		this.contaCorrentes = contaCorrentes;
	}
	
	

}
