/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applamento;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author nicolasgarciamunoz
 */
public class DocumentoCompleto {
    private String fecha;
    private String materias;
    private String proponentes;
    private String intervienen;
    private HashMap <String, String> intervencion;  //Para interviniente y discurso
    private String votacion;

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getMaterias() {
        return materias;
    }

    public void setMaterias(String materias) {
        this.materias = materias;
    }

    public String getProponentes() {
        return proponentes;
    }

    public void setProponentes(String proponentes) {
        this.proponentes = proponentes;
    }

    public String getIntervienen() {
        return intervienen;
    }

    public void setIntervienen(String intervienen) {
        this.intervienen = intervienen;
    }

    public HashMap<String, String> getIntervencion() {
        return intervencion;
    }

    public void setIntervencion(HashMap<String, String> intervencion) {
        this.intervencion = intervencion;
    }

    public String getVotacion() {
        return votacion;
    }

    public void setVotacion(String votacion) {
        this.votacion = votacion;
    }
    
    boolean isEmpty() {
        return votacion == null && fecha == null && intervencion == null && intervienen == null && materias == null && proponentes == null;
    }
    
}
