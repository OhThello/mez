package com.mez.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.mez.dao.ContasReceberDao;
import com.mez.model.ContasReceber;
import com.mez.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = ContasReceber.class)
public class ContasReceberConverter implements Converter {

private ContasReceberDao contasReceberDao;
	
	public ContasReceberConverter(){
		this.contasReceberDao = CDIServiceLocator.getBean(ContasReceberDao.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		ContasReceber retorno = null;
		
		if(value != null) {
			retorno = this.contasReceberDao.buscarPeloCodigo(new Long(value));
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Long codigo = ((ContasReceber) value).getId();
			String retorno = (codigo == null ? null : codigo.toString());
			
			return retorno;
		}
		
		return "";
	}

}
