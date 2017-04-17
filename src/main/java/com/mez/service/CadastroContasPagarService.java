package com.mez.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.mez.dao.ContasPagarDao;
import com.mez.model.ContasPagar;
import com.mez.util.jpa.Transactional;

public class CadastroContasPagarService implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private ContasPagarDao contasPagarDao;

	@Transactional
	public void salvar(ContasPagar contasPagar) throws NegocioException {

		this.contasPagarDao.guardar(contasPagar);

	}
}
