package com.mez.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.hibernate.Session;

import com.mez.model.FormaPagamento;
import com.mez.report.ExecutorRelatorio;
import com.mez.util.jsf.FacesUtil;

@Named
@RequestScoped
public class RelatorioFormaPagamentoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date dataInicio;
	private Date dataFim;
	private String forma;

	@Inject
	private FacesContext facesContext;

	@Inject
	private HttpServletResponse response;

	@Inject
	private EntityManager manager;

	public void emitir() {
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("data_inicio", this.dataInicio);
		parametros.put("data_fim", this.dataFim);
		parametros.put("forma", this.forma);

		ExecutorRelatorio executor = new ExecutorRelatorio("/relatorios/forma_pagamento.jasper", this.response,
				parametros, "Forma_Pagamento.pdf");

		Session session = manager.unwrap(Session.class);
		session.doWork(executor);

		if (executor.isRelatorioGerado()) {
			facesContext.responseComplete();
		} else {
			FacesUtil.addErrorMessage("A execução do relatório não retornou dados.");
		}
	}

	@NotNull
	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;

	}

	@NotNull
	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	
	@NotNull
	public String getForma() {
		return forma;
	}

	public void setForma(String forma) {
		this.forma = forma;
	}

	public FormaPagamento[] getFormasPagamento() {
		return FormaPagamento.values();
	}

}