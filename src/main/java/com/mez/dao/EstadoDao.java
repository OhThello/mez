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

import com.mez.model.Estado;
import com.mez.repository.filter.EstadoFilter;
import com.mez.service.NegocioException;
import com.mez.util.jpa.Transactional;

public class EstadoDao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	@SuppressWarnings("unchecked")
	public List<Estado> filtrados(EstadoFilter filtro) {
		Session session = this.manager.unwrap(Session.class);

		Criteria criteria = session.createCriteria(Estado.class);

		if (StringUtils.isNotBlank(filtro.getNomeEstado())) {
			criteria.add(Restrictions.ilike("nome", filtro.getNomeEstado(), MatchMode.ANYWHERE));
		}

		if (StringUtils.isNotBlank(filtro.getNomeSigla())) {
			criteria.add(Restrictions.ilike("sigla", filtro.getNomeSigla(), MatchMode.ANYWHERE));
		}

		return criteria.addOrder(Order.asc("codigo")).list();
	}

	public Estado guardar(Estado estado) {
		return manager.merge(estado);
	}

	@Transactional
	public void remover(Estado estado) {
		try {
			estado = buscarPeloCodigo(estado.getCodigo());
			manager.remove(estado);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Estado não pode ser excluído.");
		}
	}

	public Estado buscarPeloCodigo(Long id) {
		return manager.find(Estado.class, id);
	}

	public List<Estado> porNome(String nome) {
		return this.manager.createQuery("from Estado where upper(nome) like :nome", Estado.class)
				.setParameter("nome", nome.toUpperCase() + "%").getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Estado> buscarTodos() {
		return manager.createQuery("from Estado order by nome asc").getResultList();
	}

}
