package com.satgy.embudi.general;

import com.satgy.embudi.exception.ExceptionResponse;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

public class Fun {

    /**
     *
     * @param errorTitle short text
     * @param errorMessage can be long
     * @param status HttpStatus.BAD_REQUEST, HttpStatus.CREATED, etc.
     * @return a structure kind ResponseEntity
     */
    public static ResponseEntity getResponse(String errorTitle, String errorMessage, HttpStatusCode status){
        ExceptionResponse exceptionResponse = new ExceptionResponse(new java.util.Date(), errorTitle, errorMessage);
        return new ResponseEntity(exceptionResponse, status);
    }

    public static boolean abrirWeb(String url){
        try {
            return abrirWeb(new java.net.URI(url));
        } catch (java.net.URISyntaxException ex) {
            Logger.getLogger(Fun.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean abrirWeb(java.net.URL url) {
        try {
            return abrirWeb(url.toURI());
        } catch (java.net.URISyntaxException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean abrirWeb(java.net.URI uri) {
        java.awt.Desktop desktop = java.awt.Desktop.isDesktopSupported() ? java.awt.Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(java.awt.Desktop.Action.BROWSE)) {
            try {
                desktop.browse(uri);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean sonIguales(Object dato1, Object dato2){
        if(Str.esNulo(dato1) && Str.esNulo(dato2)){return true;} //si ambos son nulo entonces son iguales
        if(Str.esNulo(dato1)){return false;} //si solo uno de los dos es nulo y no el otro no son iguales
        if(Str.esNulo(dato2)){return false;}

        if(dato1.equals(dato2)){return true;}

        if (dato1 instanceof Date && dato2 instanceof Date){
            try {
                Date fecha1=DateFormat.getDateInstance().parse(dato1.toString());
                Date fecha2=DateFormat.getDateInstance().parse(dato2.toString());
                return fecha1.equals(fecha2);
            } catch (ParseException ex) {return false;}}

        if(esNumero(dato1) && esNumero(dato2)){
            try{
                double num1=Double.parseDouble(dato1.toString());
                double num2=Double.parseDouble(dato2.toString());
                return num1==num2;
            }catch (Exception e){return false;}}

        return false;
    }

    public static int random(int minimo, int maximo){
        int x=(int)( Math.random() * (maximo-minimo+1) + minimo);
        if(x>maximo){x=maximo;}
        if(x<minimo){x=minimo;}
        return x;
    }

    public static double random(double minimo, double maximo){
        double x=(double)( Math.random() * (maximo-minimo+1) + minimo);
        if(x>maximo){x=maximo;}
        if(x<minimo){x=minimo;}
        return x;
    }

    /**
     * Elimina los elementos repetidos del ArrayList
     * @param elementos
     * @return retorna sin elementos duplicados
     */
    public static ArrayList eliminarRepetidos(ArrayList elementos){
        java.util.Set s = new java.util.LinkedHashSet (elementos);
        elementos.clear();
        elementos.addAll(s);
        return elementos;
    }

    /**
     *
     * @param total la suma de elementos de retorno debe ser igual a total
     * @param decimales la cantidad de decimales máximos que deben tener cada elemento del ArrayList de retorno
     * @param valores, un arreglo que definirá los valores de cada elemento del ArrayList de retorno (respuesta), son proporcionales
     * @return
     */
    public static ArrayList<Double> segmentarNumero(double total, int decimales, ArrayList<Double> valores) {
        // ejemplo total = 10, decimales = 0, valores = { 20, 30, 50 }
        // retorno = { 2, 3, 5 } que sumados es 10, como el total
        ArrayList<Double> retorno = new ArrayList<>();
        double sumaValores = 0.0;

        // Calculamos la suma de los valores
        for (Double valor : valores) {
            sumaValores += valor;
        }

        // Segmentamos el número proporcionalmente
        BigDecimal sumaRetorno = BigDecimal.ZERO;
        BigDecimal totalBigDecimal = BigDecimal.valueOf(total);

        for (int i = 0; i < valores.size(); i++) {
            double valor = valores.get(i);
            BigDecimal parte = BigDecimal.valueOf(valor).multiply(totalBigDecimal).divide(BigDecimal.valueOf(sumaValores), decimales, java.math.RoundingMode.HALF_UP);
            parte = parte.setScale(decimales, java.math.RoundingMode.HALF_UP);
            retorno.add(parte.doubleValue());
            sumaRetorno = sumaRetorno.add(parte);
        }

        // Ajustamos el último elemento para que la suma sea igual a total
        BigDecimal ajuste = totalBigDecimal.subtract(sumaRetorno);
        BigDecimal ultimoElemento = BigDecimal.valueOf(retorno.get(retorno.size() - 1));
        retorno.set(retorno.size() - 1, ultimoElemento.add(ajuste).doubleValue());

        return retorno;
    }

    /**
     * Usado para los doc por cobrar y por pagar, para calcular el valor de cada dividendo
     * Sirve para el interés simple
     * @param total
     * @param partes
     * @param decimales
     * @param minimo
     * @param primero
     * @return
     */
    public static double[] segmentar(double total, int partes, int decimales, double minimo, double primero){
        if(partes==1){return new double[]{total};}
        double vec[]=new double[partes];
        double valor= Fun.redondear(total/partes, decimales);

        //hacer esto para q no existen errores por los decimales, en algunos dividendos se sumara o restara un centavo
        double aux= Fun.redondear((total-(valor*partes))*Math.pow(10, decimales),0);
        //si por ejemplo aux=-2, quiere decir que en dos partes (cuotas) hay q restar un centavo
        //si fuera aux=4, en 4 partes (cuotas), habria q sumar un centavo

        int i=0;


        if(primero>0){
            // si la 1º cuota puede tener un valor menor al mínimo, poner de una vez, un valor minimo en la 1º
            vec[0]= Fun.redondear(primero, decimales);
            i=1;
            valor= Fun.redondear((total-primero)/(partes-1), decimales);
            aux=-1+ Fun.redondear(((total-primero)-(valor*(partes-1)))*100, 0);
        }else if(valor<=minimo){
            // si la 1º cuota puede tener un valor menor al mínimo, poner de una vez, un valor minimo en la 1º
            vec[0]= Fun.redondear(minimo, decimales);
            i=1;
            valor= Fun.redondear((total-minimo)/(partes-1), decimales);
            aux=-1+ Fun.redondear(((total-minimo)-(valor*(partes-1)))*100, 0);
        }

        int signo=1;
        if(aux<0){signo=-1;aux=-aux;}//tomar el signo, y tener el valor absoluto de aux
        //System.out.println("aux:"+aux+" signo:"+signo);
        double aux2=Math.pow(0.1, decimales);//si decimales=2, tomara un valor de 0.01
        for(i=i+0;i<partes;i++){
            if(i>=(int)(aux)){aux2=0;}
            vec[i]= Fun.redondear(valor+aux2*signo, decimales);
        }
        return vec;
    }

    /**
     *
     * @param proporciones si entrego 10, 20, 30, 40  que son las proporciones <br>
     * @param valor 10 , cuanto debe sumar lo devuelto <br>
     * @param decimales 0 <br>
     * @return me devuelve 1, 2, 3, 4
     */
    public static ArrayList segmentarPorcentajes(ArrayList proporciones, double valor, int decimales){
        ArrayList datos=new ArrayList();
        double suma=0;
        for(int i=0;i<proporciones.size();i++){
            suma+=Double.parseDouble(proporciones.get(i).toString());}
        double sumaParcial=0, numero;
        for(int i=0; i<proporciones.size()-1; i++){//se cuentas todos los elementos menos 1 para que el ultima se calcule con la diferencia de la suma de los anteriores para evitar errores al redondear
            numero= Fun.redondear(valor*Double.parseDouble(proporciones.get(i).toString())/suma, decimales);
            datos.add(numero);
            sumaParcial+=numero;
        }
        datos.add(valor-sumaParcial);
        return datos;
    }
    /**
     * Usado para los doc por cobrar y por pagar, para calcular la fecha de pago o cobro de cada dividendo.
     * Si primera cuota es true, se cuenta desde la fecha inicio, si es false no.
     * @param pinicio
     * @param pfin
     * @param partes
     * @return
     */
    public static Date[] segmentar(Date pinicio, Date pfin, int partes){
        if(partes==1) { return new Date[]{pfin}; }
        Date vec[] = new Date[partes];
        Date inicio = Dat.soloFecha(pinicio);
        Date fin= Dat.soloFecha(pfin);

        Date div=inicio;
        long add=fin.getTime()-inicio.getTime();//diferencia en milisegundos entre las fechas
        add=add/(partes-1);//add es la variable q se utilizara para aumentar cada fecha de cada dividendo
        for(int i=0;i<partes;i++){
            vec[i]=new Date(div.getTime());//no pongo vec[i]=div xq se queda asignado el utimo valor de div para cada posicion de el arreglo
            div.setTime(div.getTime()+add);//en la division, se pierden algunos decimales y por eso a veces queda un milisegundo o varios milisegundos de error y se retrasa un día la fecha final
        }
        vec[partes-1]=new Date(fin.getTime());//en la ultima a veces salia un día menos, por eso de una vez le asigno el valor final
        return vec;
    }
    public static Date[] segmentar(Date pinicio, Date pfin, int partes, boolean primeracuota){
        if(partes==1){return new Date[]{pfin};}
        Date vec[]=new Date[partes];
        Date inicio= Dat.soloFecha(pinicio);
        Date fin= Dat.soloFecha(pfin);

        Date div=inicio;
        long add=fin.getTime()-inicio.getTime();//diferencia en milisegundos entre las fechas

        //add es la variable q se utilizara para aumentar cada fecha de cada dividendo
        if(primeracuota){add=add/(partes-1);}
        else{add=add/(partes);div.setTime(div.getTime()+add);}

        for(int i=0;i<partes;i++){
            vec[i]=new Date(div.getTime());//no pongo vec[i]=div xq se queda asignado el utimo valor de div para cada posicion de el arreglo
            div.setTime(div.getTime()+add);//en la division, se pierden algunos decimales y por eso a veces queda un milisegundo o varios milisegundos de error y se retrasa un día la fecha final
        }
        vec[partes-1]=new Date(fin.getTime());//en la ultima a veces salia un día menos, por eso de una vez le asigno el valor final
        return vec;
    }

    public static String validaMoneda(Object num, int decimales){return validaNumero(num, decimales, true);}

    public static String validaNumero(Object num, int decimales, boolean obligatorioDecimal){
        String aux=num.toString();
        aux=aux.replaceAll(" ", "");//quitar todos los espacios
        if(!Fun.esCantidad(aux, true)){
            return Fun.darDecimal(0, decimales);
        }else if(obligatorioDecimal){
            return Fun.darDecimal(aux, decimales);
        }else{
            return noDarDecimal(num, decimales);
        }
    }

    /**
     * SE USA PARA EL LOST FOCUS EN UN VALOR ENTERO POSITIVO
     * @param num
     * @return retorna 0 si no es entero positivo
     */
    public static String validaEnteroPositivo(Object num, String enCasoError){
        String aux=num.toString();
        aux=aux.replaceAll(" ","");//quitar todos los espacios
        if(!Fun.esEnteroLargo(aux)){
            return enCasoError;
        }else{
            return aux;
        }
    }

    public static String inv_str(String cad){
        byte aux[] = new byte[cad.length()];
        for(int i=0;i<cad.length();i++){
            aux[i]=Byte.parseByte(Integer.toString(Integer.parseInt(Byte.toString(cad.substring(i, i+1).getBytes()[0]))+80));
        }
        return new String(aux).replace('\0',' ').trim();
    }
    public static Double objectToDouble(Object valor){
        if(valor==null){return null;}
        Object obj=valor;
        if(valor instanceof javax.swing.JTextField){obj=((javax.swing.JTextField)valor).getText();}
        if(!Fun.esNumero(obj)){return null;}
        return Double.parseDouble(obj.toString());
    }

    public static boolean esNumero(Object numero){
        try{
            Double.parseDouble(numero.toString());
        }catch (Exception e){
            return false;
        }
        return true;
    }
    /**
     * Determina si el parámetro es un número entero o double, y debe ser positivo
     * @param numero
     * @param incluyeElCero true si debe inlcuir el cero como valido
     * @return
     */
    public static boolean esCantidad(Object numero, boolean incluyeElCero){
        try{
            if(incluyeElCero){if(Double.parseDouble(numero.toString())>=0){return true;}}
            else{if(Double.parseDouble(numero.toString())>0){return true;}}
        }
        catch (Exception e){
            return false;
        }
        return false;
    }

    /**
     * Incluye el cero <br>
     * @param numero
     * @return
     */
    public static boolean esCantidad(Object numero){
        try{
            if(Double.parseDouble(numero.toString())>=0){return true;}
        }
        catch (Exception e){
            return false;
        }
        return false;
    }

    /**
     * Determina si un número es un entero positivo
     * @param numero
     * @return
     */
    public static boolean esEnteroPositivo(Object numero){
        try{
            if(Long.parseLong(numero.toString())>=0){return true;}
        } catch (Exception e){ return false; }
        return false;
    }
    /**
     *
     * @param numero maximo en entero largo: 9223372036854775807, 19 digitos <br>
     * @return Solo enteros positivos
     */
    public static boolean esEnteroLargo(Object numero){
        String num=numero.toString();
        if(num.length()==0){return false;}
        for(int i=0;i<num.length();i++){
            if(!esEnteroPositivo(num.substring(i, i+1))){return false;}
        }
        return true;
    }

    /**
     *
     * @param valor valor booleano
     * @return devuelve TRUE o FALSE
     */
    public static String booleanoToString(Boolean valor){
        if(valor){return "TRUE";}
        else{return "FALSE";}
    }

    /**
     * Se utiliza para asignar con valor de tipo moneda a un campo de texto por ejemplo si tengo una variable double con valor de (12.3), se asignara por ejemplo al txt "12.30"
     * @param num
     * @param decimales
     * @return
     */
    public static String darDecimal(Object num, int decimales){
        if(!esNumero(num)){return "0."+Str.repetirCadena("0", decimales);}
        String numero=Double.toString(redondear(num, decimales));
        String separadorDecimal=".";
        if(Double.toString((double)(0.5)).contains(".")){separadorDecimal = ".";}

        if(!numero.contains(separadorDecimal)){ //Si es que no tiene decimales
            return numero + separadorDecimal + Str.repetirCadena("0", decimales);
        }

        String parteEntera;
        String parteDecimal;
        //parteentera = Mid(CStr(Numeral), 1, InStr(CStr(Numeral), SeparadorDecimal) - 1)
        parteEntera=numero.substring(0, numero.indexOf(separadorDecimal) );
        //la parte decimal queda como numero entero ej: 32,034 queda "034"

        parteDecimal=numero.substring(numero.indexOf(separadorDecimal)+1, numero.length());
        if(parteEntera.equals("")){parteEntera="0";}

        if(decimales>parteDecimal.length()){
            return parteEntera + separadorDecimal + parteDecimal + Str.repetirCadena("0", (decimales - parteDecimal.length()));
        }else{
            return parteEntera + separadorDecimal + parteDecimal;
        }
    }

    /**
     * Retorna un numero en formato de solo entero si su parte decimal es cero <br>
     * si tengo una variable double con valor de (12.0), retorna '12', <br>
     * si tiene parte decimal se retorna redondeado con la precision del valor maximoDecimales <br>
     * @param num
     * @param maximoDecimales
     * @return
     */
    public static String noDarDecimal(Object num, int maximoDecimales){
        if(!esNumero(num)){return "0";}
        String numero=num.toString();
        String separadorDecimal=".";
        if(Double.toString((double)(0.5)).contains(".")){separadorDecimal = ".";}

        if(!numero.contains(separadorDecimal)){ //Si es que no tiene decimales
            return numero;}

        numero=Double.toString(redondear(num, maximoDecimales));

        String parteEntera;
        String parteDecimal;
        //parteentera = Mid(CStr(Numeral), 1, InStr(CStr(Numeral), SeparadorDecimal) - 1)
        parteEntera=numero.substring(0, numero.indexOf(separadorDecimal) );
        //la parte decimal queda como numero entero ej: 32,034 queda "034"

        parteDecimal=numero.substring(numero.indexOf(separadorDecimal)+1, numero.length());
        if(parteEntera.equals("")){parteEntera="0";}

        if(Double.parseDouble(parteDecimal)==0){return parteEntera;}
        else{return numero;}
    }

    public static double redondear(Object num, int decimales){
        if(!num.toString().contains(".")){return Double.parseDouble(num.toString());}//Si no hay coma entonces se retona el mismo numero entero

        //Elevar al numero a la potencia 10^Decimales y tomar su parte decimal
        double aux=Math.pow(10, decimales);
        double numero=Double.parseDouble(num.toString());
        double elevado=numero*aux;

        //Tomar la parte decimal
        double parteDecimal=redondearMath(elevado-Math.floor(elevado), 8);//(int)elevado;
        if(parteDecimal<0.5){return redondearMath((elevado-parteDecimal)/aux, 8);}
        else{return redondearMath((elevado+1-parteDecimal)/aux, 8);}
    }
    private static double redondearMath(Object num, int decimales){
        double numero=Double.parseDouble(num.toString());
        int aux=(int)Math.pow(10, decimales);
        return ((double)Math.round(numero*aux))/(double)aux;
    }
    public static String darFormatoNumeroDocumento(int uno, int dos, long tres){
        return Str.ceros(uno, 3)+"-"+Str.ceros(dos, 3)+"-"+Str.ceros(tres, 9);
    }
    public static String darFormatoNumeroDocumento(int uno, long dos){
        return Str.ceros(uno, 3)+"-"+Str.ceros(dos, 9);
    }
}
