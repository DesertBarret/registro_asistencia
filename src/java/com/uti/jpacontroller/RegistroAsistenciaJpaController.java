/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uti.jpacontroller;

import com.uti.entities.RegistroAsistencia;
import java.io.Serializable;
import java.util.Date;
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
public class RegistroAsistenciaJpaController implements Serializable {

    String UNIDAD_DE_PERSISTENCIA = "SRPApacheSqlitePU";
    private EntityManagerFactory emf = null;

    public RegistroAsistenciaJpaController() throws NamingException {
        emf = Persistence.createEntityManagerFactory(UNIDAD_DE_PERSISTENCIA, System.getProperties());
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RegistroAsistencia registroAsistencia) {
        registroAsistencia.setId(getRegistroAsistenciaIdMAX() + 1);
        EntityManager em = null;
        em = getEntityManager();
        em.getTransaction().begin();
        em.persist(registroAsistencia);
        em.flush();
        em.getTransaction().commit();
        em.close();
    }

    public void edit(RegistroAsistencia registroAsistencia) {
        EntityManager em = null;
        em = getEntityManager();
        em.getTransaction().begin();
        em.merge(registroAsistencia);
        em.flush();
        em.getTransaction().commit();
        em.close();
    }

    public List<RegistroAsistencia> findRegistroAsistenciaEntities() {
        return findRegistroAsistenciaEntities(true, -1, -1);
    }

    public List<RegistroAsistencia> findRegistroAsistenciaEntities(int maxResults, int firstResult) {
        return findRegistroAsistenciaEntities(false, maxResults, firstResult);
    }

    private List<RegistroAsistencia> findRegistroAsistenciaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RegistroAsistencia.class));
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

    public RegistroAsistencia findRegistroAsistencia(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RegistroAsistencia.class, id);
        } finally {
            em.close();
        }
    }

    public List<RegistroAsistencia> findRegistroAsistenciaPasante(String cedula, Date fecha) {
        EntityManager em = getEntityManager();
        try {
            Query q = em
                    .createQuery("SELECT p FROM RegistroAsistencia p WHERE p.pasantes.cedula = ?1 AND p.fecha=?2");
            q.setParameter(1, cedula);
            q.setParameter(2, fecha);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public int getRegistroAsistenciaIdMAX() {
        EntityManager em = getEntityManager();
        try {
            Query q = em
                    .createQuery("SELECT MAX(d.id) FROM RegistroAsistencia d");

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
