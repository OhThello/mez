package com.mez.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.mez.dao.CidadeDao;
import com.mez.model.Cidade;
import com.mez.util.jpa.Transactional;

public class CadastroCidadeService implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private CidadeDao cidadeDao;
	
	@Transactional
	public void salvar(Cidade cidade) throws NegocioException {
		
		
		
		this.cidadeDao.guardar(cidade);
	}
}

