package com.mez.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.mez.dao.GrupoProdutoDao;
import com.mez.model.GrupoProduto;
import com.mez.util.jpa.Transactional;

public class CadastroGrupoProdutoService implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private GrupoProdutoDao grupoProdutoDao;
	
	@Transactional
	public void salvar(GrupoProduto grupoProduto) throws NegocioException {
		
		
		
		this.grupoProdutoDao.guardar(grupoProduto);
	}
}

