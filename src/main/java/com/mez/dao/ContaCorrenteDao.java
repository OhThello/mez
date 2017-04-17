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

import com.mez.model.ContaCorrente;
import com.mez.repository.filter.ContaCorrenteFilter;
import com.mez.service.NegocioException;
import com.mez.util.jpa.Transactional;

public class ContaCorrenteDao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	@SuppressWarnings("unchecked")
	public List<ContaCorrente> filtrados(ContaCorrenteFilter filtro) {
		Session session = this.manager.unwrap(Session.class);

		Criteria criteria = session.createCriteria(ContaCorrente.class);

		if (StringUtils.isNotBlank(filtro.getNomeConta())) {
			criteria.add(Restrictions.ilike("nome", filtro.getNomeConta(), MatchMode.ANYWHERE));
		}

		return criteria.addOrder(Order.asc("codigo")).list();
	}

	public ContaCorrente guardar(ContaCorrente contaCorrente) {
		return manager.merge(contaCorrente);
	}

	@Transactional
	public void remover(ContaCorrente contaCorrente) {
		try {
			contaCorrente = porId(contaCorrente.getCodigo());
			manager.remove(contaCorrente);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Conta corrente não pode ser excluído.");
		}
	}

	public ContaCorrente porId(Long id) {
		return manager.find(ContaCorrente.class, id);
	}

	public List<ContaCorrente> porNome(String nome) {
		return this.manager.createQuery("from ContaCorrente where upper(nome) like :nome", ContaCorrente.class)
				.setParameter("nome", nome.toUpperCase() + "%").getResultList();
	}

	public ContaCorrente buscarPeloCodigo(Long codigo) {
		return manager.find(ContaCorrente.class, codigo);
	}

	@SuppressWarnings("unchecked")
	public List<ContaCorrente> buscarTodos() {
		return manager.createQuery("from ContaCorrente order by nome asc").getResultList();
	}

}
