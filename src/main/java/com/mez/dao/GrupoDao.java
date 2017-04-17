package com.mez.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.mez.model.Grupo;
import com.mez.repository.filter.GrupoFilter;
import com.mez.service.NegocioException;
import com.mez.util.jpa.Transactional;

public class GrupoDao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Grupo guardar(Grupo grupo) {
		return manager.merge(grupo);
	}

	@SuppressWarnings("unchecked")
	public List<Grupo> filtrados(GrupoFilter filtro) {
		Session session = this.manager.unwrap(Session.class);

		Criteria criteria = session.createCriteria(Grupo.class);

		if (StringUtils.isNotBlank(filtro.getNomeGrupo())) {
			criteria.add(Restrictions.ilike("nome", filtro.getNomeGrupo(), MatchMode.ANYWHERE));
		}

		if (StringUtils.isNotBlank(filtro.getNomeDescricao())) {
			criteria.add(Restrictions.ilike("descricao", filtro.getNomeDescricao(), MatchMode.ANYWHERE));
		}

		return criteria.addOrder(Order.asc("id")).list();
	}

	@Transactional
	public void remover(Grupo grupo) {
		try {
			grupo = porId(grupo.getId());
			manager.remove(grupo);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Grupo não pode ser excluído.");
		}
	}

	public Grupo porId(Long id) {
		return manager.find(Grupo.class, id);
	}

	public List<Grupo> porNome(String nome) {
		return this.manager.createQuery("from Grupo where upper(nome) like :nome", Grupo.class)
				.setParameter("nome", nome.toUpperCase() + "%").getResultList();
	}

	public Grupo buscarPeloCodigo(Long codigo) {
		return manager.find(Grupo.class, codigo);
	}

	@SuppressWarnings("unchecked")
	public List<Grupo> buscarTodos() {
		return manager.createQuery("from Grupo order by nome desc").getResultList();
	}
	
	public Grupo peloNome(String nome) {
		return this.manager
				.createQuery("from Grupo where nome = :nome", Grupo.class)
				.setParameter("nome", nome).getSingleResult();
	}

}
