/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uti.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author JuanPablo
 */
@Entity
@Table(name = "horas_pasante_departamento")
@XmlRootElement
public class HorasPasanteDepartamento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "horas_cumplidas")
    private String horasCumplidas;
    @JoinColumn(name = "id_pasante", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Pasantes idPasante;
    @JoinColumn(name = "id_departamento", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Departamento idDepartamento;

    public HorasPasanteDepartamento() {
    }

    public HorasPasanteDepartamento(Integer id) {
        this.id = id;
    }

    public HorasPasanteDepartamento(Integer id, String horasCumplidas) {
        this.id = id;
        this.horasCumplidas = horasCumplidas;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHorasCumplidas() {
        return horasCumplidas;
    }

    public void setHorasCumplidas(String horasCumplidas) {
        this.horasCumplidas = horasCumplidas;
    }

    public Pasantes getIdPasante() {
        return idPasante;
    }

    public void setIdPasante(Pasantes idPasante) {
        this.idPasante = idPasante;
    }

    public Departamento getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(Departamento idDepartamento) {
        this.idDepartamento = idDepartamento;
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
        if (!(object instanceof HorasPasanteDepartamento)) {
            return false;
        }
        HorasPasanteDepartamento other = (HorasPasanteDepartamento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.uti.entities.HorasPasanteDepartamento[ id=" + id + " ]";
    }
}
