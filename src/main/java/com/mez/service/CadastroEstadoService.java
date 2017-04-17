package com.mez.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.mez.dao.EstadoDao;
import com.mez.model.Estado;
import com.mez.util.jpa.Transactional;

public class CadastroEstadoService implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private EstadoDao estadoDao;

	@Transactional
	public void salvar(Estado estado) throws NegocioException {

		this.estadoDao.guardar(estado);
	}
}
