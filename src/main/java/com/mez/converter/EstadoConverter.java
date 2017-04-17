package com.mez.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.mez.dao.EstadoDao;
import com.mez.model.Estado;
import com.mez.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Estado.class)
public class EstadoConverter implements Converter {

private EstadoDao estadoDao;
	
	public EstadoConverter(){
		this.estadoDao = CDIServiceLocator.getBean(EstadoDao.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Estado retorno = null;
		
		if(value != null) {
			retorno = this.estadoDao.buscarPeloCodigo(new Long(value));
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Long codigo = ((Estado) value).getCodigo();
			return codigo == null ? null : codigo.toString();
		}
		
		return "";
	}
}
