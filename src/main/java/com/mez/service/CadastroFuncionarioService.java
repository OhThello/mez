package com.mez.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.mez.dao.FuncionarioDao;
import com.mez.model.Funcionario;
import com.mez.util.jpa.Transactional;

public class CadastroFuncionarioService implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private FuncionarioDao funcionarioDao;
	
	@Transactional
	public void salvar(Funcionario funcionario) throws NegocioException {
		
		
		
		this.funcionarioDao.guardar(funcionario);
	}
}

