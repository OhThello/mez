package com.mez.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.mez.dao.UsuarioDao;
import com.mez.model.Usuario;
import com.mez.util.jpa.Transactional;

public class CadastroUsuarioService implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioDao usuarioDao;
	
	@Transactional
	public void salvar(Usuario usuario) throws NegocioException {
		
		
		
		this.usuarioDao.guardar(usuario);
	}
}

