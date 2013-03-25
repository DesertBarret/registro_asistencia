/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uti.jpacontroller;

import com.uti.entities.RegistroPc;
import java.io.Serializable;
import java.util.List;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author desertbarret
 */
public class RegistroPcJpaController implements Serializable {

    String UNIDAD_DE_PERSISTENCIA = "SRPApacheSqlitePU";
    private EntityManagerFactory emf = null;

    public RegistroPcJpaController() throws NamingException {
        emf = Persistence.createEntityManagerFactory(UNIDAD_DE_PERSISTENCIA, System.getProperties());
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<RegistroPc> findResgistroPcIP(String ip) {
        EntityManager em = getEntityManager();
        try {
            Query q = em
                    .createQuery("SELECT r FROM RegistroPc r WHERE r.ip = ?1");
            q.setParameter(1, ip);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public int getResgistroPcIdMAX() {
        EntityManager em = getEntityManager();
        try {
            Query q = em
                    .createQuery("SELECT MAX(d.id) FROM RegistroPc d");
            if ((Integer) q.getSingleResult() == null) {
                return 0;
            } else {
                return (Integer) q.getSingleResult();
            }
        } finally {
            em.close();
        }
    }

    public void create(RegistroPc registroPc) {
        registroPc.setId(getResgistroPcIdMAX() + 1);
        EntityManager em = null;
        em = getEntityManager();
        em.getTransaction().begin();
        em.persist(registroPc);
        em.flush();
        em.getTransaction().commit();
        em.close();
    }

    public void edit(RegistroPc registroPc) {
        EntityManager em = null;
        em = getEntityManager();
        em.getTransaction().begin();
        em.merge(registroPc);
        em.flush();
        em.getTransaction().commit();
        em.close();
    }

    public void destroy(Integer id) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RegistroPc registroPc = null;
            try {
                registroPc = em.getReference(RegistroPc.class, id);
                registroPc.getId();
            } catch (EntityNotFoundException enfe) {
            }
            em.remove(registroPc);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
