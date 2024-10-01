package com.satgy.embudi.general;

import com.satgy.embudi.SpringApplicationContext;
import com.satgy.embudi.general.AppProperties;

public class Par {
    public static String caracteresEspeciales=".,;:+-_*/'¡!\"·$%&/()=¿?\\ºª|@#~€¬`´^*[]{}¨Çç<>";

    public static final String secuenciaArchivo = "secuenciaArchivo.15";
    public static final String localhost="localhost";
    public static final String localhost2="127.0.0.1";

    // Estas constantes se usan en AuthenticationFilter.successfulAuthentication
    // Para generar el token cuando se ha logrado autenticar con éxito
    public static final long EXPIRATION_TIME = 864000000; // 10días, lo uso para calcular el tiempo de expiración del token
    public static final String TOKEN_PREFIX = "Bearer "; // json web token, bearer significa portador
    public static final String HEADER_STRING = "Authorization"; // Es el header por el que se enviará el TOKEN_PREFIX
    public static final String SIGN_UP_URL = "/api/inicio"; // La URL con la que los usuarios se registrarán en el sistema

    // Es una key secreta con la que se generarán los tokens, la generé en la web: randomkeygen.com en la sección "CodeIgniter Encryption Keys"
    //public static final String TOKEN_SECRET = "m2H0EwqfFT41YCT4kBz6ARUaokcvee1N";
    public static String getToken(){
        AppProperties propiedades = (AppProperties) SpringApplicationContext.getBean("AppProperties");
        return propiedades.getTokenSecret();
    }

    public static String getFormatoFecha(){
        AppProperties propiedades = (AppProperties) SpringApplicationContext.getBean("AppProperties");
        return propiedades.getFormatoFecha();
    }
    public static String getFormatoHora(){
        AppProperties propiedades = (AppProperties) SpringApplicationContext.getBean("AppProperties");
        return propiedades.getFormatoHora();
    }
    public static String getFormatoFechaHora(){
        AppProperties propiedades = (AppProperties) SpringApplicationContext.getBean("AppProperties");
        return propiedades.getFormatoFecha()+ " " + propiedades.getFormatoHora();
    }

    public static String getDireccionCorreo(){
        AppProperties propiedades = (AppProperties) SpringApplicationContext.getBean("AppProperties");
        return propiedades.getDireccionCorreo();
    }
    public static String getCorreoAutenticacion(){
        AppProperties propiedades = (AppProperties) SpringApplicationContext.getBean("AppProperties");
        return propiedades.getCorreoAutenticacion();
    }
    public static String getUsuarioCorreo(){
        AppProperties propiedades = (AppProperties) SpringApplicationContext.getBean("AppProperties");
        return propiedades.getUsuarioCorreo();
    }
    public static String getClaveCorreo(){
        AppProperties propiedades = (AppProperties) SpringApplicationContext.getBean("AppProperties");
        return propiedades.getClaveCorreo();
    }
    public static String getDireccionSMTP(){
        AppProperties propiedades = (AppProperties) SpringApplicationContext.getBean("AppProperties");
        return propiedades.getDireccionSMTP();
    }
    public static String getPuertoSMTP(){
        AppProperties propiedades = (AppProperties) SpringApplicationContext.getBean("AppProperties");
        return propiedades.getPuertoSMTP();
    }
    public static String getStarttls(){
        AppProperties propiedades = (AppProperties) SpringApplicationContext.getBean("AppProperties");
        return propiedades.getStarttls();
    }

}
