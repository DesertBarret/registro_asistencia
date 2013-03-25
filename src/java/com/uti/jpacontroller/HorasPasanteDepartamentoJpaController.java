/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uti.jpacontroller;

import com.uti.entities.Departamento;
import com.uti.entities.HorasPasanteDepartamento;
import com.uti.entities.Pasantes;
import java.io.Serializable;
import java.util.List;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

/**
 *
 * @author desertbarret
 */
public class HorasPasanteDepartamentoJpaController implements Serializable {

    String UNIDAD_DE_PERSISTENCIA = "SRPApacheSqlitePU";
    private EntityManagerFactory emf = null;

    public HorasPasanteDepartamentoJpaController() throws NamingException {
        emf = Persistence.createEntityManagerFactory(UNIDAD_DE_PERSISTENCIA, System.getProperties());
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(HorasPasanteDepartamento horasPasanteDepartamento) {
        horasPasanteDepartamento.setId(getHorasPasanteDepartamentoIdMAX() + 1);
        EntityManager em = null;
        em = getEntityManager();
        em.getTransaction().begin();
        em.persist(horasPasanteDepartamento);
        em.flush();
        em.getTransaction().commit();
        em.close();
    }

    public void edit(HorasPasanteDepartamento horasPasanteDepartamento) {
        EntityManager em = null;
        em = getEntityManager();
        em.getTransaction().begin();
        em.merge(horasPasanteDepartamento);
        em.flush();
        em.getTransaction().commit();
        em.close();
    }

    public List<HorasPasanteDepartamento> findHorasPasanteDepartamentoEntities() {
        return findHorasPasanteDepartamentoEntities(true, -1, -1);
    }

    public List<HorasPasanteDepartamento> findHorasPasanteDepartamentoEntities(int maxResults, int firstResult) {
        return findHorasPasanteDepartamentoEntities(false, maxResults, firstResult);
    }

    private List<HorasPasanteDepartamento> findHorasPasanteDepartamentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(HorasPasanteDepartamento.class));
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

    public HorasPasanteDepartamento findHorasPasanteDepartamento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(HorasPasanteDepartamento.class, id);
        } finally {
            em.close();
        }
    }

    public List<HorasPasanteDepartamento> findHorasPasanteDepartamentoExits(Pasantes pasantes, Departamento departamento) {
        EntityManager em = getEntityManager();
        try {
            Query q = em
                    .createQuery("SELECT h FROM HorasPasanteDepartamento h WHERE h.idPasante = ?1 AND h.idDepartamento =?2");
            q.setParameter(1, pasantes);
            q.setParameter(2, departamento);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public int getHorasPasanteDepartamentoIdMAX() {
        EntityManager em = getEntityManager();
        try {
            Query q = em
                    .createQuery("SELECT MAX(d.id) FROM HorasPasanteDepartamento d");
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
