/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uti.jpacontroller;

import com.uti.entities.Pasantes;
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
public class PasantesJpaController implements Serializable {

    String UNIDAD_DE_PERSISTENCIA = "SRPApacheSqlitePU";
    private EntityManagerFactory emf = null;

    public PasantesJpaController() throws NamingException {
        emf = Persistence.createEntityManagerFactory(UNIDAD_DE_PERSISTENCIA, System.getProperties());
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pasantes pasantes) {
        pasantes.setId(getPasantesIdMAX() + 1);
        EntityManager em = null;
        em = getEntityManager();
        em.getTransaction().begin();
        em.persist(pasantes);
        em.flush();
        em.getTransaction().commit();
        em.close();

    }

    public void edit(Pasantes pasantes) {
        EntityManager em = null;
        em = getEntityManager();
        em.getTransaction().begin();
        em.merge(pasantes);
        em.flush();
        em.getTransaction().commit();
        em.close();
    }

    public List<Pasantes> findPasantesEntities() {
        return findPasantesEntities(true, -1, -1);
    }

    public List<Pasantes> findPasantesEntities(int maxResults, int firstResult) {
        return findPasantesEntities(false, maxResults, firstResult);
    }

    private List<Pasantes> findPasantesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pasantes.class));
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

    public Pasantes findPasantes(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pasantes.class, id);
        } finally {
            em.close();
        }
    }

    public List<Pasantes> findPasantesRegistro(String cedula) {
        EntityManager em = getEntityManager();
        try {
            Query q = em
                    .createQuery("SELECT p FROM Pasantes p WHERE p.cedula = ?1");
            q.setParameter(1, cedula);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public List<RegistroAsistencia> findPasantesDia(Date dia) {
        EntityManager em = getEntityManager();
        try {
            Query q = em
                    .createQuery("SELECT r FROM RegistroAsistencia r WHERE r.fecha= ?1");
            q.setParameter(1, dia);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Pasantes> findPasantesActivo(String activo) {
        EntityManager em = getEntityManager();
        try {
            Query q = em
                    .createQuery("SELECT p FROM Pasantes p WHERE p.activo = ?1");
            q.setParameter(1, activo);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Pasantes> findPasantesConcluido(String concluido, String terminado) {
        EntityManager em = getEntityManager();
        try {
            Query q = em
                    .createQuery("SELECT p FROM Pasantes p WHERE p.activo = ?1 OR p.activo = ?2");
            q.setParameter(1, concluido);
            q.setParameter(2, terminado);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public int getPasantesIdMAX() {
        EntityManager em = getEntityManager();
        try {
            Query q = em
                    .createQuery("SELECT MAX(d.id) FROM Pasantes d");
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
