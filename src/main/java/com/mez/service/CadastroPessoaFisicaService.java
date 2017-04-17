package com.mez.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.mez.dao.PessoaFisicaDao;
import com.mez.model.PessoaFisica;
import com.mez.util.jpa.Transactional;

public class CadastroPessoaFisicaService implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private PessoaFisicaDao pessoaFisicaDao;
	
	@Transactional
	public void salvar(PessoaFisica pessoaFisica) throws NegocioException {
		
		
		
		this.pessoaFisicaDao.guardar(pessoaFisica);
	}
}

