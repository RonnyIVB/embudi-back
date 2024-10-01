package com.satgy.embudi.exception;

import java.util.Date;
import java.util.Map;

// Esta clase existe para tener un formato en la respuesta de las excepciones.

public class ExceptionResponse {
    private Date timestamp;
    private String mensajeError;
    private String detalleError;
    private Map <String, String> erroresValidacion; // el primer String es del campo y el segundo del error

    public ExceptionResponse() {
    }

    public ExceptionResponse(Date timestamp, String mensajeError, String detalleError) {
        this.timestamp = timestamp;
        this.mensajeError = mensajeError;
        this.detalleError = detalleError;
    }

    // este constructor se usa en el caso de que se de un error con validación
    public ExceptionResponse(Date timestamp, String mensajeError, String detalleError, Map<String, String> erroresValidacion) {
        this.timestamp = timestamp;
        this.mensajeError = mensajeError;
        this.detalleError = detalleError;
        this.erroresValidacion = erroresValidacion;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }

    public String getDetalleError() {
        return detalleError;
    }

    public void setDetalleError(String detalleError) {
        this.detalleError = detalleError;
    }

    public Map<String, String> getErroresValidacion() {
        return erroresValidacion;
    }

    public void setErroresValidacion(Map<String, String> erroresValidacion) {
        this.erroresValidacion = erroresValidacion;
    }
}
