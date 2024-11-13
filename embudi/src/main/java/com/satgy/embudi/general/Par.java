package com.satgy.embudi.general;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;

public class Par {

    @Autowired
    private static Environment env;

    public static String caracteresEspeciales=".,;:+-_*/'¡!\"·$%&/()=¿?\\ºª|@#~€¬`´^*[]{}¨Çç<>";
    public static final String SIGN_UP_URL = "/api/auth";

    private static final String secuenciaArchivo = "secuenciaArchivo.15";
    private static final String localhost="localhost";
    private static final String localhost2="127.0.0.1";

    // Estas constantes se usan en AuthenticationFilter.successfulAuthentication
    // Para generar el token cuando se ha logrado autenticar con éxito
    private static final long EXPIRATION_TIME = 864000000; // 10días, lo uso para calcular el tiempo de expiración del token
    private static final String TOKEN_PREFIX = "Bearer "; // json web token, bearer significa portador
    private static final String HEADER_STRING = "Authorization"; // Es el header por el que se enviará el TOKEN_PREFIX

    // Es una key secreta con la que se generarán los tokens, la generé en la web: randomkeygen.com en la sección "CodeIgniter Encryption Keys"
    //private static final String TOKEN_SECRET = "m2H0EwqfFT41YCT4kBz6ARUaokcvee1N";

    //private static long getJwtExpirationToken() { return Long.parseLong(env.getProperty("jwtexpirationtoken")); }
    //private static long getJwtExpirationToken() { return 864000000; } // miliseconds
    private static long getJwtExpirationToken() {
        //AppProperties properties = (AppProperties) SpringApplicationContext.getBean("AppProperties");
        AppProperties properties = new AppProperties();
        return Long.parseLong(properties.getJwtExpirationToken());
    }

    //@Value("${jwtsign}")
    //private static String jwtSign;
    //private static String getJwtSign() { return env.getProperty("jwtsign"); }
    //private static String getJwtSign() { return "m2H0EwqfFT41YCT4kBz6ARUaokcvee1N"; }
    private static String getJwtSign() {
        //AppProperties properties = (AppProperties) SpringApplicationContext.getBean("AppProperties");
        AppProperties properties = new AppProperties();
        return properties.getJwtSign();
    }

    private static String getTokenSecret () { return env.getProperty("tokenSecret"); }
    private static String getFormatoFecha () { return env.getProperty("formatoFecha"); }
    private static String getFormatoHora () { return env.getProperty("formatoHora"); }

    private static String getDireccionCorreo () { return env.getProperty("app.emailAddress"); }
    private static String getCorreoAutenticacion () { return env.getProperty("app.emailAuthentication"); }
    private static String getUsuarioCorreo () { return env.getProperty("app.emailUser"); }
    private static String getClaveCorreo () { return env.getProperty("app.emailPassword"); }
    private static String getDireccionSMTP () { return env.getProperty("app.SMTPAddress"); }
    private static String getPuertoSMTP () { return env.getProperty("app.SMTPPort"); }
    private static String getStarttls () { return env.getProperty("app.emailStartTLS"); }

}
