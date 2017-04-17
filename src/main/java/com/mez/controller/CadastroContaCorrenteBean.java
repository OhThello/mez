package com.mez.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mez.dao.ContasPagarDao;
import com.mez.dao.ContasReceberDao;
import com.mez.model.ContaCorrente;
import com.mez.model.ContasPagar;
import com.mez.model.ContasReceber;
import com.mez.service.CadastroContaCorrenteService;
import com.mez.service.NegocioException;
import com.mez.util.jsf.FacesUtil;

@Named
@RequestScoped
public class CadastroContaCorrenteBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private ContaCorrente contaCorrente;

	private List<ContasPagar> contasPagas;
	private List<ContasReceber> contasRecebidas;

	@Inject
	private CadastroContaCorrenteService cadastroContaCorrenteService;

	@Inject
	private ContasPagarDao contasPagarDao;

	@Inject
	private ContasReceberDao contasReceberDao;

	public void salvar() {
		try {
			this.cadastroContaCorrenteService.salvar(contaCorrente);
			FacesUtil.addInfoMessage("Conta corrente salvo com sucesso!");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}

		this.limpar();
	}

	@PostConstruct
	public void inicializar() {
		this.limpar();
		this.contasPagas = contasPagarDao.buscarTodos();
		this.contasRecebidas = contasReceberDao.buscarTodos();
	}

	public void limpar() {
		this.contaCorrente = new ContaCorrente();
	}

	public ContaCorrente getContaCorrente() {
		return contaCorrente;
	}

	public void setContaCorrente(ContaCorrente contaCorrente) {
		this.contaCorrente = contaCorrente;
	}

	public List<ContasPagar> getContasPagas() {
		return contasPagas;
	}

	public void setContasPagas(List<ContasPagar> contasPagas) {
		this.contasPagas = contasPagas;
	}

	public List<ContasReceber> getContasRecebidas() {
		return contasRecebidas;
	}

	public void setContasRecebidas(List<ContasReceber> contasRecebidas) {
		this.contasRecebidas = contasRecebidas;
	}

	public boolean isEditando() {
		return this.contaCorrente.getCodigo() != null;
	}

}
