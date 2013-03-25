/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uti.bean;

import com.uti.entities.Usuario;
import com.uti.jpacontroller.UsuarioJpaController;
import java.io.IOException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author JuanPablo
 */
@ManagedBean
@SessionScoped
public class SecionLogeoBean {

    /**
     * Creates a new instance of SecionLogeoBean
     */
    private String usuario;
    private String contrasenia;

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public SecionLogeoBean() {
    }
    private Boolean logeo = false;

    public void ingresar() throws IOException {
        Usuario usuarioConsulta = null;
        try {
            UsuarioJpaController usuarioJpaController = new UsuarioJpaController();
            usuario = Usuario.encryptPassword(usuario);
            contrasenia = Usuario.encryptPassword(contrasenia);
            usuarioConsulta = usuarioJpaController.findUsuario(usuario, contrasenia).get(0);
        } catch (Exception e) {
        }
        if (usuarioConsulta != null) {
            logeo = true;
            usuario = "";
            contrasenia = "";
            FacesContext.getCurrentInstance().getExternalContext()
                    .redirect("admin/pasantes.html");
        } else {
            usuario = "";
            contrasenia = "";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_WARN, "Nombre de Usuario o Contraseña INCORRECTOS", null));
        }
    }

    public Boolean getLogeo() {
        return logeo;
    }

    public void setLogeo(Boolean logeo) {
        this.logeo = logeo;
    }

    public void salir() throws IOException {
        logeo = false;
    }
}