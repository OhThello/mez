package com.mez.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.mez.dao.PessoaDao;
import com.mez.model.Pessoa;
import com.mez.util.jpa.Transactional;

public class CadastroPessoaService implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private PessoaDao pessoaDao;
	
	@Transactional
	public void salvar(Pessoa pessoa) throws NegocioException {
		
		
		
		this.pessoaDao.guardar(pessoa);
	}
}

