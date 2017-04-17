package com.mez.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import com.mez.model.ContasReceber;
import com.mez.service.NegocioException;
import com.mez.util.jpa.Transactional;

public class ContasReceberDao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public ContasReceber guardar(ContasReceber contasReceber) {
		return manager.merge(contasReceber);
	}

	@Transactional
	public void remover(ContasReceber contasReceber) {
		try {
			contasReceber = porId(contasReceber.getId());
			manager.remove(contasReceber);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Contas a Receber não pode ser excluído.");
		}
	}

	public ContasReceber porId(Long id) {
		return manager.find(ContasReceber.class, id);
	}

	public List<ContasReceber> porNome(String nome) {
		return this.manager.createQuery("from ContasReceber where upper(nome) like :nome", ContasReceber.class)
				.setParameter("nome", nome.toUpperCase() + "%").getResultList();
	}

	public ContasReceber buscarPeloCodigo(Long codigo) {
		return manager.find(ContasReceber.class, codigo);
	}

	@SuppressWarnings("unchecked")
	public List<ContasReceber> buscarTodos() {
		return manager.createQuery("from ContasReceber order by dataVencimento desc").getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<ContasReceber> listarContas(String where) {
		return manager.createQuery("from ContasReceber  where" + where + " order by id desc").getResultList();
	}

}
