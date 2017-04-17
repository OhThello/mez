package com.mez.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.mez.dao.PessoaFisicaDao;
import com.mez.model.PessoaFisica;
import com.mez.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = PessoaFisica.class)
public class PessoaFisicaConverter implements Converter {

private PessoaFisicaDao pessoaFisicaDao;
	
	public PessoaFisicaConverter(){
		this.pessoaFisicaDao = CDIServiceLocator.getBean(PessoaFisicaDao.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		PessoaFisica retorno = null;
		
		if(value != null) {
			retorno = this.pessoaFisicaDao.buscarPeloCodigo(new Long(value));
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Long codigo = ((PessoaFisica) value).getCodigo();
			return codigo == null ? null : codigo.toString();
		}
		
		return "";
	}

}
