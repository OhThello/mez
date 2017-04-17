package com.mez.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mez.dao.CargoDao;
import com.mez.model.Cargo;
import com.mez.repository.filter.CargoFilter;
import com.mez.service.NegocioException;
import com.mez.util.jsf.FacesUtil;

@Named
@RequestScoped
public class PesquisaCargoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	CargoDao cargoDao;

	private List<Cargo> cargos = new ArrayList<>();

	private Cargo cargoSelecionado;
	private CargoFilter filtro;

	public void excluir() {
		try {
			cargoDao.remover(cargoSelecionado);
			cargos.remove(cargoSelecionado);
			FacesUtil.addInfoMessage("Cargo " + cargoSelecionado.getNome() + " excluído com sucesso.");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	public PesquisaCargoBean() {
		filtro = new CargoFilter();
		cargos = new ArrayList<>();
	}

	public void pesquisar() {
		cargos = cargoDao.filtrados(filtro);
	}

	public CargoFilter getFiltro() {
		return filtro;
	}

	public List<Cargo> getCargos() {
		return cargos;
	}

	public void setCargos(List<Cargo> cargos) {
		this.cargos = cargos;
	}

	public Cargo getCargoSelecionado() {
		return cargoSelecionado;
	}

	public void setCargoSelecionado(Cargo cargoSelecionado) {
		this.cargoSelecionado = cargoSelecionado;
	}

	@PostConstruct
	public void inicializar() {
		cargos = cargoDao.buscarTodos();
	}

}
