package com.mez.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.mez.dao.ContaCorrenteDao;
import com.mez.model.ContaCorrente;
import com.mez.util.jpa.Transactional;

public class CadastroContaCorrenteService implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private ContaCorrenteDao contaCorrenteDao;
	
	@Transactional
	public void salvar(ContaCorrente contaCorrente) throws NegocioException {
		
		
		
		this.contaCorrenteDao.guardar(contaCorrente);
	}
}

