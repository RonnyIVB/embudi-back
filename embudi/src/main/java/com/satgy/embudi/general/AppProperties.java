package com.satgy.embudi.general;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 *
 * @author Ronny
 */

@Component
public class AppProperties {

    @Autowired
    private Environment ambiente;

    public String getTokenSecret () { return ambiente.getProperty("tokenSecret"); }
    public String getFormatoFecha () { return ambiente.getProperty("formatoFecha"); }
    public String getFormatoHora () { return ambiente.getProperty("formatoHora"); }

    public String getDireccionCorreo () { return ambiente.getProperty("direccionCorreo"); }
    public String getCorreoAutenticacion () { return ambiente.getProperty("correoAutenticacion"); }
    public String getUsuarioCorreo () { return ambiente.getProperty("usuarioCorreo"); }
    public String getClaveCorreo () { return ambiente.getProperty("claveCorreo"); }
    public String getDireccionSMTP () { return ambiente.getProperty("direccionSMTP"); }
    public String getPuertoSMTP () { return ambiente.getProperty("puertoSMTP"); }
    public String getStarttls () { return ambiente.getProperty("starttls"); }

}
