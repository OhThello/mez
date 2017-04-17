package com.mez.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.mez.dao.GrupoDao;
import com.mez.model.Grupo;
import com.mez.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Grupo.class)
public class GrupoConverter implements Converter {

private GrupoDao grupoDao;
	
	public GrupoConverter(){
		this.grupoDao = CDIServiceLocator.getBean(GrupoDao.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Grupo retorno = null;
		
		if(value != null) {
			retorno = this.grupoDao.buscarPeloCodigo(new Long(value));
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Long codigo = ((Grupo) value).getId();
			return codigo == null ? null : codigo.toString();
		}
		
		return "";
	}

}
