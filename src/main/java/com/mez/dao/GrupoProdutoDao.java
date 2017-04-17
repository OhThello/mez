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

import com.mez.model.GrupoProduto;
import com.mez.repository.filter.GrupoProdutoFilter;
import com.mez.service.NegocioException;
import com.mez.util.jpa.Transactional;

public class GrupoProdutoDao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public GrupoProduto guardar(GrupoProduto grupoProduto) {
		return manager.merge(grupoProduto);
	}

	@SuppressWarnings("unchecked")
	public List<GrupoProduto> filtrados(GrupoProdutoFilter filtro) {
		Session session = this.manager.unwrap(Session.class);

		Criteria criteria = session.createCriteria(GrupoProduto.class);

		if (StringUtils.isNotBlank(filtro.getNomeGrupo())) {
			criteria.add(Restrictions.ilike("nome", filtro.getNomeGrupo(), MatchMode.ANYWHERE));
		}

		return criteria.addOrder(Order.asc("codigo")).list();
	}

	@Transactional
	public void remover(GrupoProduto grupoProduto) {
		try {
			grupoProduto = porId(grupoProduto.getCodigo());
			manager.remove(grupoProduto);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Grupo Produto não pode ser excluído.");
		}
	}

	public GrupoProduto porId(Long id) {
		return manager.find(GrupoProduto.class, id);
	}

	public List<GrupoProduto> porNome(String nome) {
		return this.manager.createQuery("from GrupoProduto where upper(nome) like :nome", GrupoProduto.class)
				.setParameter("nome", nome.toUpperCase() + "%").getResultList();
	}

	public GrupoProduto buscarPeloCodigo(Long codigo) {
		return manager.find(GrupoProduto.class, codigo);
	}

	@SuppressWarnings("unchecked")
	public List<GrupoProduto> buscarTodos() {
		return manager.createQuery("from GrupoProduto order by nome desc").getResultList();
	}

}
