/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uti.bean;

import com.uti.entities.Departamento;
import com.uti.entities.Pasantes;
import com.uti.jpacontroller.DepartamentoJpaController;
import com.uti.jpacontroller.PasantesJpaController;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.naming.NamingException;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author desertbarret
 */
@ManagedBean
@RequestScoped
public class PasanteBean {

    /**
     * Creates a new instance of PasanteBean
     */
    private Pasantes pasante;
    private int departamento;
    private List<Pasantes> listPasantes;
    private List<Pasantes> listPasantesConcluidos;

    public List<Pasantes> getListPasantesConcluidos() {
        return listPasantesConcluidos;
    }

    public void setListPasantesConcluidos(List<Pasantes> listPasantesConcluidos) {
        this.listPasantesConcluidos = listPasantesConcluidos;
    }
    private SelectItem[] departamentosOptions;
    private String[] departamentosFilter;
    private SelectItem[] estadoOptions;
    private String[] estadoFilter;

    public String[] getEstadoFilter() {
        return estadoFilter;
    }

    public SelectItem[] getEstadoOptions() {
        return estadoOptions;
    }

    public void setEstadoOptions(SelectItem[] estadoOptions) {
        this.estadoOptions = estadoOptions;
    }

    public SelectItem[] getDepartamentosOptions() {
        return departamentosOptions;
    }

    public void setDepartamentosOptions(SelectItem[] departamentosOptions) {
        this.departamentosOptions = departamentosOptions;
    }

    public List<Pasantes> getListPasantes() {
        return listPasantes;
    }

    public void setListPasantes(List<Pasantes> listPasantes) {
        this.listPasantes = listPasantes;
    }

    public int getDepartamento() {
        return departamento;
    }

    public void setDepartamento(int departamento) {
        this.departamento = departamento;
    }
    private Departamento[] departamentos = null;

    public Departamento[] getDepartamentos() throws NamingException {
        updateDepartamentos();
        return departamentos;
    }

    public void setDepartamentos(Departamento[] departamentos) {
        this.departamentos = departamentos;
    }

    public Pasantes getPasante() throws NamingException {
        updateDepartamentos();
        return pasante;
    }

    public void setPasante(Pasantes pasante) {
        this.pasante = pasante;
    }

    public PasanteBean() throws NamingException {
        pasante = new Pasantes();
        updatePasantes();
        updatePasantesConcluidos();
        DepartamentoJpaController departamentoJpaController = new DepartamentoJpaController();
        departamentosFilter = new String[departamentoJpaController.findDepartamentoEntities().size()];
        for (int i = 0; i < departamentoJpaController.findDepartamentoEntities().size(); i++) {
            departamentosFilter[i] = departamentoJpaController.findDepartamentoEntities().get(i).getNombre();
        }
        departamentosOptions = createFilterOptions(departamentosFilter);

        estadoFilter = new String[2];
        estadoFilter[0] = "concluido";
        estadoFilter[1] = "terminado";
        estadoOptions = createFilterOptions(estadoFilter);
    }

    private SelectItem[] createFilterOptions(String[] data) {
        SelectItem[] options = new SelectItem[data.length + 1];
        options[0] = new SelectItem("", "Selec ");
        for (int i = 0; i < data.length; i++) {
            options[i + 1] = new SelectItem(data[i], data[i]);
        }
        return options;
    }

    public void createPasante() throws Exception {
        PasantesJpaController pasantesJpaController = new PasantesJpaController();
        Pasantes pasantesConsulta = null;
        try {
            pasantesConsulta = pasantesJpaController.findPasantesRegistro(pasante.getCedula()).get(0);
        } catch (Exception e) {
        }
        if (pasantesConsulta == null) {
            if (Pasantes.validateCedula(pasante.getCedula())) {

                DepartamentoJpaController departamentoJpaController = new DepartamentoJpaController();
                pasante.setDepartamento(departamentoJpaController.findDepartamento(departamento));
                pasante.setHorasCumplidas("00 horas 00 minutos");
                pasante.setActivo("activo");
                pasantesJpaController.create(pasante);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "El nuevo Pasante " + pasante.getNombre() + " a sido agregado exitosamente", null));
                pasante = new Pasantes();
            } else {
                pasante.setCedula("");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                        "La cedula proporcionada es INCORRECTA", null));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "La cedula proporcionada ya ha sido registrada", null));
            pasante.setCedula("");
        }
        updatePasantes();
    }

    private void updateDepartamentos() throws NamingException {
        DepartamentoJpaController departamentoJpaController = new DepartamentoJpaController();
        departamentos = new Departamento[departamentoJpaController.findDepartamentoEntities().size()];
        for (int i = 0; i < departamentoJpaController.findDepartamentoEntities().size(); i++) {
            departamentos[i] = departamentoJpaController.findDepartamentoEntities().get(i);
        }
    }

    private void updatePasantes() throws NamingException {
        PasantesJpaController pasantesJpaController = new PasantesJpaController();
        listPasantes = pasantesJpaController.findPasantesActivo("activo");
    }

    public void onEdit(RowEditEvent event) throws NamingException {
        PasantesJpaController pasantesJpaController = new PasantesJpaController();
        Pasantes pasantesed = new Pasantes();
        pasantesed = ((Pasantes) event.getObject());
        pasantesed.setHorasPasanteDepartamentoList(null);
        pasantesJpaController.edit(pasantesed);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Cambios realizados EXITOSAMENTE", null));
        updatePasantes();
    }

    public final void updatePasantesConcluidos() throws NamingException {
        PasantesJpaController pasantesJpaController = new PasantesJpaController();
        listPasantesConcluidos = pasantesJpaController.findPasantesConcluido("concluido", "terminado");
    }
    private Boolean imprimir = true;

    public Boolean getImprimir() {
        return imprimir;
    }

    public void setImprimir(Boolean imprimir) {
        this.imprimir = imprimir;
    }

    public void preProcess(Object document) {
        imprimir = false;
    }
}