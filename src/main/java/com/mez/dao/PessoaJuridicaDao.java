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

import com.mez.model.PessoaJuridica;
import com.mez.repository.filter.PessoaJuridicaFilter;
import com.mez.service.NegocioException;
import com.mez.util.jpa.Transactional;

public class PessoaJuridicaDao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public PessoaJuridica guardar(PessoaJuridica pessoaJuridica) {
		return manager.merge(pessoaJuridica);
	}

	@SuppressWarnings("unchecked")
	public List<PessoaJuridica> filtrados(PessoaJuridicaFilter filtro) {
		Session session = this.manager.unwrap(Session.class);

		Criteria criteria = session.createCriteria(PessoaJuridica.class).createAlias("cidade", "c");

		if (StringUtils.isNotBlank(filtro.getNomeFornecedor())) {
			criteria.add(Restrictions.ilike("nome", filtro.getNomeFornecedor(), MatchMode.ANYWHERE));
		}

		if (StringUtils.isNotBlank(filtro.getNomeCidade())) {
			criteria.add(Restrictions.ilike("c.nome", filtro.getNomeCidade(), MatchMode.ANYWHERE));
		}

		return criteria.addOrder(Order.asc("codigo")).list();
	}

	@Transactional
	public void remover(PessoaJuridica pessoaJuridica) {
		try {
			pessoaJuridica = porId(pessoaJuridica.getCodigo());
			manager.remove(pessoaJuridica);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Pessoa Jurídica não pode ser excluído.");
		}
	}

	public PessoaJuridica porId(Long id) {
		return manager.find(PessoaJuridica.class, id);
	}

	public List<PessoaJuridica> porNome(String nome) {
		return this.manager.createQuery("from PessoaJuridica where upper(nome) like :nome", PessoaJuridica.class)
				.setParameter("nome", nome.toUpperCase() + "%").getResultList();
	}

	public PessoaJuridica buscarPeloCodigo(Long codigo) {
		return manager.find(PessoaJuridica.class, codigo);
	}

	@SuppressWarnings("unchecked")
	public List<PessoaJuridica> buscarTodos() {
		return manager.createQuery("from PessoaJuridica order by nome asc").getResultList();
	}

}
