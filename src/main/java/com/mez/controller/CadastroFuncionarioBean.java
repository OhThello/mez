package com.mez.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mez.dao.CargoDao;
import com.mez.dao.CidadeDao;
import com.mez.model.Cargo;
import com.mez.model.Cidade;
import com.mez.model.Funcionario;
import com.mez.service.CadastroFuncionarioService;
import com.mez.service.NegocioException;
import com.mez.util.jsf.FacesUtil;

@Named
@RequestScoped
public class CadastroFuncionarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Funcionario funcionario;

	private List<Cargo> cargos;

	private List<Cidade> cidades;

	@Inject
	private CadastroFuncionarioService cadastroFuncionarioService;

	@Inject
	private CargoDao cargoDao;

	@Inject
	private CidadeDao cidadeDao;

	public void salvar() {
		try {
			this.cadastroFuncionarioService.salvar(funcionario);
			FacesUtil.addInfoMessage("Funcionário salvo com sucesso!");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}

		this.limpar();
	}

	@PostConstruct
	public void inicializar() {
		this.limpar();
		this.cargos = cargoDao.buscarTodos();
		this.cidades = cidadeDao.buscarTodos();
	}

	public void limpar() {
		this.funcionario = new Funcionario();
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public List<Cargo> getCargos() {
		return cargos;
	}

	public void setCargos(List<Cargo> cargos) {
		this.cargos = cargos;
	}

	public List<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}

	public boolean isEditando() {
		return this.funcionario.getCodigo() != null;
	}

}
