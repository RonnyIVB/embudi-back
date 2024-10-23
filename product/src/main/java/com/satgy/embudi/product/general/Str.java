package com.satgy.embudi.product.general;

import com.satgy.embudi.product.dto.Currency;

public class Str {

    public static String getAlfanumericoAleatorio(int longitud){
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        java.util.Random random = new java.util.Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(longitud)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }

    public static String formatoPrecio (java.math.BigDecimal precio, Currency currency) {
        String cod = "$";
        if (currency != null) if (currency.getCode() != null) cod = currency.getCode();
        return cod  + Fun.darDecimal(precio, 2);
    }

    public static String formatoPrecio (java.math.BigDecimal precio) {
        String cod = "$";
        return cod  + Fun.darDecimal(precio, 2);
    }

    public static String si="Sí", no="No", strTrue="TRUE", strFalse="FALSE";

    private static final String patternEmail = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    /**
     * Validar un email <br>
     *
     * @param email
     * @return true si es correcto y false si es incorrecto
     */
    public static boolean validarEmail(String email) {
        if(esNulo(email)) return false;
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(patternEmail); // Compiles the given regular expression into a pattern.
        java.util.regex.Matcher matcher = pattern.matcher(email); // Match the given input against this pattern
        return matcher.matches();
    }

    /**
     * Método usado para imprimir textos alienados a la izquierda, definiendo el tamaño de caracteres <br>
     * Si la cadena es mas corta que los espacios se rellenan con espacios a la izquierda <br>
     * @param cadena el valor que se va a imprimir <br>
     * @param espacios el máximo de espacios que debe tener <br>
     * @param alineacion 1 izquierda, 2 derecha <br>
     * @param cortarSiSePasa si este parámetro es TRUE y el largo de la cadena es mayor a espacios, entonces se corta la cadena a espacios
     * @return El texto alineado a la izquierda
     */
    public static String alinear(Object cadena, int espacios, int alineacion, boolean cortarSiSePasa){
        String str=cadena.toString();
        if(str.length()>espacios){
            if(cortarSiSePasa){
                return str.substring(0, espacios);
            }else{
                return str;
            }
        }else{
            if(alineacion==1){return str + repetirCadena(" ", (espacios - str.length()));}
            if(alineacion==2){return repetirCadena(" ", (espacios - str.length())) + str;}
            return null;
        }

    }

    public static boolean tieneEspeciales(String cadena){
        for(int i=0; i<Par.caracteresEspeciales.length(); i++) {
            if(cadena.contains(Par.caracteresEspeciales.substring(i, i+1))) {
                return true;
            }
        }
        return false;
    }

    public static String soloPrimeraMayuscula(Object cadena) {
        if (cadena == null) { return null; }
        String c = cadena.toString();
        if (c.length()==0){ return ""; }
        if (c.length()==1){ return c.toUpperCase(); }
        c = c.substring(0, 1).toUpperCase()+c.substring(1, c.length());
        return c;
    }

    /**
     * Concatena en una sola cadena varias cadenas, se usa para cuando se fusionan dos filas de una tabla
     * @param detalles
     * @return
     */
    public static String concatenarDetalles(String ... detalles){
        String res="", sep=". ";
        for (int i=0; i<detalles.length; i++) {
            if(detalles[i]!=null){
                String val=quitarEspacios(detalles[i]);
                //No se agrega si los detalles son iguales y si no contienen caracteres
                if(val.length()>0 && !val.equals(res)){
                    if(res.length()==0){res=val;}
                    else{res=res+sep+val;}
                }
            }
        }
        return res;
    }

    /**
     *
     * @param cad un parámetro String
     * @return es true si cad==null o "" o "null" o "NULL"
     */
    public static boolean esNulo(Object cad){
        if(null==cad){return true;}
        if(cad.toString().toLowerCase().trim().equals("null") || cad.toString().trim().equals("")){return true;}
        return false;
    }
    /**
     *
     * @param cad  un parámetro String
     * @return devuelve "null" si cad==null o "" o "null" o "NULL", caso contrario devuelve cad
     */
    public static String esNuloToString(String cad){
        if(esNulo(cad)){return "null";}
        else{return cad;}
    }

    /**
     *
     * @param val puede ser TRUE o FALSE
     * @return si val=TRUE retorna "Sí" caso contrario retorna "No"
     */
    public static String siNo(boolean val){
        try{
            if(val){return si;}
            else{return no;}
        }catch(Exception error){
            return null;//val puede tener nulo
        }
    }
    /**
     *
     * @param val puede ser "Sí" o "No"
     * @return TRUE si val=Sí caso contrario FALSE
     */
    public static Boolean siNo(String val){
        try{
            if(val.equals(si)){return true;}
            else{return false;}
        }catch(Exception error){
            return null;//val puede tener nulo
        }
    }

    /**
     *
     * @param val puede ser TRUE o FALSE un valor booleano
     * @return "TRUE" o "FALSE" un valor String
     */
    public static String trueFalse(boolean val){
        try{
            if(val){return strTrue;}
            else{return strFalse;}
        }catch(Exception error){
            return null;//val puede tener nulo
        }
    }

    /**
     *
     * @param val puede ser "TRUE" o "FALSE" o "T" o "F", sirve tambien con minusculas
     * @return TRUE o FALSE un valor booleano
     */
    public static Boolean trueFalse(String val){
        try{
            val=val.toUpperCase();
            if(val.equals(strTrue)||val.equals("T")){return true;
            }else if(val.equals(strFalse)||val.equals("F")){return false;
            }else{return null;}
        }catch(Exception error){
            return null;//val puede tener nulo
        }
    }
    public static String asciiString(int ascii){
        return Character.valueOf((char)ascii).toString();
    }
    public static int stringAscii(String car){
        return car.codePointAt(0);
    }


    /**
     *
     * @param cad el formato de la var cad es: "uno,dos,tres,cuatro,cinco,seis"
     * @param separador el separador puede ser varios caracteres
     * @param numcampo inicia desde uno, no desde cero, el conteo de campos
     * @return retorna el elemento de cad segun indique el numcampo
     */
    public static String darCampo(String cad,String separador,int numcampo){
        cad+=separador;//esto se hace para q se pueda buscar el ultimo elemento
        if(numcampo<1){return null;}

        int f,p;//posiciones: fin puntero
        p=0;
        for(int n=1;n<numcampo;n++){

            p=cad.indexOf(separador, p)+separador.length();
            if(p==0){return null;}
            //el separador.length() un poco dificil de entender, es para q se abusque aprtir del indice dado
            //y para q no se retorne despues el caracter separador
        }
        f=cad.indexOf(separador, p);
        if(f==-1){return null;}
        return cad.substring(p, f);
    }

    protected static String separador=" | ";
    protected static String regSeparador="(\\ \\|\\ )";

    public static String getSeparador(){return separador;}

    public static String toma1(String cadena){
        //si cad="123 | abc" retorna "123"
        String [] aux=cadena.split(regSeparador);
        if(aux.length>=1){return aux[0];}
        else{return null;}
    }
    public static String toma2(String cadena){
        //si cad="123 | abc" retorna "abc"
        String [] aux=cadena.split(regSeparador);
        if(aux.length>=2){return aux[1];}
        else{return null;}
    }
    public static String toma3(String cadena){
        //si cad="123 | abc | ert" retorna "ert"
        String [] aux=cadena.split(regSeparador);
        if(aux.length>=3){return aux[2];}
        else{return null;}
    }

    /**
     *
     * @param cadena
     * @return Retorna la extensión del nombre de un archivo, si no tiene retorna null
     */
    public static String getExtension(String cadena){
        String [] aux = cadena.split("\\.");
        if(aux.length>1){return aux[aux.length -1];}
        else{return null;}
    }

    public static String guion="-";

    public static String une(String ... cadenas){
        String cad="";
        for(int i=0;i<cadenas.length;i++){
            if(esNulo(cadenas[i])){cadenas[i]=guion;}//se pone un guión en caso de que sea nulo o vacio
            if(i==0){cad=cadenas[0];}
            else{cad+=separador+cadenas[i];}
        }
        return cad;
    }

    /**
     *
     * @param cadena
     * @return elimina los espacios dobles y los de los extremos, y elimina todas las comillas simples para que no de error
     */
    public static String quitarEspacios(Object cadena){
        if(cadena==null){return "";}
        String var=cadena.toString();
        while(var.contains("  ")){
            var=var.replaceAll("  ", " ");}
        //Para que no de problemas al guardar datos por las comillas
        var=var.replaceAll("'", "");
        var=var.replaceAll("\"", "");
        return var.trim();
    }

    /**
     *
     * @param cadena
     * @return elimina absolutamente todo caracter que sea espacio
     */
    public static String quitarTodosLosEspacios(String cadena){
        return cadena.replaceAll(" ", "");
    }

    /**
     * Si cadena="123456" y longitud=4 return="1234"
     * @param cadena
     * @param longitud
     * @return
     */
    public static String cortarCadena(Object cadena, int longitud){
        if(cadena==null){return "";}
        int longitudreal=cadena.toString().length();
        if(longitudreal<longitud){
            return cadena.toString();
        }else{
            return cadena.toString().substring(0, longitud);
        }
    }

    public static String saltoLinea(int veces) { return repetirCadena("\n" + asciiString(13), veces); }

    public static String repetirCadena(String cadena,int veces){
        int i;
        String cad="";
        for(i=1;i<=veces;i++){
            cad=cad+cadena;
        }
        return cad;
    }

    /**
     * Este método permite verificar si una cédula de identidad es verdadera
     * retorna true si es válida, caso contrario retorna false.
     * @param numero Cédula de Identidad Ecuatoriana de 10 digitos. o RUC o pasaporte
     * @param tipo 1 verifica solo cédula, 2 solo ruc, 3 cédula o ruc, 4 pasaporte
     * @return Si es verdadera true, si es falsa false
     */
    public static boolean validaCedulaORuc(String numero, int tipo){
        //si es pasaporte solo validar que no este vacio, en tipo enviar cedula ruc o pasaporte
        if (tipo==4 && !esNulo(numero)){ return true; }

        //si su longitud no es 10 o 13, o si no es un número entero (solo números) esta mal
        if (!(numero.length() == 10 || numero.length() == 13) || !Fun.esEnteroLargo(numero)) { return false;}

        int suma = 0;
        int residuo = 0;
        boolean privada = false;
        boolean publica = false;
        boolean natural = false;
        int modulo = 11;

        int provincia = Integer.parseInt(numero.substring(0, 1)+numero.substring(1, 2)); // obtiene los dos primero dígitos
        if (provincia < 0 || provincia > NUMERO_DE_PROVINCIAS ) { return false; } // si el numero de provincia esta mal, retorna false

        /* Aqui almacenamos los digitos de la cedula en variables. */
        int d1 = Integer.parseInt(numero.substring(0,1));
        int d2 = Integer.parseInt(numero.substring(1,2));
        int d3 = Integer.parseInt(numero.substring(2,3));
        int d4 = Integer.parseInt(numero.substring(3,4));
        int d5 = Integer.parseInt(numero.substring(4,5));
        int d6 = Integer.parseInt(numero.substring(5,6));
        int d7 = Integer.parseInt(numero.substring(6,7));
        int d8 = Integer.parseInt(numero.substring(7,8));
        int d9 = Integer.parseInt(numero.substring(8,9));
        int d10 = Integer.parseInt(numero.substring(9,10));

        /* El tercer digito es: */
        /* 9 para sociedades privadas y extranjeros */
        /* 6 para sociedades publicas */
        /* menor que 6 (0,1,2,3,4,5) para personas naturales */

        //El tercer dígito ingresado es inválido;
        if (d3==7 || d3==8){ return false; }

        int p1=0, p2=0, p3=0, p4=0, p5=0, p6=0, p7=0, p8=0, p9=0;
        /* Solo para personas naturales (modulo 10) */
        if (d3 < 6){
            natural = true;
            p1 = d1 * 2; if (p1 >= 10) p1 -= 9;
            p2 = d2 * 1; if (p2 >= 10) p2 -= 9;
            p3 = d3 * 2; if (p3 >= 10) p3 -= 9;
            p4 = d4 * 1; if (p4 >= 10) p4 -= 9;
            p5 = d5 * 2; if (p5 >= 10) p5 -= 9;
            p6 = d6 * 1; if (p6 >= 10) p6 -= 9;
            p7 = d7 * 2; if (p7 >= 10) p7 -= 9;
            p8 = d8 * 1; if (p8 >= 10) p8 -= 9;
            p9 = d9 * 2; if (p9 >= 10) p9 -= 9;
            modulo = 10;
        } else if(d3 == 6) {
            /* Solo para sociedades publicas (modulo 11) */
            /* Aqui el digito verficador esta en la posicion 9, en las otras 2 en la pos. 10 */
            publica = true;
            p1 = d1 * 3;
            p2 = d2 * 2;
            p3 = d3 * 7;
            p4 = d4 * 6;
            p5 = d5 * 5;
            p6 = d6 * 4;
            p7 = d7 * 3;
            p8 = d8 * 2;
            p9 = 0;
        } else if(d3 == 9) {
            /* Solo para entidades privadas (modulo 11) */
            privada = true;
            p1 = d1 * 4;
            p2 = d2 * 3;
            p3 = d3 * 2;
            p4 = d4 * 7;
            p5 = d5 * 6;
            p6 = d6 * 5;
            p7 = d7 * 4;
            p8 = d8 * 3;
            p9 = d9 * 2;
        }

        suma = p1 + p2 + p3 + p4 + p5 + p6 + p7 + p8 + p9;
        residuo = suma % modulo;

        /* Si residuo=0, dig.ver.=0, caso contrario 10 - residuo*/
        int digitoVerificador = residuo==0 ? 0: modulo - residuo;

        /* ahora comparamos el elemento de la posicion 10 con el dig. ver.*/
        if (publica==true){
            if (digitoVerificador != d9){
                //El ruc de la empresa del sector público es incorrecto
                return false;
            }
            /* El ruc de las empresas del sector publico terminan con 0001*/
            if ( !"0001".equals(numero.substring(9, 13)) ){
                //El ruc de la empresa del sector público debe terminar con 0001
                return false;
            }
        } else if(privada == true) {
            if (digitoVerificador != d10){
                //El ruc de la empresa del sector privado es incorrecto.');
                return false;
            }
            if ( !"001".equals(numero.substring(10, 13)) ){
                //El ruc de la empresa del sector privado debe terminar con 001
                return false;
            }
        } else if(natural == true) {
            if (digitoVerificador != d10){
                // El número de cédula de la persona natural es incorrecto
                return false;
            }
            if (numero.length() >10 && !"001".equals(numero.substring(10, 13)) ){
                // El ruc de la persona natural debe terminar con 001
                return false;
            }
        }
        return true;
    }

    public static boolean validaCedulaORuc3(String Numero){
        //si su longitud no es 10 o 13, esta mal
        if(!(Numero.length()==10||Numero.length()==13)){
            return false;}

        if(!Fun.esEnteroLargo(Numero)){
            return false;}
        int suma=0;
        int aUx=0;

        for(int i=0;i<9;i++){
            if(i%2==0){aUx=2*Integer.parseInt(Numero.substring(i,i+1));}
            if(i%2!=0){aUx=Integer.parseInt(Numero.substring(i,i+1));}
            if(aUx>9){aUx=aUx-9;}
            suma=suma+aUx;
        }
        suma=10-suma+10*((int)(suma/10));
        if(suma==10){suma=0;}

        if(suma==Integer.parseInt(Numero.substring(9,10))){
            return true;
        }else{
            return false;
        }
    }

    public static final int NUMERO_DE_PROVINCIAS = 28;//22;
    /**
     * Este método permite verificar si una cédula de identidad es verdadera
     * retorna true si es válida, caso contrario retorna false.
     * @param cedula Cédula de Identidad Ecuatoriana de 10 digitos. o RUC
     * @param tipo 1 verifica solo cédula, 2 solo ruc, 3 cédula o ruc
     * @return Si es verdadera true, si es falsa false
     */
    public static boolean validaCedulaORuc2(String cedula, int tipo) {

        if(tipo==3) //verifica que tenga 10 o 13 dígitos y que contenga solo valores numéricos
            if (!((cedula.length() == 10 && cedula.matches("^[0-9]{10}$")) || ((cedula.length() == 13 && cedula.matches("^[0-9]{13}$"))))) {return false;}

        if(tipo==1) //CI. Verifica que tenga 10 dígitos y que contenga solo valores numéricos
            if (!(cedula.length() == 10 && cedula.matches("^[0-9]{10}$"))) {return false;}

        if(tipo==2) //RUC. Verifica que tenga 13 dígitos y que contenga solo valores numéricos
            if (!(cedula.length() == 13 && cedula.matches("^[0-9]{13}$"))) {return false;}

        //verifica que los dos primeros dígitos correspondan a un valor entre 1 y NUMERO_DE_PROVINCIAS
        int prov = Integer.parseInt(cedula.substring(0, 2));

        if (!((prov > 0) && (prov <= NUMERO_DE_PROVINCIAS))) {
            return false;
        }

        //verifica que el último dígito de la cédula sea válido
        int[] d = new int[10];

        //Asignamos el string a un array
        for (int i = 0; i < d.length; i++) {
            d[i] = Integer.parseInt(cedula.charAt(i) + "");
        }

        int imp = 0;
        int par = 0;

        //sumamos los duplos de posición impar
        for (int i = 0; i < d.length; i += 2) {
            d[i] = ((d[i] * 2) > 9) ? ((d[i] * 2) - 9) : (d[i] * 2);
            imp += d[i];
        }

        //sumamos los digitos de posición par
        for (int i = 1; i < (d.length - 1); i += 2) {
            par += d[i];
        }

        //Sumamos los dos resultados
        int suma = imp + par;

        //Restamos de la decena superior
        int d10 = Integer.parseInt(String.valueOf(suma + 10).substring(0, 1) +
                "0") - suma;

        //Si es diez el décimo dígito es cero
        d10 = (d10 == 10) ? 0 : d10;

        //si el décimo dígito calculado es igual al digitado la cédula es correcta
        return d10 == d[9];
    }
    public static boolean validaAutorizacionSri(String numero){
        if(numero==null){return false;}
        //10 para los doc normales y 37 para los electrónicos
        if(numero.length()!=10 && numero.length()!=37 && numero.length()!=49){return false;}
        return Fun.esEnteroLargo(numero);
    }

    /**
     *
     * @param codigo puede ser el EAN13(13 digitos) o el UPC-A(12 digitos)
     * @return
     */
    public static boolean validarCodigoBarras(String codigo){
        if(codigo.length()!=12 && codigo.length()!=13) {return false;}
        if(!Fun.esEnteroLargo(codigo)){return false;}
        //si el codigo es de 13 cifras el 1º se multlipica por 1, si es de 12 cifras por 3
        //segun veo en varios codigos, si la longitud es par el 1º se multlipica por 3, y si es impar el 1º por 1
        codigo=ceros(codigo, 13);
        int total = 0;
        for(int i=0; i<codigo.length(); i++)//empieza por cero (par)
            if(i % 2 == 0) total+=Integer.parseInt(codigo.substring(i, i+1));//posicion par
            else total+=Integer.parseInt(codigo.substring(i, i+1))*3;//posicion impar
        return total % 10 == 0;
    }
    /**
     *
     * @param numero
     * @param cifras
     * @return
     */
    public static String ceros(Object numero, int cifras){
        //En caso de que el numero de cifras no abarque al numero porque
        //lo sobrepase, por ejemplo 4 cifras al numero 123456
        if( numero.toString().length() >= cifras){
            return numero.toString();
        }
        return repetirCadena("0", (cifras - numero.toString().length())) + numero.toString();
    }


    public static String egresoProducto(String Dato){
        try{
            String dato=desMezclar(desMezclar(desMezclar(Dato)));

            //1º Tomar de dos en dos los números en base 62 y pasar a decimal de 4 cifras
            //luego eliminar la primera cifra (es la randómica)
            String aux="";
            for(int i=0;i<dato.length();i=i+2){
                String grupo=dato.substring(i, i+2);
                grupo=ceros(deCod(grupo, 62), 4);
                grupo=grupo.substring(1, 4);

                aux+=grupo;
            }

            //2º Desmezclar
            aux=desMezclar(desMezclar(desMezclar(aux)));

            String aux2="";
            //3º tomar de tres en tres y al resultado dividir para 4 , obteniendo el ascii
            //luego transformar al ascii en el caracter
            for(int i=0;i<aux.length();i=i+3){
                String grupo=asciiString(Integer.parseInt(aux.substring(i, i+3))/4+10);
                aux2+=grupo;

            }
            //0123456789abcdefghi
            aux2=aux2.substring(10, aux2.length());

            return aux2;
        }catch(Exception e){
            //System.out.println("Error desencripta");
            return null;
        }
    }

    public static String ingresoProducto(String Dato){
        String dato="";
        for(int i=0;i<10;i++){dato+=simbolos[Fun.random(0, 61)];}
        dato+=Dato;

        String aux="";

        //1º pasar a ascii *4
        for(int i=0;i<dato.length();i++){
            String letra=dato.substring(i, i+1);
            String asci=ceros(Integer.toString((stringAscii(letra)-10)*4), 3);
            aux+=asci;
        }

        //2º mezclar
        aux=mezclar(mezclar(mezclar(aux)));

        String aux2="";
        //3º tomar grupos de tres en tres y sumar un randómico del 0 al 3 en el 1º dígito
        //el resultado no debe pasar de 3843 por eso pregunto si la 1º cifra es menor a 8 para que el randómico vaya de 0 a 3, si es 8 o 9 el randómico solo puede ser del 0 al 2
        for(int i=0;i<aux.length();i=i+3){
            String grupo=aux.substring(i, i+3);
            if(Integer.parseInt(grupo.substring(0, 1))<8) grupo= Fun.random(0, 3)+grupo;
            else grupo= Fun.random(0, 2)+grupo;

            aux2+=grupo;

        }

        aux="";
        //4º tomar los grupos de 4 en 4 y pasarlos a base 62
        for(int i=0;i<aux2.length();i=i+4){
            String grupo=aux2.substring(i, i+4);

            aux+=ceros(cod(grupo, 62), 2);
        }

        return mezclar(mezclar(mezclar(aux)));
    }

    private static String mezclar(String dato){
        int inf=0, sup=dato.length()-1;
        String aux="";
        while(inf<=sup){
            if(inf!=sup)
                aux+=dato.substring(sup, sup+1)+dato.substring(inf, inf+1);
            else
                aux+=dato.substring(sup, sup+1);
            inf++;sup--;
        }
        return aux;
    }

    private static String desMezclar(String dato){
        String cabeza="", cola="";
        for(int i=0;i<dato.length();i=i+2){
            cola=dato.substring(i, i+1)+cola;
            if(i<dato.length()-1) cabeza+=dato.substring(i+1, i+2);
        }
        return cabeza+cola;
    }

    //hasta base 62
    private static String [] simbolos={"0","1","2","3","4","5","6","7","8","9",
            "a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",
            "A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};


    private static String cod(String dato, int base){
        long num=Long.parseLong(dato);
        String val="";
        int res;
        long div=num;
        while(div>0){
            res=(int)(div-((long)div/base)*base);
            val=simbolos[res]+val;
            div=(div/base);
        }
        if(div!=0){val=simbolos[(int)div]+val;}
        return val;
    }

    private static int encuentra(String car){
        for(int j=0;j<simbolos.length;j++){
            if(car.equals(simbolos[j])){return j;}
        }
        return -1;
    }

    private static String deCod(String dato, int base){
        int exp=0;
        long res=0;
        for(int i=dato.length()-1;i>=0;i--){
            res+=encuentra(dato.substring(i, i+1))*(Math.pow(base, exp));
            exp++;
        }

        return Long.toString(res);
    }

}
