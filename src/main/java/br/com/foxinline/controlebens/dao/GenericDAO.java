package br.com.foxinline.controlebens.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.foxinline.controlebens.model.TipoProduto;
import br.com.foxinline.controlebens.util.JPAUtil;

public class GenericDAO<T> implements Serializable {

    
	private static final long serialVersionUID = 1L;

	private static final String PERSISTENCE_UNIT = "controleBensPU";

    protected Class<T> classe;

    public GenericDAO(Class<T> classe) {
        this.classe = classe;
    }

    protected EntityManager getEntityManager() {
        return JPAUtil.getEntityManager();
    }

    public void salvar(T entidade) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(entidade);
            tx.commit();
        } finally {
            em.close();
        }
    }

    public void atualizar(T entidade) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(entidade);
            tx.commit();
        } finally {
            em.close();
        }
    }

    public void remover(Object id) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            T entidade = em.find(classe, id);
            em.remove(entidade);
            tx.commit();
        } finally {
            em.close();
        }
    }

    public T buscarPorId(Object id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(classe, id);
        } finally {
            em.close();
        }
    }
    
    public T buscarPorCampoUnico(String nomeCampo, Object valor) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "FROM " + classe.getSimpleName() + " WHERE " + nomeCampo + " = :valor";
            List<T> resultados = em.createQuery(jpql, classe)
                                   .setParameter("valor", valor)
                                   .getResultList();
            return resultados.isEmpty() ? null : resultados.get(0);
        } finally {
            em.close();
        }
    }
    
    
    public List<T> listarTodos() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("FROM " + classe.getSimpleName(), classe).getResultList();
        } finally {
            em.close();
        }
    }
}
