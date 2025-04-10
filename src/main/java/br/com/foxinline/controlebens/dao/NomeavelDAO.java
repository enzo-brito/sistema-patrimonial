package br.com.foxinline.controlebens.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.foxinline.controlebens.model.Nomeavel;

public class NomeavelDAO<T extends Nomeavel> extends GenericDAO<T> {

   
	private static final long serialVersionUID = 1L;

	public NomeavelDAO(Class<T> classe) {
        super(classe);
    }

    public List<T> filtrarPorNome(String nome) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "FROM " + classe.getSimpleName() + " e WHERE LOWER(e.nome) LIKE :nome ORDER BY e.nome";
            return em.createQuery(jpql, classe)
                     .setParameter("nome", "%" + nome.toLowerCase() + "%")
                     .getResultList();
        } finally {
            em.close();
        }
    }
}
