package com.mez.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mez.dao.UsuarioDao;
import com.mez.model.Usuario;
import com.mez.repository.filter.UsuarioFilter;
import com.mez.service.NegocioException;
import com.mez.util.jsf.FacesUtil;

@Named
@RequestScoped
public class PesquisaUsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	UsuarioDao usuarioDao;

	private List<Usuario> usuarios = new ArrayList<>();

	private Usuario usuarioSelecionado;
	private UsuarioFilter filtro;

	public void excluir() {
		try {
			usuarioDao.remover(usuarioSelecionado);
			usuarios.remove(usuarioSelecionado);
			FacesUtil.addInfoMessage("Usuario " + usuarioSelecionado.getNome() + " excluído com sucesso.");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage("Usuario " + usuarioSelecionado.getNome() + " não pode ser excluído.");
		}
	}

	public PesquisaUsuarioBean() {
		filtro = new UsuarioFilter();
		usuarios = new ArrayList<>();
	}

	public void pesquisar() {
		usuarios = usuarioDao.filtrados(filtro);
	}

	public UsuarioFilter getFiltro() {
		return filtro;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Usuario getUsuarioSelecionado() {
		return usuarioSelecionado;
	}

	public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}

	@PostConstruct
	public void inicializar() {
		usuarios = usuarioDao.buscarTodos();
	}

}
