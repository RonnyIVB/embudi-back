package com.satgy.embudi.product.general;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

public class Par {

    @Autowired
    private static Environment env;

    public static String caracteresEspeciales=".,;:+-_*/'¡!\"·$%&/()=¿?\\ºª|@#~€¬`´^*[]{}¨Çç<>";

    public static final String FILE_SEQUENCE = "fileSequence.15";
    public static final String localhost="localhost";
    public static final String localhost2="127.0.0.1";

    // Estas constantes se usan en AuthenticationFilter.successfulAuthentication
    // Para generar el token cuando se ha logrado autenticar con éxito
    public static final long EXPIRATION_TIME = 864000000; // 10días, lo uso para calcular el tiempo de expiración del token
    public static final String TOKEN_PREFIX = "Bearer "; // json web token, bearer significa portador
    public static final String HEADER_STRING = "Authorization"; // Es el header por el que se enviará el TOKEN_PREFIX

    // Es una key secreta con la que se generarán los tokens, la generé en la web: randomkeygen.com en la sección "CodeIgniter Encryption Keys"
    //public static final String TOKEN_SECRET = "m2H0EwqfFT41YCT4kBz6ARUaokcvee1N";

    //public static long getJwtExpirationToken() { return Long.parseLong(env.getProperty("jwtexpirationtoken")); }
    public static long getJwtExpirationToken() { return 864000000; } // miliseconds
    //public static String getJwtSign() { return env.getProperty("jwtsign"); }
    public static String getJwtSign() { return "m2H0EwqfFT41YCT4kBz6ARUaokcvee1N"; }

    public static String getTokenSecret () { return env.getProperty("tokenSecret"); }
    public static String getFormatoFecha () { return env.getProperty("formatoFecha"); }
    public static String getFormatoHora () { return env.getProperty("formatoHora"); }


}
