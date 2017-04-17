package com.mez.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.mez.dao.PessoaJuridicaDao;
import com.mez.model.PessoaJuridica;
import com.mez.util.jpa.Transactional;

public class CadastroPessoaJuridicaService implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private PessoaJuridicaDao pessoaJuridicaDao;
	
	@Transactional
	public void salvar(PessoaJuridica pessoaJuridica) throws NegocioException {
		
		
		
		this.pessoaJuridicaDao.guardar(pessoaJuridica);
	}
}

