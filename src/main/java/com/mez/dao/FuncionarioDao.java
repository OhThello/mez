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

import com.mez.model.Funcionario;
import com.mez.repository.filter.FuncionarioFilter;
import com.mez.service.NegocioException;
import com.mez.util.jpa.Transactional;

public class FuncionarioDao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Funcionario guardar(Funcionario funcionario) {
		return manager.merge(funcionario);
	}

	@SuppressWarnings("unchecked")
	public List<Funcionario> filtrados(FuncionarioFilter filtro) {
		Session session = this.manager.unwrap(Session.class);

		Criteria criteria = session.createCriteria(Funcionario.class).createAlias("cargo", "c");

		if (StringUtils.isNotBlank(filtro.getNomeFuncionario())) {
			criteria.add(Restrictions.ilike("nome", filtro.getNomeFuncionario(), MatchMode.ANYWHERE));
		}

		if (StringUtils.isNotBlank(filtro.getNomeCargo())) {
			criteria.add(Restrictions.ilike("c.nome", filtro.getNomeCargo(), MatchMode.ANYWHERE));
		}
		if (filtro.getDataCriacaoDe() != null) {
			criteria.add(Restrictions.ge("dataAdmissao", filtro.getDataCriacaoDe()));
		}

		if (filtro.getDataCriacaoAte() != null) {
			criteria.add(Restrictions.le("dataAdmissao", filtro.getDataCriacaoAte()));
		}

		return criteria.addOrder(Order.asc("codigo")).list();
	}

	@Transactional
	public void remover(Funcionario funcionario) {
		try {
			funcionario = porId(funcionario.getCodigo());
			manager.remove(funcionario);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Funcionário não pode ser excluído.");
		}
	}

	public Funcionario porId(Long id) {
		return manager.find(Funcionario.class, id);
	}

	public List<Funcionario> porNome(String nome) {
		return this.manager.createQuery("from Funcionário where upper(nome) like :nome", Funcionario.class)
				.setParameter("nome", nome.toUpperCase() + "%").getResultList();
	}

	public Funcionario buscarPeloCodigo(Long codigo) {
		return manager.find(Funcionario.class, codigo);
	}

	@SuppressWarnings("unchecked")
	public List<Funcionario> buscarTodos() {
		return manager.createQuery("from Funcionario order by nome asc").getResultList();
	}

}
