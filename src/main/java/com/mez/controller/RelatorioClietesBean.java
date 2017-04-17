package com.mez.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.mez.report.ExecutorRelatorio;
import com.mez.util.jsf.FacesUtil;

@Named
@RequestScoped
public class RelatorioClietesBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String parametro;

	@Inject
	private FacesContext facesContext;

	@Inject
	private HttpServletResponse response;

	@Inject
	private EntityManager manager;

	public void emitir() {
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("nome", "%"+this.parametro+"%");

		ExecutorRelatorio executor = new ExecutorRelatorio("/relatorios/clientes.jasper", this.response, parametros,
				"Clientes.pdf");

		Session session = manager.unwrap(Session.class);
		session.doWork(executor);

		if (executor.isRelatorioGerado()) {
			facesContext.responseComplete();
		} else {
			FacesUtil.addErrorMessage("A execução do relatório não retornou dados.");
		}
		
		
	}

	public String getParametro() {
		return parametro;
	}

	public void setParametro(String parametro) {
		this.parametro = parametro;
	}

	
	

}