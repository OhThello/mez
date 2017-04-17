package com.mez.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.mez.dao.ProdutoDao;
import com.mez.model.Produto;
import com.mez.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Produto.class)
public class ProdutoConverter implements Converter {

private ProdutoDao produtoDao;
	
	public ProdutoConverter(){
		this.produtoDao = CDIServiceLocator.getBean(ProdutoDao.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Produto retorno = null;
		
		if(value != null) {
			retorno = this.produtoDao.buscarPeloCodigo(new Long(value));
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Long codigo = ((Produto) value).getId();
			return codigo == null ? null : codigo.toString();
		}
		
		return "";
	}
}
