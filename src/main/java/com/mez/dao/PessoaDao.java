package com.mez.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import com.mez.model.Pessoa;
import com.mez.service.NegocioException;
import com.mez.util.jpa.Transactional;

public class PessoaDao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Pessoa guardar(Pessoa pessoa) {
		return manager.merge(pessoa);
	}

	@Transactional
	public void remover(Pessoa pessoa) {
		try {
			pessoa = porId(pessoa.getCodigo());
			manager.remove(pessoa);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Cliente não pode ser excluído.");
		}
	}

	public Pessoa porId(Long id) {
		return manager.find(Pessoa.class, id);
	}

	public List<Pessoa> porNome(String nome) {
		return this.manager.createQuery("from Pessoa where upper(nome) like :nome", Pessoa.class)
				.setParameter("nome", nome.toUpperCase() + "%").getResultList();
	}

	public Pessoa buscarPeloCodigo(Long codigo) {
		return manager.find(Pessoa.class, codigo);
	}

	@SuppressWarnings("unchecked")
	public List<Pessoa> buscarTodos() {
		return manager.createQuery("from Pessoa order by nome asc").getResultList();
	}

}
