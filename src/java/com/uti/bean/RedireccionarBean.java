/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uti.bean;

import java.io.IOException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author JuanPablo
 */
@ManagedBean
@RequestScoped
public class RedireccionarBean {

    /**
     * Creates a new instance of RedireccionarBean
     */
    public RedireccionarBean() {
    }

    public void departamentos() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext()
                .redirect("departamentos.html");
    }

    public void pasantes() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext()
                .redirect("pasantes.html");
    }

    public void findPasantes() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext()
                .redirect("findpasantes.html");
    }
}