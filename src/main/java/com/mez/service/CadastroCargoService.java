package com.mez.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.mez.dao.CargoDao;
import com.mez.model.Cargo;
import com.mez.util.jpa.Transactional;

public class CadastroCargoService implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private CargoDao cargoDao;
	
	@Transactional
	public void salvar(Cargo cargo) throws NegocioException {
		
		
		
		this.cargoDao.guardar(cargo);
	}
}

