package br.com.foxinline.controlebens.dao;

import br.com.foxinline.controlebens.model.Bem;

public class BemDAO extends GenericDAO<Bem> {
    
	private static final long serialVersionUID = 1L;

	public BemDAO() {
        super(Bem.class);
    }
}
