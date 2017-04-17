package com.mez.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;

import com.mez.model.Compra;
import com.mez.model.Usuario;
import com.mez.model.vo.DataValor;
import com.mez.repository.filter.CompraFilter;

public class CompraDao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	@SuppressWarnings({ "unchecked" })
	public Map<Date, BigDecimal> valoresTotaisPorData(Integer numeroDeDias, Usuario criadoPor) {
		Session session = manager.unwrap(Session.class);
		
		numeroDeDias -= 1;
		
		Calendar dataInicial = Calendar.getInstance();
		dataInicial = DateUtils.truncate(dataInicial, Calendar.DAY_OF_MONTH);
		dataInicial.add(Calendar.DAY_OF_MONTH, numeroDeDias * -1);
		
		Map<Date, BigDecimal> resultado = criarMapaVazio(numeroDeDias, dataInicial);
		
		Criteria criteria = session.createCriteria(Compra.class);
		
		// select date(data_criacao) as data, sum(valor_total) as valor 
		// from pedido where data_criacao >= :dataInicial and vendedor_id = :criadoPor 
		// group by date(data_criacao)
		
		criteria.setProjection(Projections.projectionList()
				.add(Projections.sqlGroupProjection("date(data_criacao) as data", 
						"date(data_criacao)", new String[] { "data" }, 
						new Type[] { StandardBasicTypes.DATE } ))
				.add(Projections.sum("valorTotal").as("valor"))
			)
			.add(Restrictions.ge("dataCriacao", dataInicial.getTime()));
		
		if (criadoPor != null) {
			criteria.add(Restrictions.eq("vendedor", criadoPor));
		}
		
		List<DataValor> valoresPorData = criteria
				.setResultTransformer(Transformers.aliasToBean(DataValor.class)).list();
		
		for (DataValor dataValor : valoresPorData) {
			resultado.put(dataValor.getData(), dataValor.getValor());
		}
		
		return resultado;
	}

	private Map<Date, BigDecimal> criarMapaVazio(Integer numeroDeDias,
			Calendar dataInicial) {
		dataInicial = (Calendar) dataInicial.clone();
		Map<Date, BigDecimal> mapaInicial = new TreeMap<>();

		for (int i = 0; i <= numeroDeDias; i++) {
			mapaInicial.put(dataInicial.getTime(), BigDecimal.ZERO);
			dataInicial.add(Calendar.DAY_OF_MONTH, 1);
		}
		
		return mapaInicial;
	}
	
	@SuppressWarnings("unchecked")
	public List<Compra> filtrados(CompraFilter filtro) {
		Session session = this.manager.unwrap(Session.class);
		
		Criteria criteria = session.createCriteria(Compra.class)
				
				// fazemos uma associação (join) com vendedor e nomeamos como "v"
				.createAlias("vendedor", "v");
		
		if (filtro.getNumeroDe() != null) {
			// id deve ser maior ou igual (ge = greater or equals) a filtro.numeroDe
			criteria.add(Restrictions.ge("id", filtro.getNumeroDe()));
		}

		if (filtro.getNumeroAte() != null) {
			// id deve ser menor ou igual (le = lower or equal) a filtro.numeroDe
			criteria.add(Restrictions.le("id", filtro.getNumeroAte()));
		}

		if (filtro.getDataCriacaoDe() != null) {
			criteria.add(Restrictions.ge("dataCriacao", filtro.getDataCriacaoDe()));
		}
		
		if (filtro.getDataCriacaoAte() != null) {
			criteria.add(Restrictions.le("dataCriacao", filtro.getDataCriacaoAte()));
		}
	
		
		if (StringUtils.isNotBlank(filtro.getNomeVendedor())) {
			// acessamos o nome do vendedor associado ao pedido pelo alias "v", criado anteriormente
			criteria.add(Restrictions.ilike("v.nome", filtro.getNomeVendedor(), MatchMode.ANYWHERE));
		}
		
		if (filtro.getStatuses() != null && filtro.getStatuses().length > 0) {
			// adicionamos uma restrição "in", passando um array de constantes da enum StatusPedido
			criteria.add(Restrictions.in("status", filtro.getStatuses()));
		}
		
		return criteria.addOrder(Order.asc("id")).list();
	}

	public Compra guardar(Compra compra) {
		return this.manager.merge(compra);
	}

	public Compra porId(Long id) {
		return this.manager.find(Compra.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<Compra> buscarTodos() {
		return manager.createQuery("from Compra order by id desc").getResultList();
	}
	
}