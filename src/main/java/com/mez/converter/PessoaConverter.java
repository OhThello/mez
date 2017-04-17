package com.mez.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.mez.dao.PessoaDao;
import com.mez.model.Pessoa;
import com.mez.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Pessoa.class)
public class PessoaConverter implements Converter {

private PessoaDao pessoaDao;
	
	public PessoaConverter(){
		this.pessoaDao = CDIServiceLocator.getBean(PessoaDao.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Pessoa retorno = null;
		
		if(value != null) {
			retorno = this.pessoaDao.buscarPeloCodigo(new Long(value));
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Long codigo = ((Pessoa) value).getCodigo();
			return codigo == null ? null : codigo.toString();
		}
		
		return "";
	}

}
