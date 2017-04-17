package com.mez.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.mez.dao.CompraDao;
import com.mez.model.Compra;
import com.mez.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Compra.class)
public class CompraConverter implements Converter {

	//@Inject
	private CompraDao compraDao;
	
	public CompraConverter() {
		compraDao = CDIServiceLocator.getBean(CompraDao.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Compra retorno = null;
		
		if (value != null) {
			Long id = new Long(value);
			retorno = compraDao.porId(id);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Compra compra = (Compra) value;
			return compra.getId() == null ? null : compra.getId().toString();
		}
		
		return "";
	}

}
