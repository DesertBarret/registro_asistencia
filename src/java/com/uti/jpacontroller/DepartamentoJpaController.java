/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uti.jpacontroller;

import com.uti.entities.Departamento;
import java.io.Serializable;
import java.util.List;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author desertbarret
 */
public class DepartamentoJpaController implements Serializable {

    String UNIDAD_DE_PERSISTENCIA = "SRPApacheSqlitePU";
    private EntityManagerFactory emf = null;

    public DepartamentoJpaController() throws NamingException {
        emf = Persistence.createEntityManagerFactory(UNIDAD_DE_PERSISTENCIA, System.getProperties());
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Departamento departamento) {
        departamento.setId(getDepatamentoIdMAX() + 1);
        EntityManager em = null;
        em = getEntityManager();
        em.getTransaction().begin();
        em.persist(departamento);
        em.flush();
        em.getTransaction().commit();
        em.close();
    }

    public void edit(Departamento departamento) {
        EntityManager em = null;
        em = getEntityManager();
        em.getTransaction().begin();
        em.merge(departamento);
        em.flush();
        em.getTransaction().commit();
        em.close();
    }

    public List<Departamento> findDepartamentoEntities() {
        return findDepartamentoEntities(true, -1, -1);
    }

    public List<Departamento> findDepartamentoEntities(int maxResults, int firstResult) {
        return findDepartamentoEntities(false, maxResults, firstResult);
    }

    private List<Departamento> findDepartamentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Departamento.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Departamento findDepartamento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Departamento.class, id);
        } finally {
            em.close();
        }
    }

    public int getDepartamentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Departamento> rt = cq.from(Departamento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public int getDepatamentoIdMAX() {
        EntityManager em = getEntityManager();
        try {
            Query q = em
                    .createQuery("SELECT MAX(d.id) FROM Departamento d");
            if ((Integer) q.getSingleResult() == null) {
                return 0;
            } else {
                return (Integer) q.getSingleResult();
            }
        } finally {
            em.close();
        }
    }
}