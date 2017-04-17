package com.mez.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.mez.dao.CargoDao;
import com.mez.model.Cargo;
import com.mez.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Cargo.class)
public class CargoConverter implements Converter {

private CargoDao cargoDao;
	
	public CargoConverter(){
		this.cargoDao = CDIServiceLocator.getBean(CargoDao.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Cargo retorno = null;
		
		if(value != null) {
			retorno = this.cargoDao.buscarPeloCodigo(new Long(value));
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Long codigo = ((Cargo) value).getCodigo();
			return codigo == null ? null : codigo.toString();
		}
		
		return "";
	}

}
