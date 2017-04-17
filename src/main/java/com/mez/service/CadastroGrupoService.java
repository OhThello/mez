package com.mez.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.mez.dao.GrupoDao;
import com.mez.model.Grupo;
import com.mez.util.jpa.Transactional;

public class CadastroGrupoService implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private GrupoDao grupoDao;

	@Transactional
	public void salvar(Grupo grupo) throws NegocioException {

		this.grupoDao.guardar(grupo);
	}
	
	public List<Grupo> buscarTodos() {
		return grupoDao.buscarTodos();
	}
	
	public Grupo peloCodigo(Long id){
		return this.grupoDao.buscarPeloCodigo(id);
	}
	
	public Grupo peloNome(String id){
		return this.grupoDao.peloNome(id);
	}

}
