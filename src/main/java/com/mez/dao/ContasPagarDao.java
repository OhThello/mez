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

import com.mez.model.ContasPagar;
import com.mez.repository.filter.ContasPagarFilter;
import com.mez.service.NegocioException;
import com.mez.util.jpa.Transactional;

public class ContasPagarDao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	@SuppressWarnings("unchecked")
	public List<ContasPagar> filtrados(ContasPagarFilter filtro) {
		Session session = this.manager.unwrap(Session.class);

		Criteria criteria = session.createCriteria(ContasPagar.class).createAlias("pessoaJuridica", "v");

		if (filtro.getNumeroDe() != null) {
			criteria.add(Restrictions.ge("id", filtro.getNumeroDe()));
		}

		if (filtro.getNumeroAte() != null) {
			criteria.add(Restrictions.le("id", filtro.getNumeroAte()));
		}

		if (filtro.getDataCriacaoDe() != null) {
			criteria.add(Restrictions.ge("dataVencimento", filtro.getDataCriacaoDe()));
		}

		if (filtro.getDataCriacaoAte() != null) {
			criteria.add(Restrictions.le("dataVencimento", filtro.getDataCriacaoAte()));
		}

		if (StringUtils.isNotBlank(filtro.getNomeFornecedor())) {
			criteria.add(Restrictions.ilike("v.nome", filtro.getNomeFornecedor(), MatchMode.ANYWHERE));
		}

		return criteria.addOrder(Order.asc("id")).list();
	}

	public ContasPagar guardar(ContasPagar contasPagar) {
		return manager.merge(contasPagar);
	}

	@Transactional
	public void remover(ContasPagar contasPagar) {
		try {
			contasPagar = porId(contasPagar.getId());
			manager.remove(contasPagar);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Contas a Pagar não pode ser excluído.");
		}
	}

	public ContasPagar porId(Long id) {
		return manager.find(ContasPagar.class, id);
	}

	public List<ContasPagar> porNome(String nome) {
		return this.manager.createQuery("from ContasPagar where upper(nome) like :nome", ContasPagar.class)
				.setParameter("nome", nome.toUpperCase() + "%").getResultList();
	}

	public ContasPagar buscarPeloCodigo(Long codigo) {
		return manager.find(ContasPagar.class, codigo);
	}

	@SuppressWarnings("unchecked")
	public List<ContasPagar> buscarTodos() {
		return manager.createQuery("from ContasPagar order by dataVencimento desc").getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<ContasPagar> listarContas(String where) {
		return manager.createQuery("from ContasPagar  where" + where + " order by id desc").getResultList();
	}

}
