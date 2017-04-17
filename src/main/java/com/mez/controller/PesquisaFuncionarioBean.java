package com.mez.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mez.dao.FuncionarioDao;
import com.mez.model.Funcionario;
import com.mez.repository.filter.FuncionarioFilter;
import com.mez.service.NegocioException;
import com.mez.util.jsf.FacesUtil;

@Named
@RequestScoped
public class PesquisaFuncionarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	FuncionarioDao funcionarioDao;

	private List<Funcionario> funcionarios = new ArrayList<>();

	private Funcionario funcionarioSelecionado;
	private FuncionarioFilter filtro;

	public void excluir() {
		try {
			funcionarioDao.remover(funcionarioSelecionado);
			funcionarios.remove(funcionarioSelecionado);
			FacesUtil.addInfoMessage("Funcionário " + funcionarioSelecionado.getNome() + " excluído com sucesso.");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	public PesquisaFuncionarioBean() {
		filtro = new FuncionarioFilter();
		funcionarios = new ArrayList<>();
	}

	public void pesquisar() {
		funcionarios = funcionarioDao.filtrados(filtro);
	}

	public FuncionarioFilter getFiltro() {
		return filtro;
	}

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

	public Funcionario getFuncionarioSelecionado() {
		return funcionarioSelecionado;
	}

	public void setFuncionarioSelecionado(Funcionario funcionarioSelecionado) {
		this.funcionarioSelecionado = funcionarioSelecionado;
	}

	@PostConstruct
	public void inicializar() {
		funcionarios = funcionarioDao.buscarTodos();
	}

}
