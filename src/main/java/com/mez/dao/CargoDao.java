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

import com.mez.model.Cargo;
import com.mez.repository.filter.CargoFilter;
import com.mez.service.NegocioException;
import com.mez.util.jpa.Transactional;

public class CargoDao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	@SuppressWarnings("unchecked")
	public List<Cargo> filtrados(CargoFilter filtro) {
		Session session = this.manager.unwrap(Session.class);

		Criteria criteria = session.createCriteria(Cargo.class);

		if (StringUtils.isNotBlank(filtro.getNomeCargo())) {
			criteria.add(Restrictions.ilike("nome", filtro.getNomeCargo(), MatchMode.ANYWHERE));
		}

		if (StringUtils.isNotBlank(filtro.getNomeDescricao())) {
			criteria.add(Restrictions.ilike("descricao", filtro.getNomeDescricao(), MatchMode.ANYWHERE));
		}

		return criteria.addOrder(Order.asc("codigo")).list();
	}

	public Cargo guardar(Cargo cargo) {
		return manager.merge(cargo);
	}

	@Transactional
	public void remover(Cargo cargo) {
		try {
			cargo = porId(cargo.getCodigo());
			manager.remove(cargo);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Cargo não pode ser excluído.");
		}
	}

	public Cargo porId(Long id) {
		return manager.find(Cargo.class, id);
	}

	public List<Cargo> porNome(String nome) {
		return this.manager.createQuery("from Cargo where upper(nome) like :nome", Cargo.class)
				.setParameter("nome", nome.toUpperCase() + "%").getResultList();
	}

	public Cargo buscarPeloCodigo(Long codigo) {
		return manager.find(Cargo.class, codigo);
	}

	@SuppressWarnings("unchecked")
	public List<Cargo> buscarTodos() {
		return manager.createQuery("from Cargo order by nome asc").getResultList();
	}

}
