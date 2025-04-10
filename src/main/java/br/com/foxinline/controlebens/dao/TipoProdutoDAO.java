package br.com.foxinline.controlebens.dao;

import br.com.foxinline.controlebens.model.TipoProduto;

public class TipoProdutoDAO extends GenericDAO<TipoProduto> {
    
	private static final long serialVersionUID = 1L;

	public TipoProdutoDAO() {
        super(TipoProduto.class);
    }
}
