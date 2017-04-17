package com.mez.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.mez.dao.PessoaJuridicaDao;
import com.mez.model.PessoaJuridica;
import com.mez.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = PessoaJuridica.class)
public class PessoaJuridicaConverter implements Converter {

private PessoaJuridicaDao pessoaJuridicaDao;
	
	public PessoaJuridicaConverter(){
		this.pessoaJuridicaDao = CDIServiceLocator.getBean(PessoaJuridicaDao.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		PessoaJuridica retorno = null;
		
		if(value != null) {
			retorno = this.pessoaJuridicaDao.buscarPeloCodigo(new Long(value));
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Long codigo = ((PessoaJuridica) value).getCodigo();
			return codigo == null ? null : codigo.toString();
		}
		
		return "";
	}

}
