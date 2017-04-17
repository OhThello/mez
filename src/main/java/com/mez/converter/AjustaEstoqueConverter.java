package com.mez.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.mez.dao.AjustaEstoqueDao;
import com.mez.model.AjustaEstoque;
import com.mez.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = AjustaEstoque.class)
public class AjustaEstoqueConverter implements Converter {

private AjustaEstoqueDao ajustaEstoqueDao;
	
	public AjustaEstoqueConverter(){
		this.ajustaEstoqueDao = CDIServiceLocator.getBean(AjustaEstoqueDao.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		AjustaEstoque retorno = null;
		
		if(value != null) {
			retorno = this.ajustaEstoqueDao.buscarPeloCodigo(new Long(value));
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Long codigo = ((AjustaEstoque) value).getCodigo();
			return codigo == null ? null : codigo.toString();
		}
		
		return "";
	}

}
