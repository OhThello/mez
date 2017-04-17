package com.mez.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.mez.dao.ContaCorrenteDao;
import com.mez.model.ContaCorrente;
import com.mez.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = ContaCorrente.class)
public class ContaCorrenteConverter implements Converter {

private ContaCorrenteDao contaCorrenteDao;
	
	public ContaCorrenteConverter(){
		this.contaCorrenteDao = CDIServiceLocator.getBean(ContaCorrenteDao.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		ContaCorrente retorno = null;
		
		if(value != null) {
			retorno = this.contaCorrenteDao.buscarPeloCodigo(new Long(value));
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Long codigo = ((ContaCorrente) value).getCodigo();
			return codigo == null ? null : codigo.toString();
		}
		
		return "";
	}

}
