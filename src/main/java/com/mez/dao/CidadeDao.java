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

import com.mez.model.Cidade;
import com.mez.repository.filter.CidadeFilter;
import com.mez.service.NegocioException;
import com.mez.util.jpa.Transactional;

public class CidadeDao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	@SuppressWarnings("unchecked")
	public List<Cidade> filtrados(CidadeFilter filtro) {
		Session session = this.manager.unwrap(Session.class);

		Criteria criteria = session.createCriteria(Cidade.class).createAlias("estado", "e");

		if (StringUtils.isNotBlank(filtro.getNomeCidade())) {
			criteria.add(Restrictions.ilike("nome", filtro.getNomeCidade(), MatchMode.ANYWHERE));
		}

		if (StringUtils.isNotBlank(filtro.getNomeEstado())) {
			criteria.add(Restrictions.ilike("e.nome", filtro.getNomeEstado(), MatchMode.ANYWHERE));
		}

		return criteria.addOrder(Order.asc("codigo")).list();
	}

	public Cidade guardar(Cidade cidade) {
		return manager.merge(cidade);
	}

	@Transactional
	public void remover(Cidade cidade) {
		try {
			cidade = porId(cidade.getCodigo());
			manager.remove(cidade);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Cidade não pode ser excluído.");
		}
	}

	public Cidade porId(Long id) {
		return manager.find(Cidade.class, id);
	}

	public List<Cidade> porNome(String nome) {
		return this.manager.createQuery("from Cidade where upper(nome) like :nome", Cidade.class)
				.setParameter("nome", nome.toUpperCase() + "%").getResultList();
	}

	public Cidade buscarPeloCodigo(Long codigo) {
		return manager.find(Cidade.class, codigo);
	}

	@SuppressWarnings("unchecked")
	public List<Cidade> buscarTodos() {
		return manager.createQuery("from Cidade order by nome asc").getResultList();
	}

}
