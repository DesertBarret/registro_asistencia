/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uti.bean;

import com.uti.entities.Departamento;
import com.uti.jpacontroller.DepartamentoJpaController;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.naming.NamingException;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author desertbarret
 */
@ManagedBean
@RequestScoped
public class DepartamentoBean {

    /**
     * Creates a new instance of PasanteBean
     */
    private Departamento departamento;
    private List<Departamento> listDepartamentos;
    private String[] departamentosFilter;

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public List<Departamento> getListDepartamentos() {
        return listDepartamentos;
    }

    public void setListDepartamentos(List<Departamento> listDepartamentos) {
        this.listDepartamentos = listDepartamentos;
    }

    public String[] getDepartamentosFilter() {
        return departamentosFilter;
    }

    public void setDepartamentosFilter(String[] departamentosFilter) {
        this.departamentosFilter = departamentosFilter;
    }

    public DepartamentoBean() throws NamingException {
        departamento = new Departamento();
        updateDepartamentos();
    }

    public void createDepartamento() throws Exception {
        DepartamentoJpaController departamentoJpaController = new DepartamentoJpaController();

        departamento.setId(departamentoJpaController.getDepatamentoIdMAX() + 1);
        departamentoJpaController.create(departamento);
        updateDepartamentos();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "El departamento " + departamento.getNombre() + " se agrego correctamente", null));
        departamento = new Departamento();
    }

    private void updateDepartamentos() throws NamingException {
        DepartamentoJpaController departamentoJpaController = new DepartamentoJpaController();
        listDepartamentos = departamentoJpaController.findDepartamentoEntities();

    }

    public void onEdit(RowEditEvent event) throws Exception {
        DepartamentoJpaController departamentoJpaController = new DepartamentoJpaController();
        departamentoJpaController.edit(((Departamento) event.getObject()));
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Los cambios que se realizaron fueron guardados exitosamente", null));
    }
}