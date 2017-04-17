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

import com.mez.model.PessoaFisica;
import com.mez.repository.filter.PessoaFisicaFilter;
import com.mez.service.NegocioException;
import com.mez.util.jpa.Transactional;

public class PessoaFisicaDao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	@SuppressWarnings("unchecked")
	public List<PessoaFisica> filtrados(PessoaFisicaFilter filtro) {
		Session session = this.manager.unwrap(Session.class);

		Criteria criteria = session.createCriteria(PessoaFisica.class).createAlias("cidade", "c");

		if (StringUtils.isNotBlank(filtro.getNomeCliente())) {
			criteria.add(Restrictions.ilike("nome", filtro.getNomeCliente(), MatchMode.ANYWHERE));
		}

		if (StringUtils.isNotBlank(filtro.getNomeCidade())) {
			criteria.add(Restrictions.ilike("c.nome", filtro.getNomeCidade(), MatchMode.ANYWHERE));
		}

		return criteria.addOrder(Order.asc("codigo")).list();
	}

	public PessoaFisica guardar(PessoaFisica pessoaFisica) {
		return manager.merge(pessoaFisica);
	}

	@Transactional
	public void remover(PessoaFisica pessoaFisica) {
		try {
			pessoaFisica = porId(pessoaFisica.getCodigo());
			manager.remove(pessoaFisica);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Pessoa Física não pode ser excluído.");
		}
	}

	public PessoaFisica porId(Long id) {
		return manager.find(PessoaFisica.class, id);
	}

	public List<PessoaFisica> porNome(String nome) {
		return this.manager.createQuery("from PessoaFisica where upper(nome) like :nome", PessoaFisica.class)
				.setParameter("nome", nome.toUpperCase() + "%").getResultList();
	}

	public PessoaFisica buscarPeloCodigo(Long codigo) {
		return manager.find(PessoaFisica.class, codigo);
	}

	@SuppressWarnings("unchecked")
	public List<PessoaFisica> buscarTodos() {
		return manager.createQuery("from PessoaFisica order by nome asc").getResultList();
	}

}
