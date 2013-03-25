/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uti.jpacontroller;

import com.uti.entities.Usuario;
import java.io.Serializable;
import java.util.List;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author desertbarret
 */
public class UsuarioJpaController implements Serializable {

    String UNIDAD_DE_PERSISTENCIA = "SRPApacheSqlitePU";
    private EntityManagerFactory emf = null;

    public UsuarioJpaController() throws NamingException {
        emf = Persistence.createEntityManagerFactory(UNIDAD_DE_PERSISTENCIA, System.getProperties());
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<Usuario> findUsuario(String usuario, String contrasenia) {
        EntityManager em = getEntityManager();
        try {
            Query q = em
                    .createQuery("SELECT u FROM Usuario u WHERE u.usuario = ?1 AND u.contrasenia = ?2");
            q.setParameter(1, usuario);
            q.setParameter(2, contrasenia);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public int getUsuarioIdMAX() {
        EntityManager em = getEntityManager();
        try {
            Query q = em
                    .createQuery("SELECT MAX(d.id) FROM Usuario d");
            return (Integer) q.getSingleResult();
        } finally {
            em.close();
        }
    }
}
