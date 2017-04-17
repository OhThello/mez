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

import com.mez.model.AjustaEstoque;
import com.mez.repository.filter.AjusteEstoqueFilter;
import com.mez.service.NegocioException;
import com.mez.util.jpa.Transactional;

public class AjustaEstoqueDao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	@SuppressWarnings("unchecked")
	public List<AjustaEstoque> filtrados(AjusteEstoqueFilter filtro) {
		Session session = this.manager.unwrap(Session.class);

		Criteria criteria = session.createCriteria(AjustaEstoque.class).createAlias("produto", "p");;

		if (StringUtils.isNotBlank(filtro.getNomeProduto())) {
			criteria.add(Restrictions.ilike("p.nome", filtro.getNomeProduto(), MatchMode.ANYWHERE));
		}

		return criteria.addOrder(Order.asc("codigo")).list();
	}

	public AjustaEstoque guardar(AjustaEstoque ajustaEstoque) {
		return manager.merge(ajustaEstoque);
	}

	@Transactional
	public void remover(AjustaEstoque ajustaEstoque) {
		try {
			ajustaEstoque = porId(ajustaEstoque.getCodigo());
			manager.remove(ajustaEstoque);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Ajuste de Estoque não pode ser excluído.");
		}
	}

	public AjustaEstoque porId(Long id) {
		return manager.find(AjustaEstoque.class, id);
	}

	public List<AjustaEstoque> porNome(String nome) {
		return this.manager.createQuery("from AjustaEstoque where upper(nome) like :nome", AjustaEstoque.class)
				.setParameter("nome", nome.toUpperCase() + "%").getResultList();
	}

	public AjustaEstoque buscarPeloCodigo(Long codigo) {
		return manager.find(AjustaEstoque.class, codigo);
	}

	@SuppressWarnings("unchecked")
	public List<AjustaEstoque> buscarTodos() {
		return manager.createQuery("from AjustaEstoque order by id desc").getResultList();
	}

}
