package com.mez.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.mez.dao.FuncionarioDao;
import com.mez.model.Funcionario;
import com.mez.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Funcionario.class)
public class FuncionarioConverter implements Converter {

private FuncionarioDao funcionarioDao;
	
	public FuncionarioConverter(){
		this.funcionarioDao = CDIServiceLocator.getBean(FuncionarioDao.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Funcionario retorno = null;
		
		if(value != null) {
			retorno = this.funcionarioDao.buscarPeloCodigo(new Long(value));
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Long codigo = ((Funcionario) value).getCodigo();
			return codigo == null ? null : codigo.toString();
		}
		
		return "";
	}

}
