package com.mez.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.mez.dao.AjustaEstoqueDao;
import com.mez.model.AjustaEstoque;
import com.mez.util.jpa.Transactional;

public class CadastroAjustaEstoqueService implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private AjustaEstoqueDao ajustaEstoqueDao;
	
	@Transactional
	public void salvar(AjustaEstoque ajustaEstoque) throws NegocioException {
		
		
		
		this.ajustaEstoqueDao.guardar(ajustaEstoque);
	}
}

