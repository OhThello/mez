package com.mez.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.mez.dao.GrupoProdutoDao;
import com.mez.model.GrupoProduto;
import com.mez.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = GrupoProduto.class)
public class GrupoProdutoConverter implements Converter {

private GrupoProdutoDao grupoProdutoDao;
	
	public GrupoProdutoConverter(){
		this.grupoProdutoDao = CDIServiceLocator.getBean(GrupoProdutoDao.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		GrupoProduto retorno = null;
		
		if(value != null) {
			retorno = this.grupoProdutoDao.buscarPeloCodigo(new Long(value));
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Long codigo = ((GrupoProduto) value).getCodigo();
			return codigo == null ? null : codigo.toString();
		}
		
		return "";
	}

}
