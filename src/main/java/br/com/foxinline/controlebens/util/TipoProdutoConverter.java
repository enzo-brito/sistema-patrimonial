package br.com.foxinline.controlebens.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.foxinline.controlebens.dao.TipoProdutoDAO;
import br.com.foxinline.controlebens.model.TipoProduto;

@FacesConverter(value = "tipoProdutoConverter", forClass = TipoProduto.class)
public class TipoProdutoConverter implements Converter<TipoProduto> {

    @Override
    public TipoProduto getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        TipoProdutoDAO dao = new TipoProdutoDAO();
        return dao.buscarPorId(Long.parseLong(value));
    }   

    @Override
    public String getAsString(FacesContext context, UIComponent component, TipoProduto tipo) {
        return (tipo == null || tipo.getId() == null) ? "" : tipo.getId().toString();
    }
}
