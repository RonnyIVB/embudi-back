package com.satgy.embudi.general;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 *
 * @author Ronny
 */

// La función de esta clase es permitir usar un bean en una clase que no es Bean
// En la clase AuthenticationFilter que no es un Bean no puedo inyectar un Bean como un service para poder tomar datos del usuario
// Por ejemplo en una clase controller inyecto con autowired una clase service.
// Lo que necesito es agregar al header de una petición un key o valor con el usuarioid
public class SpringApplicationContext implements ApplicationContextAware {
    private static ApplicationContext CONTEXT;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        CONTEXT = applicationContext;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    // Método estático para poder acceder a los Beans de la aplicación
    public static Object getBean(String beanName) {
        return CONTEXT.getBean(beanName);
    }
}
