/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uti.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author JuanPablo
 */
@Entity
@Table(name = "registro_asistencia")
@XmlRootElement
public class RegistroAsistencia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fecha;

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    @Basic(optional = false)
    @Column(name = "hora_entrada")
    private String horaEntrada;
    @Column(name = "hora_salida")
    private String horaSalida;
    @Column(name = "hora_entrada_maniana")
    private String horaEntradaManiana;
    @Column(name = "hora_salida_maniana")
    private String horaSalidaManiana;
    @JoinColumns({
        @JoinColumn(name = "id_departamento", referencedColumnName = "id"),
        @JoinColumn(name = "id_departamento", referencedColumnName = "id")})
    @ManyToOne(optional = false)
    private Departamento departamento;
    @JoinColumns({
        @JoinColumn(name = "id_pasante", referencedColumnName = "id"),
        @JoinColumn(name = "id_pasante", referencedColumnName = "id")})
    @ManyToOne(optional = false)
    private Pasantes pasantes;

    public RegistroAsistencia() {
    }

    public RegistroAsistencia(Integer id) {
        this.id = id;
    }

    public RegistroAsistencia(Integer id, Date fecha, String horaEntrada) {
        this.id = id;
        this.fecha = fecha;
        this.horaEntrada = horaEntrada;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(String horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public String getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(String horaSalida) {
        this.horaSalida = horaSalida;
    }

    public String getHoraEntradaManiana() {
        return horaEntradaManiana;
    }

    public void setHoraEntradaManiana(String horaEntradaManiana) {
        this.horaEntradaManiana = horaEntradaManiana;
    }

    public String getHoraSalidaManiana() {
        return horaSalidaManiana;
    }

    public void setHoraSalidaManiana(String horaSalidaManiana) {
        this.horaSalidaManiana = horaSalidaManiana;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public Pasantes getPasantes() {
        return pasantes;
    }

    public void setPasantes(Pasantes pasantes) {
        this.pasantes = pasantes;
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
        if (!(object instanceof RegistroAsistencia)) {
            return false;
        }
        RegistroAsistencia other = (RegistroAsistencia) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.uti.entities.RegistroAsistencia[ id=" + id + " ]";
    }
}
