package com.mez.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.mez.dao.ContasPagarDao;
import com.mez.model.ContasPagar;
import com.mez.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = ContasPagar.class)
public class ContasPagarConverter implements Converter {

private ContasPagarDao contasPagarDao;
	
	public ContasPagarConverter(){
		this.contasPagarDao = CDIServiceLocator.getBean(ContasPagarDao.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		ContasPagar retorno = null;
		
		if(value != null) {
			retorno = this.contasPagarDao.buscarPeloCodigo(new Long(value));
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Long codigo = ((ContasPagar) value).getId();
			String retorno = (codigo == null ? null : codigo.toString());
			
			return retorno;
		}
		
		return "";
	}

}
