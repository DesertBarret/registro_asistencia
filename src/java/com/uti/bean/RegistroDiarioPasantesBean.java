/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uti.bean;

import com.uti.entities.HorasPasanteDepartamento;
import com.uti.entities.Pasantes;
import com.uti.entities.RegistroAsistencia;
import com.uti.jpacontroller.DepartamentoJpaController;
import com.uti.jpacontroller.HorasPasanteDepartamentoJpaController;
import com.uti.jpacontroller.PasantesJpaController;
import com.uti.jpacontroller.RegistroAsistenciaJpaController;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author desertbarret
 */
@ManagedBean
@RequestScoped
public class RegistroDiarioPasantesBean {

    /**
     * Creates a new instance of PasanteBean
     */
    private List<Pasantes> listPasantesDia;
    private final int HORAS_MAXIMAS_CUMPLIR = 480;

    public int getHORAS_MAXIMAS_CUMPLIR() {
        return HORAS_MAXIMAS_CUMPLIR;
    }

    public List<Pasantes> getListPasantesDia() {
        return listPasantesDia;
    }

    public void setListPasantesDia(List<Pasantes> listPasantesDia) {
        this.listPasantesDia = listPasantesDia;
    }
    private SelectItem[] departamentosOptions;
    private String[] departamentosFilter;

    public SelectItem[] getDepartamentosOptions() {
        return departamentosOptions;
    }

    public void setDepartamentosOptions(SelectItem[] departamentosOptions) {
        this.departamentosOptions = departamentosOptions;
    }

    public RegistroDiarioPasantesBean() throws NamingException {
        updatePasantesDia();
        DepartamentoJpaController departamentoJpaController = new DepartamentoJpaController();
        departamentosFilter = new String[departamentoJpaController.findDepartamentoEntities().size()];
        for (int i = 0; i < departamentoJpaController.findDepartamentoEntities().size(); i++) {
            departamentosFilter[i] = departamentoJpaController.findDepartamentoEntities().get(i).getNombre();
        }
        departamentosOptions = createFilterOptions(departamentosFilter);
    }

    private SelectItem[] createFilterOptions(String[] data) {
        SelectItem[] options = new SelectItem[data.length + 1];
        options[0] = new SelectItem("", "Selec ");
        for (int i = 0; i < data.length; i++) {
            options[i + 1] = new SelectItem(data[i], data[i]);
        }
        return options;
    }

    private void updatePasantesDia() throws NamingException {
        listPasantesDia = new ArrayList<Pasantes>();
        PasantesJpaController pasantesJpaController = new PasantesJpaController();
        try {
            for (RegistroAsistencia registroAsistencia : pasantesJpaController.findPasantesDia(new Date())) {
                listPasantesDia.add(registroAsistencia.getPasantes());
            }
        } catch (Exception e) {
        }
    }
    private String cedulaPasante;

    public String getCedulaPasante() {
        return cedulaPasante;
    }

    public void setCedulaPasante(String cedulaPasante) {
        this.cedulaPasante = cedulaPasante;
    }

    public Boolean registrarIngresoDiarioPasante(RegistroAsistencia registroAsistencia) throws NamingException {
        RegistroAsistenciaJpaController registroAsistenciaJpaController = new RegistroAsistenciaJpaController();
        PasantesJpaController pasantesJpaController;
        Pasantes pasantesRegistroDiario;
        if (registroAsistencia == null) {
            try {
                if (new java.util.Date().getHours() >= 7 && new java.util.Date().getHours() < 14) {
                    registroAsistencia = new RegistroAsistencia();
                    registroAsistencia.setFecha(new java.util.Date());
                    registroAsistencia.setHoraEntradaManiana(new java.util.Date().getHours() + "H" + new java.util.Date().getMinutes());
                    registroAsistencia.setHoraSalidaManiana(" ");
                    registroAsistencia.setHoraEntrada(" ");
                    registroAsistencia.setHoraSalida(" ");
                    pasantesJpaController = new PasantesJpaController();
                    pasantesRegistroDiario = pasantesJpaController.findPasantesRegistro(cedulaPasante).get(0);
                    registroAsistencia.setPasantes(pasantesRegistroDiario);
                    registroAsistencia.setDepartamento(pasantesRegistroDiario.getDepartamento());
                    registroAsistencia.setIpPc(recolectarIpPcRemoto());
                    registroAsistenciaJpaController.create(registroAsistencia);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                            FacesMessage.SEVERITY_INFO, "Ingreso registrado Correctamente", null));
                    cedulaPasante = "";
                    return true;
                } else if (new java.util.Date().getHours() >= 14 && new java.util.Date().getHours() <= 19) {
                    registroAsistencia = new RegistroAsistencia();
                    registroAsistencia.setFecha(new java.util.Date());
                    registroAsistencia.setHoraEntrada(new java.util.Date().getHours() + "H" + new java.util.Date().getMinutes());
                    registroAsistencia.setHoraSalida(" ");
                    registroAsistencia.setHoraEntradaManiana(" ");
                    registroAsistencia.setHoraSalidaManiana(" ");
                    pasantesJpaController = new PasantesJpaController();
                    pasantesRegistroDiario = pasantesJpaController.findPasantesRegistro(cedulaPasante).get(0);
                    registroAsistencia.setPasantes(pasantesRegistroDiario);
                    registroAsistencia.setDepartamento(pasantesRegistroDiario.getDepartamento());
                    registroAsistencia.setIpPc(recolectarIpPcRemoto());
                    registroAsistenciaJpaController.create(registroAsistencia);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                            FacesMessage.SEVERITY_INFO, "Ingreso registrado Correctamente", null));
                    cedulaPasante = "";
                    return true;
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                            FacesMessage.SEVERITY_WARN, "Fuera de la hora limite", null));

                }
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_WARN, "La cedula no pertenece a ningun pasante registrado", null));
                return false;
            }
        } else {
            if (new java.util.Date().getHours() >= 14 && new java.util.Date().getHours() <= 19) {
                if (registroAsistencia.getHoraEntrada().equals(" ")) {
                    registroAsistencia.setHoraEntrada(new java.util.Date().getHours() + "H" + new java.util.Date().getMinutes());
                    registroAsistenciaJpaController.edit(registroAsistencia);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                            FacesMessage.SEVERITY_INFO, "Ingreso registrado Correctamente", null));
                    cedulaPasante = "";
                    return true;
                }
            }
        }
        return false;
    }

    public Boolean registrarSalidaDiarioPasante(RegistroAsistencia registroAsistencia) throws NamingException, ParseException {
        RegistroAsistenciaJpaController registroAsistenciaJpaController = new RegistroAsistenciaJpaController();
        PasantesJpaController pasantesJpaController;
        Pasantes pasanteEditar;
        if (new java.util.Date().getHours() >= 7 && new java.util.Date().getHours() < 14) {
            if (!registroAsistencia.getHoraSalidaManiana().equals(" ")) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_WARN, "IMPOSIBLE volver a registrar su salida, este preceso ya fue realizado", null));
                return false;
            } else {
                registroAsistencia.setHoraSalidaManiana(new java.util.Date().getHours() + "H" + new java.util.Date().getMinutes());
                registroAsistenciaJpaController.edit(registroAsistencia);
                pasantesJpaController = new PasantesJpaController();
                pasanteEditar = pasantesJpaController.findPasantesRegistro(cedulaPasante).get(0);
                int horasEntrada = Integer.parseInt(registroAsistencia.getHoraEntradaManiana().substring(0,
                        registroAsistencia.getHoraEntradaManiana().indexOf("H")));
                int minutosEntrada = Integer.parseInt(registroAsistencia.getHoraEntradaManiana().substring(
                        registroAsistencia.getHoraEntradaManiana().indexOf("H") + 1, registroAsistencia.getHoraEntradaManiana().length()));
                String horasCumplidas = Pasantes.sumarHorasAsistidas(pasanteEditar.getHorasCumplidas(),
                        new java.util.Date().getHours(), new java.util.Date().getMinutes(), horasEntrada, minutosEntrada);
                if (Integer.parseInt(horasCumplidas.substring(0, horasCumplidas.indexOf(" horas"))) >= HORAS_MAXIMAS_CUMPLIR) {
                    pasanteEditar.setActivo("concluido");
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                            FacesMessage.SEVERITY_INFO, "Usted ya ha obtenido las " + HORAS_MAXIMAS_CUMPLIR + " horas nesesarias", null));
                }
                pasanteEditar.setHorasCumplidas(horasCumplidas);
                pasantesJpaController.edit(pasanteEditar);
                HorasPasanteDepartamento horasPasanteDepartamento = null;
                HorasPasanteDepartamentoJpaController horasPasanteDepartamentoJpaController =
                        new HorasPasanteDepartamentoJpaController();
                try {
                    horasPasanteDepartamento = horasPasanteDepartamentoJpaController.
                            findHorasPasanteDepartamentoExits(pasanteEditar, pasanteEditar.getDepartamento()).get(0);
                } catch (Exception e) {
                }
                if (horasPasanteDepartamento == null) {
                    horasPasanteDepartamento = new HorasPasanteDepartamento();
                    horasPasanteDepartamento.setIdPasante(pasanteEditar);
                    horasPasanteDepartamento.setIdDepartamento(pasanteEditar.getDepartamento());
                    horasPasanteDepartamento.setHorasCumplidas(Pasantes.sumarHorasAsistidasDepartamento(
                            "00 horas 00 minutos", new java.util.Date().getHours(),
                            new java.util.Date().getMinutes(), horasEntrada, minutosEntrada));
                    horasPasanteDepartamentoJpaController.create(horasPasanteDepartamento);
                } else {
                    horasPasanteDepartamento.setHorasCumplidas(Pasantes.sumarHorasAsistidasDepartamento(
                            horasPasanteDepartamento.getHorasCumplidas(), new java.util.Date().getHours(),
                            new java.util.Date().getMinutes(), horasEntrada, minutosEntrada));
                    horasPasanteDepartamentoJpaController.edit(horasPasanteDepartamento);
                }
                cedulaPasante = "";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_INFO, "Salida registrada Correctamente", null));
                return true;
            }

        } else if (new java.util.Date().getHours() >= 14 && new java.util.Date().getHours() <= 19) {
            if (!registroAsistencia.getHoraSalida().equals(" ")) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_WARN, "IMPOSIBLE volver a registrar su salida, este preceso ya fue realizado", null));
                return false;
            } else {
                registroAsistencia.setHoraSalida(new java.util.Date().getHours() + "H" + new java.util.Date().getMinutes());
                registroAsistenciaJpaController.edit(registroAsistencia);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_INFO, "Salida registrada Correctamente", null));
                pasantesJpaController = new PasantesJpaController();
                pasanteEditar = pasantesJpaController.findPasantesRegistro(cedulaPasante).get(0);
                int horasEntrada = Integer.parseInt(registroAsistencia.getHoraEntrada().substring(0,
                        registroAsistencia.getHoraEntrada().indexOf("H")));
                int minutosEntrada = Integer.parseInt(registroAsistencia.getHoraEntrada().substring(
                        registroAsistencia.getHoraEntrada().indexOf("H") + 1, registroAsistencia.getHoraEntrada().length()));
                String horasCumplidas = Pasantes.sumarHorasAsistidas(pasanteEditar.getHorasCumplidas(),
                        new java.util.Date().getHours(), new java.util.Date().getMinutes(), horasEntrada, minutosEntrada);
                if (Integer.parseInt(horasCumplidas.substring(0, horasCumplidas.indexOf(" horas"))) >= HORAS_MAXIMAS_CUMPLIR) {
                    pasanteEditar.setActivo("concluido");
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                            FacesMessage.SEVERITY_INFO, "Usted ya ha obtenido las " + HORAS_MAXIMAS_CUMPLIR + " horas nesesarias", null));
                }
                pasanteEditar.setHorasCumplidas(horasCumplidas);
                pasantesJpaController.edit(pasanteEditar);
                HorasPasanteDepartamento horasPasanteDepartamento = null;
                HorasPasanteDepartamentoJpaController horasPasanteDepartamentoJpaController =
                        new HorasPasanteDepartamentoJpaController();
                try {
                    horasPasanteDepartamento =
                            horasPasanteDepartamentoJpaController.
                            findHorasPasanteDepartamentoExits(pasanteEditar, pasanteEditar.getDepartamento()).get(0);
                } catch (Exception e) {
                }
                if (horasPasanteDepartamento == null) {
                    horasPasanteDepartamento = new HorasPasanteDepartamento();
                    horasPasanteDepartamento.setIdPasante(pasanteEditar);
                    horasPasanteDepartamento.setIdDepartamento(pasanteEditar.getDepartamento());
                    horasPasanteDepartamento.setHorasCumplidas(Pasantes.sumarHorasAsistidasDepartamento(
                            "00 horas 00 minutos", new java.util.Date().getHours(),
                            new java.util.Date().getMinutes(), horasEntrada, minutosEntrada));
                    horasPasanteDepartamentoJpaController.create(horasPasanteDepartamento);
                } else {
                    horasPasanteDepartamento.setHorasCumplidas(Pasantes.sumarHorasAsistidasDepartamento(
                            horasPasanteDepartamento.getHorasCumplidas(), new java.util.Date().getHours(),
                            new java.util.Date().getMinutes(), horasEntrada, minutosEntrada));
                    horasPasanteDepartamentoJpaController.edit(horasPasanteDepartamento);
                }
                cedulaPasante = "";
                return true;
            }
        }
        return false;
    }

    public void registrarIngreso() throws NamingException, Exception {
        RegistroAsistenciaJpaController registroAsistenciaJpaController = new RegistroAsistenciaJpaController();
        RegistroAsistencia registroAsistencia = null;
        try {
            registroAsistencia = registroAsistenciaJpaController.findRegistroAsistenciaPasante(cedulaPasante, new java.util.Date()).get(0);
        } catch (Exception e) {
        }
        boolean existeIP = registroAsistenciaJpaController.isIPRegistroAsistencia(recolectarIpPcRemoto(), new Date());
        boolean ipPasante = registroAsistenciaJpaController.isIpPasante(recolectarIpPcRemoto(), cedulaPasante, new java.util.Date());

        if (registroAsistencia == null) {
            if (!existeIP) {
                registrarIngresoDiarioPasante(registroAsistencia);
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_WARN, "La PC ya relizo un ingreso", null));

            }
        } else {
            if (new java.util.Date().getHours() >= 7 && new java.util.Date().getHours() < 14) {
                if (ipPasante) {
                    if (registroAsistencia.getHoraSalidaManiana().equals(" ")) {
                        registrarSalidaDiarioPasante(registroAsistencia);
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                                FacesMessage.SEVERITY_WARN, "Usted Ya marco la hora de salida de la mañana", null));
                    }
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                            FacesMessage.SEVERITY_WARN, "Solo se puede marcar la salida desde la misma pc en la que marco el ingreso", null));
                }
            } else if (new java.util.Date().getHours() >= 14 && new java.util.Date().getHours() <= 19) {
                if (ipPasante) {
                    if (registroAsistencia.getHoraEntrada().equals(" ")) {
                        registrarIngresoDiarioPasante(registroAsistencia);
                    } else if (registroAsistencia.getHoraSalida().equals(" ")) {
                        registrarSalidaDiarioPasante(registroAsistencia);
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                                FacesMessage.SEVERITY_WARN, "Usted Ya marco la hora de salida de la Tarde", null));
                    }
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                            FacesMessage.SEVERITY_WARN, "Solo se puede marcar la salida desde la misma pc en la que marco el ingreso", null));
                }
            }
        }
        updatePasantesDia();
    }

    public String recolectarIpPcRemoto() {
        HttpServletRequest request =
                (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return request.getRemoteAddr();

    }

    public void registrarIngresoAdmin() throws NamingException, Exception {
        RegistroAsistenciaJpaController registroAsistenciaJpaController = new RegistroAsistenciaJpaController();
        RegistroAsistencia registroAsistencia = null;
        try {
            registroAsistencia = registroAsistenciaJpaController.findRegistroAsistenciaPasante(cedulaPasante, new java.util.Date()).get(0);
        } catch (Exception e) {
        }
        boolean ipPasante = registroAsistenciaJpaController.isIpPasante(recolectarIpPcRemoto(), cedulaPasante, new java.util.Date());

        if (registroAsistencia == null) {
            registrarIngresoDiarioPasante(registroAsistencia);
        } else {
            if (new java.util.Date().getHours() >= 7 && new java.util.Date().getHours() < 14) {
                if (ipPasante) {
                    if (registroAsistencia.getHoraSalidaManiana().equals(" ")) {
                        registrarSalidaDiarioPasante(registroAsistencia);
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                                FacesMessage.SEVERITY_WARN, "Usted Ya marco la hora de salida de la mañana", null));
                    }
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                            FacesMessage.SEVERITY_WARN, "Solo se puede marcar la salida Desde la PC del Administrador", null));
                }
            } else if (new java.util.Date().getHours() >= 14 && new java.util.Date().getHours() <= 19) {
                if (ipPasante) {
                    if (registroAsistencia.getHoraEntrada().equals(" ")) {
                        registrarIngresoDiarioPasante(registroAsistencia);
                    } else if (registroAsistencia.getHoraSalida().equals(" ")) {
                        registrarSalidaDiarioPasante(registroAsistencia);
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                                FacesMessage.SEVERITY_WARN, "Usted Ya marco la hora de salida de la Tarde", null));
                    }
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                            FacesMessage.SEVERITY_WARN, "Solo se puede marcar la salida Desde la PC del Administrador", null));
                }
            }

        }
        updatePasantesDia();
    }
}