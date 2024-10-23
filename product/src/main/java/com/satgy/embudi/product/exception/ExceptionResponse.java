package com.satgy.embudi.product.exception;

import java.util.Date;
import java.util.Map;

// Esta clase existe para tener un formato en la respuesta de las excepciones.

public class ExceptionResponse {
    private Date timestamp;
    private String errorTitle;
    private String errorDetail;
    private Map <String, String> errors; // el primer String es del campo y el segundo del error

    public ExceptionResponse() {
    }

    public ExceptionResponse(Date timestamp, String errorTitle, String errorDetail) {
        this.timestamp = timestamp;
        this.errorTitle = errorTitle;
        this.errorDetail = errorDetail;
    }

    // este constructor se usa en el caso de que se de un error con validación
    public ExceptionResponse(Date timestamp, String errorTitle, String errorDetail, Map<String, String> errors) {
        this.timestamp = timestamp;
        this.errorTitle = errorTitle;
        this.errorDetail = errorDetail;
        this.errors = errors;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getErrorTitle() {
        return errorTitle;
    }

    public void setErrorTitle(String errorTitle) {
        this.errorTitle = errorTitle;
    }

    public String getErrorDetail() {
        return errorDetail;
    }

    public void setErrorDetail(String errorDetail) {
        this.errorDetail = errorDetail;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }
}
