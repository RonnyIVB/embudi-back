package com.satgy.embudi.general;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class AppProperties {

    //@Autowired
    //private Environment env;

    @Value("${app.jwtExpirationToken}")
    private String jwtExpirationToken;

    @Value("${app.jwtSign}")
    private String jwtSign;

    public String getJwtExpirationToken() {
        return jwtExpirationToken;
        //return env.getProperty("app.jwtExpirationToken");
    }

    public String getJwtSign() {
        return jwtSign;
        //return env.getProperty("app.jwtSign");
    }
}
