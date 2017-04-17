package com.mez.controller;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.validator.constraints.NotBlank;

import com.mez.dao.FuncionarioDao;
import com.mez.dao.GrupoDao;
import com.mez.model.Funcionario;
import com.mez.model.Grupo;
import com.mez.model.PermissaoUsuario;
import com.mez.model.Usuario;
import com.mez.service.CadastroGrupoService;
import com.mez.service.CadastroUsuarioService;
import com.mez.service.NegocioException;
import com.mez.util.jsf.FacesUtil;

@Named
@RequestScoped
public class CadastroUsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Usuario usuario;

	@NotBlank
	private String senha;
	@NotBlank
	private String senha2;

	private List<Funcionario> funcionarios;

	@Inject
	private CadastroUsuarioService cadastroUsuarioService;

	@Inject
	private GrupoDao grupoDao;

	@Inject
	private FuncionarioDao funcionarioDao;

	@Inject
	private CadastroGrupoService gpService;
	private List<Grupo> grupos = new ArrayList<>();
	private List<String> listaGrupos = new ArrayList<>();
	private List<String> gruposSelecionados = new ArrayList<String>();

	public void salvar() {
		try {
			List<Grupo> listaGruposString = new ArrayList<Grupo>();
			for (String grupo : gruposSelecionados) {
				listaGruposString.add(gpService.peloNome(grupo));

			}

			this.usuario.setSenha(criptografar(senha).toLowerCase());
			this.usuario.setGrupos(listaGruposString);
			this.cadastroUsuarioService.salvar(usuario);
			FacesUtil.addInfoMessage("Usuario salvo com sucesso!");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}

		this.limpar();
	}

	public PermissaoUsuario[] getPermissoes() {
		return PermissaoUsuario.values();
	}

	public String criptografar(String senha) {

		MessageDigest algoritmo;
		byte messageDigest[];
		StringBuilder hexString;
		try {
			algoritmo = MessageDigest.getInstance("MD5");
			messageDigest = algoritmo.digest(senha.getBytes("UTF-8"));
			hexString = new StringBuilder();
			for (byte b : messageDigest) {
				hexString.append(String.format("%02X", 0xFF & b));
			}
			senha = hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return senha;
	}

	@PostConstruct
	public void inicializar() {
		this.limpar();
		this.grupos = grupoDao.buscarTodos();
		this.funcionarios = funcionarioDao.buscarTodos();
	}

	public void limpar() {
		this.usuario = new Usuario();
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getSenha2() {
		return senha2;
	}

	public void setSenha2(String senha2) {
		this.senha2 = senha2;
	}

	public List<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}

	public List<String> getListaGrupos() {
		return listaGrupos;
	}

	public void setListaGrupos(List<String> listaGrupos) {
		this.listaGrupos = listaGrupos;
	}

	public List<String> getGruposSelecionados() {
		return gruposSelecionados;
	}

	public void setGruposSelecionados(List<String> gruposSelecionados) {
		this.gruposSelecionados = gruposSelecionados;
	}

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

	public boolean isEditando() {
		return this.usuario.getId() != null;
	}

}
