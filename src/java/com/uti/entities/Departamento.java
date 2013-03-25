/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uti.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author JuanPablo
 */
@Entity
@Table(name = "departamento")
@XmlRootElement
public class Departamento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "ingeniero_acargo")
    private String ingenieroAcargo;
    @OneToMany(mappedBy = "departamento")
    private List<Pasantes> pasantesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "departamento")
    private List<RegistroAsistencia> registroAsistenciaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idDepartamento")
    private List<HorasPasanteDepartamento> horasPasanteDepartamentoList;

    public Departamento() {
    }

    public Departamento(Integer id) {
        this.id = id;
    }

    public Departamento(Integer id, String nombre, String ingenieroAcargo) {
        this.id = id;
        this.nombre = nombre;
        this.ingenieroAcargo = ingenieroAcargo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIngenieroAcargo() {
        return ingenieroAcargo;
    }

    public void setIngenieroAcargo(String ingenieroAcargo) {
        this.ingenieroAcargo = ingenieroAcargo;
    }

    @XmlTransient
    public List<Pasantes> getPasantesList() {
        return pasantesList;
    }

    public void setPasantesList(List<Pasantes> pasantesList) {
        this.pasantesList = pasantesList;
    }

    @XmlTransient
    public List<RegistroAsistencia> getRegistroAsistenciaList() {
        return registroAsistenciaList;
    }

    public void setRegistroAsistenciaList(List<RegistroAsistencia> registroAsistenciaList) {
        this.registroAsistenciaList = registroAsistenciaList;
    }

    @XmlTransient
    public List<HorasPasanteDepartamento> getHorasPasanteDepartamentoList() {
        return horasPasanteDepartamentoList;
    }

    public void setHorasPasanteDepartamentoList(List<HorasPasanteDepartamento> horasPasanteDepartamentoList) {
        this.horasPasanteDepartamentoList = horasPasanteDepartamentoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Departamento)) {
            return false;
        }
        Departamento other = (Departamento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.uti.entities.Departamento[ id=" + id + " ]";
    }
}
