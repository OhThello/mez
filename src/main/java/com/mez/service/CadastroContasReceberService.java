package com.mez.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.mez.dao.ContasReceberDao;
import com.mez.model.ContasReceber;
import com.mez.util.jpa.Transactional;

public class CadastroContasReceberService implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private ContasReceberDao contasReceberDao;
	
	@Transactional
	public void salvar(ContasReceber contasReceber) throws NegocioException {
		
		
		
		this.contasReceberDao.guardar(contasReceber);
	}
}

