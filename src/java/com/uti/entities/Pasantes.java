/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uti.entities;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author JuanPablo
 */
@Entity
@Table(name = "pasantes")
@XmlRootElement
public class Pasantes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "apellido")
    private String apellido;
    @Basic(optional = false)
    @Column(name = "cedula")
    private String cedula;
    @Basic(optional = false)
    @Column(name = "horas_cumplidas")
    private String horasCumplidas;
    @Basic(optional = false)
    @Column(name = "activo")
    private String activo;
    @JoinColumns({
        @JoinColumn(name = "id_departamento", referencedColumnName = "id"),
        @JoinColumn(name = "id_departamento", referencedColumnName = "id"),
        @JoinColumn(name = "id_departamento", referencedColumnName = "id")})
    @ManyToOne
    private Departamento departamento;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pasantes")
    private List<RegistroAsistencia> registroAsistenciaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPasante")
    private List<HorasPasanteDepartamento> horasPasanteDepartamentoList;

    public Pasantes() {
    }

    public Pasantes(Integer id) {
        this.id = id;
    }

    public Pasantes(Integer id, String nombre, String apellido, String cedula, String horasCumplidas, String activo) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
        this.horasCumplidas = horasCumplidas;
        this.activo = activo;
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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getHorasCumplidas() {
        return horasCumplidas;
    }

    public void setHorasCumplidas(String horasCumplidas) {
        this.horasCumplidas = horasCumplidas;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
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
        if (!(object instanceof Pasantes)) {
            return false;
        }
        Pasantes other = (Pasantes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.uti.entities.Pasantes[ id=" + id + " ]";
    }

    public static String sumarHorasAsistidas(String horasAsistidas, int horasSalida,
            int minutosSalida, int horasEntrada, int minutosEntrada) throws ParseException {

        Date hora1 = new Date(103, 2, 8, horasEntrada, minutosEntrada);
        Date hora2 = new Date(103, 2, 8, horasSalida, minutosSalida);

        long lantes = hora1.getTime();
        long lahora = hora2.getTime();

        long horas = (lahora - lantes) / 3600000;
        long minutos = ((lahora - lantes) / (1000 * 60)) - horas * 60;


        int sumaHoras = Integer.parseInt(horasAsistidas.substring(0, horasAsistidas.indexOf(" horas")));
        sumaHoras = sumaHoras + (int) horas;

        int sumaMinutos = Integer.parseInt(horasAsistidas.substring(horasAsistidas.indexOf("horas ") + 6, horasAsistidas.indexOf(" minutos")));
        sumaMinutos = sumaMinutos + (int) minutos;

        if (sumaMinutos >= 60) {
            sumaHoras = sumaHoras + 1;
            sumaMinutos = sumaMinutos - 60;
        }
        return sumaHoras + " horas " + sumaMinutos + " minutos";
    }

    public static String sumarHorasAsistidasDepartamento(String horasAsistidasDepartamento, int horasSalida,
            int minutosSalida, int horasEntrada, int minutosEntrada) throws ParseException {

        Date hora1 = new Date(103, 2, 8, horasEntrada, minutosEntrada);
        Date hora2 = new Date(103, 2, 8, horasSalida, minutosSalida);

        long lantes = hora1.getTime();
        long lahora = hora2.getTime();

        long horas = (lahora - lantes) / 3600000;
        long minutos = ((lahora - lantes) / (1000 * 60)) - horas * 60;


        int sumaHoras = Integer.parseInt(horasAsistidasDepartamento.substring(0, horasAsistidasDepartamento.indexOf(" horas")));
        sumaHoras = sumaHoras + (int) horas;

        int sumaMinutos = Integer.parseInt(horasAsistidasDepartamento.substring(horasAsistidasDepartamento.indexOf("horas ") + 6, horasAsistidasDepartamento.indexOf(" minutos")));
        sumaMinutos = sumaMinutos + (int) minutos;

        if (sumaMinutos >= 60) {
            sumaHoras = sumaHoras + 1;
            sumaMinutos = sumaMinutos - 60;
        }
        return sumaHoras + " horas " + sumaMinutos + " minutos";
    }

    public static boolean validateCedula(String cedula) {
        boolean cedulaCorrecta = false;
        try {
            if (cedula.length() == 10) {
                int tercerDigito = Integer.parseInt(cedula.substring(2, 3));
                if (tercerDigito < 6) {
                    int[] coefValCedula = {2, 1, 2, 1, 2, 1, 2, 1, 2};
                    int verificador = Integer.parseInt(cedula.substring(9, 10));
                    int suma = 0;
                    int digito = 0;
                    for (int i = 0; i < (cedula.length() - 1); i++) {
                        digito = Integer.parseInt(cedula.substring(i, i + 1)) * coefValCedula[i];
                        suma += ((digito % 10) + (digito / 10));
                    }
                    if ((suma % 10 == 0) && (suma % 10 == verificador)) {
                        cedulaCorrecta = true;
                    } else if ((10 - (suma % 10)) == verificador) {
                        cedulaCorrecta = true;
                    } else {
                        cedulaCorrecta = false;
                    }
                } else {
                    cedulaCorrecta = false;
                }
            } else {
                cedulaCorrecta = false;
            }
        } catch (NumberFormatException nfe) {
            cedulaCorrecta = false;
        } catch (Exception err) {
            System.out.println("Una excepcion ocurrio en el proceso de validadcion");
            cedulaCorrecta = false;
        }
        return cedulaCorrecta;
    }
}
