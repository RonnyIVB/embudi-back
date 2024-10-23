package com.satgy.embudi.general;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Dat {

    final static long MilisegundosPorDia = 24 * 60 * 60 * 1000;
    final static long MilisegundosPorMinuto = 60 * 1000;

    public static String getEdad(Date fechaNacimiento) {
        if (fechaNacimiento != null) {
            Calendar c = new java.util.GregorianCalendar();
            c.setTime(fechaNacimiento);
            return Integer.toString(getEdad(c));
        }
        return null;
    }

    public static int getEdad(Calendar fechaNac) {
        Calendar today = Calendar.getInstance();
        int diffYear = today.get(Calendar.YEAR) - fechaNac.get(Calendar.YEAR);
        int diffMonth = today.get(Calendar.MONTH) - fechaNac.get(Calendar.MONTH);
        int diffDay = today.get(Calendar.DAY_OF_MONTH) - fechaNac.get(Calendar.DAY_OF_MONTH);
        // Si está en ese año pero todavía no los ha cumplido
        if (diffMonth < 0 || (diffMonth == 0 && diffDay < 0)) {
            diffYear = diffYear - 1; // no aparecían los dos guiones del postincremento <img class="emoji" draggable="false" alt="??" src="http://s.w.org/images/core/emoji/72x72/1f610.png">
        }
        return diffYear;
    }


    public static void validarFechaCaducidad(javax.swing.JTable tabla, int columnaFecha, String formato, java.awt.Component ventana){
        for(int i=0;i<tabla.getRowCount();i++){
            if(!Str.esNulo(tabla.getValueAt(i, columnaFecha))){
                String fecha=tabla.getValueAt(i, columnaFecha).toString();
                if(validarFecha(fecha, "MM/yyyy")){fecha= Dat.toCadena(Dat.ultimoDiaDeMes(Dat.toFecha(fecha, "MM/yyyy")), formato);}
                if(validarFecha(fecha, "MM-yyyy")){fecha= Dat.toCadena(Dat.ultimoDiaDeMes(Dat.toFecha(fecha, "MM-yyyy")), formato);}
                if(validarFecha(fecha, "yyyy/MM")){fecha= Dat.toCadena(Dat.ultimoDiaDeMes(Dat.toFecha(fecha, "yyyy/MM")), formato);}
                if(validarFecha(fecha, "yyyy-MM")){fecha= Dat.toCadena(Dat.ultimoDiaDeMes(Dat.toFecha(fecha, "yyyy-MM")), formato);}
                tabla.setValueAt(fecha, i, columnaFecha);
                //System.out.println(fecha);
                if(!validarFecha(fecha, formato)){
                    tabla.setValueAt("", i, columnaFecha);
                    javax.swing.JOptionPane.showMessageDialog(ventana, "Digite correctamente la fecha", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    /**
     *
     * @param f1 La fecha 1, debe ser la menor de preferencia <br>
     * @param f2 La fecha 2, debe ser la mayor de preferencia <br>
     * @param soloPositivo si es true me devolvera solo valores positivos, si es false me devolvera negativo si f1 es mayor a f2 <br>
     * @return
     */
    public static long diferenciaDias(Date f1, Date f2, boolean soloPositivo){
        f1=primeraHora(f1);
        f2=primeraHora(f2);
        long diferencia = ( f2.getTime() - f1.getTime() ) / MilisegundosPorDia;
        if(soloPositivo && diferencia<0){diferencia=-diferencia;}
        return diferencia;
    }

    /**
     *
     * @param f1 La fecha 1, debe ser la menor de preferencia <br>
     * @param f2 La fecha 2, debe ser la mayor de preferencia <br>
     * @param soloPositivo si es true me devolvera solo valores positivos, si es false me devolvera negativo si f1 es mayor a f2 <br>
     * @return
     */
    public static long diferenciaMinutos(Date f1, Date f2, boolean soloPositivo){
        long diferencia = ( f2.getTime() - f1.getTime() ) / MilisegundosPorMinuto;
        if(soloPositivo && diferencia<0){diferencia=-diferencia;}
        return diferencia;
    }

    /**
     *
     * @param ano, queda el mismo año introducido
     * @param mes, enero es el mes 1
     * @param dia, empieza desde 1
     * @return la fecha en tipo Date
     */
    public static Date crearFecha(int ano, int mes, int dia){
        Calendar d= Calendar.getInstance();
        d.set(ano, mes-1, dia, 0, 0, 0);
        d.set(Calendar.MILLISECOND, 0);//Fue necesario porque se aumentan los milisegundos del momento en que se toma la fecha
        return d.getTime();
    }


    /**
     * Devuelve el nombre del mes, enero es 0, diciembre es 11
     * @param mes: el uno es enero y diciembre es el 11, si esta afuera se devuelve null
     * @return
     */
    public static String darNombreDeMes(int mes){
        if(mes==1) return "Enero";
        if(mes==2) return "Febrero";
        if(mes==3) return "Marzo";
        if(mes==4) return "Abril";
        if(mes==5) return "Mayo";
        if(mes==6) return "Junio";
        if(mes==7) return "Julio";
        if(mes==8) return "Agosto";
        if(mes==9) return "Septiembre";
        if(mes==10) return "Octubre";
        if(mes==11) return "Noviembre";
        if(mes==12) return "Diciembre";
        return null;
    }
    public static javax.swing.DefaultComboBoxModel comboMeses(){
        javax.swing.DefaultComboBoxModel aux=new javax.swing.DefaultComboBoxModel();
        for(int i=1;i<13;i++){aux.addElement(darNombreDeMes(i));}
        return aux;
    }

    /**
     * Enero es 1, Diciembre es 12 <br>
     * @param nombreDeMes: Enero, Febrero, etc
     * @return
     */
    public static int darNumeroMes(String nombreDeMes){
        for(int i=1;i<13;i++){
            if(nombreDeMes.equals(darNombreDeMes(i))){return i;}
        }
        return -1;
    }

    /**
     * Devuelve el nombre del mes, enero es 0, diciembre es 11
     * @param dia: el uno es enero y diciembre es el 11, si esta afuera se devuelve null
     * @return
     */
    public static String darNombreDeDia(int dia){
        if(dia==1) return "Lunes";
        if(dia==2) return "Martes";
        if(dia==3) return "Miércoles";
        if(dia==4) return "Jueves";
        if(dia==5) return "Viernes";
        if(dia==6) return "Sábado";
        if(dia==7) return "Domingo";
        return null;
    }
    public static javax.swing.DefaultComboBoxModel comboDias(){
        javax.swing.DefaultComboBoxModel aux=new javax.swing.DefaultComboBoxModel();
        for(int i=1; i<8; i++){aux.addElement(darNombreDeDia(i));}
        return aux;
    }

    public static String darSoloFecha(java.sql.Timestamp tiempo, String formato){
        Date aux=new Date(tiempo.getTime());
        SimpleDateFormat formato_fecha = new SimpleDateFormat(formato);
        return formato_fecha.format(aux);
    }
    public static String darSoloFecha(java.util.Date fecha, String formato){
        SimpleDateFormat formato_fecha = new SimpleDateFormat(formato);
        return formato_fecha.format(fecha);
    }
    public static Date primeraHora(Date fecha){
        //se utliza para cuando se necesite hacer una búsqueda enter rangos de fechas de tipo timestamp,
        //emtonces datdesde tiene la fecha actual, al 1º segundo del día
        return ponerHora(fecha,"0:0:0");

    }
    public static Date ultimaHora(Date fecha){
        //se utliza para cuando se necesite hacer una búsqueda enter rangos de fechas de tipo timestamp,
        //emtonces dathasta tiene la fecha actual, al último segundo del día
        return ponerHora(fecha,"23:59:59");
    }
    public static Date ponerHora(Date fecha, String hora){
        //tengo una fecha que contiene sus horas minutos y segundos, y sin importar q datos tenia
        //se ponen la hora minutos y segundos del parametro hora, en el formato "hh:mm:ss"
        //Date aux=new Date(fecha.getTime());//hago esto porque hay problemas con un formato de fecha en el q no existen los minutos horas y segundos
        String vec[]=hora.split(":");
        Calendar d= Calendar.getInstance();
        d.setTime(fecha);
        d.set(d.get(Calendar.YEAR), d.get(Calendar.MONTH), d.get(Calendar.DATE), Integer.parseInt(vec[0]), Integer.parseInt(vec[1]), Integer.parseInt(vec[2]));
        d.set(Calendar.MILLISECOND, 0);//Fue necesario porque se aumentan los milisegundos del momento en que se toma la fecha
        //aux.setHours(Integer.parseInt(vec[0]));
        //aux.setMinutes(Integer.parseInt(vec[1]));
        //aux.setSeconds(Integer.parseInt(vec[2]));
        return d.getTime();
    }
    public static int darDiaSemana(Date fecha){
        //el formato de Calendar.DAY_OF_WEEK es Domingo=1 Sabado=7
        //debemos dar el formato Lunes=1 Domingo=7

        Calendar d=Calendar.getInstance();
        d.setTime(fecha);
        int aux=d.get(Calendar.DAY_OF_WEEK)-1;
        if(aux==0){aux=7;}
        return aux;
    }

    public static java.util.Date darFechaActual(){
        //Desde el postgres sería: SELECT current_timestamp;
        Date a =new Date();
        return soloFecha(a);
    }

    public static Date sumarDias(Date fecha, int dias){
        Date aux=fecha;
        aux.setTime(aux.getTime()+(long)dias*1000*60*60*24);
        aux=soloFecha(aux);
        return aux;
    }
    public static Date sumarMeses(Date fecha, int meses){
        Calendar c=Calendar.getInstance();
        c.setTime(fecha);
        c.add(Calendar.MONTH, meses);
        return c.getTime();
    }
    public static Date sumarSegundos(Date fecha, int segundos){
        Date aux=fecha;
        aux.setTime(aux.getTime()+(long)segundos*1000);
        return aux;
    }
    public static Date primerDiaDeMes(Date fecha){
        Date aux=new Date(fecha.getTime());
        Calendar d= Calendar.getInstance();
        d.setTime(aux);
        d.set(Calendar.DAY_OF_MONTH, 1);
        aux=d.getTime();
        return aux;
    }
    public static Date ultimoDiaDeMes(Date fecha){
        Date aux=new Date(fecha.getTime());
        Calendar d= Calendar.getInstance();
        d.setTime(aux);

        d.set(Calendar.DAY_OF_MONTH, 1);
        if(d.get(Calendar.MONTH)==11){//si es diciembre, enero es 0
            d.add(Calendar.YEAR, 1);
            d.set(Calendar.MONTH, 0);
        }else{
            d.add(Calendar.MONTH, 1);
        }
        d.setTime(new Date(d.getTime().getTime()-1000*60*60*24));//restar un día
        return d.getTime();
        /*aux.setDate(1);
        if(aux.getMonth()==11)//si es diciembre, enero es 0
            {aux.setYear(aux.getYear()+1);
            aux.setMonth(0);}
        else
            {aux.setMonth(aux.getMonth()+1);}
        //hasta aqui se tiene el 1º dia del mes siguiente, restarle un dia y tener el ultimo dia del mes
        aux.setTime(aux.getTime()-1000*60*60*24);
        aux=soloFecha(aux);
        return aux;*/
    }
    public static Date soloFecha(Date fecha) {
        Date aux=new Date(fecha.getTime());//hago esto porque hay problemas con un formato de fecha en el q no existen los minutos horas y segundos
        Calendar d= Calendar.getInstance();
        d.setTime(aux);
        d.set(Calendar.MINUTE, 0);
        d.set(Calendar.HOUR_OF_DAY, 0);
        d.set(Calendar.SECOND, 0);
        d.set(Calendar.MILLISECOND, 0);
        aux=d.getTime();
        //esto se hace para setear los milisegundos
        return new Date(aux.getTime()-aux.getTime()%1000);
    }
    public static int darAno(Date fecha) {
        Calendar d= Calendar.getInstance();
        d.setTime(fecha);
        return d.get(Calendar.YEAR);
    }

    /**
     *
     * @param fecha
     * @return enero es 1, diciembre es 12
     */
    public static int darMes(Date fecha) {
        Calendar d= Calendar.getInstance();
        d.setTime(fecha);
        return d.get(Calendar.MONTH)+1;
    }
    public static int darDiaMes(Date fecha) {
        Calendar d= Calendar.getInstance();
        d.setTime(fecha);
        return d.get(Calendar.DAY_OF_MONTH);
    }

    public static Date unirFechaHora(Date fecha, Date hora){
        Calendar calFecha= Calendar.getInstance(), calHora=Calendar.getInstance();
        calFecha.setTime(fecha);
        calHora.setTime(hora);
        calFecha.set(Calendar.HOUR_OF_DAY, calHora.get(Calendar.HOUR_OF_DAY));
        calFecha.set(Calendar.MINUTE, calHora.get(Calendar.MINUTE));
        calFecha.set(Calendar.SECOND, calHora.get(Calendar.SHORT));
        calFecha.set(Calendar.MILLISECOND, 0);
        return calFecha.getTime();
    }
    public static boolean validarFecha(Object fecha, String formato) {
        if (fecha == null) {return false;}
        SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
        if (fecha.toString().trim().length()!=dateFormat.toPattern().length()) {return false;}
        dateFormat.setLenient(false);
        try {dateFormat.parse(fecha.toString().trim());
        }catch (ParseException pe) {return false;}
        return true;
    }
    public static String toCadena(Date fecha, String formato){
        /**Convierte una variable de tipo cade a una tipo Date.
         * @param fecha Variable de tipo cadena.
         * @return Date Generada con la información de la variable de texto original*/
        SimpleDateFormat formato_fecha = new SimpleDateFormat(formato);//"yyyy/MM/dd");
        return formato_fecha.format(fecha);
    }
    public static Date toFecha(String fecha, String formato){
        try{
            SimpleDateFormat formato_fecha = new SimpleDateFormat(formato);//"dd/MM/yyyy");
            return formato_fecha.parse(fecha);

        }catch(ParseException e){
            e.printStackTrace();
            //System.out.println("Error al dar formato a la fecha...");
        }
        return null;
    }
}
