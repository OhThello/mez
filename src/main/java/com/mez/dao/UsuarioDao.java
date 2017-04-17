package com.mez.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.mez.model.Usuario;
import com.mez.repository.filter.UsuarioFilter;
import com.mez.service.NegocioException;
import com.mez.util.jpa.Transactional;

public class UsuarioDao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Usuario guardar(Usuario usuario) {
		return manager.merge(usuario);
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> filtrados(UsuarioFilter filtro) {
		Session session = this.manager.unwrap(Session.class);

		Criteria criteria = session.createCriteria(Usuario.class);

		if (StringUtils.isNotBlank(filtro.getNomeUsuario())) {
			criteria.add(Restrictions.ilike("nome", filtro.getNomeUsuario(), MatchMode.ANYWHERE));
		}

		return criteria.addOrder(Order.asc("id")).list();
	}

	@Transactional
	public void remover(Usuario usuario) {
		try {
			usuario = porId(usuario.getId());
			manager.remove(usuario);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Usuário não pode ser excluído.");
		}
	}

	public Usuario porId(Long id) {
		return manager.find(Usuario.class, id);
	}

	public List<Usuario> porNome(String nome) {
		return this.manager.createQuery("from Usuario where upper(nome) like :nome", Usuario.class)
				.setParameter("nome", nome.toUpperCase() + "%").getResultList();
	}

	public Usuario buscarPeloCodigo(Long codigo) {
		return manager.find(Usuario.class, codigo);
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> buscarTodos() {
		return manager.createQuery("from Usuario order by nome asc").getResultList();
	}

	public List<Usuario> vendedores() {
		// TODO filtrar apenas vendedores (por um grupo específico)
		return this.manager.createQuery("from Usuario", Usuario.class).getResultList();
	}

	public Usuario porEmail(String email) {
		Usuario usuario = null;

		try {
			usuario = this.manager.createQuery("from Usuario where lower(email) = :email", Usuario.class)
					.setParameter("email", email.toLowerCase()).getSingleResult();
		} catch (NoResultException e) {
			// nenhum usuário encontrado com o e-mail informado
		}

		return usuario;
	}

}
