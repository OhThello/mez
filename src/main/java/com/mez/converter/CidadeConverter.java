package com.mez.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.mez.dao.CidadeDao;
import com.mez.model.Cidade;
import com.mez.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Cidade.class)
public class CidadeConverter implements Converter {

private CidadeDao cidadeDao;
	
	public CidadeConverter(){
		this.cidadeDao = CDIServiceLocator.getBean(CidadeDao.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Cidade retorno = null;
		
		if(value != null) {
			retorno = this.cidadeDao.buscarPeloCodigo(new Long(value));
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Long codigo = ((Cidade) value).getCodigo();
			return codigo == null ? null : codigo.toString();
		}
		
		return "";
	}

}
